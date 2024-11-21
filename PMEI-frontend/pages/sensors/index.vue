<template>
  <div class="sensors-container">
    <h2>Sensors</h2>
    <div class="sensor-list">
      <div class="sensor-item" v-for="sensor in sensors" :key="sensor.id">
        <h3>{{ sensor.title }}</h3>
        <p>{{ sensor.type }}</p>
        <span>Status: {{ sensor.status }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Hardcoded list of sensors
      sensors: [
        { id: 1, title: 'Sensor 1', type: 'Temperature', status: 'Active' },
        { id: 2, title: 'Sensor 2', type: 'Pressure', status: 'Inactive' },
        { id: 3, title: 'Sensor 3', type: 'Humidity', status: 'Active' },
        { id: 4, title: 'Sensor 4', type: 'Motion', status: 'Pending' },
      ],
    }
  },
}
</script>

<style scoped>
.sensors-container {
  padding: 20px;
  background-color: #f9f9f9;
}

.sensor-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.sensor-item {
  padding: 15px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.sensor-item h3 {
  margin: 0 0 10px;
  font-size: 18px;
  font-weight: bold;
}

.sensor-item p {
  margin: 0 0 10px;
  font-size: 14px;
}

.sensor-item span {
  font-size: 14px;
  color: #666;
}
</style>
