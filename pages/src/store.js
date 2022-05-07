import Vue from 'vue'
import Vuex from 'vuex'
import {
  getUserInfo,
  getToken
} from '@/utils/auth'

Vue.use(Vuex)

// 声明数据存储对象
const state = {
  myname: '',
  user: {
    info: getUserInfo(),
    token: getToken()
  }
}

// mutations是固定的写法，意思是改变的，要改变state的数值的方法，必须写在mutations里就可以了
const mutations = {
  SET_USER_INFO: (state, info) => {
    state.user.info = info
  }
}

// getter提供获取数据值
const getters = {
  getUserInfo(state) {
    return state.user.info
  },
}

// actions异步修改状态,actions是可以调用Mutations里的方法的
const actions = {
}

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions
})
