// 导入封装好的 axios 实例
import request from '@/utils/request'

/**
 * 获取文章列表
 * @param {object} query - 查询参数，例如 { page: 1, limit: 10 }
 * @returns {Promise} - 返回一个 Promise 对象，resolve 的是 axios 的响应
 */
export function fetchList(query) {
  return request({
    // 请求 URL，这里是 mock 数据的地址
    url: '/vue-element-admin/article/list',
    // 请求方法
    method: 'get',
    // URL 查询参数
    params: query
  })
}

/**
 * 根据 ID 获取文章详情
 * @param {number} id - 文章 ID
 * @returns {Promise}
 */
export function fetchArticle(id) {
  return request({
    url: '/vue-element-admin/article/detail',
    method: 'get',
    params: { id }
  })
}

/**
 * 获取文章的 PV (Page View)
 * @param {number} pv - PV 数
 * @returns {Promise}
 */
export function fetchPv(pv) {
  return request({
    url: '/vue-element-admin/article/pv',
    method: 'get',
    params: { pv }
  })
}

/**
 * 创建新文章
 * @param {object} data - 文章数据
 * @returns {Promise}
 */
export function createArticle(data) {
  return request({
    url: '/vue-element-admin/article/create',
    method: 'post',
    // 请求体数据
    data
  })
}

/**
 * 更新文章
 * @param {object} data - 需要更新的文章数据
 * @returns {Promise}
 */
export function updateArticle(data) {
  return request({
    url: '/vue-element-admin/article/update',
    method: 'post',
    data
  })
}