<template>
  <!-- 使用 Element UI 的面包屑组件，并自定义分隔符为 '/' -->
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <!-- 使用 transition-group 实现面包屑项的动画效果 -->
    <transition-group name="breadcrumb">
      <!-- 遍历 levelList 数组，生成面包屑的每一项 -->
      <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
        <!-- 如果是不可重定向的项或是最后一项，则显示为纯文本 -->
        <span v-if="item.redirect==='noRedirect'||index==levelList.length-1" class="no-redirect">{{ item.meta.title }}</span>
        <!-- 否则，显示为可点击的链接 -->
        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
// 导入 path-to-regexp 库，用于处理带参数的路由路径
import pathToRegexp from 'path-to-regexp'

export default {
  data() {
    return {
      // 用于存储面包屑导航项的数组
      levelList: null
    }
  },
  watch: {
    // 监听路由对象的变化
    $route() {
      // 当路由变化时，重新生成面包屑
      this.getBreadcrumb()
    }
  },
  created() {
    // 组件创建时，立即生成面包屑
    this.getBreadcrumb()
  },
  methods: {
    // 生成面包屑导航列表的核心方法
    getBreadcrumb() {
      // 过滤出所有包含 meta.title 的匹配路由记录
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title)
      const first = matched[0]

      // 如果第一个匹配的路由不是 Dashboard，则在最前面手动添加一个指向首页的路由
      if (!this.isDashboard(first)) {
        matched = [{ path: '/dashboard', meta: { title: '首页' }}].concat(matched)
      }

      // 再次过滤，排除那些在 meta 中明确设置了 breadcrumb: false 的路由
      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    // 判断一个路由是否是 Dashboard 路由
    isDashboard(route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      // 比较路由名称（不区分大小写）
      return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
    },
    // 编译带参数的路径
    pathCompile(path) {
      // 解决 issue: https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route
      // 使用 pathToRegexp.compile 将路径模板编译成一个函数
      var toPath = pathToRegexp.compile(path)
      // 将当前路由的参数填充到路径中
      return toPath(params)
    },
    // 处理面包屑项的点击事件
    handleLink(item) {
      const { redirect, path } = item
      // 如果路由配置了 redirect，则直接跳转到 redirect 指定的路径
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      // 否则，跳转到编译后的路径（处理了动态参数）
      this.$router.push(this.pathCompile(path))
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>