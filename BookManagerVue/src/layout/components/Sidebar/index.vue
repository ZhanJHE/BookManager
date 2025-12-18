<template>
    <div>
        <!-- Logo 组件，仅在 showLogo 为 true 时显示 -->
        <logo v-if="showLogo" :collapse="isCollapse" />

        <!-- Element UI 的滚动条组件，用于包裹菜单 -->
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <!-- Element UI 的菜单组件 -->
            <el-menu
                :default-active="activeMenu"      
                :collapse="isCollapse"          
                :background-color="variables.menuBg"  
                :text-color="variables.menuText"      
                :unique-opened="false"           
                :active-text-color="variables.menuActiveText" 
                :collapse-transition="false"     
                mode="vertical"                  
            >
                <!-- 遍历权限路由，为每个路由创建一个 SidebarItem -->
                <sidebar-item
                    v-for="route in permission_routes"
                    :key="route.path"
                    :item="route"
                    :base-path="route.path"
                />
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/styles/variables.scss";

export default {
    components: { SidebarItem, Logo },
    computed: {
        // 使用 mapGetters 辅助函数，将 store 中的 getters (permission_routes, sidebar) 映射到局部计算属性
        ...mapGetters(["permission_routes", "sidebar"]),
        // 计算当前激活的菜单项
        activeMenu() {
            const route = this.$route;
            const { meta, path } = route;
            // 如果路由元信息中设置了 activeMenu，则优先使用该值，以实现高亮特定菜单项
            if (meta.activeMenu) {
                return meta.activeMenu;
            }
            // 否则，使用当前路由的 path 作为激活菜单项
            return path;
        },
        // 从 store 的 settings 模块中获取是否显示 Logo 的配置
        showLogo() {
            return this.$store.state.settings.sidebarLogo;
        },
        // 从 SCSS 文件中获取样式变量，用于动态设置菜单样式
        variables() {
            return variables;
        },
        // 计算侧边栏是否处于折叠状态
        isCollapse() {
            return !this.sidebar.opened;
        },
    },
};
</script>