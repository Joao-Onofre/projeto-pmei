<template>
  <div class="wrapper">
    <!-- Search Bar -->
    <div class="search-container">
      <input type="text" v-model="searchQuery" class="search-bar" placeholder="Search by Sensor ID, Type or Status" />
    </div>

    <!-- Sensor Table -->
    <table class="table">
      <thead>
        <tr>
          <th>Sensor ID</th>
          <th>Type</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <!-- Display filtered sensors -->
        <tr v-for="sensor in filteredSensors" :key="sensor.sensorId">
          <td>{{ sensor.sensorId }}</td>
          <td>{{ sensor.type }}</td>
          <td>{{ sensor.status }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      // Mock sensor data
      sensors: [
        { sensorId: '123', type: 'temperature', status: 'active' },
        { sensorId: '456', type: 'acceleration', status: 'inactive' },
        { sensorId: '789', type: 'temperature', status: 'active' },
        { sensorId: '101', type: 'humidity', status: 'inactive' },
      ],
    }
  },
  computed: {
    filteredSensors() {
      // Return filtered sensors based on the search query for Sensor ID, Type, and Status
      return this.sensors.filter(
        (sensor) =>
          sensor.sensorId
            .toLowerCase()
            .includes(this.searchQuery.toLowerCase()) ||
          sensor.type.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          sensor.status.toLowerCase().includes(this.searchQuery.toLowerCase()),
      )
    },
  },
}
</script>

<style scoped>
.search-container {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-start;
}

.search-bar {
  margin-top: 20px;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 100%;
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

.table th,
.table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.table th {
  background-color: #f4f4f4;
  font-weight: bold;
}
</style>
