// 导入封装好的 axios 实例
import request from '@/utils/request'

/**
 * 获取图书总数量
 * @returns {Promise} - 返回一个 Promise 对象
 */
export function getCount() {
    return request({
        url: '/bookInfo/getCount',
        method: 'get'
    })
}

/**
 * 查询所有图书信息
 * @returns {Promise}
 */
export function queryBookInfos() {
    return request({
        url: '/bookInfo/queryBookInfos',
        method: 'get'
    })
}

/**
 * 分页查询图书信息
 * @param {object} params - 查询参数，例如 { page: 1, limit: 10, name: '三体' }
 * @returns {Promise}
 */
export function queryBookInfosByPage(params) {
    return request({
        url: '/bookInfo/queryBookInfosByPage',
        method: 'get',
        params
    })
}

/**
 * 添加一本图书
 * @param {object} data - 图书信息数据
 * @returns {Promise}
 */
export function addBookInfo(data) {
    return request({
        url: '/bookInfo/addBookInfo',
        method: 'post',
        data
    })
}

/**
 * 根据 ID 删除一本图书
 * @param {object} data - 包含图书 ID 的对象，例如 { id: 1 }
 * @returns {Promise}
 */
export function deleteBookInfo(data) {
    return request({
        url: '/bookInfo/deleteBookInfo',
        method: 'delete',
        data
    })
}

/**
 * 批量删除图书
 * @param {object} data - 包含图书 ID 列表的对象，例如 { ids: [1, 2, 3] }
 * @returns {Promise}
 */
export function deleteBookInfos(data) {
    return request({
        url: '/bookInfo/deleteBookInfos',
        method: 'delete',
        data
    })
}

/**
 * 更新图书信息
 * @param {object} data - 需要更新的图书信息数据
 * @returns {Promise}
 */
export function updateBookInfo(data) {
    return request({
        url: '/bookInfo/updateBookInfo',
        method: 'put',
        data
    })
}