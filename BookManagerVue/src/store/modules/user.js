// 导入用户相关的 API 函数
import { login, logout, getInfo } from '@/api/user'
// 导入操作 token 的工具函数
import { getToken, setToken, removeToken } from '@/utils/auth'
// 导入重置路由的函数
import { resetRouter } from '@/router'

// 定义一个函数，用于获取模块的默认（初始）状态
const getDefaultState = () => {
  return {
    // 认证令牌，优先从 Cookie 中获取
    token: getToken(),
    // 用户 ID
    id: 0,
    // 用户名
    name: '',
    // 用户头像 URL
    avatar: '',
    // 用户的角色列表
    roles: []
  }
}

// 初始化 state
const state = getDefaultState()

// 定义 mutations，用于同步修改 state
// 这是唯一可以合法修改 state 的地方
const mutations = {
  // 重置 state 为初始状态
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  // 设置 token
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  // 设置用户 ID
  SET_ID: (state, id) => {
    state.id = id
  },
  // 设置用户名
  SET_NAME: (state, name) => {
    state.name = name
  },
  // 设置用户头像
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  // 设置用户角色
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

// 定义 actions，用于处理异步操作和业务逻辑
// actions 会提交 (commit) mutations 来修改 state
const actions = {
  // 用户登录 action
  login({ commit }, userInfo) {
    const { username, password, isadmin } = userInfo
    return new Promise((resolve, reject) => {
      // 调用 api/user.js 中的 login 方法，发起登录请求
      login({ username: username.trim(), userpassword: password, isadmin: isadmin }).then(response => {
        const { status, message, data } = response

        // 如果后端返回的状态码不是 200，则登录失败
        if (status !== 200) {
          // 使用 reject 将错误信息传递出去，以便页面捕获
          reject(message)
        }

        // 登录成功，提交 mutation 来更新 state 中的 token
        commit('SET_TOKEN', data.token)
        // 同时，将 token 存入 Cookie，以便持久化和下次自动登录
        setToken(data.token)
        // 操作成功，调用 resolve
        resolve()
      }).catch(error => {
        // 网络请求等失败，捕获错误并 reject
        reject(error)
      })
    })
  },

  // 获取用户信息 action
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 调用 api/user.js 中的 getInfo 方法，携带 token 请求用户信息
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          reject('验证失败，请重新登录')
        }

        // --- 注意：这里的角色和头像是前端写死的逻辑，实际项目中通常由后端返回 ---
        data['avatar'] = '/pic/02.jpg' // 默认头像
        if (data.isadmin === 1) {
          data['roles'] = ['admin'] // 如果 isadmin 为 1，角色为 admin
          data['avatar'] = '/pic/05.jpg' // admin 头像
        } else {
          data['roles'] = ['reader'] // 否则角色为 reader
        }
        // ---------------------------------------------------------------------

        const { userid, roles, username, avatar } = data

        // 验证角色信息是否存在且不为空
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles 必须是非空数组!')
        }

        // 提交一系列 mutations 来更新 state 中的用户信息
        commit('SET_ID', userid)
        commit('SET_ROLES', roles)
        commit('SET_NAME', username)
        commit('SET_AVATAR', avatar)
        // 将完整的用户信息 resolve 出去，供权限系统使用
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 用户登出 action
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 调用 api/user.js 中的 logout 方法，通知后端用户登出
      logout(state.token).then(() => {
        // 1. 从 Cookie 中移除 token
        removeToken()
        // 2. 重置路由表，移除所有动态添加的路由
        resetRouter()
        // 3. 提交 mutation，重置 user 模块的 state
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 移除 token action (通常在 token 失效时被动调用)
  resetToken({ commit }) {
    return new Promise(resolve => {
      // 1. 从 Cookie 中移除 token
      removeToken()
      // 2. 提交 mutation，重置 user 模块的 state
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  // 开启命名空间，使得此模块的 actions, mutations, getters 成为带命名空间的，
  // 调用时需要如 `dispatch('user/login', ...)`
  namespaced: true,
  state,
  mutations,
  actions
}