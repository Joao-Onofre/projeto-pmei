<template>
  <div class="dashboard">
    <div
      class="box"
      v-for="item in boxes"
      :key="item.id"
      :class="{ hoverable: true }"
      @click="navigate(item.link)"
    >
      <img :src="`/images/${item.icon}`" alt="Icon" class="box-icon" />
      <h3>{{ item.title }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boxes: [
        {
          id: 1,
          title: 'Packages',
          link: '/packages',
          icon: 'packages-icon.png',
        },
        { id: 2, title: 'Orders', link: '/orders', icon: 'orders-icon.png' },
        { id: 3, title: 'Alerts', link: '/alerts', icon: 'alerts-icon.png' },
        { id: 4, title: 'Sensors', link: '/sensors', icon: 'sensors-icon.png' },
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
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 2x2 grid */
  gap: 20px;
  padding: 20px;
  min-height: 100vh;
  background-color: #f9f9f9;
}

/* Style individual boxes */
.box {
  display: flex;
  flex-direction: column; /* Stack the icon and title vertically */
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
  padding: 20px;
}

/* Hover effect for boxes */
.box:hover {
  background-color: #e6e6e6;
  transform: translateY(-5px);
}

/* Style the icons inside the boxes */
.box-icon {
  width: 40px;
  height: 40px;
  margin-bottom: 10px; /* Space between icon and text */
}
</style>
