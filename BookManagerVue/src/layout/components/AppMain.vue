<template>
    <!-- 主内容区域 -->
    <section class="app-main">
        <!-- 
      使用 transition 组件实现路由切换的动画效果
      - name="fade-transform" 定义了动画的类名
      - mode="out-in" 表示当前元素先离开，新元素再进入
    -->
        <transition name="fade-transform" mode="out-in">
            <!-- 
        router-view 用于渲染当前路由匹配的组件
        - :key="key" 确保在路由路径变化时，即使是同一个组件也能被重新渲染，触发钩子函数
      -->
            <router-view :key="key" />
        </transition>
    </section>
</template>

<script>
export default {
    name: "AppMain",
    computed: {
        // 计算属性，返回当前路由的完整路径，作为 router-view 的 key
        // 这确保了当路由路径改变时，即使是同一个组件也会被重新创建和渲染，从而触发完整的生命周期钩子
        key() {
            return this.$route.path;
        },
    },
};
</script>

<style scoped>
.app-main {
    /* 50px 是顶部导航栏的高度 */
    min-height: calc(100vh - 50px);
    width: 100%;
    position: relative;
    overflow: hidden;
}

/* 当有固定头部时，为主内容区域添加上边距，以避免内容被遮挡 */
.fixed-header + .app-main {
    padding-top: 50px;
}
</style>

<style lang="scss">
// 修复在打开 el-dialog 时，固定头部（fixed-header）样式错乱的 bug
// 当 el-dialog 打开时，会给 body 添加 el-popup-parent--hidden 类，导致滚动条消失，页面宽度变化
// 这里通过调整内边距来补偿滚动条消失带来的宽度变化
.el-popup-parent--hidden {
    .fixed-header {
        padding-right: 15px;
    }
}
</style>