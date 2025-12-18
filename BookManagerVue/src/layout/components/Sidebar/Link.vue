<template>
    <!--
    使用动态组件 <component>，
    - :is="type" 根据计算属性 type 的值来决定渲染哪个组件 ('a' 或 'router-link')
    - v-bind="linkProps(to)" 将计算出的属性对象绑定到组件上
  -->
    <component :is="type" v-bind="linkProps(to)">
        <!-- slot 用于接收父组件传递过来的内容，比如菜单项的图标和标题 -->
        <slot />
    </component>
</template>

<script>
// 引入外部链接验证函数
import { isExternal } from "@/utils/validate";

export default {
    props: {
        // 链接的目标地址，必需
        to: {
            type: String,
            required: true,
        },
    },
    computed: {
        // 计算属性：判断链接是否为外部链接
        isExternal() {
            return isExternal(this.to);
        },
        // 计算属性：根据是否为外部链接，决定渲染的组件类型
        type() {
            if (this.isExternal) {
                // 如果是外部链接，渲染为 a 标签
                return "a";
            }
            // 如果是内部链接，渲染为 router-link 组件
            return "router-link";
        },
    },
    methods: {
        // 方法：根据链接类型生成相应的 props
        linkProps(to) {
            if (this.isExternal) {
                // 外部链接的 props
                return {
                    href: to,
                    target: "_blank", // 在新标签页中打开
                    rel: "noopener", // 安全性考虑，防止新页面通过 window.opener 访问原始页面
                };
            }
            // 内部链接的 props
            return {
                to: to, // to prop 用于 router-link 指定目标路由
            };
        },
    },
};
</script>