<template>
  <div class="wrapper">
    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Package ID or Status"
      />
    </div>

    <!-- Package Table -->
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Status</th>
          <th>Sensors</th>
        </tr>
      </thead>
      <tbody>
        <!-- Display filtered packages -->
        <tr v-for="pkg in filteredPackages" :key="pkg.id">
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
      searchQuery: '',
      // Mock package data
      packages: [
        {
          id: '123',
          status: 'in_transit',
          sensors: [
            { sensorId: 'sensor001', type: 'temperature', status: 'active' },
            { sensorId: 'sensor002', type: 'acceleration', status: 'active' },
          ],
        },
        {
          id: '456',
          status: 'delivered',
          sensors: [
            { sensorId: 'sensor003', type: 'humidity', status: 'inactive' },
          ],
        },
        {
          id: '789',
          status: 'pending',
          sensors: [
            { sensorId: 'sensor004', type: 'pressure', status: 'active' },
          ],
        },
      ],
    }
  },
  computed: {
    filteredPackages() {
      // Return filtered packages based on the search query for both ID and Status
      return this.packages.filter(
        (pkg) =>
          pkg.id.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          pkg.status.toLowerCase().includes(this.searchQuery.toLowerCase()),
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
</style>
