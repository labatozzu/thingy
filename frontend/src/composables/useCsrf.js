export function getTokenFromCookie() {
  const m = document.cookie.match(/\bXSRF-TOKEN=([^;]+)/)
  return m ? decodeURIComponent(m[1]) : ''
}

export async function getCsrfToken() {
  const res = await fetch('/api/csrf', { credentials: 'include' })
  if (res.ok) {
    const data = await res.json()
    return data.token || getTokenFromCookie() || ''
  }
  return getTokenFromCookie()
}
