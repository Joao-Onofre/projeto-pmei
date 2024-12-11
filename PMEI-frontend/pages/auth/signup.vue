<template>
  <div class="auth-container">
    <div class="auth-box">
      <h2 class="auth-title">Create Your Account</h2>
      <p class="auth-subtitle">Sign up to get started</p>
      <form @submit.prevent="handleSignup">
        <div class="form-group">
          <label for="name">Name</label>
          <input
            type="text"
            id="name"
            v-model="form.name"
            placeholder="Enter your name"
            required
          />
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input
            type="email"
            id="email"
            v-model="form.email"
            placeholder="Enter your email"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            v-model="form.password"
            placeholder="Create a password"
            required
          />
        </div>
        <div class="form-group">
          <label for="confirm-password">Confirm Password</label>
          <input
            type="password"
            id="confirm-password"
            v-model="form.confirmPassword"
            placeholder="Confirm your password"
            required
          />
        </div>
        <button type="submit" class="auth-button">Sign Up</button>
        <p class="auth-footer">
          Already have an account? <a href="/auth">Log in here</a>
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
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
      },
    }
  },
  methods: {
    handleSignup() {
      // Mock signup action for testing
      if (this.form.password !== this.form.confirmPassword) {
        alert('Passwords do not match!')
        return
      }
      console.log('Signing up with:', this.form)
      alert('Signup successful! (Mock)')
    },
  },
}
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f9f9f9;
  padding: 20px;
}

.auth-box {
  width: 100%;
  max-width: 400px;
  padding: 30px;
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
</style>
