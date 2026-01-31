<script setup>
import { ref, onMounted } from 'vue'

const message = ref('')
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const res = await fetch('/api/hello')
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
  <p v-if="loading">Loading...</p>
  <p v-else-if="error">Error: {{ error }}</p>
  <p v-else>{{ message }}</p>
</template>
