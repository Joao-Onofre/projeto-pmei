<template>
    <div class="container">
        <div class="wrapper">
            <!-- Error Display -->
            <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>

            <!-- Search Bar and Create Button -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <input type="text" v-model="searchQuery" class="search-bar" placeholder="Search by Product Type Name" />
            </div>
            <!-- Button for Redirecting to Create Page -->
            <button class="btn btn-create" @click="redirectToCreatePage">Create New Product Type</button>
            <!-- Product Type Table -->
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Type Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Display filtered product types -->
                    <tr v-for="type in filteredTypes" :key="type.id">
                        <td>{{ type.id }}</td>
                        <td>{{ type.type }}</td>
                        <td>
                            <button class="btn btn-edit" @click="editProductType(type.id)">Edit</button>
                            <button class="btn btn-delete" @click="deleteProductType(type.id)">Delete</button>
                        </td>
                    </tr>
                    <!-- Placeholder row when no types match the search -->
                    <tr v-if="filteredTypes.length === 0">
                        <td colspan="3">No product types match your search.</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

// Configuration and API endpoint
const config = useRuntimeConfig()
const api = config.public.API_URL
const searchQuery = ref('') // For the search input

// Fetching product types
const { data: productTypes, error, refresh } = await useFetch(`${api}/product-type`)

const router = useRouter()

// Computed property for filtered product types
const filteredTypes = computed(() => {
    return productTypes.value
        ? productTypes.value.filter((type) =>
            type.type.toLowerCase().includes(searchQuery.value.toLowerCase())
        )
        : []
})

// Redirect to the Update Product Type page
function editProductType(typeId) {
    router.push(`/product_types/update/${typeId}`)
}

// Delete a product type and refresh the list
async function deleteProductType(id) {
    try {
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        }
        const response = await $fetch(`${api}/product-type/${id}`, requestOptions)
        if (response) {
            await refresh() // Refresh the list after successful deletion
        }
    } catch (err) {
        console.error('Error deleting product type:', err)
    }
}

// Redirect to the Create Product Type page
function redirectToCreatePage() {
    router.push('/product_types/create')
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
