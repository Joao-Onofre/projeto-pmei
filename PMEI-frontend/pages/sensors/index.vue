<template>
  <div class="wrapper">
    <a href="/sensors/create" class="btn btn-primary">Create New Sensor</a>

    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Sensor ID, Current Value, Type or Status"
      />
    </div>

    <!-- Sensor Table -->
    <table class="table">
      <thead>
        <tr>
          <th>Sensor ID</th>
          <th>Current Value</th>
          <th>Timestamp</th>
          <th>Sensor Type</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <!-- Display filtered sensors -->
        <tr v-for="sensor in filteredSensors" :key="sensor.sensorId">
          <td>{{ sensor.sensorId || 'N/A' }}</td>
          <td>
            {{ sensor.currentValue !== null ? sensor.currentValue : 'N/A' }}
          </td>
          <td>{{ sensor.formattedTimestamp || 'N/A' }}</td>
          <td>{{ sensor.sensorType || 'N/A' }}</td>
          <td>{{ sensor.statusType || 'N/A' }}</td>
        </tr>
      </tbody>
    </table>

    <p v-if="filteredSensors.length === 0">No sensors found.</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      sensors: [],
    }
  },
  computed: {
    filteredSensors() {
      if (!this.searchQuery) return this.sensors

      const query = this.searchQuery.toLowerCase()
      return this.sensors.filter(
        (sensor) =>
          sensor.sensorId?.toString().includes(query) ||
          sensor.currentValue?.toString().includes(query) ||
          sensor.sensorType?.toLowerCase().includes(query) ||
          sensor.statusType?.toLowerCase().includes(query),
      )
    },
  },
  methods: {
    async fetchSensors() {
      try {
        const config = useRuntimeConfig() // Access runtimeConfig
        const response = await fetch(`${config.public.API_URL}/sensor`, {
          headers: {
            Accept: 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNjkwMjI4NywiZXhwIjoxNzM2OTA1ODg3fQ.lTnZgyy7V88p2q9EBtm6wKs_ItXVfl7zDelAkUIXTeif_Aji6RYKwsoIemCQOW3y',
          },
        })
        const data = await response.json()
        this.sensors = data
      } catch (error) {
        console.error('Error fetching sensors:', error)
      }
    },
  },
  mounted() {
    this.fetchSensors()
  },
}
</script>

<style scoped>
.wrapper {
  padding: 50px;
  margin-top: 20px;
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
