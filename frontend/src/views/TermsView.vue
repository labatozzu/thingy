<script setup>
import { useI18n } from 'vue-i18n'
const { t } = useI18n()
</script>

<template>
  <div class="terms">
    <h1>{{ t('terms.title') }}</h1>
    <p>{{ t('terms.placeholder') }}</p>
    <p><router-link to="/register">{{ t('terms.backToSignUp') }}</router-link></p>
  </div>
</template>

<style scoped>
.terms {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  max-width: 32rem;
  width: 100%;
}
.terms h1 {
  margin-top: 0;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}
.terms a {
  color: #1a1a2e;
  display: inline-block;
  padding: 0.5rem 0.25rem;
  margin: -0.5rem -0.25rem;
}
@media (min-width: 640px) {
  .terms {
    padding: 1.5rem;
  }
}
</style>
