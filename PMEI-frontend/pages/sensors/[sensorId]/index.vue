<template>
  <div class="wrapper">
    <h1>Edit Sensor</h1>

    <form @submit.prevent="updateSensor">
      <div class="form-group">
        <label for="currentValue">Current Value</label>
        <input
          type="number"
          step="0.1"
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
          <option value="Active">Active</option>
          <option value="Inactive">Inactive</option>
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
    const sensorId = this.$route.params.sensorId
    if (sensorId) {
      await this.fetchSensor(sensorId)
    } else {
      console.error('Sensor ID is missing from URL')
      alert('Sensor ID is missing from URL')
    }
  },
  methods: {
    async fetchSensor(sensorId) {
      try {
        const config = useRuntimeConfig()
        const response = await fetch(
          `${config.public.API_URL}/sensor/${sensorId}`,
          {
            headers: {
              Accept: 'application/json',
              Authorization:
                'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzA0ODI4MywiZXhwIjoxNzM3MDUxODgzfQ.6WV5OVM6s8vBz4ut88iJtdtbSpAA2o2ew8-eaF0rv7W4TC21QlzCAC86b3LmOCgI',
            },
          },
        )

        if (response.ok) {
          const data = await response.json()
          this.sensor = data

          // Convert numeric status to string if needed
          if (typeof this.sensor.statusType === 'number') {
            this.sensor.statusType =
              this.sensor.statusType === 1 ? 'Active' : 'Inactive'
          }
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
        const config = useRuntimeConfig()
        const sensorId = this.sensor.sensorId

        const requestData = {
          currentValue: parseFloat(this.sensor.currentValue),
          statusType: this.sensor.statusType,
        }

        console.log('Updating sensor with values:', requestData)

        const url = `${config.public.API_URL}/sensor/${sensorId}`
        console.log('PUT request URL:', url)

        const response = await fetch(url, {
          method: 'PUT',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzA0ODI4MywiZXhwIjoxNzM3MDUxODgzfQ.6WV5OVM6s8vBz4ut88iJtdtbSpAA2o2ew8-eaF0rv7W4TC21QlzCAC86b3LmOCgI',
          },
          body: JSON.stringify(requestData),
        })

        // Log the raw response for debugging
        console.log('Response status:', response.status)
        console.log('Response headers:', Object.fromEntries(response.headers))

        const responseText = await response.text()
        console.log('Raw response:', responseText)

        if (response.ok) {
          // Only try to parse as JSON if there's actual content
          let responseData = null
          if (responseText) {
            try {
              responseData = JSON.parse(responseText)
              console.log('Response data:', responseData)
            } catch (e) {
              console.log('Response was not JSON, but request was successful')
            }
          }

          alert('Sensor updated successfully!')
          this.$router.push('/sensors')
        } else {
          console.error('Failed to update sensor. Response:', responseText)
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
