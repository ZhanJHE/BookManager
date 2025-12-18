// 导入 mockjs 库，用于生成随机数据和拦截 AJAX 请求
const Mock = require('mockjs')
// 导入一个工具函数，用于将 URL 参数字符串转换为对象
const { param2Obj } = require('./utils')

// 导入用户相关的模拟 API
const user = require('./user')
// 导入表格相关的模拟 API
const table = require('./table')

// 将所有模拟 API 模块合并到一个数组中
const mocks = [
  ...user,
  ...table
]

// 这个函数用于在前端模拟 XHR (XMLHttpRequest) 请求。
// 注意：请谨慎使用，因为它会重定义 XMLHttpRequest，
// 这可能导致许多第三方库（例如需要监听 progress 事件的库）失效。
function mockXHR() {
  // 对 Mock.js 的 XHR 功能进行补丁，以解决一些已知问题
  // 参考: https://github.com/nuysoft/Mock/issues/300
  Mock.XHR.prototype.proxy_send = Mock.XHR.prototype.send
  Mock.XHR.prototype.send = function() {
    if (this.custom.xhr) {
      this.custom.xhr.withCredentials = this.withCredentials || false

      if (this.responseType) {
        this.custom.xhr.responseType = this.responseType
      }
    }
    this.proxy_send(...arguments)
  }

  // 包装器函数，将 Mock.js 的请求参数转换为类似 Express.js 的 req 对象
  function XHR2ExpressReqWrap(respond) {
    return function(options) {
      let result = null
      if (respond instanceof Function) {
        const { body, type, url } = options
        // 构造一个类似 Express.js 的请求对象
        // 参考: https://expressjs.com/en/4x/api.html#req
        result = respond({
          method: type, // 请求方法
          body: JSON.parse(body), // 请求体
          query: param2Obj(url) // URL 查询参数
        })
      } else {
        result = respond
      }
      // 使用 Mock.js 的 mock 方法处理最终结果，可以生成随机数据
      return Mock.mock(result)
    }
  }

  // 遍历所有模拟 API 配置，并为每个配置创建一个 mock 拦截
  for (const i of mocks) {
    Mock.mock(
      new RegExp(i.url), // 将 URL 字符串转换为正则表达式，用于匹配请求
      i.type || 'get', // 请求类型，默认为 'get'
      XHR2ExpressReqWrap(i.response) // 包装后的响应函数
    )
  }
}

// 导出 mocks 数组和 mockXHR 函数，供其他地方使用
module.exports = {
  mocks,
  mockXHR
}