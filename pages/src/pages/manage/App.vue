<template>
  <el-container id="app">
    <el-header id="userInfo">
      <el-row>
        <el-col :span="2">
          <img :src="title" style="max-height: 4rem; margin-top: 1rem" />
        </el-col>
        <el-col :span="2" :offset="20">
          <el-dropdown @command="handleCommand">
            <span class="nameTitle" type="text">{{ myName }}</span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="showUserInfo"
                >修改信息</el-dropdown-item
              >
              <el-dropdown-item command="logout">登出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="height: 100%; padding: 0">
      <el-container>
        <el-aside
          width="10rem"
          style="background-color: #545c64; min-height: 47.5rem"
        >
          <el-row>
            <el-col>
              <el-menu
                :default-active="this.$route.path"
                class="el-menu-vertical-demo"
                router
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
              >
                <el-menu-item index="/">
                  <i class="el-icon-menu"></i>
                  <span slot="title">图文浏览</span>
                </el-menu-item>

                <el-menu-item index="/userpage" v-if="type == 1">
                  <i class="el-icon-setting"></i>
                  <span slot="title">用户管理</span>
                </el-menu-item>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main style="padding: 0">
          <router-view />
        </el-main>
      </el-container>
    </el-main>
    <el-footer style="height: 5.2rem">
      <p>ArHay</p>
      <img :src="wechat" />
      <p>719510848</p>
      <img :src="phone" />
      <p>13042051232</p>
    </el-footer>

    <el-dialog
      title="修改信息"
      :visible.sync="showDialog"
      width="20%"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="form" label-width="5rem">
        <el-form-item label="姓 名">
          <el-input v-model="form.name" placeholder="输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="性 别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="邮 箱">
          <el-input v-model="form.email" placeholder="输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="密 码">
          <el-input
            type="password"
            v-model="form.psw"
            placeholder="输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="id" style="display: none">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="type" style="display: none">
          <el-input v-model="form.type"></el-input>
        </el-form-item>
      </el-form>

      <el-button type="primary" @click="updateUser(form)">确 定</el-button>
      <el-button @click="showDialog = false">取 消</el-button>
    </el-dialog>
  </el-container>
</template>

<script>
import Cookies from 'vue-js-cookie'
import wechat from '~/images/wechat.png'
import phone from '~/images/phone.png'
import mail from '~/images/mail.png'
import title from '~/images/title.png'
import { createOrUpdateUser } from '@/api/user'


export default {
  name: 'App',
  data() {

    return {
      showDialog: false,
      type: 1,
      myName: '',
      wechat: wechat,
      phone: phone,
      mail: mail,
      title: title,
      form: {
        name: '',
        sex: '',
        email: '',
        psw: '',
        type: '',
        id: ''
      }
    }
  },
  computed: {
  },
  created() {
    this.checkMyInfo()
  },
  mounted() {
    console.log(Cookies.get('userInfo'))
    this.checkMyInfo()
  },
  methods: {
    handleClose() {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.form.name = null
          this.form.sex = null
          this.form.email = null
          this.form.psw = null
          this.form.id = null
          this.form.type = null
          this.showDialog = false
          _
        }

        )
        .catch(_ => { _ })
    },
    updateUser(form) {
      console.log(form)
      this.showDialog = false
      createOrUpdateUser(form).then(response => {
        if (response.success) {
          this.$message({
            message: response.data,
            type: 'success'
          })
          Cookies.set('userInfo', form)
          this.form.name = null
          this.form.sex = null
          this.form.email = null
          this.form.psw = null
          this.form.id = null
          this.form.type = null
        }
      })
    },
    showUserInfo() {
      this.form.name = JSON.parse(Cookies.get('userInfo')).name
      this.form.sex = JSON.parse(Cookies.get('userInfo')).sex
      this.form.email = JSON.parse(Cookies.get('userInfo')).email
      //为了通过验证，默认是邮箱
      this.form.psw = JSON.parse(Cookies.get('userInfo')).email
      this.form.id = JSON.parse(Cookies.get('userInfo')).id
      this.form.type = JSON.parse(Cookies.get('userInfo')).type
      this.showDialog = true
    },
    checkMyInfo() {
      if (Cookies.get('userInfo') == null) {
        location.href = './index.html'
        this.$message({
          message: '请登录',
          type: 'warning'
        })
      } else {
        this.myName = JSON.parse(Cookies.get('userInfo')).name
        this.type = JSON.parse(Cookies.get('userInfo')).type
      }
    },
    handleCommand(command) {
      switch (command) {
        case 'logout':
          this.logout()
          break
        case 'showUserInfo':
          this.showUserInfo()
          break
      }
    },
    logout() {
      Cookies.remove('userInfo')
      location.href = './index.html'
      this.$message({
        message: '成功登出！',
        type: 'success'
      })
    }
  }
}
</script>


<style lang="less">
.el-main {
  background-color: #e7eff7;
}
#app {
  min-height: 55rem;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#userInfo {
  background: #545c64;
  min-height: 6rem /* 36/16 */;
  color: #ffd04b;
}

.nameTitle {
  justify-content: flex-end;
  font-size: 2rem /* 30/16 */;
  line-height: 5rem;
  color: #ffd04b;
}

.el-footer {
  background-color: #747f8a;
  text-align: center;
  line-height: 5rem /* 60/16 */;
}

.el-footer img {
  display: inline-block;
  width: 1.7rem /* 20/16 */;
  height: 1.7rem;
  text-align: center;
}
.el-footer p {
  display: inline-block;
  font-size: 1rem;
  color: #ffd04b;
}
</style>
