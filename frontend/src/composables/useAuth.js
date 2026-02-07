import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { getCsrfToken } from './useCsrf'

export const user = ref(null)

export async function fetchMe() {
  try {
    const res = await fetch('/api/me', { credentials: 'include' })
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
