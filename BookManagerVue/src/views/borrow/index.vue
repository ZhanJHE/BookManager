<template>
    <div class="app-container">
        <!-- 顶部功能区 -->
        <div class="filter-container" style="margin-bottom: 15px">
            <!-- 用户名搜索框（仅管理员可见） -->
            <el-input
                v-permission="['admin']"
                v-model="queryParam.username"
                placeholder="用户名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <!-- 图书名搜索框 -->
            <el-input
                v-model="queryParam.bookname"
                placeholder="图书名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <br /><br />
            <!-- 功能按钮 -->
            <el-button
                v-waves
                class="filter-item"
                type="primary"
                style="font-size: 20px"
                icon="el-icon-a-042"
                @click="handleFilter"
            >
                搜索
            </el-button>
            <el-button
                v-waves
                class="filter-item"
                type="primary"
                style="font-size: 20px"
                icon="el-icon-a-041"
                @click="handleShowAll"
            >
                显示全部
            </el-button>
            <!-- 批量删除按钮（仅管理员可见） -->
            <el-button
                v-permission="['admin']"
                class="filter-item"
                style="margin-left: 10px; font-size: 20px"
                type="danger"
                icon="el-icon-a-022"
                @click="handleDeleteSome"
            >
                批量删除
            </el-button>
        </div>

        <!-- 借阅数据表格 -->
        <el-table
            ref="multipleTable"
            :data="tableData"
            border
            style="width: 100%"
        >
            <el-table-column fixed type="selection" width="55"> </el-table-column>
            <el-table-column
                fixed
                prop="borrowid"
                label="序号"
                width="100"
            >
            </el-table-column>
            <el-table-column
                prop="username"
                label="用户名"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookname"
                label="图书名"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="borrowtimestr" label="借书时间">
            </el-table-column>
            <el-table-column label="还书时间">
                <template slot-scope="scope">
                    <!-- 根据还书时间是否为空显示不同状态 -->
                    <span
                        v-if="
                            scope.row.returntimestr === null ||
                            scope.row.returntimestr === ''
                        "
                        style="color: red"
                        >等待还书</span
                    >
                    <span v-else style="color: #1aac1a">{{ scope.row.returntimestr }}</span>
                </template>
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                :width="roleIsAdmin ? '260px' : '160px'"
            >
                <template slot-scope="scope">
                    <!-- 删除按钮（仅管理员可见） -->
                    <el-button
                        v-permission="['admin']"
                        @click="handleDelete(scope.row, scope.$index)"
                        type="danger"
                        icon="iconfont icon-r-delete"
                        style="font-size: 16px"
                        >删除</el-button
                    >
                    <!-- 归还图书按钮（仅在未还书时显示） -->
                    <el-button
                        v-if="
                            scope.row.returntimestr === null ||
                            scope.row.returntimestr === ''
                        "
                        @click="handleReturn(scope.row, scope.$index)"
                        type="success"
                        icon="iconfont icon-r-refresh"
                        style="font-size: 16px"
                        >归还图书</el-button
                    >
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="queryParam.page"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="queryParam.limit"
            layout="total, sizes, prev, pager, next, jumper"
            :total="recordTotal"
            style="margin-top: 15px"
        >
        </el-pagination>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import permission from "@/directive/permission/index.js"; // 引入权限判断指令
import waves from "@/directive/waves"; // 引入 waves 指令
import {
    getCount,
    queryBorrows,
    queryBorrowsByPage,
    addBorrow,
    deleteBorrow,
    deleteBorrows,
    updateBorrow,
    returnBook,
} from "@/api/borrow";

export default {
    name: "Borrow",
    directives: { waves, permission },
    // 组件创建后执行
    created() {
        // 根据用户角色初始化查询参数
        if (this.roleIsAdmin) {
            this.queryParam.userid = null; // 管理员查看所有借阅记录
        } else {
            this.queryParam.userid = this.id; // 普通用户只看自己的借阅记录
        }
        // 获取第一页数据
        queryBorrowsByPage(this.queryParam).then((res) => {
            console.log("首页数据获取成功", res);
            this.tableData = res.data;
            this.recordTotal = res.count;
        });
    },
    methods: {
        // 每页记录数改变时的处理
        handleSizeChange(curSize) {
            const params = this.queryParam;
            params.limit = curSize;
            queryBorrowsByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 当前页改变时的处理
        handleCurrentChange(curPage) {
            const params = this.queryParam;
            params.page = curPage;
            queryBorrowsByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 搜索处理
        handleFilter() {
            this.queryParam.page = 1;
            queryBorrowsByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 显示全部处理
        handleShowAll() {
            this.queryParam.page = 1;
            this.queryParam.username = null;
            this.queryParam.bookname = null;
            queryBorrowsByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 删除单条记录
        handleDelete(row, index) {
            this.$confirm("确定要删除该条记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBorrow(row).then((res) => {
                    if (res === 1) {
                        this.$message.success("删除记录成功");
                        this.tableData.splice(index, 1);
                        // 如果当前页数据删完，则跳转到上一页
                        if (this.tableData.length === 0) {
                            this.queryParam.page = this.queryParam.page - 1;
                            this.handleCurrentChange(this.queryParam.page);
                        }
                    } else {
                        this.$message.error("存在未完成的借阅信息，删除失败");
                    }
                });
            });
        },

        // 批量删除
        handleDeleteSome() {
            this.$confirm("确定要删除这些记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                const items = this.$refs.multipleTable.selection; // 获取选中的行
                deleteBorrows(items).then((res) => {
                    if (res > 0) {
                        this.$message.success("删除" + res + "条记录成功");
                        if (this.tableData.length === res) {
                            // 如果本页内容全部删光了,当前页为上一页
                            if (this.queryParam.page !== 0) {
                                this.queryParam.page = this.queryParam.page - 1;
                            }
                        }
                        // 重新加载当前页
                        this.handleCurrentChange(this.queryParam.page);
                    } else {
                        this.$message.error("删除记录失败");
                    }
                });
            });
        },

        // 还书处理
        handleReturn(row, index) {
            this.$confirm("确定要还书吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                returnBook(row.borrowid, row.bookid).then((res) => {
                    if (res === 1) {
                        this.$message.success("还书成功");
                        this.handleCurrentChange(this.queryParam.page);
                    } else if (res === 0) {
                        this.$message.error("该图书已经是归还的状态");
                    } else {
                        this.$message.error("还书失败");
                    }
                });
            });
        },
    },
    data() {
        return {
            tableData: [], // 表格数据
            recordTotal: 0, // 总记录数
            queryParam: {
                // 查询参数
                page: 1,
                limit: 10,
                userid: null,
                username: null,
                bookname: null,
            },
        };
    },
    computed: {
        ...mapGetters(["id", "name", "roles"]), // 从 Vuex store 获取用户信息
        // 判断当前用户是否为管理员
        roleIsAdmin() {
            return this.roles.includes("admin");
        },
    },
};
</script>

<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.avatar {
    width: 150px;
    height: 200px;
    display: block;
}
</style>