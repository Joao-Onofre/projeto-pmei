<template>
	<div class="container mt-5">
		<div v-if="pack" class="mt-5">
			<h2>Order Details:</h2>
			<p><strong>Package ID:</strong> {{ pack.id }}</p>
			<p><strong>Package Type:</strong> {{ pack.packageTypeName }}</p>
			<p><strong>Order ID:</strong> {{ pack.order }}</p>
		</div>
		<nuxt-link to="/packages" class="btn btn-outline-dark mt-4">Return</nuxt-link>
		<hr>
		<div v-if="pack.packageProducts && pack.packageProducts.length">
			<h2 class="mb-4">Products:</h2>
			<table class="table">
				<thead class="thead-dark text-center">
					<tr>
						<th>Product ID</th>
						<th>Name</th>
						<th>Quantity</th>
					</tr>
				</thead>
				<tbody class="text-center">
					<tr v-for="product in pack.packageProducts" :key="product.id">
						<td>{{ product.id }}</td>
						<td>{{ product.productName }}</td>
						<td>{{ product.quantity }}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<hr>
		<div v-if="pack.sensorList && pack.sensorList.length">
			<h2 class="mb-4">Sensors:</h2>
			<table class="table">
				<thead class="thead-dark text-center">
					<tr>
						<th>Sensor ID</th>
						<th>Type</th>
						<th>Status</th>
						<th>Value</th>
					</tr>
				</thead>
				<tbody class="text-center">
					<tr v-for="sensor in pack.sensorList" :key="sensor.id">
						<td>{{ sensor.sensorId }}</td>
						<td>{{ sensor.sensorType }}</td>
						<td>{{ sensor.statusType }}</td>
						<td>{{ sensor.currentValue }}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div v-if="messages.length" class="mt-5">
			<h4 class="text-warning">Error messages:</h4>
			<ul class="list-group">
				<li v-for="message in messages" :key="message" class="list-group-item bg-dark text-warning">{{ message }}</li>
			</ul>
		</div>
	</div>
</template>

<script setup>

import { ref } from "vue"
import { useRoute, useRuntimeConfig, useFetch } from "#imports"

const route = useRoute()
const package_id = route.params.id

const config = useRuntimeConfig()
const api = config.public.API_URL

// Reactive state for messages
const messages = ref([])

// Fetch order data
const { data: pack, error: packErr } = await useFetch(`${api}/package/${package_id}`)

if (packErr.value) messages.value.push(packErr.value)


</script>
