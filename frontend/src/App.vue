<script setup>
import { onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useAuth } from './composables/useAuth'

const route = useRoute()
const { user, fetchMe, logout } = useAuth()

onMounted(() => {
  fetchMe()
})

watch(() => route.path, () => {
  fetchMe()
})
</script>

<template>
  <div class="app">
    <header class="header">
      <router-link to="/" class="logo">My App</router-link>
      <nav class="nav">
        <router-link to="/">Home</router-link>
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
.main {
  flex: 1;
  padding: 1.5rem;
  max-width: 48rem;
  margin: 0 auto;
  width: 100%;
}
</style>
