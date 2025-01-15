<template>
  <div class="wrapper">
    <!-- Form Title -->
    <h2>Create New Sensor</h2>

    <!-- Sensor Creation Form -->
    <form @submit.prevent="createSensor" class="form-container">
      <!-- Sensor Type Input -->
      <div class="form-group">
        <label for="sensorType">Sensor Type</label>
        <select
          v-model="sensorType"
          id="sensorType"
          class="form-control"
          required
        >
          <option value="" disabled>Select Sensor Type</option>
          <option value="Temperature">Temperature</option>
          <option value="Humidity">Humidity</option>
        </select>
      </div>

      <!-- Submit Button -->
      <button type="submit" class="btn btn-primary">Create Sensor</button>
    </form>

    <!-- Success Message -->
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sensorType: '', // Sensor type selected by the user
      successMessage: '', // Message to display after successful creation
    }
  },
  methods: {
    async createSensor() {
      try {
        const config = useRuntimeConfig() // Access runtimeConfig
        const response = await fetch(`${config.public.API_URL}/sensor`, {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNjkwMjI4NywiZXhwIjoxNzM2OTA1ODg3fQ.lTnZgyy7V88p2q9EBtm6wKs_ItXVfl7zDelAkUIXTeif_Aji6RYKwsoIemCQOW3y',
          },
          body: JSON.stringify({ sensorType: this.sensorType }),
        })

        if (response.ok) {
          this.successMessage = 'Sensor created successfully!'
          this.sensorType = '' // Reset form
        } else {
          throw new Error('Failed to create sensor')
        }
      } catch (error) {
        console.error('Error creating sensor:', error)
        this.successMessage = 'Error creating sensor. Please try again.'
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

h2 {
  text-align: center;
  margin-bottom: 30px;
}

/* Form Container Styling */
.form-container {
  max-width: 500px;
  margin: 0 auto;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Form Group Styling */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

.form-control {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

/* Button Styling */
.btn {
  display: block;
  width: 100%;
  padding: 10px;
  font-size: 16px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn:hover {
  background-color: #0056b3;
}

/* Success Message Styling */
.success-message {
  text-align: center;
  margin-top: 20px;
  color: #28a745;
  font-weight: bold;
}
</style>
