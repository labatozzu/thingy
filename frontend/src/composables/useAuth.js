import { ref } from 'vue'
import { useRouter } from 'vue-router'
import * as api from '../api/client'

export const user = ref(null)

export async function fetchMe() {
  try {
    const res = await api.get('/api/me')
    if (res.ok) {
      const data = await res.json()
      user.value = data
    } else {
      user.value = null
    }
  } catch {
    user.value = null
  }
}

export function useAuth() {
  const router = useRouter()

  async function logout() {
    await api.post('/logout', {})
    user.value = null
    router.push('/login')
  }

  return { user, fetchMe, logout }
}
