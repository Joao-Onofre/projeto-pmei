<template>
  <div class="wrapper">
    <a href="/sensors/create" class="btn btn-primary">Create New Sensor</a>

    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Sensor ID, Type or Status"
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
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <!-- Display filtered and paginated sensors -->
        <tr v-for="sensor in paginatedSensors" :key="sensor.sensorId">
          <td>{{ sensor.sensorId || 'N/A' }}</td>
          <td>
            {{ sensor.currentValue !== null ? sensor.currentValue : 'N/A' }}
          </td>
          <td>{{ sensor.formattedTimestamp || 'N/A' }}</td>
          <td>{{ sensor.sensorType || 'N/A' }}</td>
          <td>{{ sensor.statusType || 'N/A' }}</td>
          <td>
            <a
              :href="`/sensors/${sensor.sensorId}`"
              class="btn btn-secondary btn-sm"
              >Edit</a
            >
            <button
              @click="deleteSensor(sensor.sensorId)"
              class="btn btn-danger btn-sm"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-if="filteredSensors.length === 0">No sensors found.</p>

    <!-- Pagination -->
    <div class="pagination" v-if="filteredSensors.length > itemsPerPage">
      <button
        @click="currentPage = 1"
        :disabled="currentPage === 1"
        class="btn btn-secondary btn-sm"
      >
        First
      </button>
      <button
        @click="currentPage--"
        :disabled="currentPage === 1"
        class="btn btn-secondary btn-sm"
      >
        Previous
      </button>
      <span class="page-info">
        Page {{ currentPage }} of {{ totalPages }}
      </span>
      <button
        @click="currentPage++"
        :disabled="currentPage === totalPages"
        class="btn btn-secondary btn-sm"
      >
        Next
      </button>
      <button
        @click="currentPage = totalPages"
        :disabled="currentPage === totalPages"
        class="btn btn-secondary btn-sm"
      >
        Last
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      sensors: [],
      currentPage: 1,
      itemsPerPage: 5,
    }
  },
  computed: {
    filteredSensors() {
      // First sort sensors by timestamp (most recent first)
      const sortedSensors = [...this.sensors].sort((a, b) => {
        const dateA = new Date(a.formattedTimestamp)
        const dateB = new Date(b.formattedTimestamp)
        return dateB - dateA
      })

      // Then apply search filter
      if (!this.searchQuery) return sortedSensors

      const query = this.searchQuery.toLowerCase()
      return sortedSensors.filter(
        (sensor) =>
          sensor.sensorId?.toString().includes(query) ||
          sensor.currentValue?.toString().includes(query) ||
          sensor.sensorType?.toLowerCase().includes(query) ||
          sensor.statusType?.toLowerCase().includes(query),
      )
    },
    paginatedSensors() {
      const start = (this.currentPage - 1) * this.itemsPerPage
      const end = start + this.itemsPerPage
      return this.filteredSensors.slice(start, end)
    },
    totalPages() {
      return Math.ceil(this.filteredSensors.length / this.itemsPerPage)
    },
  },
  watch: {
    // Reset to first page when search query changes
    searchQuery() {
      this.currentPage = 1
    },
    // Ensure current page is valid when filtered results change
    filteredSensors() {
      if (this.currentPage > this.totalPages) {
        this.currentPage = this.totalPages || 1
      }
    },
  },
  methods: {
    async fetchSensors() {
      try {
        const config = useRuntimeConfig()
        const response = await fetch(`${config.public.API_URL}/sensor`, {
          headers: {
            Accept: 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzEzNzc5MywiZXhwIjoxNzM3MTQxMzkzfQ.rVDV8NN1xjsNpdzRJwZT4xnlEvtWIe77h7bof-7Q9N5gXkbwxVTOsUbNDyAFNIza',
          },
        })
        const data = await response.json()
        this.sensors = data
      } catch (error) {
        console.error('Error fetching sensors:', error)
      }
    },
    async deleteSensor(sensorId) {
      if (confirm('Are you sure you want to delete this sensor?')) {
        try {
          const config = useRuntimeConfig()
          const response = await fetch(
            `${config.public.API_URL}/sensor/${sensorId}`,
            {
              method: 'DELETE',
              headers: {
                Accept: 'application/json',
                Authorization:
                  'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzEzNzc5MywiZXhwIjoxNzM3MTQxMzkzfQ.rVDV8NN1xjsNpdzRJwZT4xnlEvtWIe77h7bof-7Q9N5gXkbwxVTOsUbNDyAFNIza',
              },
            },
          )

          if (response.ok) {
            this.sensors = this.sensors.filter(
              (sensor) => sensor.sensorId !== sensorId,
            )
            alert('Sensor deleted successfully!')
          } else {
            alert('Failed to delete sensor.')
          }
        } catch (error) {
          console.error('Error deleting sensor:', error)
        }
      }
    },
  },
  mounted() {
    this.fetchSensors()
  },
}
</script>

<style scoped>
.search-container {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-start;
}

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

.btn {
  display: inline-block;
  width: auto;
  padding: 6px 12px;
  font-size: 14px;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 0 5px;
}

.btn-primary {
  background-color: #007bff;
}

.btn-secondary {
  background-color: #6c757d;
}

.btn-danger {
  background-color: #dc3545;
}

.btn-sm {
  font-size: 12px;
}

.btn:hover {
  background-color: #0056b3;
}

.btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  gap: 10px;
}

.page-info {
  margin: 0 15px;
  font-size: 14px;
}
</style>
