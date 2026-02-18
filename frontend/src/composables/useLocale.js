import { useI18n } from 'vue-i18n'

const LOCALE_KEY = 'locale'

export function useLocale() {
  const { locale } = useI18n()

  function setLocale(lang) {
    locale.value = lang
    localStorage.setItem(LOCALE_KEY, lang)
  }

  function getStoredLocale() {
    return localStorage.getItem(LOCALE_KEY) || 'en'
  }

  return { locale, setLocale, getStoredLocale }
}
