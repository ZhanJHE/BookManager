// 导入 Vue 和 VueRouter
import Vue from 'vue'
import Router from 'vue-router'

// 在 Vue 中全局安装 VueRouter 插件
Vue.use(Router)

/* 导入项目的基础布局组件 */
import Layout from '@/layout'

// --- 路由配置说明 ---
// 详细文档请参考: https://panjiachen.github.io/vue-element-admin-site/zh/guide/essentials/router-and-nav.html
//
// hidden: true                   // 如果设置为 true，该路由不会在侧边栏显示 (默认为 false)
// alwaysShow: true               // 如果设置为 true，将始终显示根菜单
//                                // 如果不设置 alwaysShow，当路由有超过一个子路由时，它会变成嵌套模式，否则不显示根菜单
// redirect: 'noRedirect'         // 如果设置了 noRedirect，面包屑导航将不会重定向
// name: 'router-name'            // 路由名称，必须设置，且唯一，用于 <keep-alive> 缓存
// meta: {
//   roles: ['admin', 'editor']   // 页面角色权限控制，可以设置多个角色
//   title: 'title'               // 在侧边栏和面包屑中显示的名称 (推荐设置)
//   icon: 'svg-name'/'el-icon-x' // 在侧边栏中显示的图标
//   breadcrumb: false            // 如果设置为 false，该项将隐藏在面包屑中 (默认为 true)
//   activeMenu: '/example/list'  // 如果设置了路径，侧边栏将高亮显示你设置的路径
//   noCache: true                // 如果设置为 true，页面将不会被 <keep-alive> 缓存 (默认为 false)
// }
// --------------------

/**
 * constantRoutes (常量路由)
 * 这些路由是基础页面，没有权限要求，所有角色都可以访问。
 */
export const constantRoutes = [
  {
    path: '/login',
    // 路由懒加载：只有当访问此路由时，才会加载对应的组件
    component: () => import('@/views/login/index'),
    // 不在侧边栏显示
    hidden: true
  },

  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    // 根路径
    path: '/',
    // 使用通用的布局组件
    component: Layout,
    // 访问根路径时，自动重定向到 /dashboard
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      // meta 对象用于存储路由的元信息，如标题、图标等
      meta: { title: '首页', icon: 'el-icon-a-011' }
    }]
  }
]

/**
 * asyncRoutes (异步路由)
 * 这些路由需要根据用户的角色动态加载，用于权限控制。
 */
export const asyncRoutes = [
  {
    path: '/bookmanage',
    name: 'Bookmanage',
    component: Layout,
    redirect: '/bookmanage/bookinfo',
    // 始终显示根菜单
    alwaysShow: true,
    meta: {
      title: '图书管理',
      icon: 'el-icon-a-041'
    },
    children: [
      {
        path: 'bookinfo',
        name: 'Bookinfo',
        component: () => import('@/views/bookinfo/index'),
        meta: {
          title: '图书信息管理',
          icon: 'el-icon-a-061',
          // 'admin' 和 'reader' 角色的用户可以访问此页面
          roles: ['admin', 'reader'],
          // 页面不被缓存
          noCache: true
        }
      },
      {
        path: 'booktype',
        name: 'Booktype',
        component: () => import('@/views/booktype/index'),
        meta: {
          title: '图书类型管理',
          icon: 'el-icon-a-02',
          // 只有 'admin' 角色的用户可以访问
          roles: ['admin'],
          noCache: true
        }
      },
      {
        path: 'borrow',
        name: 'Borrow',
        component: () => import('@/views/borrow/index'),
        meta: {
          title: '借阅信息管理',
          icon: 'el-icon-a-021',
          roles: ['admin', 'reader'],
          noCache: true
        }
      }
    ]
  },

  {
    path: '/other',
    name: 'Other',
    component: Layout,
    redirect: '/other/user',
    alwaysShow: true,
    meta: {
      title: '其他管理',
      icon: 'el-icon-a-031'
    },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/index'),
        meta: {
          title: '用户管理',
          icon: 'el-icon-a-01',
          roles: ['admin'],
          noCache: true
        }
      },
      {
        path: 'password',
        name: 'Password',
        component: () => import('@/views/password/index'),
        meta: {
          title: '修改密码',
          icon: 'el-icon-a-051',
          roles: ['admin', 'reader'],
          noCache: true
        }
      }
    ]
  },

  // 404 页面必须放在路由配置的最后，作为捕获所有未匹配路由的备用项
  { path: '*', redirect: '/404', hidden: true }
]

// 创建一个新的路由实例的工厂函数
const createRouter = () => new Router({
  // mode: 'history', // 使用 history 模式，URL 中没有 #。需要后端服务器支持。默认为 'hash' 模式。
  // 定义路由切换时的滚动行为
  scrollBehavior: () => ({ y: 0 }),
  // 初始加载的路由为常量路由
  routes: constantRoutes
})

// 创建路由实例
const router = createRouter()

/**
 * 重置路由。
 * 这在用户退出登录时非常重要，因为它会移除所有通过 addRoutes 添加的动态路由，
 * 确保新登录的用户拥有正确的权限。
 * 详细解释: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
 */
export function resetRouter() {
  // 创建一个新的路由实例
  const newRouter = createRouter()
  // 将当前路由的匹配器重置为新路由的匹配器
  router.matcher = newRouter.matcher
}

// 默认导出路由实例，供 main.js 使用
export default router