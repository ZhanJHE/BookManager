<template>
    <div class="app-container">
        <!-- 顶部功能区 -->
        <div class="filter-container" style="margin-bottom: 15px">
            <!-- 书名搜索输入框 -->
            <el-input
                v-model="queryParam.bookname"
                placeholder="书名"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <!-- 作者搜索输入框 -->
            <el-input
                v-model="queryParam.bookauthor"
                placeholder="作者"
                style="width: 200px"
                class="filter-item"
                @keyup.enter.native="handleFilter"
            />
            <!-- 图书类型选择器 -->
            <el-select
                v-model="queryParam.booktypeid"
                filterable
                placeholder="类型"
                clearable
                class="filter-item"
                style="width: 200px"
            >
                <el-option
                    v-for="item in typeData"
                    :key="item.booktypeid"
                    :label="item.booktypename"
                    :value="item.booktypeid"
                />
            </el-select>
            <br />
            <br />
            <!-- 功能按钮 -->
            <el-button
                v-waves
                style="font-size: 20px"
                class="filter-item"
                type="primary"
                icon="el-icon-a-042"
                @click="handleFilter"
            >
                搜索
            </el-button>
            <el-button
                v-waves
                style="font-size: 20px"
                class="filter-item"
                type="primary"
                icon="el-icon-a-041"
                @click="handleShowAll"
            >
                显示全部
            </el-button>
            <el-button
                v-permission="['admin']"  
                class="filter-item"
                style="margin-left: 10px; font-size: 20px"
                type="primary"
                icon="el-icon-a-07"
                @click="handleCreate"
            >
                添加图书
            </el-button>
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

        <!-- 添加/编辑图书的对话框 -->
        <el-dialog
            :title="formTitle"
            :visible.sync="dialogFormVisible"
            width="40%"
        >
            <el-row>
                <el-col :span="16">
                    <!-- 图书信息表单 -->
                    <el-form
                        :model="form"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                    >
                        <el-form-item label="图书名称" prop="bookname">
                            <el-input v-model="form.bookname"></el-input>
                        </el-form-item>

                        <el-form-item label="作者" prop="bookauthor">
                            <el-input v-model="form.bookauthor"></el-input>
                        </el-form-item>

                        <el-form-item label="价格" prop="bookprice">
                            <el-input v-model="form.bookprice"></el-input>
                        </el-form-item>

                        <el-form-item label="图书类型" prop="booktypeid">
                            <el-select
                                v-model="form.booktypeid"
                                placeholder="请选择类型"
                            >
                                <el-option
                                    v-for="item in typeData"
                                    :key="item.booktypeid"
                                    :label="item.booktypename"
                                    :value="item.booktypeid"
                                >
                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="书籍描述" prop="bookdesc">
                            <el-input
                                type="textarea"
                                v-model="form.bookdesc"
                            ></el-input>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="8">
                    <div align="center">
                        <h3>点击下方图片上传封面</h3>
                        <!-- 封面上传组件 -->
                        <el-upload
                            class="avatar-uploader"
                            action="http://localhost:9111/BookManager/update/updateImg" 
                            :on-success="handleAvatarSuccess" 
                            :before-upload="beforeAvatarUpload" 
                        >
                            <img
                                v-if="form.bookimg"
                                :src="
                                    $store.state.settings.baseApi +
                                    form.bookimg
                                "
                                class="avatar"
                                alt="封面无法显示"
                            />
                            <i
                                v-else
                                class="el-icon-plus avatar-uploader-icon"
                            ></i>
                        </el-upload>
                    </div>
                </el-col>
            </el-row>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 选择借阅用户的对话框 -->
        <el-dialog
            title="选择用户"
            :visible.sync="dialogFormVisible2"
            width="400px"
        >
            <el-form :model="form2">
                <el-form-item label="用户名" prop="userid" label-width="80px">
                    <el-select v-model="form2.booktypeid" placeholder="请选择用户">
                        <el-option
                            v-for="item in userData"
                            :key="item.userid"
                            :label="item.username"
                            :value="item.userid"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="submitForm2">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 图书信息数据表格 -->
        <el-table
            ref="multipleTable"
            :data="tableData"
            border
            style="width: 100%"
            @selection-change="handleSelectionChange" 
        >
            <el-table-column fixed type="selection" width="55"> </el-table-column>
            <el-table-column
                fixed
                prop="bookid"
                label="序号"
                width="100"
            >
            </el-table-column>
            <el-table-column
                v-if="roleIsAdmin === false" 
                label="图书封面"
                width="155"
            >
                <template slot-scope="scope">
                    <el-image
                        :src="$store.state.settings.baseApi + scope.row.bookimg"
                        style="width: 130px; height: 180px"
                    ></el-image>
                </template>
            </el-table-column>
            <el-table-column
                prop="bookname"
                label="图书名称"
                width="150"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookauthor"
                label="图书作者"
                width="100"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookprice"
                label="图书价格"
                width="100"
            >
            </el-table-column>
            <el-table-column
                prop="booktypename"
                label="图书类型名"
                width="100"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="bookdesc"
                label="图书描述"
                min-width="300"
                show-overflow-tooltip 
            >
            </el-table-column>
            <!-- 图书状态列 -->
            <el-table-column label="图书状态" width="100">
                <template slot-scope="scope">
                    <span v-if="scope.row.isborrowed === 1" style="color: red"
                        >已借出</span
                    >
                    <span v-else style="color: #1aac1a">未借出</span>
                </template>
            </el-table-column>
            <!-- 操作列，根据用户角色动态调整宽度 -->
            <el-table-column
                fixed="right"
                label="操作"
                :width="roleIsAdmin ? '370px' : '160px'"
            >
                <template slot-scope="scope">
                    <!-- 编辑按钮，仅管理员可见 -->
                    <el-button
                        v-permission="['admin']"
                        @click="handleUpdate(scope.row)"
                        type="primary"
                        icon="iconfont icon-r-edit"
                        style="font-size: 16px"
                        >编辑</el-button
                    >
                    <!-- 删除按钮，仅管理员可见 -->
                    <el-button
                        v-permission="['admin']"
                        @click="handleDelete(scope.row, scope.$index)"
                        type="danger"
                        icon="iconfont icon-r-delete"
                        style="font-size: 16px"
                        >删除</el-button
                    >
                    <!-- 借阅图书按钮 -->
                    <el-button
                        @click="handleBorrow(scope.row)"
                        type="success"
                        icon="iconfont icon-r-shield"
                        style="font-size: 16px"
                        >借阅图书</el-button
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
// 导入 Vuex 的 mapGetters 辅助函数，用于将 store 中的 getter 映射到局部计算属性
import { mapGetters } from "vuex";
// 引入权限判断指令
import permission from "@/directive/permission/index.js";
// 引入 waves 指令，用于添加波纹动画效果
import waves from "@/directive/waves";
// 导入图书信息管理相关的 API 函数
import {
    getCount,
    queryBookInfosByPage,
    addBookInfo,
    deleteBookInfo,
    deleteBookInfos,
    updateBookInfo,
} from "@/api/bookinfo";
// 导入图书类型管理相关的 API 函数
import { queryBookTypes } from "@/api/booktype";
// 导入借阅管理相关的 API 函数
import { borrowBook } from "@/api/borrow";
// 导入用户管理相关的 API 函数
import { queryUsers } from "@/api/user";

