import { createApp } from 'vue'
import { createI18n } from 'vue-i18n'
import App from './App.vue'
import { router } from './router'
import en from './locales/en.json'
import fi from './locales/fi.json'

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('locale') || 'en',
  fallbackLocale: 'en',
  messages: { en, fi },
})

createApp(App).use(i18n).use(router).mount('#app')
