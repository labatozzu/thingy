<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { getCsrfToken } from '../composables/useCsrf'
import * as api from '../api/client'

const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const termsAccepted = ref(false)
const showPassword = ref(false)
const error = ref('')
const loading = ref(false)
const csrfToken = ref('')
const router = useRouter()

const canSubmit = computed(() => termsAccepted.value && !loading.value)

async function submit() {
  error.value = ''
  if (password.value !== confirmPassword.value) {
    error.value = 'Passwords don\'t match'
    return
  }
  loading.value = true
  try {
    const token = csrfToken.value || (await getCsrfToken())
    csrfToken.value = token
    const res = await api.post('/api/register', {
      body: JSON.stringify({ email: email.value, password: password.value }),
      headers: { 'Content-Type': 'application/json' },
    })
    if (res.ok) {
      router.push('/login')
    } else if (res.status === 409) {
      error.value = 'Email already in use'
    } else {
      error.value = 'Registration failed'
    }
  } catch (e) {
    error.value = e.message || 'Registration failed'
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  csrfToken.value = await getCsrfToken()
})
</script>

<template>
  <div class="register">
    <h1>Sign up</h1>
    <form @submit.prevent="submit">
      <div class="field">
        <label for="reg-email">Email</label>
        <input id="reg-email" v-model="email" type="email" required autocomplete="email" />
      </div>
      <div class="field">
        <label for="reg-password">Password</label>
        <div class="input-with-toggle">
          <input
            id="reg-password"
            v-model="password"
            :type="showPassword ? 'text' : 'password'"
            required
            autocomplete="new-password"
          />
          <button
            type="button"
            class="toggle-password"
            :aria-label="showPassword ? 'Hide password' : 'Show password'"
            @click="showPassword = !showPassword"
          >
            <svg v-if="showPassword" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24" />
              <line x1="1" y1="1" x2="23" y2="23" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
              <circle cx="12" cy="12" r="3" />
            </svg>
          </button>
        </div>
      </div>
      <div class="field">
        <label for="reg-confirm">Confirm password</label>
        <div class="input-with-toggle">
          <input
            id="reg-confirm"
            v-model="confirmPassword"
            :type="showPassword ? 'text' : 'password'"
            required
            autocomplete="new-password"
          />
          <button
            type="button"
            class="toggle-password"
            :aria-label="showPassword ? 'Hide password' : 'Show password'"
            @click="showPassword = !showPassword"
          >
            <svg v-if="showPassword" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24" />
              <line x1="1" y1="1" x2="23" y2="23" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
              <circle cx="12" cy="12" r="3" />
            </svg>
          </button>
        </div>
      </div>
      <div class="field terms">
        <label class="checkbox-label">
          <input v-model="termsAccepted" type="checkbox" required />
          <span>I agree to the <router-link to="/terms">Terms of Service</router-link> and <router-link to="/privacy">Privacy Policy</router-link></span>
        </label>
      </div>
      <p v-if="error" class="error">{{ error }}</p>
      <button type="submit" :disabled="!canSubmit">{{ loading ? 'Creating account...' : 'Sign up' }}</button>
    </form>
    <p><router-link to="/login">Already have an account? Sign in</router-link></p>
  </div>
</template>

<style scoped>
.register {
  background: #fff;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  max-width: 24rem;
}
.register h1 {
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
.field input[type="email"],
.field input[type="password"],
.field input[type="text"] {
  width: 100%;
  padding: 0.5rem 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}
.input-with-toggle {
  position: relative;
  display: flex;
}
.input-with-toggle input {
  flex: 1;
  padding-right: 2.5rem;
}
.toggle-password {
  position: absolute;
  right: 0.5rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.25rem;
  color: #666;
}
.toggle-password:hover {
  color: #333;
}
.field.terms .checkbox-label {
  display: flex;
  align-items: flex-start;
  gap: 0.5rem;
  font-weight: 400;
  cursor: pointer;
}
.field.terms input[type="checkbox"] {
  margin-top: 0.25rem;
  flex-shrink: 0;
}
.field.terms a {
  color: #1a1a2e;
  text-decoration: underline;
}
.register button[type="submit"] {
  margin-top: 0.5rem;
  padding: 0.5rem 1rem;
  background: #1a1a2e;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}
.register button[type="submit"]:hover:not(:disabled) {
  background: #16213e;
}
.register button[type="submit"]:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
.error {
  color: #c00;
  margin: 0.5rem 0 0;
}
.register > p:last-child {
  margin-top: 1rem;
}
.register a {
  color: #1a1a2e;
  display: inline-block;
}
</style>
