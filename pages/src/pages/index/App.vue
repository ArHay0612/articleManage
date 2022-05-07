<template>
  <el-container id="app">
    <el-main>
      <div id="loginDialog">
        <img :src="title" id="title" />

        <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
          auto-complete="on"
          label-position="left"
        >
          <el-form-item prop="username">
            <span class="svg-container"></span>
            <el-input
              v-model="loginForm.username"
              placeholder="邮箱"
              name="username"
              type="text"
              auto-complete="on"
            />
          </el-form-item>

          <el-form-item prop="password">
            <span class="svg-container"></span>
            <el-input
              :type="passwordType"
              v-model="loginForm.password"
              placeholder="密码"
              name="password"
              auto-complete="on"
              @keyup.enter.native="handleLogin"
            />
            <span class="show-pwd" @click="showPwd"></span>
          </el-form-item>

          <el-button
            :loading="loading"
            type="primary"
            style="width:100%;margin-bottom:30px;"
            @click.native.prevent="handleLogin"
          >登录</el-button>
        </el-form>
        <el-button :loading="loading" type="success" style="width:100%;" @click="showDialog">注册</el-button>
      </div>

      <el-dialog title="注册" :visible.sync="dialogVisible" width="20%" :before-close="handleClose">
        <el-form ref="form" :rules="rules" :model="form" label-width="5rem">
          <el-form-item label="姓 名" prop="name">
            <el-input v-model="form.name" placeholder="输入姓名"></el-input>
          </el-form-item>
          <el-form-item label="性 别" prop="sex">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
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
    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script>
import Cookies from 'vue-js-cookie'
import wechat from '~/images/wechat.png'
import phone from '~/images/phone.png'
import mail from '~/images/mail.png'
import title from '~/images/title.png'
import { loginByEmail } from '@/api/login'
import { createOrUpdateUser } from '@/api/user'


export default {
  name: 'App',
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
    const validateUsername = (rule, value, callback) => {
      if (value.trim().length < 1) {
        callback(new Error('邮箱必填'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能低于6个字符长度'))
      } else {
        callback()
      }
    }
    return {
      form: {
        name: '',
        sex: '',
        email: '',
        psw: '',
        type: 2,
        id: null
      },
      dialogVisible: false,
      wechat: wechat,
      phone: phone,
      mail: mail,
      title: title,
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
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
      passwordType: 'password',
      loading: false,
    }
  },
  computed: {
  },
  created() {
  },
  mounted() {
    if (Cookies.get('userInfo')) {
      location.href = './manage.html'
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.form.name = null
      this.form.sex = null
      this.form.email = null
      this.form.psw = null
      this.form.id = null
      this.form.type = 2
    },
    showDialog() {
      this.dialogVisible = true
    },
    updateUser(form) {
      createOrUpdateUser(form).then(response => {
        if (response.success) {
          this.dialogVisible = false
          this.$message({
            message: '注册成功，请登录',
            type: 'success'
          })
          this.form.name = null
          this.form.sex = null
          this.form.email = null
          this.form.psw = null
          this.form.id = null
          this.form.type = 2
        }
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          loginByEmail(this.loginForm.username, this.loginForm.password).then(response => {

            if (response.success) {
              this.$message({
                message: '欢迎，' + response.data.name,
                type: 'success'
              })
              Cookies.set('userInfo', response.data)
              Cookies.set('taxpayername', response.data.taxpayername)
              location.href = './manage.html'

            }
          })
        } else {
          this.$message({
            message: '输入有误',
            type: 'warning'
          })
        }
      })
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
  }
}
</script>


<style lang="less">
#app {
  min-height: 55rem;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.el-main {
  display: flex;
  align-items: center;
  background-color: #e7eff7;
}
#title {
  width: 100%;
  padding-bottom: 2rem;
}
#loginDialog {
  background-color: #747f8a;
  border-radius: 10%;
  padding: 2rem 3rem;
  margin: 0 auto;
  width: 20%;
}
#userInfo {
  background: #545c64;
  min-height: 6rem /* 36/16 */;
  color: #ffd04b;
  .el-button {
    justify-content: flex-end;
    font-size: 1rem /* 30/16 */;
    line-height: 4rem;
    color: #ffd04b;
  }
}
.el-footer {
  background-color: #e7eff7;
  color: #000;
  text-align: center;
  line-height: 5rem /* 60/16 */;
}
.footerImg {
  width: 1.7rem /* 20/16 */;
  height: 1.7rem /* 20/16 */;
}
</style>
