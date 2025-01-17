<template>
	<div class="container">
		<div class="wrapper">
			<!-- Error Display -->
			<div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>

			<!-- Search Bar and Create Button -->
			<div class="d-flex justify-content-between align-items-center mb-3">
				<input type="text" v-model="searchQuery" class="search-bar"
					placeholder="Search by Product Name, Type, or Description" />
			</div>
			<div class="action-buttons">
				<nuxt-link to="/packages/create" class="btn btn-create">Create New Package</nuxt-link>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>ID</th>
						<th>Package Type</th>
						<th>Total Sensors</th>
						<th>Total Products</th>
						<th>Order ID</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<!-- Display filtered orders -->
					<tr v-for="pack in packages" :key="pack.id">
						<td>{{ pack.id }}</td>
						<td>{{ pack.packageTypeName }}</td>
						<td>{{ totalSensors(pack) }}</td>
						<td>{{ totalProducts(pack) }}</td>
						<td>{{ pack.order }}</td>
						<td>
							<nuxt-link :to="`/packages/${pack.id}`" class="btn btn-dark">Details</nuxt-link>
							<button @click.prevent="deletePackage(pack.id)" class="btn btn-delete">Delete</button>
							<nuxt-link :to="`/packages/update/${pack.id}`" class="btn btn-edit">Update</nuxt-link>
						</td>
					</tr>
					<tr v-if="packages.length === 0">
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

// Reactive state for orders
const packages = ref([]);
const getPackages = async () => {
	try {
		const response = await fetch(`${api}/package`);
		packages.value = await response.json();
	} catch (error) {
		console.error("Error fetching packages:", error);
	}
}

console.log(packages.value)

// Calculate total sensors in a package
const totalSensors = (pack) => {
	return pack.sensorList ? pack.sensorList.length : 0;
};

// Calculate total products in a package
const totalProducts = (pack) => {
	return pack.packageProducts
		? pack.packageProducts.reduce((total, product) => total + product.quantity, 0)
		: 0;
};


async function deletePackage(id) {
	try {
		const requestOptions = {
			method: 'DELETE',
			headers: { 'Content-Type': 'application/json' },
		}
		const response = await fetch(`${api}/package/${id}`, requestOptions)
		console.log(response)

		if (!response.ok) {
			throw new Error('Failed to delete the package.')
		}

		// Refresh the data after successful deletion
		await getPackages()

	} catch (error) {
		console.error('Error deleting package:', error.message)
		// Optionally display the error in the UI
	}
}

// Fetch orders when the component is mounted
getPackages();
</script>
