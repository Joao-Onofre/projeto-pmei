<template>
  <div class="wrapper">
    <table class="table">
      <thead>
        <tr>
          <th>Sensor ID</th>
          <th>Type</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="sensor in sensors" :key="sensor.sensorId">
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
      // Mock sensor data
      sensors: [
        { sensorId: '123', type: 'temperature', status: 'active' },
        { sensorId: '456', type: 'acceleration', status: 'inactive' },
        { sensorId: '789', type: 'temperature', status: 'active' },
      ],
    }
  },
}
</script>

<style scoped>
.wrapper {
  padding: 50px;
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
