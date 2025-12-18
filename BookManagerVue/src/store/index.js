// 导入 Vue 和 Vuex
import Vue from 'vue'
import Vuex from 'vuex'
// 导入全局的 getters
import getters from './getters'

// 在 Vue 中全局安装 Vuex 插件
Vue.use(Vuex)

// 使用 webpack 的 require.context API 动态导入所有模块
// require.context(directory, useSubdirectories, regExp)
// directory: 要查找的文件夹
// useSubdirectories: 是否查找子目录
// regExp: 匹配文件的正则表达式
const modulesFiles = require.context('./modules', true, /\.js$/)

// 自动导入所有 Vuex 模块并以模块名为键组织它们
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // 将模块路径（例如 './app.js'）转换为模块名（'app'）
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  // 获取模块导出的内容
  const value = modulesFiles(modulePath)
  // 将模块（必须是 default export）赋值给对应的模块名
  modules[moduleName] = value.default
  return modules
}, {})

// 创建 Vuex.Store 实例
const store = new Vuex.Store({
  // `modules` 属性将所有子模块集成到主 store 中
  // 模块化的方式使得 store 更易于维护和扩展
  modules,
  // `getters` 属性用于从 state 中派生出一些状态，可以认为是 store 的计算属性
  // 这里将全局的 getters 注入到根 store 中，方便组件访问
  getters
})

// 导出 store 实例，供 main.js 注入到 Vue 根实例中
export default store