<template>
	<div class="container mt-5">
		<h1>Create Order</h1>

		<!-- Customer Username Input -->
		<div class="form-group">
			<label for="customerUsername">Customer Username:</label>
			<input type="text" id="customerUsername" v-model="order.customerUsername" class="form-control" placeholder="Enter Customer Username" />
		</div>

		<!-- Product Selection -->
		<div class="form-group mt-3">
			<label for="productSelection">Select Product:</label>
			<div class="input-group">
				<select
					id="productSelection"
					v-model="selectedProduct"
					class="form-control">
					<option v-for="product in products" :key="product.id" :value="product">
						{{ product.name }} (ID: {{ product.id }})
					</option>
				</select>
				<input type="number" class="form-control ml-2" v-model.number="productQuantity" placeholder="Quantity" min="1" />
				<button class="btn btn-primary ml-2" @click="addProductToOrder">Add</button>
			</div>
		</div>
		<nuxt-link to="/orders" class="btn btn-link">Return</nuxt-link>

		<!-- Selected Products Table -->
		<div class="mt-4">
			<h4>Selected Products:</h4>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Product ID</th>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(product, index) in order.productList" :key="index">
						<td>{{ product.id }}</td>
						<td>{{ product.name }}</td>
						<td>{{ product.quantity }}</td>
						<td>
							<button class="btn btn-delete" @click="removeProduct(index)">Remove</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- Submit Button -->
		<button class="btn btn-success mt-4" @click="submitOrder">Create Order</button>
	</div>
</template>

<script setup>
import { ref } from "vue";

const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: products, error: productErr } = await useFetch(`${api}/product`)

const order = ref({
	customerUsername: "", // Textbox for customer username
	productList: [],
});

const selectedProduct = ref(null);
const productQuantity = ref(1);

// Add the selected product to the order
const addProductToOrder = () => {
	if (!selectedProduct.value || productQuantity.value < 1) return;

	const existingProduct = order.value.productList.find(
		(p) => p.id === selectedProduct.value.id
	);

	if (existingProduct) {
		existingProduct.quantity += productQuantity.value;
	} else {
		order.value.productList.push({
			id: selectedProduct.value.id,
			quantity: productQuantity.value,
		});
	}

	selectedProduct.value = null;
	productQuantity.value = 1;
};

// Remove a product from the order
const removeProduct = (index) => {
	order.value.productList.splice(index, 1);
};

// Submit the order
const submitOrder = async () => {
	try {
		const response = await fetch(`${api}/order`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(order.value),
		});

		if (!response.ok) {
			throw new Error("Failed to create order");
		}

		alert("Order created successfully!");
		order.value = { customerUsername: "", productList: [] };
	} catch (error) {
		console.error("Error creating order:", error);
		alert("Error creating order: " + error.message);
	}
};
</script>