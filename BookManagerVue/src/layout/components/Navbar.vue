<template>
    <div class="navbar">
        <!-- 汉堡包菜单，用于切换侧边栏的展开/收起 -->
        <hamburger
            :is-active="sidebar.opened"
            class="hamburger-container"
            @toggleClick="toggleSideBar"
        />

        <!-- 面包屑导航 -->
        <breadcrumb class="breadcrumb-container" />

        <!-- 右侧菜单 -->
        <div class="right-menu">
            <!-- 用户头像和名称的下拉菜单 -->
            <el-dropdown class="avatar-container" trigger="click">
                <div class="avatar-wrapper">
                    <!-- 用户头像 -->
                    <img
                        :src="avatar"
                        class="user-avatar"
                        style="border: 1px solid lightgrey"
                    />
                    <!-- 用户名 -->
                    <span class="user-name"> {{ name }} </span>
                    <!-- 下拉箭头 -->
                    <i class="el-icon-caret-bottom" />
                </div>
                <el-dropdown-menu
                    slot="dropdown"
                    class="user-dropdown"
                    style="padding-bottom: 10px"
                >
                    <router-link to="/">
                        <el-dropdown-item> 首页 </el-dropdown-item>
                    </router-link>
                    <!-- 退出登录选项，divided 属性添加分割线 -->
                    <el-dropdown-item divided @click.native="logout">
                        <span style="display: block">退出登录</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
// 导入面包屑导航组件
import Breadcrumb from "@/components/Breadcrumb";
// 导入汉堡包菜单组件
import Hamburger from "@/components/Hamburger";

export default {
    components: {
        Breadcrumb,
        Hamburger,
    },
    computed: {
        // 使用 mapGetters 辅助函数将 store 中的 getters (sidebar, avatar, name) 映射到局部计算属性
        ...mapGetters(["sidebar", "avatar", "name"]),
    },
    methods: {
        // 切换侧边栏的展开/收起状态
        toggleSideBar() {
            this.$store.dispatch("app/toggleSideBar");
        },
        // 异步方法，处理用户退出登录
        async logout() {
            // 派发 user/logout action 来执行退出登录的逻辑
            await this.$store.dispatch("user/logout");
            // 退出后重定向到登录页面
            this.$router.push(`/login`);
        },
    },
};
</script>

<style lang="scss" scoped>
.navbar {
    height: 60px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

    .hamburger-container {
        line-height: 60px;
        height: 100%;
        float: left;
        cursor: pointer;
        transition: background 0.3s;
        -webkit-tap-highlight-color: transparent; // 移动端点击时无高亮

        &:hover {
            background: rgba(0, 0, 0, 0.025);
        }
    }

    .breadcrumb-container {
        float: left;
        font-size: 18px;
        margin-top: 8px;
    }

    .right-menu {
        float: right;
        height: 100%;
        line-height: 50px;

        &:focus {
            outline: none;
        }

        .right-menu-item {
            display: inline-block;
            padding: 0 8px;
            height: 100%;
            font-size: 18px;
            color: #5a5e66;
            vertical-align: text-bottom;

            &.hover-effect {
                cursor: pointer;
                transition: background 0.3s;

                &:hover {
                    background: rgba(0, 0, 0, 0.025);
                }
            }
        }

        .avatar-container {
            margin-right: 30px;

            .avatar-wrapper {
                margin-top: 5px;
                position: relative;

                .user-avatar {
                    cursor: pointer;
                    width: 50px;
                    height: 50px;
                    border-radius: 5px;
                }

                .user-name {
                    cursor: pointer;
                    font-size: 22px;
                }

                .el-icon-caret-bottom {
                    cursor: pointer;
                    position: absolute;
                    right: -20px;
                    top: 35px;
                    font-size: 12px;
                }
            }
        }
    }
}
</style>