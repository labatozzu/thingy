<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import * as api from '../api/client'

const route = useRoute()
const router = useRouter()
const item = ref(null)
const loading = ref(true)
const error = ref(null)
const saving = ref(false)
const saveError = ref(null)

const itemId = computed(() => route.params.id)

const FIT_OPTIONS = [
  { value: 'TRUE_TO_SIZE', label: 'True to size' },
  { value: 'RUNS_SMALL', label: 'Runs small' },
  { value: 'RUNS_BIG', label: 'Runs big' },
]

const VISIBILITY_OPTIONS = [
  { value: 'PRIVATE', label: 'Private' },
  { value: 'FRIENDS_ONLY', label: 'Friends only' },
  { value: 'PUBLIC', label: 'Public' },
]

const STATUS_OPTIONS = [
  { value: 'NOT_AVAILABLE', label: 'Not available' },
  { value: 'AVAILABLE', label: 'Available' },
]

async function fetchItem() {
  if (!itemId.value) return
  loading.value = true
  error.value = null
  try {
    const res = await api.get(`/api/items/${itemId.value}`)
    if (res.status === 404) {
      error.value = 'Item not found'
      item.value = null
      return
    }
    if (!res.ok) throw new Error(res.statusText)
    item.value = await res.json()
  } catch (e) {
    error.value = e.message || 'Failed to load item'
    item.value = null
  } finally {
    loading.value = false
  }
}

async function save() {
  if (!item.value) return
  saving.value = true
  saveError.value = null
  try {
    const res = await api.patch(`/api/items/${itemId.value}`, {
      body: {
        waist: item.value.waist,
        lengthIn: item.value.lengthIn,
        fitEstimate: item.value.fitEstimate,
        visibility: item.value.visibility,
        status: item.value.status,
      },
    })
    if (!res.ok) throw new Error(res.statusText)
    item.value = await res.json()
  } catch (e) {
    saveError.value = e.message || 'Failed to save'
  } finally {
    saving.value = false
  }
}

function goBack() {
  router.push('/my-items')
}

onMounted(fetchItem)
watch(itemId, fetchItem)
</script>

<template>
  <div class="item-detail">
    <a href="#" class="back-link" @click.prevent="goBack">← Back to My items</a>
    <p v-if="loading">Loading...</p>
    <p v-else-if="error" class="error">{{ error }}</p>
    <div v-else-if="item" class="detail-card">
      <div class="detail-photo">
        <img v-if="item.photoUrl" :src="item.photoUrl" :alt="item.title" loading="lazy" />
        <div v-else class="photo-placeholder">
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
            <circle cx="8.5" cy="8.5" r="1.5" />
            <polyline points="21 15 16 10 5 21" />
          </svg>
        </div>
      </div>
      <div class="detail-body">
        <h1 class="detail-title">{{ item.title }}</h1>
        <form class="detail-specs" @submit.prevent="save">
          <label class="spec-row">
            <span class="spec-label">Waist</span>
            <input
              v-model.number="item.waist"
              type="number"
              min="24"
              max="48"
              class="spec-input"
            />
            <span class="spec-unit">"</span>
          </label>
          <label class="spec-row">
            <span class="spec-label">Length</span>
            <input
              v-model.number="item.lengthIn"
              type="number"
              min="26"
              max="38"
              class="spec-input"
            />
            <span class="spec-unit">"</span>
          </label>
          <label class="spec-row">
            <span class="spec-label">Fit</span>
            <select v-model="item.fitEstimate" class="spec-select">
              <option v-for="o in FIT_OPTIONS" :key="o.value" :value="o.value">
                {{ o.label }}
              </option>
            </select>
          </label>
          <label class="spec-row">
            <span class="spec-label">Visibility</span>
            <select v-model="item.visibility" class="spec-select">
              <option v-for="o in VISIBILITY_OPTIONS" :key="o.value" :value="o.value">
                {{ o.label }}
              </option>
            </select>
          </label>
          <label class="spec-row">
            <span class="spec-label">Status</span>
            <select v-model="item.status" class="spec-select">
              <option v-for="o in STATUS_OPTIONS" :key="o.value" :value="o.value">
                {{ o.label }}
              </option>
            </select>
          </label>
          <p v-if="saveError" class="save-error">{{ saveError }}</p>
          <button type="submit" class="save-btn" :disabled="saving">
            {{ saving ? 'Saving...' : 'Save changes' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.item-detail {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}
.back-link {
  display: inline-flex;
  align-items: center;
  min-height: 44px;
  margin-bottom: 1rem;
  padding: 0.25rem 0;
  color: #16213e;
  text-decoration: none;
  font-size: 0.9rem;
}
.back-link:hover,
.back-link:active {
  text-decoration: underline;
}
.error {
  color: #c00;
}
.detail-card {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}
.detail-photo {
  aspect-ratio: 1;
  width: 100%;
  background: #f0f0f0;
  border-radius: 8px;
  overflow: hidden;
}
.detail-photo img {
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
.detail-body {
  flex: 1;
}
.detail-title {
  margin: 0 0 1rem;
  font-size: 1.5rem;
  font-weight: 600;
}
.detail-specs {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin: 0;
}
.spec-row {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 0.5rem 0.75rem;
}
.spec-label {
  font-weight: 600;
  color: #666;
  width: 100%;
  min-width: 0;
}
.spec-input {
  width: 5rem;
  min-height: 44px;
  padding: 0.5rem 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}
.spec-input:focus {
  outline: none;
  border-color: #16213e;
}
.spec-unit {
  color: #666;
}
.spec-select {
  width: 100%;
  min-height: 44px;
  padding: 0.5rem 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}
.spec-select:focus {
  outline: none;
  border-color: #16213e;
}
.save-error {
  color: #c00;
  margin: 0;
}
.save-btn {
  margin-top: 0.5rem;
  min-height: 44px;
  padding: 0.75rem 1.25rem;
  background: #16213e;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  align-self: flex-start;
}
.save-btn:hover:not(:disabled),
.save-btn:active:not(:disabled) {
  background: #1a1a2e;
}
.save-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
@media (min-width: 640px) {
  .item-detail {
    padding: 1.5rem;
  }
  .spec-label {
    width: auto;
    min-width: 5rem;
  }
  .spec-select {
    width: auto;
    min-width: 10rem;
  }
}
@media (min-width: 768px) {
  .detail-photo {
    max-width: 400px;
  }
}
</style>