export default {
    // 组件名称
    name: "Bookinfo",
    // 注册局部指令
    directives: { waves, permission },
    // 组件创建后执行
    created() {
        // 从服务器获取数据表格第一页的信息
        queryBookInfosByPage(this.queryParam).then((res) => {
            console.log("首页数据获取成功", res);
            this.tableData = res.data;
            this.recordTotal = res.count;
        });
        // 从服务器获取所有的图书类型
        queryBookTypes().then((res) => {
            console.log("图书类型获取成功", res);
            this.typeData = res;
        });
    },
    // 组件挂载后执行
    mounted() {
        // 如果不是管理员，则设置每页显示5条记录
        if (this.roleIsAdmin === false) {
            this.queryParam.limit = 5;
            this.handleSizeChange(this.queryParam.limit);
        }
    },
    methods: {
        // 每页记录数改变时的处理
        handleSizeChange(curSize) {
            const params = this.queryParam;
            params.limit = curSize;
            queryBookInfosByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 当前页改变时的处理
        handleCurrentChange(curPage) {
            const params = this.queryParam;
            params.page = curPage;

            queryBookInfosByPage(params).then((res) => {
                console.log("分页数据获取成功", res);
                this.tableData = res.data;
                this.recordTotal = res.count;
            });
        },

        // 搜索图书处理
        handleFilter() {
            this.queryParam.page = 1; // 搜索时重置到第一页
            queryBookInfosByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 显示全部处理
        handleShowAll() {
            this.queryParam.page = 1; // 重置到第一页
            this.queryParam.bookname = null;
            this.queryParam.bookauthor = null;
            this.queryParam.booktypeid = null;
            queryBookInfosByPage(this.queryParam).then((res) => {
                if (res.code === 0) {
                    this.tableData = res.data;
                    this.recordTotal = res.count;
                }
            });
        },

        // 图片上传成功回调
        handleAvatarSuccess(res, file) {
            console.log(res);
            console.log(file);
            if (res.code === 0) {
                this.$message.success("上传成功");
                this.form.bookimg = res.data; // 更新表单中的图片路径
            } else {
                this.$message.error("上传失败，请联系管理员");
            }
        },

        // 图片上传前校验
        beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg";
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error("上传封面图片只能是 JPG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传封面图片大小不能超过 2MB!");
            }
            return isJPG && isLt2M;
        },

        // 添加记录处理
        handleCreate() {
            // 获取所有图书类型
            queryBookTypes().then((res) => {
                console.log("图书类型获取成功", res);
                this.typeData = res;
            });
            this.formType = 0; // 设置表单类型为添加
            // 初始化表单数据
            this.form = {
                bookid: null,
                bookname: "",
                bookauthor: "",
                bookprice: "",
                booktypeid: 1,
                bookdesc: "",
                isborrowed: 0,
                bookimg: "",
            };
            this.dialogFormVisible = true; // 显示对话框
        },

        // 编辑记录处理
        handleUpdate(row) {
            // 获取所有图书类型
            queryBookTypes().then((res) => {
                console.log("图书类型获取成功", res);
                this.typeData = res;
            });
            this.formType = 1; // 设置表单类型为编辑
            // 将行数据填充到表单
            this.form = {
                bookid: row.bookid,
                bookname: row.bookname,
                bookauthor: row.bookauthor,
                bookprice: row.bookprice,
                booktypeid: row.booktypeid,
                bookdesc: row.bookdesc,
                isborrowed: row.isborrowed,
                bookimg: row.bookimg,
            };
            this.dialogFormVisible = true; // 显示对话框
        },

        // 借阅图书处理
        handleBorrow(row) {
            if (this.roleIsAdmin) {
                // 管理员借书，显示选择用户对话框
                this.dialogFormVisible2 = true;
                this.form2.bookid = row.bookid; // 设置图书ID

                // 获取所有用户信息
                queryUsers().then((res) => {
                    this.userData = res;
                });
            } else {
                // 普通用户借书，直接确认
                this.$confirm("您确定要借书吗?", "提示").then(() => {
                    borrowBook(this.id, row.bookid).then((res) => {
                        if (res === 1) {
                            this.$message.success("借书成功");
                            this.handleCurrentChange(this.queryParam.page);
                        } else {
                            this.$message.error("借书失败");
                        }
                        this.dialogFormVisible2 = false; // 关闭对话框
                    });
                });
            }
        },

        // 提交表单（添加或更新）
        submitForm() {
            if (this.formType === 0) {
                // 添加记录
                addBookInfo(this.form).then((res) => {
                    if (res === 1) {
                        this.$message.success("添加记录成功");
                        // 跳转到第一页
                        getCount().then((res) => {
                            this.recordTotal = res;
                            this.queryParam.page = 1;
                            this.handleCurrentChange(this.queryParam.page);
                        });
                    } else {
                        this.$message.error("添加记录失败");
                    }
                    this.dialogFormVisible = false; // 关闭对话框
                });
            } else if (this.formType === 1) {
                // 更新记录
                updateBookInfo(this.form).then((res) => {
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

        // 提交借书表单
        submitForm2() {
            borrowBook(this.form2.userid, this.form2.bookid).then((res) => {
                if (res === 1) {
                    this.$message.success("借书成功");
                    this.handleCurrentChange(this.queryParam.page);
                } else {
                    this.$message.error("借书失败");
                }
                this.dialogFormVisible2 = false; // 关闭对话框
            });
        },

        // 删除单条记录
        handleDelete(row, index) {
            this.$confirm("确定要删除该条记录吗?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBookInfo(row).then((res) => {
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
                            "该图书存在与读者的借阅信息，请尝试先删除所有的本图书借阅信息再重试"
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
                deleteBookInfos(items).then((res) => {
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
            typeData: [], // 图书类型数据
            userData: [], // 用户数据
            queryParam: {
                // 查询参数
                page: 1,
                limit: 10,
                bookname: null,
                bookauthor: null,
                booktypeid: null,
            },
            dialogFormVisible: false, // 添加/编辑对话框可见性
            dialogFormVisible2: false, // 借书对话框可见性
            formType: 0, // 表单类型 0:添加, 1:编辑
            form: {
                // 添加/编辑表单数据
                bookid: null,
                bookname: "",
                bookauthor: "",
                bookprice: 0,
                booktypeid: 1,
                bookdesc: "",
                isborrowed: 0,
                bookimg: "",
            },
            form2: {
                // 借书表单数据
                userid: 1,
                bookid: 1,
            },
            rules: {
                // 表单验证规则
                bookname: [
                    {
                        required: true,
                        message: "请输入图书名称",
                        trigger: "blur",
                    },
                ],
                bookauthor: [
                    { required: true, message: "请输入作者", trigger: "blur" },
                ],
                bookprice: [
                    { required: true, message: "请输入价格", trigger: "blur" },
                ],
                booktypeid: [
                    { required: true, message: "请选择类型", trigger: "blur" },
                ],
                bookdesc: [
                    { required: true, message: "请输入描述", trigger: "blur" },
                ],
                isborrowed: [
                    { required: true, message: "请选择状态", trigger: "blur" },
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
        // 判断当前用户是否为管理员
        roleIsAdmin() {
            if (this.roles[0] === "admin") return true;
            else return false;
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