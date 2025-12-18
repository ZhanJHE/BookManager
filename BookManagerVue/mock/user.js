
// 定义不同用户角色的 token
const tokens = {
  admin: {
    token: 'admin-token' // 管理员 token
  },
  editor: {
    token: 'editor-token' // 普通编辑者 token
  }
}

// 定义不同 token 对应的用户信息
const users = {
  'admin-token': {
    roles: ['admin'], // 角色
    introduction: 'I am a super administrator', // 简介
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', // 头像
    name: 'Super Admin' // 用户名
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}

// 导出模拟 API 配置
module.exports = [
  // 用户登录接口
  {
    url: '/vue-admin-template/user/login', // 请求 URL
    type: 'post', // 请求类型
    response: config => {
      const { username } = config.body // 从请求体中获取用户名
      const token = tokens[username] // 根据用户名查找对应的 token

      // 如果 token 不存在，模拟一个错误响应
      if (!token) {
        return {
          code: 60204,
          message: '账号或密码错误。'
        }
      }

      // 如果 token 存在，返回成功的响应
      return {
        code: 20000,
        data: token
      }
    }
  },

  // 获取用户信息接口
  {
    url: '/vue-admin-template/user/info\.*', // 请求 URL，使用正则表达式匹配
    type: 'get', // 请求类型
    response: config => {
      const { token } = config.query // 从 URL 查询参数中获取 token
      const info = users[token] // 根据 token 查找对应的用户信息

      // 如果用户信息不存在，模拟一个错误响应
      if (!info) {
        return {
          code: 50008,
          message: '登录失败，无法获取用户详情。'
        }
      }

      // 如果用户信息存在，返回成功的响应
      return {
        code: 20000,
        data: info
      }
    }
  },

  // 用户登出接口
  {
    url: '/vue-admin-template/user/logout',
    type: 'post',
    response: _ => {
      // 直接返回成功的响应
      return {
        code: 20000,
        data: 'success'
      }
    }
  }
]