/*
 * @Description: 
 * @Author: Rabbiter
 * @Date: 2023-02-21 18:13:21
 */
// 导入项目默认设置
import defaultSettings from '@/settings'

// 从默认设置中解构出 showSettings, fixedHeader, 和 sidebarLogo
const { showSettings, fixedHeader, sidebarLogo } = defaultSettings

// 定义 settings 模块的 state
const state = {
  // API 的基础 URL
  baseApi: "http://localhost:9111/BookManager",
  // 是否显示右侧的设置面板
  showSettings: showSettings,
  // 是否固定顶部的 Header
  fixedHeader: fixedHeader,
  // 是否显示侧边栏的 Logo
  sidebarLogo: sidebarLogo
}

// 定义 settings 模块的 mutations
const mutations = {
  // 动态修改 state 中的设置项
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    // 检查 state 中是否存在指定的 key
    if (state.hasOwnProperty(key)) {
      // 如果存在，则更新其值
      state[key] = value
    }
  }
}

// 定义 settings 模块的 actions
const actions = {
  // 触发修改设置的 action
  changeSetting({ commit }, data) {
    // 调用 CHANGE_SETTING mutation，并传递包含 key 和 value 的数据对象
    commit('CHANGE_SETTING', data)
  }
}

// 导出 settings 模块
export default {
  // 启用命名空间
  namespaced: true,
  // 导出 state
  state,
  // 导出 mutations
  mutations,
  // 导出 actions
  actions
}