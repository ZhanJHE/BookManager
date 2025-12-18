// 导入封装好的 axios 实例
import request from '@/utils/request'

/**
 * 获取借阅记录总数量
 * @returns {Promise}
 */
export function getCount() {
    return request({
        url: '/borrow/getCount',
        method: 'get'
    })
}

/**
 * 查询所有借阅信息
 * @returns {Promise}
 */
export function queryBorrows() {
    return request({
        url: '/borrow/queryBorrows',
        method: 'get'
    })
}

/**
 * 分页查询借阅信息
 * @param {object} params - 查询参数，例如 { page: 1, limit: 10, username: 'zhangsan' }
 * @returns {Promise}
 */
export function queryBorrowsByPage(params) {
    return request({
        url: '/borrow/queryBorrowsByPage',
        method: 'get',
        params
    })
}

/**
 * 添加一条借阅记录
 * @param {object} data - 借阅信息数据
 * @returns {Promise}
 */
export function addBorrow(data) {
    return request({
        url: '/borrow/addBorrow',
        method: 'post',
        data
    })
}

/**
 * 根据 ID 删除一条借阅记录
 * @param {object} data - 包含借阅 ID 的对象，例如 { id: 1 }
 * @returns {Promise}
 */
export function deleteBorrow(data) {
    return request({
        url: '/borrow/deleteBorrow',
        method: 'delete',
        data
    })
}

/**
 * 批量删除借阅记录
 * @param {object} data - 包含借阅 ID 列表的对象，例如 { ids: [1, 2, 3] }
 * @returns {Promise}
 */
export function deleteBorrows(data) {
    return request({
        url: '/borrow/deleteBorrows',
        method: 'delete',
        data
    })
}

/**
 * 更新借阅信息
 * @param {object} data - 需要更新的借阅信息数据
 * @returns {Promise}
 */
export function updateBorrow(data) {
    return request({
        url: '/borrow/updateBorrow',
        method: 'put',
        data
    })
}

/**
 * 用户借书
 * @param {number} userid - 用户 ID
 * @param {number} bookid - 图书 ID
 * @returns {Promise}
 */
export function borrowBook(userid, bookid) {
    return request({
        url: '/borrow/borrowBook',
        method: 'post',
        params: {
            userid,
            bookid
        }
    })
}

/**
 * 用户还书
 * @param {number} borrowid - 借阅记录 ID
 * @param {number} bookid - 图书 ID
 * @returns {Promise}
 */
export function returnBook(borrowid, bookid) {
    return request({
        url: '/borrow/returnBook',
        method: 'post',
        params: {
            borrowid,
            bookid
        }
    })
}