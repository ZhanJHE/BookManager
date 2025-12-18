// 导入 js-cookie 库，用于在客户端操作 Cookie
import Cookies from 'js-cookie'

// 定义 app 模块的 state
const state = {
  sidebar: {
    // `opened` 属性表示侧边栏是否展开
    // 从 Cookie 中读取 'sidebarStatus' 的值，如果存在则转换为布尔值，否则默认为 true (展开)
    opened: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    // `withoutAnimation` 属性用于控制侧边栏展开/折叠时是否禁用动画
    withoutAnimation: false
  },
  // 设备类型，默认为 'desktop' (桌面端)
  device: 'desktop'
}

// 定义 app 模块的 mutations，用于同步修改 state
const mutations = {
  // 切换侧边栏的展开/折叠状态
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    // 将侧边栏状态持久化到 Cookie
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1)
    } else {
      Cookies.set('sidebarStatus', 0)
    }
  },
  // 关闭侧边栏
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  // 切换设备类型
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  }
}

// 定义 app 模块的 actions，用于处理异步操作或封装多个 mutation
const actions = {
  // 切换侧边栏状态
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  // 关闭侧边栏
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  // 切换设备类型
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  }
}

// 导出 app 模块
export default {
  // 启用命名空间，使得模块具有更高的封装度和复用性
  namespaced: true,
  state,
  mutations,
  actions
}