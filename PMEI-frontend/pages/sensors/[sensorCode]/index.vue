<template>
  <div class="wrapper">
    <h1>Edit Sensor</h1>

    <form @submit.prevent="updateSensor">
      <div class="form-group">
        <label for="currentValue">Current Value</label>
        <input
          type="number"
          id="currentValue"
          v-model="sensor.currentValue"
          class="form-control"
          required
        />
      </div>

      <div class="form-group">
        <label for="statusType">Status</label>
        <select
          id="statusType"
          v-model="sensor.statusType"
          class="form-control"
          required
        >
          <option :value="1">Active</option>
          <option :value="2">Inactive</option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>

    <a href="/sensors" class="btn btn-secondary mt-3">Back to Sensor List</a>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sensor: {
        sensorId: null,
        currentValue: null,
        statusType: null,
      },
    }
  },
  async mounted() {
    // Obtenha o ID da URL usando this.$route.params.id
    const sensorId = this.$route.params.id
    if (sensorId) {
      await this.fetchSensor(sensorId) // Se o ID estiver presente, fazemos a requisição para buscar o sensor
    } else {
      console.error('Sensor ID is missing from URL')
      alert('Sensor ID is missing from URL')
    }
  },
  methods: {
    async fetchSensor(sensorId) {
      try {
        const config = useRuntimeConfig() // Acesso ao runtimeConfig
        const response = await fetch(
          `${config.public.API_URL}/sensor/${sensorId}`,
          {
            headers: {
              Accept: 'application/json',
              Authorization:
                'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNjkwMzk5NCwiZXhwIjoxNzM2OTA3NTk0fQ.OjG5qmgFBaKFeCREtP0zwIGGLrmX7c-PyHYeQ4gRhscK98BKPRBroX3jXZIhoX_8',
            },
          },
        )

        if (response.ok) {
          const data = await response.json()
          this.sensor = data // Preenche o objeto sensor com os dados recebidos
        } else {
          console.error('Error fetching sensor:', response.statusText)
          alert('Error fetching sensor')
        }
      } catch (error) {
        console.error('Error fetching sensor:', error)
        alert('Error fetching sensor')
      }
    },
    async updateSensor() {
      try {
        const config = useRuntimeConfig() // Acesso ao runtimeConfig
        const sensorId = this.sensor.sensorId

        const response = await fetch(
          `${config.public.API_URL}/sensor/${sensorId}`,
          {
            method: 'PUT',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
              Authorization: 'Bearer YOUR_TOKEN_HERE', // Coloque seu token aqui
            },
            body: JSON.stringify({
              currentValue: this.sensor.currentValue,
              statusType: this.sensor.statusType,
            }),
          },
        )

        if (response.ok) {
          alert('Sensor updated successfully!')
          this.$router.push('/sensors') // Redireciona de volta para a lista de sensores
        } else {
          alert('Failed to update sensor.')
        }
      } catch (error) {
        console.error('Error updating sensor:', error)
        alert('Error updating sensor')
      }
    },
  },
}
</script>

<style scoped>
.wrapper {
  padding: 50px;
  margin-top: 20px;
}

h1 {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  font-weight: bold;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.btn {
  padding: 10px;
  font-size: 16px;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
}

.btn-secondary {
  background-color: #6c757d;
}

.btn:hover {
  background-color: #0056b3;
}

.mt-3 {
  margin-top: 20px;
}
</style>
