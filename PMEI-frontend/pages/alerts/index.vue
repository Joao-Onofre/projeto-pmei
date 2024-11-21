<template>
  <div class="wrapper">
    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Alert ID, Package ID or Type"
      />
    </div>

    <!-- Alert Table -->
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
        <!-- Display filtered alerts -->
        <tr v-for="alert in filteredAlerts" :key="alert.id">
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
      searchQuery: '',
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
  computed: {
    filteredAlerts() {
      // Return filtered alerts based on the search query
      return this.alerts.filter(
        (alert) =>
          alert.id.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          alert.packageId
            .toLowerCase()
            .includes(this.searchQuery.toLowerCase()) ||
          alert.type.toLowerCase().includes(this.searchQuery.toLowerCase()),
      )
    },
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

/* Styling for the search bar */
.search-container {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-end;
}

.search-bar {
  margin-top: 20px;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 100%;
}
</style>
