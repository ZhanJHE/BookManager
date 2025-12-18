<script>
export default {
    name: "MenuItem",
    // 声明为函数式组件，使其无状态、无实例（没有 this 上下文），性能更高
    functional: true,
    props: {
        // 图标 class
        icon: {
            type: String,
            default: "",
        },
        // 标题
        title: {
            type: String,
            default: "",
        },
    },
    // render 函数，用于生成虚拟 DOM。h 是 createElement 的别名，context 包含了组件的上下文信息
    render(h, context) {
        // 从 context.props 中获取 icon 和 title
        const { icon, title } = context.props;
        const vnodes = [];

        // 如果传入了 icon
        if (icon) {
            // 如果图标是 Element UI 的图标（以 'el-icon' 开头）
            if (icon.includes("el-icon")) {
                // 创建一个 i 元素来显示 Element UI 图标
                vnodes.push(<i class={[icon, "sub-el-icon"]} />);
            } else {
                // 否则，视为自定义的 SVG 图标，并使用 svg-icon 组件来渲染
                vnodes.push(<svg-icon icon-class={icon} />);
            }
        }

        // 如果传入了 title
        if (title) {
            // 创建一个 span 元素来显示标题，并设置 slot="title" 以便被 el-menu-item 或 el-submenu 正确识别
            vnodes.push(<span slot="title">{title}</span>);
        }
        // 返回包含所有虚拟节点的数组
        return vnodes;
    },
};
</script>

<style scoped>
/* Element UI 图标的补充样式 */
.sub-el-icon {
    color: currentColor; /* 继承父元素的颜色 */
    width: 1em;
    height: 1em;
    font-size: 24px;
}
</style>