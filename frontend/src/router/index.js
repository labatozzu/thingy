import { createRouter, createWebHistory } from 'vue-router'
import { user, fetchMe } from '../composables/useAuth'

const routes = [
  { path: '/', name: 'home', component: () => import('../views/HomeView.vue') },
  { path: '/my-items', name: 'my-items', component: () => import('../views/MyItemsView.vue') },
  { path: '/login', name: 'login', component: () => import('../views/LoginView.vue') },
  { path: '/register', name: 'register', component: () => import('../views/RegisterView.vue') },
  { path: '/terms', name: 'terms', component: () => import('../views/TermsView.vue') },
  { path: '/privacy', name: 'privacy', component: () => import('../views/PrivacyView.vue') },
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
    if ((to.path === '/login' || to.path === '/register') && user.value) {
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
