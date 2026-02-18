<script setup>
import { ref, onMounted, watch, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import { useAuth } from './composables/useAuth'

const route = useRoute()
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
      <router-link to="/" class="logo">My App</router-link>
      <nav class="nav">
        <div class="hamburger-wrapper" @click.stop>
          <button
            type="button"
            class="hamburger"
            :aria-expanded="menuOpen"
            aria-label="Menu"
            @click="toggleMenu"
          >
            <span></span>
            <span></span>
            <span></span>
          </button>
          <div v-if="menuOpen" class="menu-dropdown">
            <router-link to="/" @click="closeMenu">Home</router-link>
            <router-link v-if="user" to="/my-items" @click="closeMenu">My items</router-link>
          </div>
        </div>
        <template v-if="user">
          <span class="user">Logged in as {{ user.email }}</span>
          <button type="button" class="btn" @click="logout">Logout</button>
        </template>
        <router-link v-else to="/login">Login</router-link>
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
  padding: 0.75rem 1.5rem;
  background: #1a1a2e;
  color: #eee;
}
.logo {
  font-weight: 700;
  color: #eee;
  text-decoration: none;
}
.logo:hover {
  color: #fff;
}
.nav {
  display: flex;
  align-items: center;
  gap: 1rem;
}
.nav a {
  color: #b8b8d0;
  text-decoration: none;
}
.nav a:hover,
.nav a.router-link-active {
  color: #fff;
}
.user {
  color: #b8b8d0;
  font-size: 0.9rem;
}
.btn {
  padding: 0.4rem 0.75rem;
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
  gap: 5px;
  width: 32px;
  height: 32px;
  padding: 0;
  background: none;
  border: none;
  cursor: pointer;
  color: #eee;
}
.hamburger span {
  display: block;
  width: 100%;
  height: 2px;
  background: currentColor;
}
.hamburger:hover {
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
.menu-dropdown a {
  display: block;
  padding: 0.5rem 1rem;
  color: #b8b8d0;
  text-decoration: none;
}
.menu-dropdown a:hover {
  background: #1a1a2e;
  color: #fff;
}
.main {
  flex: 1;
  padding: 1.5rem;
  max-width: 48rem;
  margin: 0 auto;
  width: 100%;
}
</style>
