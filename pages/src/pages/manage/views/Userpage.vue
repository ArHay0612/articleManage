<template>
  <div class="UserPage">
    <div style="padding:1rem;">
      <el-button @click="showDialog(null)" type="primary" class="addButton">新增用户</el-button>
    </div>
    <div style="padding:1rem">
      <h1>用户管理</h1>
    </div>
    <el-table :data="data" border style="width: 100%;margin:0 auto;" max-height="680">
      <el-table-column prop="name" label="姓 名" width="140"></el-table-column>
      <el-table-column prop="sex" label="性 别" width="120"></el-table-column>
      <el-table-column prop="email" label="邮 箱" width="200"></el-table-column>
      <el-table-column prop="role" label="身 份" width="120"></el-table-column>
      <el-table-column label="操 作" width="200">
        <template slot-scope="scope">
          <el-button @click="showDialog(scope.row)" type="primary">编 辑</el-button>
          <el-button @click="handleDelete(scope.row)" type="danger">删 除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="20%"
      :before-close="handleClose"
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="5rem">
        <el-form-item label="姓 名" prop="name">
          <el-input v-model="form.name" placeholder="输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="性 别" prop="sex">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="身 份" prop="type">
          <el-radio v-model="form.type" label="2">普通用户</el-radio>
          <el-radio v-model="form.type" label="1">管理员</el-radio>
        </el-form-item>
        <el-form-item label="邮 箱" prop="email">
          <el-input v-model="form.email" placeholder="输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="密 码" prop="psw">
          <el-input type="password" v-model="form.psw" placeholder="输入密码"></el-input>
        </el-form-item>

        <el-form-item label="id" style="display:none">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="updateUser(form)">确 定</el-button>
      <el-button @click="handleClose">取 消</el-button>
    </el-dialog>
  </div>
</template>

<script>

import Cookies from 'vue-js-cookie'
import { findAllUser, deleteUser, createOrUpdateUser } from '@/api/user'

export default {
  name: 'UserPage',
  components: {
  },
  data() {
    // 定义验证邮箱
    let validEmail = (rule, value, callback) => {
      if (value === '' || value === null) {
        callback()
      }
      // var regEmail=/^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/ // 不能校验例如qq邮箱
      var regEmail = /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
      if (!regEmail.test(value)) {
        callback(new Error('请输入正确的邮箱地址'))
      } else {
        callback()
      }
    }
    return {
      dialogVisible: false,
      data: null,
      dialogTitle: null,
      form: {
        name: '',
        sex: '',
        email: '',
        psw: '',
        type: null,
        id: null
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择权限', trigger: 'blur' },
        ],
        psw: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, validator: validEmail, trigger: 'blur' }
        ],
      },
    }
  },
  mounted() {
    this.findAllUser()
  },
  methods: {

    showDialog(row) {
      if (row == null) {
        this.dialogTitle = '新增用户'
      } else {
        this.dialogTitle = '修改' + row.name
        this.form.name = row.name
        this.form.type = String(row.type)
        this.form.sex = row.sex
        this.form.email = row.email
        //为了通过验证，默认是邮箱
        this.form.psw = row.email
        this.form.id = row.id
      }
      this.dialogVisible = true
    },
    handleClose() {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.form.name = null
          this.form.sex = null
          this.form.email = null
          this.form.psw = null
          this.form.id = null
          this.form.type = null
          this.dialogVisible = false
          _
        })
        .catch(_ => { _ })
    },
    updateUser(form) {
      createOrUpdateUser(form).then(response => {
        if (response.success) {
          this.findAllUser()
          this.dialogVisible = false
          this.$message({
            message: response.data,
            type: 'success'
          })
          if (JSON.parse(Cookies.get('userInfo')).id == this.form.id) {
            Cookies.set('userInfo', form)
          }
          this.form.name = null
          this.form.sex = null
          this.form.email = null
          this.form.psw = null
          this.form.id = null
          this.form.type = null
        }
      })
    },
    handleDelete(row) {
      deleteUser(row.id).then(response => {
        if (response.success) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.findAllUser()
        }
      })
    },
    findAllUser() {
      findAllUser().then(response => {
        this.data = response.data
      })
    }
  }
}
</script>

<style lang="less">
.UserPage {
  text-align: center;
}
.addButton {
  float: left;
}
</style>