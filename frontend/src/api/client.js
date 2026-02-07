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
