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
            <!-- New Button for Redirecting to Create Page -->
            <button class="btn btn-create" @click="redirectToCreatePage">Create New Product</button>
            <!-- Product Table -->
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Display filtered products -->
                    <tr v-for="product in filteredProducts" :key="product.id">
                        <td>{{ product.id }}</td>
                        <td>{{ product.name }}</td>
                        <td>{{ product.description }}</td>
                        <td>{{ product.productTypeName }}</td>
                        <td>{{ product.price.toFixed(2) }}€</td>
                        <td>
                            <button class="btn btn-edit" @click="editProduct(product.id)">Edit</button>
                            <button class="btn btn-delete" @click="deleteProduct(product.id)">Delete</button>
                        </td>
                    </tr>
                    <!-- Placeholder row when no products match the search -->
                    <tr v-if="filteredProducts.length === 0">
                        <td colspan="6">No products match your search.</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const config = useRuntimeConfig()
const api = config.public.API_URL
const searchQuery = ref('') // For the search input
const { data: products, error, refresh } = await useFetch(`${api}/product`) // Fetching products

const router = useRouter()

// Computed property for filtered products
const filteredProducts = computed(() => {
    // Return filtered products based on the search query
    return products.value.filter(
        (product) =>
            product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
            product.description.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
            product.productTypeName.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
})

// Redirect to the Update Product page
function editProduct(productId) {
    router.push(`/products/update/${productId}`)
}

// Delete a product and refresh the list
async function deleteProduct(id) {
    try {
        const requestOptions = {
            method: 'DELETE',
            headers: { "Content-Type": "application/json" },
        }
        const response = await $fetch(`${api}/product/${id}`, requestOptions)
        if (response) {
            await refresh() // Refresh the product list after a successful delete
        }
    } catch (err) {
        console.error('Error deleting product:', err)
    }
}

// Redirect to the Create Product page
function redirectToCreatePage() {
    router.push('/products/create')
}

</script>

<style scoped>
.wrapper {
    padding: 50px;
}

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

.btn:hover {
    opacity: 0.9;
}
</style>
