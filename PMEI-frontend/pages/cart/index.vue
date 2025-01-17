<template>
	<div class="container">
		<div class="wrapper">
			<!-- Error Display -->
			<div v-if="error" class="alert alert-danger">Error: {{ error }}</div>

			<!-- Buttons for actions -->
			<div class="action-buttons mb-3">
				<button class="btn btn-export" @click="exportToCSV">Export to CSV</button>
			</div>

			<!-- Cart Table -->
			<table class="table">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Description</th>
						<th>Type</th>
						<th>Price</th>
						<th>Quantity</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<!-- Display cart items -->
					<tr v-for="product in cart.products" :key="product.id">
						<td>{{ product.id }}</td>
						<td>{{ product.name }}</td>
						<td>{{ product.description }}</td>
						<td>{{ product.productTypeName }}</td>
						<td>{{ product.price.toFixed(2) * product.quantity }}€</td>
						<td>{{ product.quantity }}</td>
						<td>
							<button class="btn btn-increment" @click="incrementProduct(product.id)">+</button>
							<button class="btn btn-decrement" @click="decrementProduct(product.id)">-</button>
							<button class="btn btn-delete" @click="deleteProduct(product.id)">Delete</button>
						</td>
					</tr>
					<!-- Placeholder row when cart is empty -->
					<tr v-if="!cart.products || cart.products.length === 0">
						<td colspan="7">Your cart is empty.</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const config = useRuntimeConfig()
const api = config.public.API_URL
const username = 'customer1';
const error = ref(null);
const cart = ref({ id: null, customerId: '', products: [] });

const fetchCart = async () => {
	try {
		const response = await fetch(`${api}/cart/customer/${username}`);
		if (!response.ok) throw new Error('Failed to fetch cart');
		const data = await response.json();
		cart.value = data;
	} catch (err) {
		error.value = err.message;
	}
};

const incrementProduct = async (productId) => {
	try {
		const response = await fetch(`${api}/cart/customer/${username}/add?productId=${productId}`, {
			method: 'POST',
		});
		if (!response.ok) throw new Error('Failed to increment product');
		await fetchCart();
	} catch (err) {
		error.value = err.message;
	}
};

const decrementProduct = async (productId) => {
	try {
		const response = await fetch(`${api}/cart/customer/${username}/remove/${productId}`, {
			method: 'DELETE',
		});
		if (!response.ok) throw new Error('Failed to decrement product');
		await fetchCart();
	} catch (err) {
		error.value = err.message;
	}
};

const deleteProduct = async (productId) => {
	try {
		while (true) {
			const response = await fetch(`${api}/cart/customer/${username}/remove/${productId}`, {
				method: 'DELETE',
			});
			if (!response.ok) throw new Error('Failed to delete product');
			const updatedCart = await response.json();
			if (!updatedCart.products.find((p) => p.id === productId)) break;
		}
		await fetchCart();
	} catch (err) {
		error.value = err.message;
	}
};

// Export cart data to CSV
const exportToCSV = () => {
	if (!cart.value.products || cart.value.products.length === 0) {
		alert('No products to export.');
		return;
	}

	const csvContent = [
		['ID', 'Name', 'Description', 'Type', 'Price', 'Quantity'], // CSV Header
		...cart.value.products.map((product) => [
			product.id,
			product.name,
			product.description,
			product.productTypeName,
			product.price.toFixed(2),
			product.quantity,
		]),
	]
		.map((row) => row.map((field) => `"${field}"`).join(',')) // Format as CSV
		.join('\n');

	const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
	const link = document.createElement('a');
	const url = URL.createObjectURL(blob);

	link.setAttribute('href', url);
	link.setAttribute('download', 'cart.csv');
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
};

// Fetch cart data on mount
onMounted(fetchCart);
</script>

<style scoped>
.wrapper {
	padding: 50px;
	margin-top: 40px;
}

.action-buttons {
	margin-top: 10px;
	display: flex;
	gap: 10px;
}

.table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
	table-layout: fixed;
}

.table th,
.table td {
	border: 1px solid #ddd;
	padding: 8px;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
}

.table th {
	background-color: #f4f4f4;
	font-weight: bold;
}

.btn {
	padding: 6px 12px;
	margin: 0 4px;
	border: none;
	cursor: pointer;
	font-size: 14px;
}

.btn-increment {
	background-color: #28a745;
	color: white;
	border-radius: 4px;
}

.btn-decrement {
	background-color: #ffc107;
	color: white;
	border-radius: 4px;
}

.btn-delete {
	background-color: #dc3545;
	color: white;
	border-radius: 4px;
}

.btn-export {
	background-color: #17a2b8;
	color: white;
	border-radius: 4px;
}

.btn:hover {
	opacity: 0.9;
}
</style>
