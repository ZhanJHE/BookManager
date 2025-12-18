<template>
    <!-- 侧边栏 Logo 容器，根据 collapse 属性动态添加 'collapse' 类 -->
    <div class="sidebar-logo-container" :class="{ collapse: collapse }">
        <!-- 使用 transition 组件实现 Logo 切换时的淡入淡出效果 -->
        <transition name="sidebarLogoFade">
            <!-- 折叠状态下的 Logo -->
            <router-link
                v-if="collapse"
                key="collapse"
                class="sidebar-logo-link"
                to="/"
            >
                <!-- 如果有 logo，则显示图片 -->
                <img v-if="logo" :src="logo" class="sidebar-logo" />
                <!-- 否则，显示标题文字 -->
                <h1 v-else class="sidebar-title">{{ title }}</h1>
            </router-link>
            <!-- 展开状态下的 Logo -->
            <router-link v-else key="expand" class="sidebar-logo-link" to="/">
                <img v-if="logo" :src="logo" class="sidebar-logo" />
                <h1 class="sidebar-title">{{ title }}</h1>
            </router-link>
        </transition>
    </div>
</template>

<script>
export default {
    name: "SidebarLogo",
    props: {
        // 控制侧边栏是否折叠，必需
        collapse: {
            type: Boolean,
            required: true,
        },
    },
    data() {
        return {
            // 应用标题
            title: "图书管理系统",
            // 应用 Logo 的 URL
            logo: "https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png",
        };
    },
};
</script>

<style lang="scss" scoped>
// 定义 Logo 淡入淡出动画
.sidebarLogoFade-enter-active {
    transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
    opacity: 0;
}

// Logo 容器样式
.sidebar-logo-container {
    position: relative;
    width: 100%;
    height: 50px;
    line-height: 50px;
    background: #2b2f3a;
    text-align: center;
    overflow: hidden;

    // Logo 链接样式
    & .sidebar-logo-link {
        height: 100%;
        width: 100%;

        // Logo 图片样式
        & .sidebar-logo {
            width: 32px;
            height: 32px;
            vertical-align: middle;
            margin-right: 12px;
        }

        // 标题样式
        & .sidebar-title {
            display: inline-block;
            margin: 0;
            color: #fff;
            font-weight: 600;
            line-height: 50px;
            font-size: 14px;
            font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
            vertical-align: middle;
        }
    }

    // 折叠状态下的样式调整
    &.collapse {
        .sidebar-logo {
            margin-right: 0px; // 折叠时移除右边距
        }
    }
}
</style>