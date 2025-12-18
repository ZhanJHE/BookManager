import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// 创建一个 axios 实例
// 我们之后的所有请求都将使用这个实例
const service = axios.create({
  // baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  baseURL: 'http://localhost:9111/BookManager/', // api 的 base_url
  // withCredentials: true, // 跨域请求时发送 cookies
  timeout: 5000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么

    // 判断 store 中是否存在 token
    if (store.getters.token) {
      // 让每个请求携带 token
      // ['X-Token'] 是一个自定义的 headers key
      // 请根据实际情况修改
      config.headers['X-Token'] = getToken()
    }
    return config
  },
  error => {
    // 处理请求错误
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  /**
   * 如果你想获取 http 信息，如 headers 或 status
   * 请 return  response => response
  */

  /**
   * 通过自定义代码确定请求状态
   * 这里只是一个例子
   * 你也可以通过 HTTP 状态码来判断状态
   */
  response => {
    // 直接返回后端响应的 data 部分
    const res = response.data
    return res
  },
  error => {
    // 处理响应错误
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service