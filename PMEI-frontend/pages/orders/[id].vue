<template>
	<div class="container mt-5">
		<div v-if="order" class="mt-5">
			<h2>Order Details:</h2>
			<p><strong>Order ID:</strong> {{ order.code }}</p>
			<p><strong>Status:</strong> {{ order.statusName }}</p>
			<p><strong>Customer:</strong> {{ order.customerUsername }}</p>
			<p><strong>Creation Date:</strong>
				{{ new Date(order.creationDate).toLocaleString() }}
			</p>
			<p><strong>Delivery Date:</strong>
				{{ new Date(order.deliveryDate).toLocaleString() }}
			</p>
			<p><strong>Active:</strong> {{ order.terminated }}</p>
			<p><strong>Termination Date:</strong>
				{{ new Date(order.terminationDate).toLocaleString() }}
			</p>
		</div>
		<nuxt-link to="/orders" class="btn btn-outline-dark mt-4">Return</nuxt-link>
		<hr>
		<div v-if="order.packageList && order.packageList.length">
			<h2 class="mb-4">Packages:</h2>
			<table class="table">
				<thead class="thead-dark text-center">
					<tr>
						<th>Package ID</th>
						<th>Package Type</th>
					</tr>
				</thead>
				<tbody class="text-center">
					<tr v-for="pack in order.packageList" :key="pack.id">
						<td>{{ pack.id }}</td>
						<td>{{ pack.packageTypeName }}</td>
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
const order_id = route.params.id

const config = useRuntimeConfig()
const api = config.public.API_URL

// Reactive state for messages
const messages = ref([])

// Fetch order data
const { data: order, error: orderErr } = await useFetch(`${api}/order/${order_id}`)

if (orderErr.value) messages.value.push(orderErr.value)


</script>
