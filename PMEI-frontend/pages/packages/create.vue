<template>
	<div class="wrapper">
		<h2 class="mb-3">Create Product</h2>

		<div class="form-group mb-3">
			<label for="orderSelection">Select Order:</label>
			<div class="input-group">
				<select id="orderSelection" v-model="selectedOrder" class="form-control">
					<option v-for="order in orders" :key="order.id" :value="order">
						{{ order.id }}
					</option>
				</select>
			</div>

			<label for="packTypeSelection">Select Package Type:</label>
			<div class="input-group">
				<select id="packTypeSelection" v-model="selectedPackType" class="form-control">
					<option v-for="packType in packTypes" :key="packType.id" :value="packType">
						{{ packType.id }} - {{ packType.type }}
					</option>
				</select>
			</div>
		</div>

		<div class="d-flex justify-content-between">
			<button type="reset" class="btn btn-secondary">RESET</button>
			<button class="btn btn-primary" @click="submitPack">CREATE</button>
			<nuxt-link to="/packages" class="btn btn-link">Return</nuxt-link>
		</div>
	</div>
</template>

<script setup>
import { ref } from "vue";

// Configuration for API
const config = useRuntimeConfig();
const api = config.public.API_URL;

// Reactive state
const orders = ref([]); // List of orders from the backend
const packTypes = ref([]); // List of package types from the backend
const selectedOrder = ref(null); // Selected order
const selectedPackType = ref(null); // Selected package type

// Fetch orders from the backend
const fetchOrders = async () => {
	try {
		const response = await fetch(`${api}/order`);
		if (!response.ok) {
			throw new Error("Failed to fetch orders");
		}
		orders.value = await response.json();
	} catch (error) {
		console.error("Error fetching orders:", error);
	}
};

// Fetch package types from the backend
const fetchPackTypes = async () => {
	try {
		const response = await fetch(`${api}/packagetype`);
		if (!response.ok) {
			throw new Error("Failed to fetch package types");
		}
		packTypes.value = await response.json();
	} catch (error) {
		console.error("Error fetching package types:", error);
	}
};

// Submit a new package
const submitPack = async () => {
	if (!selectedOrder.value || !selectedPackType.value) {
		alert("Please select both an order and a package type.");
		return;
	}

	const payload = {
		order: selectedOrder.value.id,
		packageType: selectedPackType.value.id,
	};

	try {
		const response = await fetch(`${api}/package`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(payload),
		});

		if (!response.ok) {
			throw new Error("Failed to create package");
		}

		alert("Package created successfully!");
		selectedOrder.value = null;
		selectedPackType.value = null;
	} catch (error) {
		console.error("Error creating package:", error);
		alert("Error creating package: " + error.message);
	}
};

// Fetch initial data when component is mounted
fetchOrders();
fetchPackTypes();
</script>
