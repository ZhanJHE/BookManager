// 导入异步路由和常量路由
import { asyncRoutes, constantRoutes } from '@/router'

/**
 * 检查用户是否拥有访问特定路由的权限
 * @param {Array} roles - 用户的角色列表，例如 ['admin', 'editor']
 * @param {object} route - 单个路由对象
 * @returns {boolean} - 如果用户有权限，返回 true，否则返回 false
 */
function hasPermission(roles, route) {
  // 如果路由配置了 meta.roles，则需要进行权限验证
  if (route.meta && route.meta.roles) {
    // 检查用户的角色中是否至少有一个存在于路由的 meta.roles 数组中
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    // 如果路由没有配置 meta.roles，则默认所有用户都有权限访问
    return true
  }
}

/**
 * 递归地过滤异步路由表，生成用户可访问的路由
 * @param {Array} routes - 从 '@/router' 导入的 asyncRoutes
 * @param {Array} roles - 用户的角色列表
 * @returns {Array} - 返回过滤后的、该用户可访问的路由列表
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    // 创建路由的副本，以避免修改原始路由配置
    const tmp = { ...route }
    // 检查用户是否有权访问此路由
    if (hasPermission(roles, tmp)) {
      // 如果路由有子路由，则递归地过滤子路由
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      // 将有权访问的路由（及其过滤后的子路由）添加到结果数组中
      res.push(tmp)
    }
  })

  return res
}

// 定义 permission 模块的 state
const state = {
  // 存储当前用户可访问的完整路由表（常量路由 + 动态路由）
  routes: [],
  // 存储通过权限验证后，动态添加的路由部分
  addRoutes: []
}

// 定义 permission 模块的 mutations
const mutations = {
  // 设置动态路由
  SET_ROUTES: (state, routes) => {
    // 将动态生成的路由存入 addRoutes
    state.addRoutes = routes
    // 将常量路由和动态路由合并，形成完整的、可供侧边栏渲染的路由表
    state.routes = constantRoutes.concat(routes)
  }
}

// 定义 permission 模块的 actions
const actions = {
  // 根据用户角色生成动态路由
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      let accessedRoutes
      // 调用过滤函数，根据角色筛选出可访问的动态路由
      accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      // 提交 mutation，将生成的路由存入 state
      commit('SET_ROUTES', accessedRoutes)
      // 将生成的路由 resolve 出去，供 permission.js 使用
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}