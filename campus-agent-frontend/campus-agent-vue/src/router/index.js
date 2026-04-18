import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Schedule from '../views/Schedule.vue'
import Remind from '../views/Remind.vue'
import Dining from '../views/Dining.vue'
import Repair from '../views/Repair.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { title: '校园生活小管家 - 首页' }
  },
  {
    path: '/schedule',
    name: 'Schedule',
    component: Schedule,
    meta: { title: '校园生活小管家 - 我的课表' }
  },
  {
    path: '/remind',
    name: 'Remind',
    component: Remind,
    meta: { title: '校园生活小管家 - 我的提醒' }
  },
  {
    path: '/dining',
    name: 'Dining',
    component: Dining,
    meta: { title: '校园生活小管家 - 食堂推荐' }
  },
  {
    path: '/repair',
    name: 'Repair',
    component: Repair,
    meta: { title: '校园生活小管家 - 报修服务' }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫：设置页面标题
router.afterEach((to) => {
  document.title = to.meta.title || '校园生活小管家'
})

export default router
