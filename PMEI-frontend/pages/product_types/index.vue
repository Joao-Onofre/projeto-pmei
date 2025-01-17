<template>
    <div class="container">
        <div class="wrapper">
            <div v-if="error" class="alert alert-danger">Error: {{ error.message }}</div>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <input type="text" v-model="searchQuery" class="search-bar" placeholder="Search by Product Type Name" />
            </div>
            <div class="action-buttons">
                <button class="btn btn-create" @click="redirectToCreatePage">Create New Product</button>
                <label for="file-upload" class="btn btn-import">Import Types</label>
                <input id="file-upload" type="file" @change="importProductType" style="display: none" />
                <button class="btn btn-export" @click="exportToCSV">Export to CSV</button>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Type Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="type in filteredTypes" :key="type.id">
                        <td>{{ type.id }}</td>
                        <td>{{ type.type }}</td>
                        <td>
                            <button class="btn btn-edit" @click="editProductType(type.id)">Edit</button>
                            <button class="btn btn-delete" @click="deleteProductType(type.id)">Delete</button>
                        </td>
                    </tr>
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

const config = useRuntimeConfig()
const api = config.public.API_URL
const searchQuery = ref('')

const { data: productTypes, error, refresh } = await useFetch(`${api}/product-type`)

const router = useRouter()

const filteredTypes = computed(() => {
    return productTypes.value
        ? productTypes.value.filter((type) =>
            type.type.toLowerCase().includes(searchQuery.value.toLowerCase())
        )
        : []
})

function editProductType(typeId) {
    router.push(`/product_types/update/${typeId}`)
}

async function deleteProductType(id) {
    try {
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        }
        const response = await $fetch(`${api}/product-type/${id}`, requestOptions)
        if (response) {
            await refresh()
        }
    } catch (err) {
        console.error('Error deleting product type:', err)
    }
}

function redirectToCreatePage() {
    router.push('/product_types/create')
}

async function importProductType(event) {
    const file = event.target.files[0]
    if (!file) return

    const formData = new FormData()
    formData.append('file', file)

    try {
        const response = await fetch(`${api}/product-type/import`, {
            method: 'POST',
            body: formData,
        })

        if (!response.ok) {
            const error = await response.json()
            throw new Error(error.message || 'Failed to import product types')
        }

        alert('Product types imported successfully')
        await refresh()
    } catch (err) {
        console.error('Error importing products types:', err)
        alert(`Error importing products types: ${err.message}`)
    }
}

function exportToCSV() {
    if (!productTypes.value || productTypes.value.length === 0) {
        alert("No products to export.");
        return;
    }

    const csvContent = [
        ["ID", "type"],
        ...productTypes.value.map(product => [
            product.id,
            product.type
        ]),
    ]
        .map(row => row.map(field => `"${field}"`).join(","))
        .join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    const url = URL.createObjectURL(blob);

    link.setAttribute("href", url);
    link.setAttribute("download", "productTypes.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
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
