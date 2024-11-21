<template>
  <div class="wrapper">
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Status</th>
          <th>Sensors</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="pkg in packages" :key="pkg.id">
          <td>{{ pkg.id }}</td>
          <td>{{ pkg.status }}</td>
          <td>
            <ul>
              <li v-for="sensor in pkg.sensors" :key="sensor.sensorId">
                {{ sensor.type }} ({{ sensor.status }})
              </li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Mock package data
      packages: [
        {
          id: 'package123',
          status: 'in_transit',
          sensors: [
            { sensorId: 'sensor001', type: 'temperature', status: 'active' },
            { sensorId: 'sensor002', type: 'acceleration', status: 'active' },
          ],
        },
        {
          id: 'package456',
          status: 'delivered',
          sensors: [
            { sensorId: 'sensor003', type: 'humidity', status: 'inactive' },
          ],
        },
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
