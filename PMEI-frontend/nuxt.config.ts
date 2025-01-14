// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/academics/api'
    }
  },

  css: [
    'bootstrap/dist/css/bootstrap.css',  // Import Bootstrap CSS
    '~/assets/css/main.css'  // Add custom global CSS file
  ],

  build: {
    transpile: ['bootstrap']
  }
})