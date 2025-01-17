<template>
    <div class="wrapper">
        <h2 class="mb-3">Create Product</h2>
        <form @submit.prevent="create" class="form">
            <!-- Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Name:</label>
                <input v-model.trim="productForm.name" type="text" id="name" class="form-control">
                <span v-if="nameError" class="text-danger">
                    ERROR: {{ nameError }}
                </span>
            </div>

            <!-- Description -->
            <div class="mb-3">
                <label for="description" class="form-label">Description:</label>
                <input v-model.trim="productForm.description" type="text" id="description" class="form-control">
                <span v-if="descriptionError" class="text-danger">
                    ERROR: {{ descriptionError }}
                </span>
            </div>

            <!-- Price -->
            <div class="mb-3">
                <label for="price" class="form-label">Price:</label>
                <input v-model.trim="productForm.price" type="number" step="0.01" id="price" class="form-control">
                <span v-if="priceError" class="text-danger">
                    ERROR: {{ priceError }}
                </span>
            </div>

            <!-- Product Type -->
            <div class="mb-3">
                <label for="productType" class="form-label">Product Type:</label>
                <select v-model="productForm.productTypeId" id="productType" class="form-select">
                    <option value="">--- Please select Product Type ---</option>
                    <option v-for="type in productTypes" :key="type.id" :value="type.id">
                        {{ type.type }}
                    </option>
                </select>
                <span v-if="productTypeError" class="text-danger">
                    ERROR: {{ productTypeError }}
                </span>
            </div>

            <!-- Buttons -->
            <div class="d-flex justify-content-between">
                <button type="reset" class="btn btn-secondary">RESET</button>
                <button type="submit" :disabled="isFormInvalid" class="btn btn-primary">CREATE</button>
                <nuxt-link to="/products" class="btn btn-link">Return</nuxt-link>
            </div>
        </form>

        <!-- Display messages -->
        <div v-if="messages.length" class="alert alert-info mt-3">
            <pre>{{ messages }}</pre>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const productForm = reactive({
    name: null,
    description: null,
    price: null,
    productTypeId: null,
})
const messages = ref([])
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: productTypes } = await useFetch(`${api}/product-type`)

// Field validation rules
const nameError = computed(() => {
    if (productForm.name === null) return null
    if (!productForm.name) return 'Name is required'
    return null
})
const descriptionError = computed(() => {
    if (productForm.description === null) return null
    if (!productForm.description) return 'Description is required'
    return null
})
const priceError = computed(() => {
    if (productForm.price === null) return null
    if (!productForm.price) return 'Price is required'
    if (productForm.price <= 0) return 'Price must be greater than zero'
    return null
})
const productTypeError = computed(() => {
    if (productForm.productTypeId === null) return null
    if (!productForm.productTypeId) return 'Product Type is required'
    return null
})
const isFormInvalid = computed(() => {
    return nameError.value || descriptionError.value || priceError.value || productTypeError.value
})

// Create product function
async function create() {
    try {
        await $fetch(`${api}/product`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: productForm,
            onResponse({ request, response, options }) {
                messages.value.push({
                    method: options.method,
                    request: request,
                    status: response.status,
                    statusText: response.statusText,
                    payload: response._data,
                })
            },
        })
        router.push('/products')
    } catch (e) {
        console.log(e)
    }
}
</script>
