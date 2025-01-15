<template>
    <div class="wrapper">
        <div class="container">
            <h2 class="mb-3">Create Product Type</h2>
            <form @submit.prevent="create" class="form">
                <!-- Type -->
                <div class="mb-3">
                    <label for="type" class="form-label">Type:</label>
                    <input v-model.trim="productTypeForm.type" type="text" id="type" class="form-control">
                    <span v-if="typeError" class="text-danger">
                        ERROR: {{ typeError }}
                    </span>
                </div>

                <!-- Buttons -->
                <div class="d-flex justify-content-between">
                    <button type="reset" class="btn btn-secondary">RESET</button>
                    <button type="submit" :disabled="isFormInvalid" class="btn btn-primary">CREATE</button>
                    <nuxt-link to="/product-types" class="btn btn-link">Return</nuxt-link>
                </div>
            </form>

            <!-- Display messages -->
            <div v-if="messages.length" class="alert alert-info mt-3">
                <pre>{{ messages }}</pre>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const productTypeForm = reactive({
    type: null,
})
const messages = ref([])
const config = useRuntimeConfig()
const api = config.public.API_URL

// Validation rule for 'type'
const typeError = computed(() => {
    if (productTypeForm.type === null) return null
    if (!productTypeForm.type) return 'Type is required'
    return null
})

const isFormInvalid = computed(() => typeError.value)

// Create product type function
async function create() {
    try {
        await $fetch(`${api}/product-type`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: productTypeForm,
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
        router.push('/product_types')
    } catch (e) {
        console.log(e)
    }
}
</script>

<style scoped>
.text-danger {
    color: red;
}

.yo {
    margin-top: 50px !important;
}
</style>
