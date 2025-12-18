<template>
    <div class="dashboard-container">
        <div
            title="欢迎页面"
            style="
                padding: 50px;
                overflow: hidden;
                color: #409eff;
                font: lighter 16px 'Lucida Sans Unicode';
            "
            data-options="iconCls:'icon-heart',plain:true"
        >
            <i
                class="el-icon-a-01"
                style="
                    font-size: 32px;
                    font-weight: 600;
                    color: black;
                "
            >
                欢迎使用图书管理系统
            </i>
            <hr />

            <img :src="require('@/assets/index.jpg')" style="width: 78%" />
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
    name: "Dashboard",
    computed: {
        // 使用 mapGetters 辅助函数，将 store 中的 getters 映射到局部计算属性
        ...mapGetters(["id", "name", "roles"]),
    },
};
</script>