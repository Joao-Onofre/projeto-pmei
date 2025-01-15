<template>
    <div class="wrapper">
        <h2 class="mb-3">Update Product</h2>
        <form @submit.prevent="update" class="form">
            <!-- Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Name:</label>
                <input v-model.trim="productForm.name" type="text" id="name" class="form-control" />
                <span v-if="nameError" class="text-danger">ERROR: {{ nameError }}</span>
            </div>

            <!-- Description -->
            <div class="mb-3">
                <label for="description" class="form-label">Description:</label>
                <input v-model.trim="productForm.description" type="text" id="description" class="form-control" />
                <span v-if="descriptionError" class="text-danger">ERROR: {{ descriptionError }}</span>
            </div>

            <!-- Price -->
            <div class="mb-3">
                <label for="price" class="form-label">Price:</label>
                <input v-model.trim="productForm.price" type="number" step="0.01" id="price" class="form-control" />
                <span v-if="priceError" class="text-danger">ERROR: {{ priceError }}</span>
            </div>

            <!-- Product Type -->
            <div class="mb-3">
                <label for="productType" class="form-label">Product Type:</label>
                <select v-model="productForm.productTypeId" id="productType" class="form-select">
                    <option value="">--- Please select Product Type ---</option>
                    <option v-for="type in productTypes" :key="type.id" :value="type.id">{{ type.type }}</option>
                </select>
                <span v-if="productTypeError" class="text-danger">ERROR: {{ productTypeError }}</span>
            </div>

            <!-- Buttons -->
            <div class="d-flex justify-content-between">
                <button type="submit" :disabled="isFormInvalid" class="btn btn-primary">UPDATE</button>
                <nuxt-link to="/products" class="btn btn-link">Return</nuxt-link>
            </div>
        </form>

        <!-- Display message -->
        <div v-if="message" class="alert alert-info mt-3">{{ message }}</div>
    </div>
</template>
<script setup>
import { ref, reactive, computed, watchEffect } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const id = route.params.id
const config = useRuntimeConfig()
const api = config.public.API_URL

// Reactive product form
const productForm = reactive({
    name: '',
    description: '',
    price: null,
    productTypeId: null,
})

const message = ref('')
const isLoading = ref(true)
const error = ref(null)

// Fetch product details and product types
const { data: productTypes } = await useFetch(`${api}/product-type`)
const { data: product, error: productError } = await useFetch(`${api}/product/${id}`)

// Initialize product form when product data is fetched
watchEffect(() => {
    if (product?.value && !productError.value) {
        // Ensure price is a number
        productForm.name = product.value.name || ''
        productForm.description = product.value.description || ''
        productForm.price = parseFloat(product.value.price) || null
        productForm.productTypeId = product.value.productTypeId || null
        isLoading.value = false
    } else if (productError.value) {
        error.value = productError.value
        isLoading.value = false
    }
})

// Validation rules
const nameError = computed(() => (!productForm.name ? 'Name is required' : null))
const descriptionError = computed(() => (!productForm.description ? 'Description is required' : null))
const priceError = computed(() => (!productForm.price || productForm.price <= 0 ? 'Price must be greater than zero' : null))
const productTypeError = computed(() => (!productForm.productTypeId ? 'Product Type is required' : null))

const isFormInvalid = computed(() => nameError.value || descriptionError.value || priceError.value || productTypeError.value)

// Update product
async function update() {
    try {
        // Format price before sending
        productForm.price = parseFloat(productForm.price.toString().replace(',', '.')) || 0

        // Send the update request
        const response = await $fetch(`${api}/product/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(productForm),
        })

        // Check if the response indicates success
        if (response) {
            router.push('/products')
        } else {
            message.value = 'Failed to update the product.'
        }
    } catch (err) {
        console.error('Update error:', err)
        message.value = 'An unexpected error occurred.'
    }
}

</script>
