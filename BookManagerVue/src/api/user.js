import request from '@/utils/request'

/**
 * 用户登录接口
 * @param {object} data 包含用户名、密码和是否为管理员的标识
 * @returns {Promise} 返回一个 Promise 对象，其中包含登录结果
 */
export function login(data) {
  return request({
    url: '/user/login', // 请求的后端 API 地址
    method: 'post',      // HTTP 请求方法
    data               // 发送到后端的数据 (Request Body)
  })
}

/**
 * 获取用户信息接口
 * @param {string} token 用户的认证令牌
 * @returns {Promise} 返回一个 Promise 对象，其中包含用户信息
 */
export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token } // URL 查询参数
  })
}

/**
 * 用户登出接口
 * @param {string} token 用户的认证令牌
 * @returns {Promise} 返回一个 Promise 对象
 */
export function logout(token) {
  return request({
    url: '/user/logout',
    method: 'post',
    params: { token }
  })
}

/**
 * 用户注册接口
 * @param {object} params 包含注册所需的用户信息
 * @returns {Promise} 返回一个 Promise 对象
 */
export function register(params) {
  return request({
    url: '/user/register',
    method: 'post',
    params
  })
}

/**
 * 修改密码接口
 * @param {object} params 包含用户 ID 和新旧密码
 * @returns {Promise} 返回一个 Promise 对象
 */
export function alterPassword(params) {
  return request({
    url: '/user/alterPassword',
    method: 'post',
    params
  })
}

/**
 * 获取用户总数接口
 * @returns {Promise} 返回一个 Promise 对象，其中包含用户总数
 */
export function getCount() {
  return request({
    url: '/user/getCount',
    method: 'get'
  })
}

/**
 * 查询所有用户信息接口
 * @returns {Promise} 返回一个 Promise 对象，其中包含所有用户信息的列表
 */
export function queryUsers() {
  return request({
    url: '/user/queryUsers',
    method: 'get'
  })
}

/**
 * 分页查询用户信息接口
 * @param {object} params 包含分页参数（如页码、每页数量）
 * @returns {Promise} 返回一个 Promise 对象，其中包含分页后的用户信息
 */
export function queryUsersByPage(params) {
  return request({
    url: '/user/queryUsersByPage',
    method: 'get',
    params
  })
}

/**
 * 添加新用户接口
 * @param {object} data 新用户的信息
 * @returns {Promise} 返回一个 Promise 对象
 */
export function addUser(data) {
  return request({
    url: '/user/addUser',
    method: 'post',
    data
  })
}

/**
 * 删除单个用户接口
 * @param {object} data 包含要删除的用户 ID
 * @returns {Promise} 返回一个 Promise 对象
 */
export function deleteUser(data) {
  return request({
    url: '/user/deleteUser',
    method: 'delete',
    data
  })
}

/**
 * 批量删除用户接口
 * @param {object} data 包含要删除的多个用户 ID 的列表
 * @returns {Promise} 返回一个 Promise 对象
 */
export function deleteUsers(data) {
  return request({
    url: '/user/deleteUsers',
    method: 'delete',
    data
  })
}

/**
 * 更新用户信息接口
 * @param {object} data 包含要更新的用户信息
 * @returns {Promise} 返回一个 Promise 对象
 */
export function updateUser(data) {
  return request({
    url: '/user/updateUser',
    method: 'put',
    data
  })
}