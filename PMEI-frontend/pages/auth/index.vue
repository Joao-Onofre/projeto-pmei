<template>
  <div class="auth-container">
    <div class="auth-box">
      <h2 class="auth-title">Welcome</h2>
      <p class="auth-subtitle">Please log in to access your account</p>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">Username</label>
          <input
            type="username"
            id="username"
            v-model="form.username"
            placeholder="Enter your username"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            v-model="form.password"
            placeholder="Enter your password"
            required
          />
        </div>
        <div v-if="error" class="error-message">
          {{ error }}
        </div>
        <button type="submit" class="auth-button">Log In</button>
        <p class="auth-footer">
          Don’t have an account? <a href="/auth/signup">Sign up here</a>
        </p>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      error: null,
    }
  },
  setup() {},
  methods: {
    async handleLogin() {
      try {
        const config = useRuntimeConfig()
        const api = config.public.API_URL

        // Authenticate and retrieve the JWT token
        const authResponse = await fetch(`${api}/auth/login`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.form),
        })

        if (!authResponse.ok) {
          throw new Error('Failed to log in. Please check your credentials.')
        }

        const token = await authResponse.text()
        console.log('Login successful:', token)

        // Save the JWT token in localStorage
        localStorage.setItem('jwt_token', token)
        localStorage.setItem('username', this.form.username)

        // Fetch the user details
        const userResponse = await fetch(`${api}/user/${this.form.username}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`, // Pass the JWT token for authentication
          },
        })

        if (!userResponse.ok) {
          throw new Error('Failed to fetch user details.')
        }

        const userData = await userResponse.json()
        console.log('User data fetched:', userData)

        // Save userType in localStorage
        localStorage.setItem('user_type', userData.userType)

        // Redirect to the homepage
        this.$router.push('/')
      } catch (error) {
        console.error('Login error:', error)
        this.error = error.message
      }
    },
  },
}
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
  padding: 100px 20px 20px 20px;
}

.auth-box {
  width: 100%;
  max-width: 400px;
  padding: 30px 30px 30px 30px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.auth-title {
  margin-bottom: 10px;
  font-size: 24px;
  font-weight: bold;
}

.auth-subtitle {
  margin-bottom: 20px;
  font-size: 16px;
  color: #666;
}

.form-group {
  margin-bottom: 15px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-size: 14px;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.auth-button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #ffffff;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.auth-button:hover {
  background-color: #0056b3;
}

.auth-footer {
  margin-top: 20px;
  font-size: 14px;
}

.auth-footer a {
  color: #007bff;
  text-decoration: none;
}

.auth-footer a:hover {
  text-decoration: underline;
}

.error-message {
  color: red;
  margin-top: 10px;
  font-size: 0.9rem;
}
</style>
