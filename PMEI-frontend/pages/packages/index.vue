<template>
  <div class="packages-container">
    <h2>Packages</h2>
    <div class="package-list">
      <div
        class="package-item"
        v-for="packageItem in packages"
        :key="packageItem.id"
      >
        <h3>{{ packageItem.title }}</h3>
        <p>{{ packageItem.description }}</p>
        <span>Status: {{ packageItem.status }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Hardcoded list of packages
      packages: [
        {
          id: 1,
          title: 'Package 1',
          description: 'Description for Package 1',
          status: 'Shipped',
        },
        {
          id: 2,
          title: 'Package 2',
          description: 'Description for Package 2',
          status: 'In Transit',
        },
        {
          id: 3,
          title: 'Package 3',
          description: 'Description for Package 3',
          status: 'Delivered',
        },
        {
          id: 4,
          title: 'Package 4',
          description: 'Description for Package 4',
          status: 'Pending',
        },
      ],
    }
  },
}
</script>

<style scoped>
.packages-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.package-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.package-item {
  padding: 15px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.package-item h3 {
  margin: 0 0 10px;
  font-size: 18px;
  font-weight: bold;
}

.package-item p {
  margin: 0 0 10px;
  font-size: 14px;
}

.package-item span {
  font-size: 14px;
  color: #666;
}
</style>
