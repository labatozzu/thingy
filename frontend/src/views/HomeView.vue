<script setup>
import { ref, onMounted } from 'vue'

const message = ref('')
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const res = await fetch('/api/hello', { credentials: 'include' })
    if (!res.ok) throw new Error(res.statusText)
    const data = await res.json()
    message.value = data.message ?? 'Hello, world'
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
    <p v-if="loading">Loading...</p>
    <p v-else-if="error" class="error">Error: {{ error }}</p>
    <p v-else class="message">{{ message }}</p>
  </div>
</template>

<style scoped>
.home {
  background: #fff;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}
.error {
  color: #c00;
}
.message {
  margin: 0;
  font-size: 1.1rem;
}
</style>
