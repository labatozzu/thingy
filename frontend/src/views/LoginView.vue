<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCsrfToken } from '../composables/useCsrf'
import { useAuth } from '../composables/useAuth'
import * as api from '../api/client'

const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)
const csrfToken = ref('')
const router = useRouter()
const { fetchMe } = useAuth()

async function submit() {
  error.value = ''
  loading.value = true
  try {
    const token = csrfToken.value || (await getCsrfToken())
    csrfToken.value = token
    const body = new URLSearchParams({ username: email.value, password: password.value, _csrf: token })
    const res = await api.post('/login', {
      body: body.toString(),
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    })
    if (res.ok) {
      await fetchMe()
      router.push('/')
    } else {
      error.value = 'Invalid email or password'
    }
  } catch (e) {
    error.value = e.message || 'Login failed'
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  csrfToken.value = await getCsrfToken()
})
</script>

<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="submit">
      <div class="field">
        <label for="email">Email</label>
        <input id="email" v-model="email" type="email" required autocomplete="email" />
      </div>
      <div class="field">
        <label for="password">Password</label>
        <input id="password" v-model="password" type="password" required autocomplete="current-password" />
      </div>
      <p v-if="error" class="error">{{ error }}</p>
      <button type="submit" :disabled="loading">{{ loading ? 'Signing in...' : 'Sign in' }}</button>
    </form>
    <p><router-link to="/">Back to home</router-link> · <router-link to="/register">Sign up</router-link></p>
  </div>
</template>

<style scoped>
.login {
  background: #fff;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  max-width: 24rem;
}
.login h1 {
  margin-top: 0;
  margin-bottom: 1.25rem;
  font-size: 1.5rem;
}
.field {
  margin-bottom: 1rem;
}
.field label {
  display: block;
  margin-bottom: 0.25rem;
  font-weight: 500;
  color: #333;
}
.field input {
  width: 100%;
  padding: 0.5rem 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}
.login button[type="submit"] {
  margin-top: 0.5rem;
  padding: 0.5rem 1rem;
  background: #1a1a2e;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}
.login button[type="submit"]:hover:not(:disabled) {
  background: #16213e;
}
.login button[type="submit"]:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
.error {
  color: #c00;
  margin: 0.5rem 0 0;
}
.login a {
  color: #1a1a2e;
  margin-top: 1rem;
  display: inline-block;
}
</style>
