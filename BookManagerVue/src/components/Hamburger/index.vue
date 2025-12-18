<template>
  <!-- 容器 div，设置内边距并监听点击事件 -->
  <div style="padding: 0 15px;" @click="toggleClick">
    <!-- SVG 图标作为汉堡按钮 -->
    <svg
      :class="{'is-active':isActive}" 
      
      class="hamburger"
      viewBox="0 0 1024 1024"
      xmlns="http://www.w3.org/2000/svg"
      width="64"
      height="64"
    >
      <!-- SVG 路径数据，定义了汉堡菜单的图标形状 -->
      <path d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM142.4 642.1L298.7 519a8.84 8.84 0 0 0 0-13.9L142.4 381.9c-5.8-4.6-14.4-.5-14.4 6.9v246.3a8.9 8.9 0 0 0 14.4 7z" />
    </svg>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  props: {
    // `isActive` prop 用于控制汉堡按钮的激活状态（例如，侧边栏是否展开）
    isActive: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    // 点击事件处理函数
    toggleClick() {
      // 触发一个名为 'toggleClick' 的自定义事件，通知父组件按钮被点击了
      this.$emit('toggleClick')
    }
  }
}
</script>

<style scoped>
.hamburger {
  display: inline-block;
  vertical-align: middle;
  width: 20px;
  height: 20px;
}

/* 当汉堡按钮处于激活状态时 */
.hamburger.is-active {
  /* 旋转180度 */
  transform: rotate(180deg);
}
</style>