<template>
    <div class="app-container">
        <!-- 密码修改表单 -->
        <el-form
            ref="form"
            :model="form"
            :rules="passwordRules"
            label-width="100px"
        >
            <!-- 旧密码输入框 -->
            <el-form-item label="旧密码" prop="oldPassword">
                <el-input
                    v-model="form.oldPassword"
                    type="password"
                    placeholder="请输入旧密码"
                    style="width: 300px"
                />
            </el-form-item>
            <!-- 新密码输入框 -->
            <el-form-item label="新密码" prop="newPassword">
                <el-input
                    v-model="form.newPassword"
                    type="password"
                    placeholder="请输入新密码"
                    style="width: 300px"
                />
            </el-form-item>
            <!-- 确认新密码输入框 -->
            <el-form-item label="确认新密码" prop="repeat">
                <el-input
                    v-model="form.repeat"
                    type="password"
                    placeholder="请再输入一遍新密码"
                    style="width: 300px"
                />
            </el-form-item>
            <!-- 提交按钮 -->
            <el-form-item>
                <el-button
                    type="primary"
                    @click="onSubmit"
                    icon="el-icon-a-051"
                    style="font-size: 20px"
                    >更改密码</el-button
                >
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import { alterPassword } from "@/api/user";
export default {
    data() {
        // 自定义验证规则：确认新密码
        const validateRepeat = (rule, value, callback) => {
            if (value !== this.form.newPassword) {
                callback(new Error("两次输入的密码不一致!"));
            } else {
                callback();
            }
        };
        return {
            // 表单数据
            form: {
                oldPassword: "",
                newPassword: "",
                repeat: "",
            },
            // 表单验证规则
            passwordRules: {
                oldPassword: [
                    { required: true, message: "请输入旧密码", trigger: "blur" },
                ],
                newPassword: [
                    { required: true, message: "请输入新密码", trigger: "blur" },
                ],
                repeat: [
                    { required: true, message: "请再输入新密码", trigger: "blur" },
                    { trigger: "blur", validator: validateRepeat }, // 使用自定义验证器
                ],
            },
        };
    },
    methods: {
        // 提交表单
        onSubmit() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    const isadmin = this.roles[0] === "admin" ? 1 : 0;
                    console.log(isadmin);
                    // 调用修改密码接口
                    alterPassword({
                        userid: this.id,
                        username: this.name,
                        isadmin: isadmin,
                        oldPassword: this.form.oldPassword,
                        newPassword: this.form.newPassword,
                    }).then((res) => {
                        if (res === 0) this.$message.error("旧密码不正确");
                        else {
                            this.$message.success("修改成功");
                            // 清空表单
                            this.form = {
                                oldPassword: "",
                                newPassword: "",
                                repeat: "",
                            };
                        }
                    });
                } else {
                    console.log("不允许提交!");
                    return;
                }
            });
        },
    },
    computed: {
        // 使用 mapGetters 从 Vuex store 中获取用户信息
        ...mapGetters(["id", "name", "roles"]),
    },
};
</script>

<style></style>