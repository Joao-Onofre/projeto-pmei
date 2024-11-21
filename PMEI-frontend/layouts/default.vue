<template>
  <div class="layout-wrapper">
    <header class="header">
      <Header />
    </header>
    <main class="main-content">
      <NuxtPage />
    </main>
    <footer class="footer">
      <Footer />
    </footer>
  </div>
</template>

<script>
import Header from '~/components/Header.vue'
import Footer from '~/components/Footer.vue'

export default {
  components: {
    Header,
    Footer,
  },
}
</script>

<style scoped>
.layout-wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.header {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000; /* Ensure it stays above other content */
}

.footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  z-index: 1000; /* Ensure it stays above other content */
}

.main-content {
  flex: 1;
}
</style>
