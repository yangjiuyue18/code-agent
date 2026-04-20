import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import axios from 'axios'
import VueCookies from 'vue-cookies'

// 全局配置
Vue.config.productionTip = false
// 引入Cookies
Vue.use(VueCookies)
// 全局配置Axios - 核心：相对路径，无IP和端口
Vue.prototype.$axios = axios
axios.defaults.baseURL = '/' // 相对路径，与后端同端口
axios.defaults.withCredentials = true // 保留，无需修改

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

