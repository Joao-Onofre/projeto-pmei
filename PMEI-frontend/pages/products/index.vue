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
            <!-- Buttons for actions -->
            <div class="action-buttons">
                <!-- Show create and import buttons only if user is not a Customer -->
                <button v-if="userType !== 'Customer'" class="btn btn-create" @click="redirectToCreatePage">Create New
                    Product</button>
                <label v-if="userType !== 'Customer'" for="file-upload" class="btn btn-import">Import Products</label>
                <input id="file-upload" type="file" @change="importProducts" style="display: none" />
                <button class="btn btn-export" @click="exportToCSV">Export to CSV</button>
            </div>
            <!-- Product Table -->
            <table class="table">
                <thead>
                    <tr>
                        <th style="width: 5%;">ID</th>
                        <th style="width: 15%;">Name</th>
                        <th style="width: 20%;">Description</th>
                        <th style="width: 15%;">Type</th>
                        <th style="width: 10%;">Price</th>
                        <th style="width: 18%;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="product in filteredProducts" :key="product.id">
                        <td>{{ product.id }}</td>
                        <td>{{ product.name }}</td>
                        <td>{{ product.description }}</td>
                        <td>{{ product.productTypeName }}</td>
                        <td>{{ product.price.toFixed(2) }}€</td>
                        <td>
                            <!-- Show "Add to Cart" button for all users -->
                            <button v-if="userType === 'Customer'" class="btn btn-success"
                                @click="addToCart(product.id)">Add to Cart</button>
                            <!-- Show "Edit" and "Delete" buttons only if user is not a Customer -->
                            <button v-if="userType !== 'Customer'" class="btn btn-edit"
                                @click="editProduct(product.id)">Edit</button>
                            <button v-if="userType !== 'Customer'" class="btn btn-delete"
                                @click="deleteProduct(product.id)">Delete</button>
                        </td>
                    </tr>
                    <tr v-if="filteredProducts.length === 0">
                        <td colspan="6">No products match your search.</td>
                    </tr>
                </tbody>
            </table>

        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

const config = useRuntimeConfig();
const api = config.public.API_URL;
const searchQuery = ref('');
const { data: products, error, refresh } = await useFetch(`${api}/product`);

const router = useRouter();

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

const filteredProducts = computed(() => {
    return products.value.filter(
        (product) =>
            product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
            product.description.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
            product.productTypeName.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
});

function editProduct(productId) {
    router.push(`/products/update/${productId}`);
}

async function deleteProduct(id) {
    try {
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        };
        const response = await $fetch(`${api}/product/${id}`, requestOptions);
        if (response) {
            await refresh();
        }
    } catch (err) {
        console.error('Error deleting product:', err);
    }
}

function redirectToCreatePage() {
    router.push('/products/create');
}

async function importProducts(event) {
    const file = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch(`${api}/product/import`, {
            method: 'POST',
            body: formData,
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Failed to import products');
        }

        await refresh();
    } catch (err) {
        console.error('Error importing products:', err);
        alert(`Error importing products: ${err.message}`);
    }
}

async function addToCart(productId) {
    try {
        const response = await fetch(`${api}/cart/customer/${username}/add?productId=${productId}`, {
            method: 'POST',
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error('Backend Error:', errorText);
            throw new Error(errorText || 'Failed to add product to cart');
        }
    } catch (err) {
        console.error('Error adding product to cart:', err);
        alert(`Error adding product to cart: ${err.message}`);
    }
}

function exportToCSV() {
    if (!products.value || products.value.length === 0) {
        alert('No products to export.');
        return;
    }

    const csvContent = [
        ['ID', 'Name', 'Description', 'Type', 'Price'],
        ...products.value.map((product) => [
            product.id,
            product.name,
            product.description,
            product.productTypeName,
            product.price.toFixed(2),
        ]),
    ]
        .map((row) => row.map((field) => `"${field}"`).join(','))
        .join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);

    link.setAttribute('href', url);
    link.setAttribute('download', 'products.csv');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
</script>

<style scoped>
.search-container {
    margin-bottom: 20px;
    display: flex;
    justify-content: flex-start;
}

.search-bar {
    padding: 8px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
    width: 100%;
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

/* Button styles */
.btn {
    padding: 6px 12px;
    margin: 0 4px;
    border: none;
    cursor: pointer;
    font-size: 14px;
}

.btn-edit {
    background-color: #007bff;
    color: white;
    border-radius: 4px;
}

.btn-delete {
    background-color: #dc3545;
    color: white;
    border-radius: 4px;
}

.btn-create {
    background-color: #28a745;
    color: white;
    border-radius: 4px;
    font-size: 14px;
}

.btn-import {
    background-color: #ffc107;
    color: white;
    border-radius: 4px;
    font-size: 14px;
}

.btn:hover {
    opacity: 0.9;
}

.btn-export {
    background-color: #17a2b8;
    color: white;
    border-radius: 4px;
    font-size: 14px;
}

.btn-export:hover {
    opacity: 0.9;
}
</style>
