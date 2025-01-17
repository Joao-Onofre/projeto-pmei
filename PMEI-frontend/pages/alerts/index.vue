<template>
  <div class="wrapper">
    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Alert ID, Sensor ID, Message or Timestamp"
      />
    </div>

    <!-- Alert Table -->
    <table class="table">
      <thead>
        <tr>
          <th>Alert ID</th>
          <th>Sensor ID</th>
          <th>Message</th>
          <th>Timestamp</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <!-- Display filtered and paginated alerts -->
        <tr v-for="alert in paginatedAlerts" :key="alert.id">
          <td>{{ alert.alertId || 'N/A' }}</td>
          <td>{{ alert.sensorId || 'N/A' }}</td>
          <td>{{ alert.message || 'N/A' }}</td>
          <td>{{ alert.formattedTimestamp || 'N/A' }}</td>
          <td>
            <button
              @click="deleteAlert(alert.alertId)"
              class="btn btn-danger btn-sm"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-if="filteredAlerts.length === 0">No alerts found.</p>

    <!-- Pagination -->
    <div class="pagination" v-if="filteredAlerts.length > itemsPerPage">
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
      alerts: [],
      currentPage: 1,
      itemsPerPage: 5,
    }
  },
  computed: {
    filteredAlerts() {
      // First sort alerts by timestamp (most recent first)
      const sortedAlerts = [...this.alerts].sort((a, b) => {
        const dateA = new Date(a.formattedTimestamp)
        const dateB = new Date(b.formattedTimestamp)
        return dateB - dateA
      })

      // Then apply search filter
      if (!this.searchQuery) return sortedAlerts

      const query = this.searchQuery.toLowerCase()
      return sortedAlerts.filter(
        (alert) =>
          alert.id?.toString().includes(query) ||
          alert.sensorId?.toString().includes(query) ||
          alert.message?.toLowerCase().includes(query) ||
          alert.formattedTimestamp?.toLowerCase().includes(query),
      )
    },
    paginatedAlerts() {
      const start = (this.currentPage - 1) * this.itemsPerPage
      const end = start + this.itemsPerPage
      return this.filteredAlerts.slice(start, end)
    },
    totalPages() {
      return Math.ceil(this.filteredAlerts.length / this.itemsPerPage)
    },
  },
  watch: {
    // Reset to first page when search query changes
    searchQuery() {
      this.currentPage = 1
    },
    // Ensure current page is valid when filtered results change
    filteredAlerts() {
      if (this.currentPage > this.totalPages) {
        this.currentPage = this.totalPages || 1
      }
    },
  },
  methods: {
    async fetchAlerts() {
      try {
        const config = useRuntimeConfig()
        const response = await fetch(`${config.public.API_URL}/alert`, {
          headers: {
            Accept: 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzE0OTc2NSwiZXhwIjoxNzM3MTUzMzY1fQ.eI6WZ3KgveBzYLTBW-ap3O63e64qTJ69DJ2YW1PK645yNr2LOGo-x2zFcorqgSqw',
          },
        })
        const data = await response.json()
        this.alerts = data
      } catch (error) {
        console.error('Error fetching alerts:', error)
      }
    },
    async deleteAlert(alertId) {
      if (confirm('Are you sure you want to delete this alert?')) {
        try {
          const config = useRuntimeConfig()
          const response = await fetch(
            `${config.public.API_URL}/alert/${alertId}`, // Use 'alertId' para o identificador correto
            {
              method: 'DELETE',
              headers: {
                Accept: 'application/json',
                Authorization:
                  'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzE0OTc2NSwiZXhwIjoxNzM3MTUzMzY1fQ.eI6WZ3KgveBzYLTBW-ap3O63e64qTJ69DJ2YW1PK645yNr2LOGo-x2zFcorqgSqw', // Verifique se o token ainda é válido
              },
            },
          )

          if (response.ok) {
            this.alerts = this.alerts.filter(
              (alert) => alert.alertId !== alertId,
            ) // Use 'alertId' aqui também
            alert('Alert deleted successfully!')
          } else {
            alert('Failed to delete alert.')
          }
        } catch (error) {
          console.error('Error deleting alert:', error)
        }
      }
    },
  },
  mounted() {
    this.fetchAlerts()
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
