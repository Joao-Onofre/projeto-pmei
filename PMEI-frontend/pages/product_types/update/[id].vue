<template>
    <div class="wrapper">
        <div class="container">
            <h2 class="mb-3">Update Product Type</h2>
            <form @submit.prevent="update" class="form">
                <!-- Type -->
                <div class="mb-3">
                    <label for="type" class="form-label">Type:</label>
                    <input v-model.trim="productTypeForm.type" type="text" id="type" class="form-control" />
                    <span v-if="typeError" class="text-danger">ERROR: {{ typeError }}</span>
                </div>

                <!-- Buttons -->
                <div class="d-flex justify-content-between">
                    <button type="submit" :disabled="isFormInvalid" class="btn btn-primary">UPDATE</button>
                    <nuxt-link to="/product-types" class="btn btn-link">Return</nuxt-link>
                </div>
            </form>

            <!-- Display message -->
            <div v-if="message" class="alert alert-info mt-3">{{ message }}</div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, computed, watchEffect } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const id = route.params.id // The ID of the product type to update
const config = useRuntimeConfig()
const api = config.public.API_URL

// Reactive form for product type
const productTypeForm = reactive({
    type: '',
})

const message = ref('')
const isLoading = ref(true)
const error = ref(null)

// Fetch the product type details
const { data: productType, error: productTypeError } = await useFetch(`${api}/product-type/${id}`)

// Initialize the form when the product type data is fetched
watchEffect(() => {
    if (productType?.value && !productTypeError.value) {
        productTypeForm.type = productType.value.type || ''
        isLoading.value = false
    } else if (productTypeError.value) {
        error.value = productTypeError.value
        isLoading.value = false
    }
})

// Validation rules
const typeError = computed(() => (!productTypeForm.type ? 'Type is required' : null))
const isFormInvalid = computed(() => typeError.value)

// Update product type
async function update() {
    try {
        // Send the update request
        const response = await $fetch(`${api}/product-type/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(productTypeForm),
        })

        // Check if the response indicates success
        if (response) {
            message.value = 'Product Type updated successfully!'
            router.push('/product_types')
        }
    } catch (err) {
        console.error('Update error:', err)
        message.value = 'An unexpected error occurred.'
    }
}
</script>

<style scoped>
.text-danger {
    color: red;
}

.btn {
    padding: 6px 12px;
    margin: 5px;
    border: none;
    cursor: pointer;
    font-size: 14px;
}

.btn-primary {
    background-color: #007bff;
    color: white;
    border-radius: 4px;
}

.btn-link {
    background: none;
    border: none;
    color: #007bff;
    cursor: pointer;
    text-decoration: underline;
}
</style>
