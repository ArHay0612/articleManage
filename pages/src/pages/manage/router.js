import Vue from 'vue'
import Router from 'vue-router'
import Userpage from './views/Userpage.vue'
import Declarepage from './views/Declarepage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'declarepage',
      component: Declarepage
    },
    {
      path: '/userpage',
      name: 'userpage',
      component: Userpage
    },
  ]
})
