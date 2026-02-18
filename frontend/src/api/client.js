import { getCsrfToken } from '../composables/useCsrf'

const credentials = 'include'

export async function get(path) {
  return fetch(path, { credentials })
}

export async function post(path, { body = null, headers = {} } = {}) {
  const token = await getCsrfToken()
  return fetch(path, {
    method: 'POST',
    credentials,
    headers: {
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
      ...headers,
      'X-XSRF-TOKEN': token,
    },
    ...(body != null && { body: typeof body === 'string' ? body : JSON.stringify(body) }),
  })
}
