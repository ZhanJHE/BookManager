<template>
    <div class="app-container">
        <!-- 顶部功能区 -->
        <div class="filter-container" style="margin-bottom: 15px">
            <!-- 类型名搜索输入框 -->
            <el-input
                v-model="queryParam.booktypename"
                placeholder="类型名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <br />
            <br />
            <!-- 功能按钮 -->
            <el-button
                v-waves
                class="filter-item"
                style="font-size: 20px"
                type="primary"
                icon="el-icon-a-042"
                @click="handleFilter"
            >
                搜索
            </el-button>
            <el-button
                v-waves
                class="filter-item"
                style="font-size: 20px"
                type="primary"
                icon="el-icon-a-041"
                @click="handleShowAll"
            >
                显示全部
            </el-button>
            <el-button
                class="filter-item"
                style="margin-left: 10px; font-size: 20px"
                type="primary"
                icon="el-icon-a-07"
                @click="handleCreate"
            >
                添加类型
            </el-button>
            <el-button
                class="filter-item"
                style="margin-left: 10px; font-size: 20px"
                type="danger"
                icon="el-icon-a-022"
                @click="handleDeleteSome"
            >
                批量删除
            </el-button>
        </div>

        <!-- 添加/编辑类型的对话框 -->
        <el-dialog
            :title="formTitle"
            :visible.sync="dialogFormVisible"
            width="30%"
        >
            <!-- 类型信息表单 -->
            <el-form
                :model="form"
                :rules="rules"
                ref="ruleForm"
                label-width="80px"
            >
                <el-form-item label="类型名称" prop="booktypename">
                    <el-input v-model="form.booktypename"></el-input>
                </el-form-item>

                <el-form-item label="类型描述" prop="booktypedesc">
                    <el-input
                        type="textarea"
                        v-model="form.booktypedesc"
                    ></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 图书类型数据表格 -->
        <el-table
            ref="multipleTable"
            :data="tableData"
            border
            style="width: 100%"
        >
            <el-table-column fixed type="selection" width="55"> </el-table-column>
            <el-table-column
                fixed
                prop="booktypeid"
                label="序号"
                width="100"
            >
            </el-table-column>
            <el-table-column
                prop="booktypename"
                label="类型名称"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="booktypedesc"
                label="类型描述"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="240">
                <template slot-scope="scope">
                    <el-button
                        @click="handleUpdate(scope.row)"
                        type="primary"
                        icon="iconfont icon-r-edit"
                        style="font-size: 16px"
                        >编辑</el-button
                    >
                    <el-button
                        @click="handleDelete(scope.row, scope.$index)"
                        type="danger"
                        icon="iconfont icon-r-delete"
                        style="font-size: 16px"
                        >删除</el-button
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
import waves from "@/directive/waves"; // 引入 waves 指令
import {
    getCount,
    queryBookTypes,
    queryBookTypesByPage,
    addBookType,
    deleteBookType,
    deleteBookTypes,
    updateBookType,
} from "@/api/booktype";

export default {
    name: "BookType",
    directives: { waves },
    // 组件创建后执行
    created() {
        // 从服务器获取数据表格第一页的信息
        queryBookTypesByPage(this.queryParam).then((res) => {
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
            queryBookTypesByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 当前页改变时的处理
        handleCurrentChange(curPage) {
            const params = this.queryParam;
            params.page = curPage;
            queryBookTypesByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 搜索处理
        handleFilter() {
            this.queryParam.page = 1;
            queryBookTypesByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 显示全部处理
        handleShowAll() {
            this.queryParam.page = 1;
            this.queryParam.booktypename = null;
            queryBookTypesByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 添加记录处理
        handleCreate() {
            this.formType = 0; // 设置表单类型为添加
            // 初始化表单数据
            this.form = {
                booktypeid: null,
                booktypename: "",
                booktypedesc: "",
            };
            this.dialogFormVisible = true; // 显示对话框
        },

        // 编辑记录处理
        handleUpdate(row) {
            this.formType = 1; // 设置表单类型为编辑
            // 将行数据填充到表单
            this.form = {
                booktypeid: row.booktypeid,
                booktypename: row.booktypename,
                booktypedesc: row.booktypedesc,
            };
            this.dialogFormVisible = true; // 显示对话框
        },

        // 提交表单（添加或更新）
        submitForm() {
            if (this.formType === 0) {
                // 添加记录
                addBookType(this.form).then((res) => {
                    if (res === 1) {
                        this.$message.success("添加记录成功");
                        // 跳转到最后一页
                        getCount().then((res) => {
                            this.recordTotal = res;
                            this.queryParam.page =
                                Math.ceil(
                                    this.recordTotal / this.queryParam.limit
                                ) || 1;
                            this.handleCurrentChange(this.queryParam.page);
                        });
                    } else {
                        this.$message.error("添加记录失败");
                    }
                    this.dialogFormVisible = false; // 关闭对话框
                });
            } else if (this.formType === 1) {
                // 更新记录
                updateBookType(this.form).then((res) => {
                    if (res === 1 || res === 0) {
                        this.$message.success("更新记录成功");
                        this.handleCurrentChange(this.queryParam.page);
                    } else {
                        this.$message.error("更新记录失败");
                    }
                    this.dialogFormVisible = false; // 关闭对话框
                });
            }
        },

        // 删除单条记录
        handleDelete(row, index) {
            this.$confirm("确定要删除该条记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBookType(row).then((res) => {
                    if (res === 1) {
                        this.$message.success("删除记录成功");
                        this.tableData.splice(index, 1);
                        // 如果当前页数据删完，则跳转到上一页
                        if (this.tableData.length === 0) {
                            this.queryParam.page = this.queryParam.page - 1;
                            this.handleCurrentChange(this.queryParam.page);
                        }
                    } else if (res === -1) {
                        this.$message.error(
                            "该图书类型下存在图书，请先删除所属的图书再尝试删除类型"
                        );
                    } else {
                        this.$message.error("删除记录失败");
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
                deleteBookTypes(items).then((res) => {
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
    },
    data() {
        return {
            tableData: [], // 表格数据
            recordTotal: 0, // 总记录数
            queryParam: {
                // 查询参数
                page: 1,
                limit: 10,
                booktypename: null,
            },
            dialogFormVisible: false, // 对话框可见性
            formType: 0, // 表单类型 0:添加, 1:编辑
            form: {
                // 表单数据
                bookid: null,
                booktypename: "",
                booktypedesc: "",
            },
            rules: {
                // 表单验证规则
                booktypename: [
                    {
                        required: true,
                        message: "请输入图书类型名称",
                        trigger: "blur",
                    },
                ],
                booktypedesc: [
                    {
                        required: true,
                        message: "请输入图书类型描述",
                        trigger: "blur",
                    },
                ],
            },
        };
    },
    computed: {
        ...mapGetters(["id", "name", "roles"]), // 从 Vuex store 获取用户信息
        // 计算对话框标题
        formTitle() {
            return this.formType === 0 ? "添加记录" : "修改记录";
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