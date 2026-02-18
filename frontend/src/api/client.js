import { getCsrfToken } from '../composables/useCsrf'

const credentials = 'include'

function defaultHeaders() {
  const locale = typeof localStorage !== 'undefined' ? localStorage.getItem('locale') || 'en' : 'en'
  return { 'Accept-Language': locale }
}

export async function get(path) {
  return fetch(path, {
    credentials,
    headers: defaultHeaders(),
  })
}

export async function post(path, { body = null, headers = {} } = {}) {
  const token = await getCsrfToken()
  return fetch(path, {
    method: 'POST',
    credentials,
    headers: {
      ...defaultHeaders(),
      ...headers,
      'X-XSRF-TOKEN': token,
    },
    ...(body != null && { body }),
  })
}

export async function patch(path, { body = null, headers = {} } = {}) {
  const token = await getCsrfToken()
  return fetch(path, {
    method: 'PATCH',
    credentials,
    headers: {
      'Content-Type': 'application/json',
      ...defaultHeaders(),
      ...headers,
      'X-XSRF-TOKEN': token,
    },
    ...(body != null && { body: typeof body === 'string' ? body : JSON.stringify(body) }),
  })
}
