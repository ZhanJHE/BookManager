<template>
  <!-- id="app" 是整个 Vue 应用的根 DOM 元素 -->
  <div id="app">
    <!-- 
      <router-view /> 是一个核心组件，它会渲染当前路由匹配到的组件。
      当你的 URL 变化时，Vue Router 会找到对应的组件，并将其渲染在这里。
      所有页面级组件都会在这里被展示。
    -->
    <router-view />
  </div>
</template>

<script>
export default {
  // 组件的名称，通常用于调试和在开发者工具中识别组件
  name: 'App'
}
</script>

<!-- 
  这个组件没有 scoped 样式，意味着如果这里有 <style> 标签，
  它的样式将会是全局的，影响整个应用。
  当前没有样式，保持了根组件的简洁性。
-->