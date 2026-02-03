import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// Rewrite Set-Cookie so browser accepts them when using proxy (Path=/, no Secure, domain for localhost)
function proxyCookieRewrite(proxy) {
  proxy.on('proxyRes', (proxyRes, req, res) => {
    const setCookie = proxyRes.headers['set-cookie']
    if (setCookie) {
      const rewritten = (Array.isArray(setCookie) ? setCookie : [setCookie]).map((c) =>
        c
          .replace(/;\s*Secure/gi, '')
          .replace(/;\s*Domain=[^;]+/gi, '')
          .replace(/;\s*Path=[^;]+/gi, '; Path=/')
      )
      proxyRes.headers['set-cookie'] = rewritten
    }
  })
}

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        configure: proxyCookieRewrite,
      },
      '/login': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        configure: proxyCookieRewrite,
      },
      '/logout': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        configure: proxyCookieRewrite,
      },
    },
  },
})
