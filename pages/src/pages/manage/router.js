import Vue from 'vue'
import Router from 'vue-router'
import Userpage from './views/Userpage.vue'
import Article from './views/Article.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'article',
      component: Article
    },
    {
      path: '/userpage',
      name: 'userpage',
      component: Userpage
    },
  ]
})
