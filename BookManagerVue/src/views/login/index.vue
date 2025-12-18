<template>
  <!-- 登录页面的根容器 -->
  <div class="login-container">
    <!-- 
      登录表单，使用了 ElementUI 的 el-form 组件
      :model="loginForm" - 绑定表单数据对象
      :rules="loginRules" - 绑定表单验证规则
      ref="loginForm" - 表单的引用，用于后续操作（如校验）
      auto-complete="on" - 开启自动完成
      label-position="left" - 标签位置在左侧
    -->
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <!-- 标题容器 -->
      <div class="title-container">
        <h3 class="title">登录图书管理系统</h3>
      </div>

      <!-- 用户名输入项 -->
      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-a-052"></i> <!-- 图标 -->
        </span>
        <el-input
          class="yuan" 
          ref="username" 
          v-model="loginForm.username" 
          placeholder="请输入用户名" 
          name="username" 
          type="text" 
          tabindex="1" 
          auto-complete="on"
        />
      </el-form-item>

      <!-- 密码输入项 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-a-051"></i> <!-- 图标 -->
        </span>
        <el-input
          class="yuan"
          :key="passwordType" 
          ref="password" 
          v-model="loginForm.password" 
          :type="passwordType" 
          placeholder="请输入密码" 
          name="password" 
          tabindex="2" 
          auto-complete="on" 
          @keyup.enter.native="handleLogin" 
        />
        <!-- 密码可见性切换图标 -->
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <!-- 权限选择 -->
      <el-form-item prop="authority">
        <span class="svg-container">
          <i class="el-icon-a-062"></i> <!-- 图标 -->
        </span>
        <el-select v-model="loginForm.isadmin" placeholder="请选择" style="width: 418px">
          <el-option :key="0" label="读者" :value="0"></el-option>
          <el-option :key="1" label="管理员" :value="1"></el-option>
        </el-select>
      </el-form-item>
      
      <!-- 登录与注册按钮 -->
      <div style="height: 40px; margin-bottom: 30px;">
        <el-button :loading="loading" type="primary" style="width: 48%; float: left;" @click.native.prevent="handleLogin">登录</el-button>
        <el-button :loading="loading" type="success" style="width: 48%; float: right;" @click.native.prevent="handleRegister">注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    // 定义用户名校验规则（这里是空实现，可以直接在 loginRules 中定义）
    const validateUsername = (rule, value, callback) => {
      callback()
    }
    // 定义密码校验规则（这里是空实现）
    const validatePassword = (rule, value, callback) => {
      callback()
    }
    return {
      // 表单数据模型
      loginForm: {
        username: '',
        password: '',
        isadmin: 0 // 默认选择“读者”
      },
      // 表单校验规则
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      // 登录按钮的加载状态
      loading: false,
      // 密码输入框的类型（password 或 text）
      passwordType: 'password',
      // 登录成功后的重定向路径
      redirect: undefined
    }
  },
  methods: {
    // 切换密码可见性
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    // 处理登录逻辑
    handleLogin() {
      // 首先校验整个表单
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 校验通过
          this.loading = true // 显示加载状态
          // 调用 Vuex store 中的 user/login action
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            // 登录成功后，跳转到首页
            this.$router.push({ path: '/' })
            this.loading = false // 关闭加载状态
          }).catch((message) => {
            // 登录失败，显示错误信息
            this.$message.error(message)
            this.loading = false // 关闭加载状态
          })
        } else {
          // 校验失败
          console.log('不允许提交!')
          return false
        }
      })
    },
    // 处理注册逻辑
    handleRegister() {
      // 跳转到注册页面
      this.$router.push({ path: '/register' })
    }
  }
}
</script>

<style lang="scss">
/* 
  全局 SCSS 样式（没有 scoped 属性）
  主要用于修复 ElementUI 在特定主题下 input 背景不协调和光标颜色的问题。
*/

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* 重置 element-ui 的一些默认样式 */
.login-container {
  .el-input.yuan {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent; // 背景透明
      border: 0px; // 无边框
      -webkit-appearance: none; // 移除 webkit 默认外观
      border-radius: 0px; // 无圆角
      padding: 12px 5px 12px 15px;
      color: $light_gray; // 文字颜色
      height: 47px;
      caret-color: $cursor; // 光标颜色

      // 修改浏览器自动填充的样式
      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important; // 用背景色覆盖默认的黄色
        -webkit-text-fill-color: $cursor !important; // 修改自动填充的文字颜色
      }
    }
  }

  .el-input {
    display: inline-block;
    height: 47px;
    width: 100%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
/* 
  局部 SCSS 样式（有 scoped 属性）
  这些样式只对当前组件生效。
*/

$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg; // 背景色
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
    font-size: 20px;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none; // 防止文本被选中
  }

}
</style>