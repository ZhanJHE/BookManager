<template>
    <!-- 只渲染没有被 hidden: true 标记的路由 -->
    <div v-if="!item.hidden">
        <!-- 
        情况一：当只有一个可显示的子路由时，直接显示该子路由作为菜单项。
        - hasOneShowingChild: 判断是否只有一个可显示的子路由，并将该子路由赋值给 this.onlyOneChild。
        - !onlyOneChild.children: 确保这个唯一的子路由没有自己的子节点。
        - onlyOneChild.noShowingChildren: 或者，这个子路由有子节点，但所有子节点都被隐藏了。
        - !item.alwaysShow: 如果父路由设置了 alwaysShow: true，则无论如何都显示为下拉菜单。
      -->
        <template
            v-if="
                hasOneShowingChild(item.children, item) &&
                (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
                !item.alwaysShow
            "
        >
            <!-- 使用 AppLink 组件来处理内部和外部链接 -->
            <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
                <!-- Element UI 的菜单项 -->
                <el-menu-item
                    :index="resolvePath(onlyOneChild.path)"
                    :class="{ 'submenu-title-noDropdown': !isNest }"
                >
                    <!-- Item 组件用于显示图标和标题 -->
                    <item
                        :icon="
                            onlyOneChild.meta.icon ||
                            (item.meta && item.meta.icon)
                        "
                        :title="onlyOneChild.meta.title"
                    />
                </el-menu-item>
            </app-link>
        </template>

        <!-- 情况二：有多个可显示的子路由，渲染为一个可展开的子菜单 -->
        <el-submenu
            v-else
            ref="subMenu"
            :index="resolvePath(item.path)"
            popper-append-to-body
        >
            <!-- 子菜单的标题 -->
            <template slot="title">
                <item
                    v-if="item.meta"
                    :icon="item.meta && item.meta.icon"
                    :title="item.meta.title"
                />
            </template>
            <!-- 递归调用自身，渲染子菜单项 -->
            <sidebar-item
                v-for="child in item.children"
                :key="child.path"
                :is-nest="true" 
                :item="child"
                :base-path="resolvePath(child.path)" 
                class="nest-menu"
            />
        </el-submenu>
    </div>
</template>

<script>
import path from "path";
import { isExternal } from "@/utils/validate";
import Item from "./Item";
import AppLink from "./Link";
import FixiOSBug from "./FixiOSBug";

export default {
    name: "SidebarItem",
    components: { Item, AppLink },
    mixins: [FixiOSBug], // 混入修复 iOS bug 的逻辑
    props: {
        // 路由对象，必需
        item: {
            type: Object,
            required: true,
        },
        // 是否为嵌套菜单，用于样式调整
        isNest: {
            type: Boolean,
            default: false,
        },
        // 基础路径，用于拼接子路由路径
        basePath: {
            type: String,
            default: "",
        },
    },
    data() {
        // data 中返回一个空对象，onlyOneChild 在 hasOneShowingChild 方法中被赋值
        this.onlyOneChild = null;
        return {};
    },
    methods: {
        // 判断是否只有一个可显示的子路由
        hasOneShowingChild(children = [], parent) {
            // 过滤出所有未被隐藏的子路由
            const showingChildren = children.filter((item) => {
                if (item.hidden) {
                    return false;
                } else {
                    // 将可显示的子路由暂存到 this.onlyOneChild，以便在模板中使用
                    this.onlyOneChild = item;
                    return true;
                }
            });

            // 如果只有一个可显示的子路由，返回 true
            if (showingChildren.length === 1) {
                return true;
            }

            // 如果没有可显示的子路由，则将父路由自身作为 onlyOneChild，并添加 noShowingChildren 标记
            // 这样，父级菜单项会显示，但没有下拉箭头
            if (showingChildren.length === 0) {
                this.onlyOneChild = { ...parent, path: "", noShowingChildren: true };
                return true;
            }

            return false;
        },
        // 解析路径，处理外部链接和嵌套路由的路径拼接
        resolvePath(routePath) {
            // 如果是外部链接，直接返回
            if (isExternal(routePath)) {
                return routePath;
            }
            // 如果基础路径是外部链接，也直接返回
            if (isExternal(this.basePath)) {
                return this.basePath;
            }
            // 使用 path.resolve 拼接基础路径和路由路径，生成最终的 URL
            return path.resolve(this.basePath, routePath);
        },
    },
};
</script>