// getters 用于从 store 的 state 中派生出一些状态，可以看作是 store 的计算属性。
// 它们将不同模块的 state 聚合到根级别，方便组件以统一的方式访问。
const getters = {
  // app 模块
  sidebar: state => state.app.sidebar,       // 获取侧边栏的折叠状态
  device: state => state.app.device,         // 获取当前设备类型 (desktop/mobile)

  // user 模块
  token: state => state.user.token,         // 获取用户认证令牌
  id: state => state.user.id,               // 获取用户 ID
  avatar: state => state.user.avatar,       // 获取用户头像
  name: state => state.user.name,           // 获取用户名
  roles: state => state.user.roles,         // 获取用户角色列表

  // permission 模块
  permission_routes: state => state.permission.routes // 获取用户有权访问的路由
}

// 默认导出 getters 对象
export default getters