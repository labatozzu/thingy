import { createRouter, createWebHistory } from 'vue-router'
import { user, fetchMe } from '../composables/useAuth'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'

const routes = [
  { path: '/', name: 'home', component: HomeView },
  { path: '/login', name: 'login', component: LoginView },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach(async (to, _from, next) => {
  try {
    if (user.value === null) {
      await fetchMe()
    }
    if (to.path === '/login' && user.value) {
      next('/')
    } else if (to.path === '/' && !user.value) {
      next('/login')
    } else {
      next()
    }
  } catch {
    next('/login')
  }
})
