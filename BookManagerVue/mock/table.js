// 导入 mockjs 库
const Mock = require('mockjs')

// 使用 Mock.js 生成模拟数据
const data = Mock.mock({
  // 生成一个包含 30 个元素的数组
  'items|30': [{
    id: '@id', // 生成一个唯一的 ID
    title: '@sentence(10, 20)', // 生成一个包含 10 到 20 个单词的句子
    'status|1': ['published', 'draft', 'deleted'], // 从数组中随机选择一个状态
    author: 'name', // 生成一个名字
    display_time: '@datetime', // 生成一个日期时间字符串
    pageviews: '@integer(300, 5000)' // 生成一个 300 到 5000 之间的整数
  }]
})

// 导出模拟 API 配置
module.exports = [
  {
    url: '/vue-admin-template/table/list', // 请求 URL
    type: 'get', // 请求类型
    response: config => {
      const items = data.items // 获取生成的模拟数据项
      return {
        code: 20000, // 成功状态码
        data: {
          total: items.length, // 数据总数
          items: items // 当前页的数据
        }
      }
    }
  }
]