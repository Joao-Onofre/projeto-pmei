<template>
  <div class="wrapper">
    <!-- Tabela de Alertas -->
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
        <!-- Exibe os alertas filtrados e paginados -->
        <tr v-for="alert in paginatedAlerts" :key="alert.alertId">
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

    <!-- Paginação -->
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
import { ref, computed, onMounted, watch } from 'vue'

export default {
  setup() {
    let username = null
    let userType = null
    let token = null
    if (typeof window !== 'undefined') {
      username = localStorage.getItem('username')
      userType = localStorage.getItem('user_type')
      token = localStorage.getItem('jwt_token')
      console.log(userType)
      if (!username || !userType || !token) {
        error.value = 'No username or user type found. Please log in.'
      }
    }

    const alerts = ref([])
    const currentPage = ref(1)
    const itemsPerPage = 5

    const filteredAlerts = computed(() => {
      const sortedAlerts = [...alerts.value].sort((a, b) => {
        const dateA = new Date(a.formattedTimestamp)
        const dateB = new Date(b.formattedTimestamp)
        return dateB - dateA
      })

      return sortedAlerts
    })

    const paginatedAlerts = computed(() => {
      const start = (currentPage.value - 1) * itemsPerPage
      const end = start + itemsPerPage
      return filteredAlerts.value.slice(start, end)
    })

    const totalPages = computed(() =>
      Math.ceil(filteredAlerts.value.length / itemsPerPage),
    )

    watch(filteredAlerts, () => {
      if (currentPage.value > totalPages.value) {
        currentPage.value = totalPages.value || 1
      }
    })

    const fetchOrders = async () => {
      try {
        const config = useRuntimeConfig()
        let ordersData = []

        if (userType === 'Administrator') {
          // Administrador: Busca direta dos alertas
          const responseAlerts = await fetch(`${config.public.API_URL}/alert`, {
            headers: {
              Accept: 'application/json',
              Authorization: 'Bearer ' + token,
            },
          })

          if (responseAlerts.ok) {
            const alertsData = await responseAlerts.json()
            alerts.value = alertsData // Armazena diretamente os alertas
          } else {
            console.error(
              'Failed to fetch alerts for administrator:',
              await responseAlerts.text(),
            )
            alerts.value = []
          }
        } else if (userType === 'Customer') {
          // Cliente: Busca de pedidos e mapeamento de sensores
          const responseOrders = await fetch(
            `${config.public.API_URL}/customer/${username}/orders`,
            {
              headers: {
                Accept: 'application/json',
                Authorization: 'Bearer ' + token,
              },
            },
          )

          if (responseOrders.ok) {
            ordersData = await responseOrders.json()
          } else {
            console.error(
              'Failed to fetch orders for customer:',
              await responseOrders.text(),
            )
            alerts.value = []
            return
          }

          if (ordersData.length === 0) {
            console.log('No orders found.')
            alerts.value = []
            return
          }

          const sensorIds = ordersData.flatMap((order) =>
            order.packageList.flatMap((pkg) =>
              pkg.sensorList.map((sensor) => sensor.sensorId),
            ),
          )

          if (sensorIds.length > 0) {
            const responseAlerts = await fetch(
              `${config.public.API_URL}/alert/sensors?ids=${sensorIds.join(',')}`,
              {
                headers: {
                  Accept: 'application/json',
                  Authorization: 'Bearer ' + token,
                },
              },
            )

            if (responseAlerts.ok) {
              const alertsData = await responseAlerts.json()
              alerts.value = alertsData
            } else {
              console.error(
                'Error fetching alerts for sensors:',
                await responseAlerts.text(),
              )
              alerts.value = []
            }
          } else {
            console.log('No sensors found in the orders.')
            alerts.value = []
          }
        }
      } catch (error) {
        console.error('Error fetching alerts:', error)
      }
    }

    const deleteAlert = async (alertId) => {
      if (confirm('Are you sure you want to delete this alert?')) {
        try {
          const config = useRuntimeConfig()
          const response = await fetch(
            `${config.public.API_URL}/alert/${alertId}`,
            {
              method: 'DELETE',
              headers: {
                Accept: 'application/json',
                Authorization: 'Bearer ' + token,
              },
            },
          )

          if (response.ok) {
            alerts.value = alerts.value.filter(
              (alert) => alert.alertId !== alertId,
            )
            alert('Alert deleted successfully!')
          } else {
            alert('Failed to delete alert.')
          }
        } catch (error) {
          console.error('Error deleting alert:', error)
        }
      }
    }

    onMounted(fetchOrders)

    return {
      alerts,
      currentPage,
      itemsPerPage,
      filteredAlerts,
      paginatedAlerts,
      totalPages,
      deleteAlert,
    }
  },
}
</script>

<style scoped>
.wrapper {
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

.btn {
  display: inline-block;
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
</style>
