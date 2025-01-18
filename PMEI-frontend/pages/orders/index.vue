<template>
	<div class="container">
		<div class="wrapper">
			<!-- Search Bar and Create Button -->
			<div class="mt-5"></div>
			<div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>

			<div v-if="userType !== 'Customer'" class="action-buttons">
				<nuxt-link to="/orders/create" class="btn btn-create">Create New Order</nuxt-link>
			</div>
			<table class="table">
				<thead>
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
				<tbody>
					<!-- Display filtered orders -->
					<tr v-for="order in orders" :key="order.id">
						<td>{{ order.id }}</td>
						<td>{{ order.statusName }}</td>
						<td>{{ order.customerUsername }}</td>
						<td>{{ order.statusName }}</td>
						<td>{{ totalPackages(order) }}</td>
						<td>{{ totalSensors(order) }}</td>
						<td>{{ order.terminated ? "Inactive" : "Active" }}</td>
						<td>
							<nuxt-link :to="`/orders/${order.id}`" class="btn btn-dark">Details</nuxt-link>
							<nuxt-link :to="`/orders/update/${order.id}`" class="btn btn-edit">Update</nuxt-link>
							<button @click.prevent="deleteOrder(order.id)" class="btn btn-delete">Terminate</button>
						</td>
					</tr>
					<tr v-if="orders.length === 0">
						<td colspan="6">No products match your search.</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script setup>
import { ref, computed } from "vue"
const config = useRuntimeConfig()
const api = config.public.API_URL
const error = ref(null);

let username = null;
let userType = null;
if (typeof window !== 'undefined') {
	username = localStorage.getItem('username');
	userType = localStorage.getItem('user_type');
	console.log(userType)
	if (!username || !userType) {
		error.value = 'No username or user type found. Please log in.';
	}
}


// Reactive state for orders
const orders = ref([]);
const getOrders = async () => {
	try {
		// Determine the endpoint based on user type
		const endpoint = userType === 'customer' ? `${api}/${username}/orders` : `${api}/order`;

		// Fetch data from the appropriate endpoint
		const response = await fetch(endpoint);
		if (!response.ok) {
			throw new Error('Failed to fetch orders');
		}

		orders.value = await response.json();
	} catch (error) {
		console.error('Error fetching orders:', error);
	}
};

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
