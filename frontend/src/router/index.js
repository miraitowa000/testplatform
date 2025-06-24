import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue'),
    meta: { title: '注册账号' }
  },
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { title: '项目总览' }
  },
  {
    path: '/projects',
    name: 'Projects',
    component: () => import('../views/Projects.vue'),
    meta: { title: '项目管理' }
  },
  {
    path: '/api-testing',
    name: 'ApiTesting',
    component: () => import('../views/ApiTesting.vue'),
    meta: { title: '接口测试' }
  },
  {
    path: '/defects',
    name: 'Defects',
    component: () => import('../views/Defects.vue'),
    meta: { title: '缺陷管理' }
  }
]

const router = createRouter({
  history: createWebHistory('/'),
  routes
})

// 全局路由守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

export default router