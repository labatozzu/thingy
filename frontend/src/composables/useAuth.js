import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { getCsrfToken } from './useCsrf'

const user = ref(null)

export function useAuth() {
  const router = useRouter()

  async function fetchMe() {
    const res = await fetch('/api/me', { credentials: 'include' })
    if (res.ok) {
      user.value = await res.json()
    } else {
      user.value = null
    }
  }

  async function logout() {
    const token = await getCsrfToken()
    await fetch('/logout', {
      method: 'POST',
      credentials: 'include',
      headers: { 'X-XSRF-TOKEN': token },
    })
    user.value = null
    router.push('/login')
  }

  return { user, fetchMe, logout }
}
