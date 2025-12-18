// 导入封装好的 axios 实例
import request from '@/utils/request'

/**
 * 获取表格列表数据
 * @param {object} params - 查询参数
 * @returns {Promise} - 返回一个 Promise 对象
 */
export function getList(params) {
  return request({
    // 请求 URL，这里是 mock 数据的地址
    url: '/vue-admin-template/table/list',
    // 请求方法
    method: 'get',
    // URL 查询参数
    params
  })
}