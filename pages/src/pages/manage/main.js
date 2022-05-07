import 'normalize.css' // css reset
import '@/assets/css/common.css'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store'
import cookie from 'vue-js-cookie'
// 全局挂载 VueQuillEditor
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

Vue.use(VueQuillEditor)


Vue.config.productionTip = false
Vue.use(cookie)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
