<template>
  <div class="wrapper">
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Package ID</th>
          <th>Type</th>
          <th>Value</th>
          <th>Timestamp</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="alert in alerts" :key="alert.id">
          <td>{{ alert.id }}</td>
          <td>{{ alert.packageId }}</td>
          <td>{{ alert.type }}</td>
          <td>{{ alert.value }}</td>
          <td>{{ new Date(alert.timestamp).toLocaleString() }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Mock alert data
      alerts: [
        {
          id: '123',
          packageId: '1123',
          type: 'temperature',
          value: 75,
          timestamp: '2023-10-01T12:34:56Z',
        },
        {
          id: '456',
          packageId: '1456',
          type: 'humidity',
          value: 50,
          timestamp: '2023-10-02T14:20:00Z',
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
