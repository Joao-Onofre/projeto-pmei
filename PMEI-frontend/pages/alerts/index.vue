<template>
  <div class="alerts-container">
    <h2>Alerts</h2>
    <div class="alert-list">
      <div class="alert-item" v-for="alert in alerts" :key="alert.id">
        <h3>{{ alert.title }}</h3>
        <p>{{ alert.message }}</p>
        <span>Status: {{ alert.status }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Hardcoded list of alerts
      alerts: [
        {
          id: 1,
          title: 'Alert 1',
          message: 'This is the message for Alert 1',
          status: 'Active',
        },
        {
          id: 2,
          title: 'Alert 2',
          message: 'This is the message for Alert 2',
          status: 'Resolved',
        },
        {
          id: 3,
          title: 'Alert 3',
          message: 'This is the message for Alert 3',
          status: 'Pending',
        },
        {
          id: 4,
          title: 'Alert 4',
          message: 'This is the message for Alert 4',
          status: 'Active',
        },
      ],
    }
  },
}
</script>

<style scoped>
.alerts-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.alert-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.alert-item {
  padding: 15px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.alert-item h3 {
  margin: 0 0 10px;
  font-size: 18px;
  font-weight: bold;
}

.alert-item p {
  margin: 0 0 10px;
  font-size: 14px;
}

.alert-item span {
  font-size: 14px;
  color: #666;
}
</style>
