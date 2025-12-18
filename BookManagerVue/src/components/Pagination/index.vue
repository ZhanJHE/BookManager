<template>
  <!-- 分页容器，当 hidden prop 为 true 时，通过 CSS 类隐藏该组件 -->
  <div :class="{'hidden':hidden}" class="pagination-container">
    <!-- Element UI 的分页组件 -->
    <el-pagination
      :background="background" 
      :current-page.sync="currentPage" 
      :page-size.sync="pageSize" 
      :layout="layout" 
      :page-sizes="pageSizes" 
      :total="total" 
      v-bind="$attrs" 
      @size-change="handleSizeChange" 
      @current-change="handleCurrentChange" 
    />
  </div>
</template>

<script>
// 导入滚动到指定位置的工具函数
import { scrollTo } from '@/utils/scroll-to'

export default {
  name: 'Pagination',
  props: {
    // 总条目数，必需
    total: {
      required: true,
      type: Number
    },
    // 当前页，默认为 1
    page: {
      type: Number,
      default: 1
    },
    // 每页显示条目数，默认为 20
    limit: {
      type: Number,
      default: 20
    },
    // 每页显示个数选择器的选项
    pageSizes: {
      type: Array,
      default() {
        return [10, 20, 30, 50]
      }
    },
    // 分页组件的布局
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    // 是否显示背景色
    background: {
      type: Boolean,
      default: true
    },
    // 改变分页时是否自动滚动到页面顶部
    autoScroll: {
      type: Boolean,
      default: true
    },
    // 是否隐藏分页组件
    hidden: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    // 计算属性，用于双向绑定当前页码
    currentPage: {
      get() {
        return this.page
      },
      set(val) {
        // 当值改变时，触发 update:page 事件，以支持 .sync 修饰符
        this.$emit('update:page', val)
      }
    },
    // 计算属性，用于双向绑定每页显示数量
    pageSize: {
      get() {
        return this.limit
      },
      set(val) {
        // 当值改变时，触发 update:limit 事件，以支持 .sync 修饰符
        this.$emit('update:limit', val)
      }
    }
  },
  methods: {
    // 每页显示条目数改变时的处理函数
    handleSizeChange(val) {
      // 触发 pagination 事件，并传递新的页码和每页数量
      this.$emit('pagination', { page: this.currentPage, limit: val })
      // 如果开启了自动滚动，则滚动到页面顶部
      if (this.autoScroll) {
        scrollTo(0, 800)
      }
    },
    // 当前页码改变时的处理函数
    handleCurrentChange(val) {
      // 触发 pagination 事件，并传递新的页码和每页数量
      this.$emit('pagination', { page: val, limit: this.pageSize })
      // 如果开启了自动滚动，则滚动到页面顶部
      if (this.autoScroll) {
        scrollTo(0, 800)
      }
    }
  }
}
</script>

<style scoped>
.pagination-container {
  background: #fff;
  padding: 32px 16px;
}
.pagination-container.hidden {
  display: none;
}
</style>