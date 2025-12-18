<template>
    <!-- 根容器，根据设备和侧边栏状态动态添加 class -->
    <div :class="classObj" class="app-wrapper">
        <!-- 在移动设备且侧边栏打开时，显示一个遮罩层，用于点击关闭侧边栏 -->
        <div
            v-if="device === 'mobile' && sidebar.opened"
            class="drawer-bg"
            @click="handleClickOutside"
        />

        <!-- 侧边栏组件 -->
        <sidebar class="sidebar-container" />

        <!-- 主内容容器 -->
        <div class="main-container">
            <!-- 导航栏容器，根据 fixedHeader 状态决定是否固定在顶部 -->
            <div :class="{ 'fixed-header': fixedHeader }">
                <!-- 导航栏组件 -->
                <navbar />
            </div>
            <!-- 应用主内容区域，用于渲染路由匹配的组件 -->
            <app-main />
        </div>
    </div>
</template>

<script>
// 导入布局的子组件：导航栏、侧边栏、主内容区域
import { Navbar, Sidebar, AppMain } from "./components";
// 导入用于处理窗口大小变化的 Mixin
import ResizeMixin from "./mixin/ResizeHandler";

export default {
    name: "Layout",
    components: {
        Navbar,
        Sidebar,
        AppMain,
    },
    // 混入 ResizeMixin，用于监听窗口变化并更新设备状态 (desktop/mobile)
    mixins: [ResizeMixin],
    computed: {
        // 从 Vuex store 获取侧边栏状态
        sidebar() {
            return this.$store.state.app.sidebar;
        },
        // 从 Vuex store 获取设备类型 (desktop/mobile)
        device() {
            return this.$store.state.app.device;
        },
        // 从 Vuex store 获取导航栏是否固定的设置
        fixedHeader() {
            return this.$store.state.settings.fixedHeader;
        },
        // 动态计算根容器的 class，用于控制布局样式
        classObj() {
            return {
                hideSidebar: !this.sidebar.opened, // 侧边栏关闭时
                openSidebar: this.sidebar.opened, // 侧边栏打开时
                withoutAnimation: this.sidebar.withoutAnimation, // 是否禁用动画
                mobile: this.device === "mobile", // 是否为移动设备
            };
        },
    },
    methods: {
        // 处理在移动设备上点击遮罩层的事件
        handleClickOutside() {
            // 派发 action 关闭侧边栏，不带动画
            this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
        },
    },
};
</script>

<style lang="scss" scoped>
// 引入样式 mixin 和变量
@import "~@/styles/mixin.scss";
@import "~@/styles/variables.scss";

// 应用根容器样式
.app-wrapper {
    @include clearfix; // 清除浮动
    position: relative;
    height: 100%;
    width: 100%;
    // 在移动设备上，当侧边栏打开时，固定布局位置
    &.mobile.openSidebar {
        position: fixed;
        top: 0;
    }
}
// 移动设备侧边栏打开时的背景遮罩层
.drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
}

// 固定导航栏的样式
.fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    // 宽度根据侧边栏的宽度动态计算
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
}

// 侧边栏收起时，固定导航栏的宽度
.hideSidebar .fixed-header {
    width: calc(100% - 54px);
}

// 移动设备上，固定导航栏的宽度为 100%
.mobile .fixed-header {
    width: 100%;
}
</style>