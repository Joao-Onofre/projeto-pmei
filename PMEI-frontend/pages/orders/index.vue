<template>
	<div class="wrapper">
		<!-- Search Bar -->
		<div class="search-container">
			<input
				type="text"
				v-model="searchQuery"
				class="search-bar"
				placeholder="Search by Order ID, Status or Customer ID" />
		</div>

		<!-- Order Table -->
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2>Orders</h2>
			<nuxt-link to="/orders/create" class="btn btn-light btn-lg">Create a New Order</nuxt-link>
		</div>
		<table class="order-table">
			<thead class="thead-dark text-center">
				<tr>
					<th>ID</th>
					<th>Status</th>
					<th>Customer ID</th>
					<th>Delivery State</th>
					<th>Packages</th>
					<th>Sensors</th>
					<th>Activity</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody class="text-center">
				<!-- Display filtered orders -->
				<tr v-for="order in orders" :key="order.id">
					<td>{{ order.id }}</td>
					<td>{{ order.status }}</td>
					<td>{{ order.customerUsername }}</td>
					<td>{{ order.statusName }}</td>
					<td>{{ totalPackages(order) }}</td>
					<td>{{ totalSensors(order) }}</td>
					<td>{{ order.terminated }}</td>
					<td class="d-flex gap-2 justify-content-center">
						<button @click.prevent="deleteOrder(order.id)" class="btn btn-outline-danger btn-sm">Delete</button>
						<nuxt-link :to="`/orders/${order.id}`" class="btn btn-outline-dark btn-sm">Details</nuxt-link>
						<nuxt-link :to="`/orders/update/${order.id}`" class="btn btn-outline-info btn-sm">Update</nuxt-link>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</template>

<script setup>
import { ref, computed } from "vue"
const config = useRuntimeConfig()
const api = config.public.API_URL

// Reactive state for orders
const orders = ref([]);
const getOrders = async () => {
	try {
		const response = await fetch(`${api}/order`);
		orders.value = await response.json();
	} catch (error) {
		console.error("Error fetching orders:", error);
	}
}

// Helper function to calculate total packages
const totalPackages = (order) => {
	return order.packageList ? order.packageList.length : 0;
};

// Helper function to calculate total sensors
const totalSensors = (order) => {
	if (!order.packageList) return 0;
	return order.packageList.reduce((total, pack) => {
		return total + (pack.sensorList ? pack.sensorList.length : 0);
	}, 0);
};


async function deleteOrder(id) {
	try {
		const requestOptions = {
			method: 'DELETE',
			headers: { 'Content-Type': 'application/json' },
		}
		const response = await fetch(`${api}/order/${id}`, requestOptions)
		console.log(response)

		if (!response.ok) {
			throw new Error('Failed to delete the order.')
		}

		// Refresh the data after successful deletion
		await getOrders()

	} catch (error) {
		console.error('Error deleting order:', error.message)
		// Optionally display the error in the UI
	}
}

// Fetch orders when the component is mounted
getOrders();
</script>
