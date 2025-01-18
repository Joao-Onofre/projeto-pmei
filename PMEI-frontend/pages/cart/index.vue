<template>
	<div class="container">
		<div class="wrapper">
			<!-- Error Display -->
			<div v-if="error" class="alert alert-danger">Error: {{ error }}</div>

			<!-- Buttons for actions -->
			<div class="action-buttons mb-3">
				<button class="btn btn-export" @click="exportToCSV">Export to CSV</button>
				<button class="btn btn-create" @click="submitOrder">Create Order</button>
			</div>

			<!-- Cart Table Wrapper for Horizontal Scroll -->
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th width="5%">ID</th>
							<th width="9%">Name</th>
							<th width="16%">Description</th>
							<th width="7%">Type</th>
							<th width="4%">Price</th>
							<th width="6%">Amount</th>
							<th width="14%">Actions</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="product in cart.products" :key="product.id">
							<td>{{ product.id }}</td>
							<td>{{ product.name }}</td>
							<td>{{ product.description }}</td>
							<td>{{ product.productTypeName }}</td>
							<td>{{ (product.price * product.quantity).toFixed(2) }}€</td>
							<td>{{ product.quantity }}</td>
							<td>
								<button class="btn btn-increment" @click="incrementProduct(product.id)">+</button>
								<button class="btn btn-decrement" @click="decrementProduct(product.id)">-</button>
								<button class="btn btn-delete" @click="deleteProduct(product.id)">Delete</button>
							</td>
						</tr>
						<tr v-if="!cart.products || cart.products.length === 0">
							<td colspan="7">Your cart is empty.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'

const router = useRouter()
const config = useRuntimeConfig();
const api = config.public.API_URL;
const error = ref(null);
const cart = ref({ id: null, customerId: '', products: [] });

let username = null;

if (typeof window !== 'undefined') {
	username = localStorage.getItem('username');
	if (!username) {
		error.value = 'No username found. Please log in.';
	}
}

const order = ref({
	customerUsername: username, // Textbox for customer username
	productList: [],
});

const fetchCart = async () => {
	if (!username) {
		error.value = 'No username found. Please log in.';
		return;
	}

	try {
		const response = await fetch(`${api}/cart/customer/${username}`);
		if (response.ok) {
			// If the cart exists, parse and set it
			const data = await response.json();
			cart.value = data;
		} else if (response.status === 404) {
			// If the cart does not exist, initialize an empty cart
			cart.value = { id: null, customerId: username, products: [] };
		} else {
			// Handle other server errors
			throw new Error('Failed to fetch cart');
		}
	} catch (err) {
		error.value = err.message;
	}
};

const incrementProduct = async (productId) => {
	if (!username) return;
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
	if (!username) return;
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
	if (!username) return;
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
console.log(cart)
const submitOrder = async () => {
	try {
		// Populate the order productList from the cart
		order.value.productList = cart.value.products.map((products) => ({
			id: products.id, // Use 'product' key for the product ID
			quantity: products.quantity, // Map quantity from the cart
		}));

		// Validate if the productList is not empty
		if (!order.value.productList.length) {
			alert("Your cart is empty. Add products to the cart before placing an order.");
			return;
		}

		// Make the POST request
		let response = await fetch(`${api}/order`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(order.value),
		});

		if (!response.ok) {
			throw new Error("Failed to create order");
		}

		response = await fetch(`${api}/cart/customer/${username}/clear`, {
			method: "DELETE",
		});


		alert("Order created successfully!");
		order.value = { customerUsername: username, productList: [] }; // Reset the order
		router.push('/orders')
	} catch (error) {
		console.error("Error creating order:", error);
		alert("Error creating order: " + error.message);
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
		.map((row) => row.map((field) => "${field}").join(',')) // Format as CSV
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


onMounted(async () => {
	if (typeof window !== 'undefined') {
		const token = localStorage.getItem('jwt_token');
		if (!token) {
			// Redirect to auth page if not authenticated
			router.push('/auth');
		} else {
			// Proceed with fetching the cart
			await fetchCart();
		}
	}
});
</script>

<style scoped>
.wrapper {
	margin-top: 15px;
}

.action-buttons {
	margin-top: 10px;
	display: flex;
	gap: 10px;
}

.table-responsive {
	overflow-x: auto;
	/* Enable horizontal scrolling */
	-webkit-overflow-scrolling: touch;
	/* Smooth scrolling on touch devices */
	width: 100%;
}

.table {
	width: 100%;
	/* Ensure the table spans the full width */
	border-collapse: collapse;
	margin: 20px 0;
	table-layout: auto;
	/* Let the table resize dynamically */
}

.table th,
.table td {
	border: 1px solid #ddd;
	padding: 8px;
	white-space: nowrap;
	/* Prevent wrapping of table content */
	text-align: left;
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