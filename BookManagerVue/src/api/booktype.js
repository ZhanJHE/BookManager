// 导入封装好的 axios 实例
import request from '@/utils/request'

/**
 * 获取图书类型总数量
 * @returns {Promise}
 */
export function getCount() {
    return request({
        url: '/bookType/getCount',
        method: 'get'
    })
}

/**
 * 查询所有图书类型
 * @returns {Promise}
 */
export function queryBookTypes() {
    return request({
        url: '/bookType/queryBookTypes',
        method: 'get'
    })
}

/**
 * 分页查询图书类型
 * @param {object} params - 查询参数，例如 { page: 1, limit: 10, name: '科幻' }
 * @returns {Promise}
 */
export function queryBookTypesByPage(params) {
    return request({
        url: '/bookType/queryBookTypesByPage',
        method: 'get',
        params
    })
}

/**
 * 添加一个新的图书类型
 * @param {object} data - 图书类型信息数据
 * @returns {Promise}
 */
export function addBookType(data) {
    return request({
        url: '/bookType/addBookType',
        method: 'post',
        data
    })
}

/**
 * 根据 ID 删除一个图书类型
 * @param {object} data - 包含类型 ID 的对象，例如 { id: 1 }
 * @returns {Promise}
 */
export function deleteBookType(data) {
    return request({
        url: '/bookType/deleteBookType',
        method: 'delete',
        data
    })
}

/**
 * 批量删除图书类型
 * @param {object} data - 包含类型 ID 列表的对象，例如 { ids: [1, 2, 3] }
 * @returns {Promise}
 */
export function deleteBookTypes(data) {
    return request({
        url: '/bookType/deleteBookTypes',
        method: 'delete',
        data
    })
}

/**
 * 更新图书类型信息
 * @param {object} data - 需要更新的图书类型信息数据
 * @returns {Promise}
 */
export function updateBookType(data) {
    return request({
        url: '/bookType/updateBookType',
        method: 'put',
        data
    })
}