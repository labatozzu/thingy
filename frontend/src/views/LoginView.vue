<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { getCsrfToken } from '../composables/useCsrf'
import { useAuth } from '../composables/useAuth'
import * as api from '../api/client'

const { t } = useI18n()
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
      error.value = t('login.error')
    }
  } catch (e) {
    error.value = t('login.errorGeneric')
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
    <h1>{{ t('login.title') }}</h1>
    <form @submit.prevent="submit">
      <div class="field">
        <label for="email">{{ t('login.email') }}</label>
        <input id="email" v-model="email" type="email" required autocomplete="email" />
      </div>
      <div class="field">
        <label for="password">{{ t('login.password') }}</label>
        <input id="password" v-model="password" type="password" required autocomplete="current-password" />
      </div>
      <p v-if="error" class="error">{{ error }}</p>
      <button type="submit" :disabled="loading">{{ loading ? t('login.submitting') : t('login.submit') }}</button>
    </form>
    <p><router-link to="/">{{ t('login.backToHome') }}</router-link> · <router-link to="/register">{{ t('login.signUp') }}</router-link></p>
  </div>
</template>

<style scoped>
.login {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  max-width: 24rem;
  width: 100%;
}
.login h1 {
  margin-top: 0;
  margin-bottom: 1rem;
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
  min-height: 44px;
  padding: 0.5rem 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}
.login button[type="submit"] {
  margin-top: 0.5rem;
  min-height: 44px;
  padding: 0.75rem 1rem;
  width: 100%;
  background: #1a1a2e;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}
.login button[type="submit"]:hover:not(:disabled),
.login button[type="submit"]:active:not(:disabled) {
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
.login > p:last-of-type {
  margin-top: 1rem;
}
.login a {
  color: #1a1a2e;
  display: inline-block;
  padding: 0.5rem 0.25rem;
  margin: -0.5rem -0.25rem;
}
@media (min-width: 640px) {
  .login {
    padding: 1.5rem;
  }
  .login h1 {
    margin-bottom: 1.25rem;
  }
  .login button[type="submit"] {
    width: auto;
  }
}
</style>
