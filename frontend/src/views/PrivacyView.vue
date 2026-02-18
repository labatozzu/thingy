<script setup>
import { useI18n } from 'vue-i18n'
const { t } = useI18n()
</script>

<template>
  <div class="privacy">
    <h1>{{ t('privacy.title') }}</h1>
    <p>{{ t('privacy.placeholder') }}</p>
    <p><router-link to="/register">{{ t('privacy.backToSignUp') }}</router-link></p>
  </div>
</template>

<style scoped>
.privacy {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  max-width: 32rem;
  width: 100%;
}
.privacy h1 {
  margin-top: 0;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}
.privacy a {
  color: #1a1a2e;
  display: inline-block;
  padding: 0.5rem 0.25rem;
  margin: -0.5rem -0.25rem;
}
@media (min-width: 640px) {
  .privacy {
    padding: 1.5rem;
  }
}
</style>
