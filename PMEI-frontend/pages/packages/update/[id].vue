<template>
	<div class="container mt-5">
		<h1>Update Package</h1>

		<!-- Package Type Selection -->
		<div class="form-group mt-3">
			<label for="packageTypeSelection">Select Package Type:</label>
			<div class="input-group">
				<select
					id="packageTypeSelection"
					v-model="order.packageType"
					class="form-control">
					<option v-for="packType in packageTypes" :key="packType.id" :value="packType.id">
						{{ packType.type }} (ID: {{ packType.id }})
					</option>
				</select>
			</div>
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
				<input
					type="number"
					class="form-control ml-2"
					v-model.number="productQuantity"
					placeholder="Quantity"
					min="1" />
				<button class="btn btn-primary ml-2" @click="addProductToPackage">Add</button>
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
					<tr v-for="(product, index) in order.packageProducts" :key="index">
						<td>{{ product.product }}</td>
						<td>{{ product.name }}</td>
						<td>{{ product.quantity }}</td>
						<td>
							<button class="btn btn-danger" @click="removeProduct(index)">Remove</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- Submit Button -->
		<button class="btn btn-success mt-4" @click="submitPackage">Update Package</button>
	</div>
</template>

<script setup>
import { ref } from "vue";

const config = useRuntimeConfig();
const api = config.public.API_URL;
const route = useRoute();
const package_id = route.params.id;

// Fetch products and package types
const { data: products, error: productErr } = await useFetch(`${api}/product`);
const { data: packageTypes, error: packTypeErr } = await useFetch(`${api}/packagetype`);

// Reactive order object
const order = ref({
	packageProducts: [],
	packageType: null,
});

const selectedProduct = ref(null);
const productQuantity = ref(1);

// Add a product to the package
const addProductToPackage = () => {
	if (!selectedProduct.value || productQuantity.value < 1) return;

	// Check if the product already exists in the package
	const existingProduct = order.value.packageProducts.find(
		(p) => p.product === selectedProduct.value.id
	);

	if (existingProduct) {
		existingProduct.quantity += productQuantity.value;
	} else {
		order.value.packageProducts.push({
			product: selectedProduct.value.id,
			name: selectedProduct.value.name,
			quantity: productQuantity.value,
		});
	}

	selectedProduct.value = null;
	productQuantity.value = 1;
};

// Remove a product from the package
const removeProduct = (index) => {
	order.value.packageProducts.splice(index, 1);
};

// Submit the updated package
const submitPackage = async () => {
	try {
		const response = await fetch(`${api}/package/${package_id}`, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				packageProducts: order.value.packageProducts.map((p) => ({
					product: p.product,
					quantity: p.quantity,
				})),
				packageType: order.value.packageType,
			}),
		});

		if (!response.ok) {
			throw new Error("Failed to update package");
		}

		alert("Package updated successfully!");
		order.value = { packageProducts: [], packageType: null };
	} catch (error) {
		console.error("Error updating package:", error);
		alert("Error updating package: " + error.message);
	}
};
</script>
