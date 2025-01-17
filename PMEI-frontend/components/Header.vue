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
					<!-- Admin-Specific Links -->
					<li v-if="isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/product_types">Product Types</NuxtLink>
					</li>
					<li v-if="isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/package_types">Package Types</NuxtLink>
					</li>
					<li v-if="isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/packages">Packages</NuxtLink>
					</li>
					<li v-if="isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/sensors">Sensors</NuxtLink>
					</li>

					<!-- Customer Links -->
					<li v-if="isCustomer || isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/cart">Cart</NuxtLink>
					</li>
					<li v-if="isCustomer || isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/products">Products</NuxtLink>
					</li>
					<li v-if="isCustomer || isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/orders">Orders</NuxtLink>
					</li>
					<li v-if="isCustomer || isAdmin" class="nav-item">
						<NuxtLink class="nav-link" to="/alerts">Alerts</NuxtLink>
					</li>
				</ul>

				<!-- Login/Logout Links -->
				<div class="navbar-nav ms-auto">
					<NuxtLink v-if="!isLoggedIn" class="nav-link login-link" to="/auth">Login</NuxtLink>
					<div v-else class="nav-link login-link" @click="logout">Logout</div>
				</div>
			</div>
		</div>
	</nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const isLoggedIn = ref(false);
const isAdmin = ref(false);
const isCustomer = ref(false);

const checkLoginStatus = () => {
	if (typeof window !== 'undefined') {
		const token = localStorage.getItem('jwt_token');
		const userType = localStorage.getItem('user_type');
		isLoggedIn.value = !!token;

		if (userType) {
			isAdmin.value = userType === 'Administrator';
			isCustomer.value = userType === 'Customer';
		} else {
			isAdmin.value = false;
			isCustomer.value = false;
		}
	}
};

const logout = () => {
	if (typeof window !== 'undefined') {
		localStorage.removeItem('jwt_token');
		localStorage.removeItem('username');
		localStorage.removeItem('user_type');
		checkLoginStatus();
		router.push('/auth');
	}
};

onMounted(() => {
	checkLoginStatus();

	// Listen for localStorage changes (works for other tabs too)
	if (typeof window !== 'undefined') {
		window.addEventListener('storage', checkLoginStatus);
	}
});

onUnmounted(() => {
	// Clean up the event listener
	if (typeof window !== 'undefined') {
		window.removeEventListener('storage', checkLoginStatus);
	}
});
</script>

<style scoped>
.navbar {
	margin-bottom: 20px;
}

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
