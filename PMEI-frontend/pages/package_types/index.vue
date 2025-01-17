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
			<button class="btn btn-create" @click="redirectToCreatePage">Create New Package Type</button>
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
					<tr v-for="type in productTypes" :key="type.id">
						<td>{{ type.id }}</td>
						<td>{{ type.type }}</td>
						<td>
							<button class="btn btn-edit" @click="editPackageType(type.id)">Edit</button>
							<button class="btn btn-delete" @click="deletePackageType(type.id)">Delete</button>
						</td>
					</tr>
					<!-- Placeholder row when no types match the search -->
					<tr v-if="productTypes.length === 0">
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
const { data: productTypes, error, refresh } = await useFetch(`${api}/packagetype`)

const router = useRouter()

// Redirect to the Update Product Type page
function editPackageType(typeId) {
	router.push(`/package_types/update/${typeId}`)
}

// Redirect to the Create Product Type page
function redirectToCreatePage() {
	router.push('/package_types/create')
}
</script>