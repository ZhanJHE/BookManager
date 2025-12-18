<template>
  <!-- 如果是外部图标，则渲染一个 div，应用外部图标样式 -->
  <div v-if="isExternal" :style="styleExternalIcon" class="svg-external-icon svg-icon" v-on="$listeners" />
  <!-- 否则，渲染一个 svg 元素来显示内部图标 -->
  <svg v-else :class="svgClass" aria-hidden="true" v-on="$listeners">
    <!-- use 元素通过 xlink:href 引用 SVG 图标 -->
    <use :xlink:href="iconName" />
  </svg>
</template>

<script>
// 导入用于判断是否为外部链接的工具函数
import { isExternal } from '@/utils/validate'

export default {
  name: 'SvgIcon',
  props: {
    // 图标的名称，必需
    iconClass: {
      type: String,
      required: true
    },
    // 自定义的 CSS 类名
    className: {
      type: String,
      default: ''
    }
  },
  computed: {
    // 判断是否为外部图标
    isExternal() {
      return isExternal(this.iconClass)
    },
    // 内部图标的名称，格式为 #icon-xxx
    iconName() {
      return `#icon-${this.iconClass}`
    },
    // SVG 元素的 CSS 类
    svgClass() {
      if (this.className) {
        return 'svg-icon ' + this.className
      } else {
        return 'svg-icon'
      }
    },
    // 外部图标的样式
    styleExternalIcon() {
      return {
        mask: `url(${this.iconClass}) no-repeat 50% 50%`,
        '-webkit-mask': `url(${this.iconClass}) no-repeat 50% 50%`
      }
    }
  }
}
</script>

<style scoped>
/* SVG 图标的基本样式 */
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor; /* 使用当前元素的颜色填充 */
  overflow: hidden;
}

/* 外部 SVG 图标的样式 */
.svg-external-icon {
  background-color: currentColor; /* 背景色为当前元素的颜色 */
  mask-size: cover!important; /* 遮罩尺寸覆盖整个元素 */
  display: inline-block;
}
</style>