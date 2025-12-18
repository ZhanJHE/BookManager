// 导入核心的 Vue 库
import Vue from 'vue'

// 引入 normalize.css，用于重置和统一不同浏览器的默认样式，以提供更好的一致性。
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

// 引入 ElementUI 组件库及其默认的 chalk 主题样式
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// 引入项目自定义的全局样式文件
import '@/styles/index.scss' // global css

// 引入应用的根组件
import App from './App'
// 引入 Vuex 状态管理
import store from './store'
// 引入 Vue Router 路由管理
import router from './router'

// 引入图标
import '@/icons' // icon
// 引入权限控制逻辑，在路由跳转前进行验证
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use Mock.js for mock api
 * you can execute: mockXHR()
 *
 * Currently Mock.js will be used in the production environment,
 * please remove it before going online ! ! !
 */
// 判断当前环境是否为生产环境，如果是，则启用 mock-server
// 这允许在没有后端 API 的情况下开发和测试前端
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// 全局注册 ElementUI 插件
// 这使得我们可以在项目的任何组件中直接使用 ElementUI 提供的组件
Vue.use(ElementUI)

// 设置 Vue 的配置项，关闭生产模式下的提示信息
Vue.config.productionTip = false

// 创建 Vue 的根实例
new Vue({
  // 将根实例挂载到 DOM 中 id 为 'app' 的元素上
  el: '#app',
  // 将 router 实例注入到所有子组件中，使得所有组件都可以通过 this.$router 和 this.$route 访问路由
  router,
  // 将 store 实例注入到所有子组件中，使得所有组件都可以通过 this.$store 访问状态
  store,
  // 使用 render 函数来渲染根组件 App
  // h 是 createElement 的别名，h(App) 表示创建一个 App 组件的 VNode
  render: h => h(App)
})