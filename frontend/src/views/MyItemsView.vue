<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import * as api from '../api/client'

const { t } = useI18n()
const items = ref([])
const loading = ref(true)
const error = ref(null)

onMounted(async () => {
  try {
    const res = await api.get('/api/items')
    if (!res.ok) throw new Error(res.statusText)
    const data = await res.json()
    items.value = data
  } catch (e) {
    error.value = t('items.errorLoad')
    items.value = []
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="my-items">
    <h1>{{ t('items.title') }}</h1>
    <p v-if="loading">{{ t('items.loading') }}</p>
    <p v-else-if="error" class="error">{{ error }}</p>
    <p v-else-if="items.length === 0" class="empty">{{ t('items.empty') }}</p>
    <div v-else class="card-grid">
      <router-link
        v-for="item in items"
        :key="item.id"
        :to="`/items/${item.id}`"
        class="card"
      >
        <div class="card-photo">
          <img v-if="item.photoUrl" :src="item.photoUrl" :alt="item.title" loading="lazy" />
          <div v-else class="photo-placeholder">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
              <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
              <circle cx="8.5" cy="8.5" r="1.5" />
              <polyline points="21 15 16 10 5 21" />
            </svg>
          </div>
        </div>
        <h3 class="card-title">{{ item.title }}</h3>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
.my-items {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}
.my-items h1 {
  margin-top: 0;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}
.error {
  color: #c00;
}
.empty {
  color: #666;
}
.card-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}
.card {
  display: block;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background: #fff;
  overflow: hidden;
  text-decoration: none;
  color: inherit;
  cursor: pointer;
  transition: box-shadow 0.15s ease;
  min-height: 44px;
}
.card:hover,
.card:active {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
}
@media (min-width: 640px) {
  .my-items {
    padding: 1.5rem;
  }
  .my-items h1 {
    margin-bottom: 1.25rem;
  }
  .card-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
@media (min-width: 1024px) {
  .card-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
.card-photo {
  aspect-ratio: 1;
  background: #f0f0f0;
  overflow: hidden;
}
.card-photo img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.photo-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #b8b8d0;
}
.card-title {
  margin: 0;
  padding: 1rem;
  font-size: 1rem;
  font-weight: 600;
}
</style>
