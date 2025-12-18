// 导入路由实例
import router from './router'
// 导入 Vuex store 实例
import store from './store'
// 从 ElementUI 导入消息提示组件
import { Message } from 'element-ui'
// 导入页面顶部进度条插件
import NProgress from 'nprogress'
// 导入进度条样式
import 'nprogress/nprogress.css'
// 从 utils/auth 中导入获取 token 的函数
import { getToken } from '@/utils/auth'
// 从 utils/get-page-title 中导入获取页面标题的函数
import getPageTitle from '@/utils/get-page-title'

// 配置 NProgress，不显示加载时的小圈圈
NProgress.configure({ showSpinner: false })

// 定义一个不需要登录就可以访问的路由白名单
const whiteList = ['/login', '/register']

// 注册一个全局前置守卫，在每次路由跳转前执行
router.beforeEach(async(to, from, next) => {
  // 启动页面顶部的进度条
  NProgress.start()

  // 根据路由元信息设置页面标题
  document.title = getPageTitle(to.meta.title)

  // 从 cookie 中获取 token，判断用户是否已登录
  const hasToken = getToken()

  if (hasToken) {
    // 如果存在 token，说明用户已登录
    if (to.path === '/login') {
      // 如果已登录的用户试图访问登录页，则重定向到首页
      next({ path: '/' })
      NProgress.done()
    } else {
      // 如果访问的不是登录页
      // 通过检查 store 中是否有 roles 信息，判断是否已获取过用户信息
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      if (hasRoles) {
        // 如果已有角色信息，说明动态路由已添加，直接放行
        next()
      } else {
        try {
          // 如果没有角色信息，则需要异步获取
          // 注意：roles 必须是一个数组，例如: ['admin'] or ['developer','editor']
          const { roles } = await store.dispatch('user/getInfo')

          // 根据获取到的角色，异步生成对应的可访问路由
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)

          // 动态地将可访问的路由添加到路由实例中
          router.addRoutes(accessRoutes)

          // 使用一个 hack 方法来确保 addRoutes 已经完成
          // 设置 replace: true，这样导航就不会在历史记录中留下记录
          next({ ...to, replace: true })
        } catch (error) {
          // 如果在获取用户信息或生成路由的过程中发生错误（例如 token 失效）
          // 1. 触发 action，重置 token
          await store.dispatch('user/resetToken')
          // 2. 显示错误提示
          Message.error(error || 'Has Error')
          // 3. 重定向到登录页，并附带上之前的目标路径，以便登录后能直接跳转回来
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    // 如果没有 token
    if (whiteList.indexOf(to.path) !== -1) {
      // 如果目标路径在白名单中，则直接放行
      next()
    } else {
      // 其他所有不在白名单且没有 token 的页面，都重定向到登录页
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

// 注册一个全局后置钩子，在每次路由跳转后执行
router.afterEach(() => {
  // 结束页面顶部的进度条
  NProgress.done()
})