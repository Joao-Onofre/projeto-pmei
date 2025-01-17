<template>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<!-- Brand/Home Link -->
			<NuxtLink class="navbar-brand" to="/">Home</NuxtLink>

			<!-- Toggler for Mobile View -->
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- Navigation Links -->
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto">
					<!-- Align to the left -->
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/packages">Packages</NuxtLink>
					</li>
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/orders">Orders</NuxtLink>
					</li>
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/alerts">Alerts</NuxtLink>
					</li>
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/sensors">Sensors</NuxtLink>
					</li>
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/products">Products</NuxtLink>
					</li>
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/product_types">Product Types</NuxtLink>
					</li>
				</ul>
				<!-- Login Link -->
				<div class="navbar-nav ms-auto">
					<!-- Align to the right -->
					<NuxtLink v-show="!isLoggedIn()" class="nav-link login-link" to="/auth">Login</NuxtLink>
					<div v-show="isLoggedIn()" class="nav-link login-link" @click="logout">Logout</div>
				</div>
			</div>
		</div>
	</nav>
</template>

<script>
export default {
	name: 'Header',
	methods: {
		isLoggedIn() {
			if (!import.meta.browser) return false;
			return !!localStorage.getItem('jwt_token');
		},
		logout() {
			localStorage.removeItem('jwt_token');
			this.$router.go();
		}
	}
}
</script>

<style scoped>
/* Space below the navbar */
.navbar {
	margin-bottom: 20px;
}

/* Custom style for the login link */
.login-link {
	font-weight: bold;
	color: #007bff;
	transition: color 0.3s ease;
}

.login-link:hover {
	color: #0056b3;
	text-decoration: underline;
}
</style>
