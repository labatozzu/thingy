<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import * as api from '../api/client'

const { t } = useI18n()
const message = ref('')
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const res = await api.get('/api/message')
    if (!res.ok) throw new Error(res.statusText)
    const data = await res.json()
    message.value = data.message ?? t('home.welcome')
  } catch (e) {
    error.value = e.message
    message.value = ''
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="home">
    <p v-if="loading">{{ t('home.loading') }}</p>
    <p v-else-if="error" class="error">{{ t('home.error', { message: error }) }}</p>
    <p v-else class="message">{{ message }}</p>
  </div>
</template>

<style scoped>
.home {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}
@media (min-width: 640px) {
  .home {
    padding: 1.5rem;
  }
}
.error {
  color: #c00;
}
.message {
  margin: 0;
  font-size: 1.1rem;
}
</style>
