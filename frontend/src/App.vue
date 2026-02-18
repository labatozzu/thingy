<script setup>
import { ref, onMounted, watch, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useAuth } from './composables/useAuth'
import { useLocale } from './composables/useLocale'

const route = useRoute()
const { t } = useI18n()
const { locale, setLocale } = useLocale()
const { user, fetchMe, logout } = useAuth()
const menuOpen = ref(false)

function toggleMenu() {
  menuOpen.value = !menuOpen.value
}

function closeMenu() {
  menuOpen.value = false
}

onMounted(() => {
  fetchMe()
  document.addEventListener('click', closeMenu)
})

onUnmounted(() => {
  document.removeEventListener('click', closeMenu)
})

watch(() => route.path, () => {
  fetchMe()
  closeMenu()
})
</script>

<template>
  <div class="app">
    <header class="header">
      <router-link to="/" class="logo">{{ t('app.name') }}</router-link>
      <nav class="nav">
        <div class="lang-switcher">
          <button
            type="button"
            class="lang-btn"
            :class="{ active: locale === 'en' }"
            @click="setLocale('en')"
          >
            EN
          </button>
          <button
            type="button"
            class="lang-btn"
            :class="{ active: locale === 'fi' }"
            @click="setLocale('fi')"
          >
            FI
          </button>
        </div>
        <div class="hamburger-wrapper" @click.stop>
          <button
            type="button"
            class="hamburger"
            :aria-expanded="menuOpen"
            :aria-label="t('app.menu')"
            @click="toggleMenu"
          >
            <span></span>
            <span></span>
            <span></span>
          </button>
          <div v-if="menuOpen" class="menu-dropdown">
            <router-link to="/" @click="closeMenu">{{ t('app.home') }}</router-link>
            <router-link v-if="user" to="/my-items" @click="closeMenu">{{ t('app.myItems') }}</router-link>
          </div>
        </div>
        <template v-if="user">
          <span class="user">{{ t('app.loggedInAs', { email: user.email }) }}</span>
          <button type="button" class="btn" @click="logout">{{ t('app.logout') }}</button>
        </template>
        <router-link v-else to="/login" class="nav-link-btn">{{ t('app.login') }}</router-link>
      </nav>
    </header>
    <main class="main">
      <router-view />
    </main>
  </div>
</template>

<style>
* {
  box-sizing: border-box;
}
body {
  margin: 0;
  font-family: system-ui, -apple-system, sans-serif;
  background: #f5f5f5;
  -webkit-tap-highlight-color: transparent;
}
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  padding-left: max(1rem, env(safe-area-inset-left));
  padding-right: max(1rem, env(safe-area-inset-right));
  padding-top: max(0.75rem, env(safe-area-inset-top));
  background: #1a1a2e;
  color: #eee;
}
.logo {
  font-weight: 700;
  color: #eee;
  text-decoration: none;
  font-size: 1.5rem;
}
.logo:hover,
.logo:active {
  color: #fff;
}
.nav {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.lang-switcher {
  display: flex;
  gap: 0;
}
.lang-btn {
  min-height: 44px;
  min-width: 36px;
  padding: 0 0.5rem;
  background: transparent;
  border: none;
  color: #b8b8d0;
  font-size: 0.85rem;
  cursor: pointer;
}
.lang-btn:hover,
.lang-btn.active {
  color: #fff;
}
.nav a {
  color: #b8b8d0;
  text-decoration: none;
}
.nav a:hover,
.nav a:active,
.nav a.router-link-active {
  color: #fff;
}
.nav-link-btn {
  display: flex;
  align-items: center;
  min-height: 44px;
  padding: 0 0.5rem;
}
.user {
  color: #b8b8d0;
  font-size: 0.9rem;
  display: none;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 8rem;
}
.btn {
  min-height: 44px;
  min-width: 44px;
  padding: 0.5rem 0.75rem;
  background: #16213e;
  color: #eee;
  border: 1px solid #0f3460;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}
.btn:hover {
  background: #0f3460;
  color: #fff;
}
.hamburger-wrapper {
  position: relative;
}
.hamburger {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 5px;
  min-width: 44px;
  min-height: 44px;
  padding: 10px;
  background: none;
  border: none;
  cursor: pointer;
  color: #eee;
}
.hamburger span {
  display: block;
  width: 22px;
  height: 2px;
  background: currentColor;
}
.hamburger:hover,
.hamburger:active {
  color: #fff;
}
.menu-dropdown a {
  display: block;
  min-height: 44px;
  padding: 0.75rem 1rem;
  color: #b8b8d0;
  text-decoration: none;
}
.menu-dropdown a:hover,
.menu-dropdown a:active {
  background: #1a1a2e;
  color: #fff;
}
.menu-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  margin-top: 0.5rem;
  min-width: 10rem;
  padding: 0.5rem 0;
  background: #16213e;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}
.main {
  flex: 1;
  padding: 1rem;
  padding-left: max(1rem, env(safe-area-inset-left));
  padding-right: max(1rem, env(safe-area-inset-right));
  padding-bottom: max(1rem, env(safe-area-inset-bottom));
  max-width: 48rem;
  margin: 0 auto;
  width: 100%;
}
@media (min-width: 640px) {
  .header {
    padding: 0.75rem 1.5rem;
    padding-left: max(1.5rem, env(safe-area-inset-left));
    padding-right: max(1.5rem, env(safe-area-inset-right));
  }
  .user {
    display: inline;
    max-width: 12rem;
  }
  .main {
    padding: 1.5rem;
  }
}
@media (min-width: 768px) {
  .user {
    max-width: none;
  }
}
</style>
