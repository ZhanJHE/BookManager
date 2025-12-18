export default {
  computed: {
    // 从 Vuex store 中获取当前设备类型
    device() {
      return this.$store.state.app.device
    }
  },
  mounted() {
    // 修复在 iOS 设备上点击菜单会触发 mouseleave 事件的 bug
    // 详情请见：https://github.com/PanJiaChen/vue-element-admin/issues/1135
    this.fixBugIniOS()
  },
  methods: {
    fixBugIniOS() {
      // 获取子菜单组件的引用
      const $subMenu = this.$refs.subMenu
      if ($subMenu) {
        // 保存原始的 handleMouseleave 方法
        const handleMouseleave = $subMenu.handleMouseleave
        // 重写 handleMouseleave 方法
        $subMenu.handleMouseleave = (e) => {
          // 如果是移动设备，则直接返回，不执行原始的 handleMouseleave 逻辑
          if (this.device === 'mobile') {
            return
          }
          // 如果不是移动设备，则正常调用原始的 handleMouseleave 方法
          handleMouseleave(e)
        }
      }
    }
  }
}