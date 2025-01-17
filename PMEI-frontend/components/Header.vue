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
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/cart">Cart</NuxtLink>
					</li>
					<li class="nav-item">
						<NuxtLink class="nav-link" to="/package_types">Package Types</NuxtLink>
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
import { ref, onMounted, watch } from 'vue';

const isLoggedIn = ref(false);

const checkLoginStatus = () => {
	if (typeof window !== 'undefined') {
		isLoggedIn.value = !!localStorage.getItem('jwt_token');
	}
};

// Watch `localStorage` directly for changes in the same tab
const observeLocalStorage = () => {
	if (typeof window !== 'undefined') {
		const originalSetItem = localStorage.setItem;
		localStorage.setItem = function (key, value) {
			const event = new Event('local-storage-change');
			event.key = key;
			event.newValue = value;
			window.dispatchEvent(event);
			originalSetItem.apply(this, arguments);
		};
	}
};

// Listen for custom `local-storage-change` events
onMounted(() => {
	observeLocalStorage();
	checkLoginStatus();
	if (typeof window !== 'undefined') {
		window.addEventListener('local-storage-change', checkLoginStatus);
	}
});

// Cleanup listener
onUnmounted(() => {
	if (typeof window !== 'undefined') {
		window.removeEventListener('local-storage-change', checkLoginStatus);
	}
});

const logout = () => {
	if (typeof window !== 'undefined') {
		localStorage.removeItem('jwt_token');
		localStorage.removeItem('username');
		localStorage.removeItem('user_type');
		checkLoginStatus();
	}
};
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
