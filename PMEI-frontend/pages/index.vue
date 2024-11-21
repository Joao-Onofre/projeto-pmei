<template>
  <div class="dashboard">
    <div
      class="box"
      v-for="item in boxes"
      :key="item.id"
      :class="{ hoverable: true }"
      @click="navigate(item.link)"
    >
      <h3>{{ item.title }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boxes: [
        { id: 1, title: 'Packages', link: '/packages' },
        { id: 2, title: 'Orders', link: '/orders' },
        { id: 3, title: 'Alerts', link: '/alerts' },
        { id: 4, title: 'Sensors', link: '/sensors' },
      ],
    }
  },
  methods: {
    navigate(link) {
      this.$router.push(link)
    },
  },
}
</script>

<style scoped>
/* Center the dashboard */
.dashboard {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
  min-height: 100vh;
  background-color: #f9f9f9;
}

/* Style individual boxes */
.box {
  width: 150px;
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
}

/* Hover effect for boxes */
.box:hover {
  background-color: #e6e6e6;
  transform: translateY(-5px);
}
</style>
