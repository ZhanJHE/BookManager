<template>
    <!-- 根容器，用于整个注册页面 -->
    <div class="login-container">
        <!-- Element UI 的表单组件，用于收集用户注册信息 -->
        <el-form
            ref="loginForm"
            :model="loginForm"
            :rules="loginRules"
            class="login-form"
            auto-complete="on"
            label-position="left"
        >
            <!-- 标题容器 -->
            <div class="title-container">
                <h3 class="title">注册新账号</h3>
            </div>

            <!-- 用户名输入框 -->
            <el-form-item prop="username">
                <span class="svg-container">
                    <i class="el-icon-a-052"></i>
                </span>
                <el-input
                    ref="username"
                    v-model="loginForm.username"
                    placeholder="请输入用户名"
                    name="username"
                    type="text"
                    tabindex="1"
                    auto-complete="on"
                />
            </el-form-item>

            <!-- 密码输入框 -->
            <el-form-item prop="password">
                <span class="svg-container">
                    <i class="el-icon-a-051"></i>
                </span>
                <el-input
                    ref="password"
                    v-model="loginForm.password"
                    type="password"
                    placeholder="请输入密码"
                    name="password"
                    tabindex="2"
                    auto-complete="on"
                />
            </el-form-item>

            <!-- 确认密码输入框 -->
            <el-form-item prop="repeat">
                <span class="svg-container">
                    <i class="el-icon-a-051"></i>
                </span>
                <el-input
                    ref="repeat"
                    v-model="loginForm.repeat"
                    type="password"
                    placeholder="请确认密码"
                    name="repeat"
                    tabindex="3"
                    auto-complete="on"
                    @keyup.enter.native="handleRight"
                />
            </el-form-item>

            <!-- 按钮容器，包含确认和返回登录按钮 -->
            <div style="height: 40px; margin-bottom: 30px">
                <el-button
                    :loading="loading"
                    type="primary"
                    style="width: 48%; float: left"
                    @click.native.prevent="handleRight"
                    >确认</el-button
                >
                <el-button
                    :loading="loading"
                    type="success"
                    style="width: 48%; float: right"
                    @click.native.prevent="handleBack"
                    >返回登录</el-button
                >
            </div>
        </el-form>
    </div>
</template>

<script>
// 导入注册 API 请求函数
import { register } from "@/api/user";

export default {
    name: "Login",
    data() {
        // 自定义验证规则，用于检查两次输入的密码是否一致
        const validateRepeat = (rule, value, callback) => {
            if (value !== this.loginForm.password) {
                callback(new Error("两次输入的密码不一致!"));
            } else {
                callback();
            }
        };
        return {
            // 表单数据模型
            loginForm: {
                username: "",
                password: "",
                repeat: "",
            },
            // 表单验证规则
            loginRules: {
                username: [
                    {
                        required: true,
                        message: "请输入用户名",
                        trigger: "blur",
                    },
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" },
                ],
                repeat: [
                    {
                        required: true,
                        message: "请再次输入密码",
                        trigger: "blur",
                    },
                    // 使用自定义验证器
                    { trigger: "blur", validator: validateRepeat },
                ],
            },
            // 控制按钮加载状态
            loading: false,
        };
    },
    methods: {
        // 处理注册逻辑
        handleRight() {
            // 触发表单验证
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    // 调用注册 API
                    register({
                        username: this.loginForm.username,
                        password: this.loginForm.password,
                    }).then((res) => {
                        if (res === 0) {
                            this.$message.error("注册失败，可能账号重复了");
                        } else {
                            this.$message.success("注册成功");
                        }
                    });
                } else {
                    console.log("不允许提交!");
                    return false;
                }
            });
        },
        // 处理返回登录页面的逻辑
        handleBack() {
            this.$router.push("/login");
        },
    },
};
</script>

<style lang="scss">
// 全局样式，用于覆盖 Element UI 的默认样式
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

// 修复在某些浏览器中 input 背景和光标颜色的问题
@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
        color: $cursor;
    }
}

// 重置 Element UI 的部分样式
.login-container {
    .el-input {
        display: inline-block;
        height: 47px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: $light_gray;
            height: 47px;
            caret-color: $cursor;

            // 修复浏览器自动填充时 input 背景色的问题
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
    // 隐藏 Element UI select 组件的向上箭头
    .el-icon-arrow-up:before {
        content: "";
    }
}
</style>

<style lang="scss" scoped>
// 组件作用域内的样式
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;
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
        user-select: none;
    }
}
</style>