<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <NuxtLink class="navbar-brand" to="/">Home</NuxtLink>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/packages">Packages</NuxtLink>
          </li>
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/orders">Orders</NuxtLink>
          </li>
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/alerts">Alerts</NuxtLink>
          </li>
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/sensors">Sensors</NuxtLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Header',
}
</script>

<style scoped>
/* Custom header styles */
.navbar {
  margin-bottom: 20px; /* Space between the navbar and the content */
}
</style>
