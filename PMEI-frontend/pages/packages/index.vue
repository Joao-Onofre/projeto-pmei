<template>
	<div class="container mt-5">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2>Packages</h2>
			<nuxt-link to="/packages/create" class="btn btn-light btn-lg">Create a New Package</nuxt-link>
		</div>
		<table class="order-table">
			<thead class="thead-dark text-center">
				<tr>
					<th>ID</th>
					<th>Package Type</th>
					<th>Total Sensors</th>
					<th>Total Products</th>
					<th>Order ID</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody class="text-center">
				<!-- Display filtered orders -->
				<tr v-for="pack in packages" :key="pack.id">
					<td>{{ pack.id }}</td>
					<td>{{ pack.packageTypeName }}</td>
					<td>{{ totalSensors(pack) }}</td>
					<td>{{ totalProducts(pack) }}</td>
					<td>{{ pack.order }}</td>
					<td class="d-flex gap-2 justify-content-center">
						<button @click.prevent="deletePackage(pack.id)" class="btn btn-outline-danger btn-sm">Delete</button>
						<nuxt-link :to="`/packages/${pack.id}`" class="btn btn-outline-dark btn-sm">Details</nuxt-link>
						<!--<nuxt-link :to="`/packages/update/${pack.id}`" class="btn btn-outline-info btn-sm">Update</nuxt-link>-->
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
