<template>
	<div class="container_create mt-5 d-flex flex-column align-items-center">
		<h2 class="mb-4 text-dark">Update order - {{ id }}</h2>
		<form @submit.prevent="update" class="bg-light p-5 rounded shadow-lg" style="max-width: 500px; width: 100%;">
			<div class="mb-4">
				<label class="form-label text-dark">Status:</label>
				<select v-model.number="statusForm.status" class="form-control">
					<option value="" selected disabled>--- Select Order Status ---</option>
					<option v-for="orderStatus in statuses" :value="orderStatus.id">
						{{ orderStatus.id }} - {{ orderStatus.status }}
					</option>
				</select>
			</div>
			<div class="d-flex justify-content-between">
				<button type="submit" class="btn btn-outline-dark btn-lg">Update</button>
				<button type="reset" class="btn btn-outline-danger btn-lg">Reset</button>
			</div>
		</form>
		<nuxt-link to="/orders" class="btn btn-outline-light mt-4 btn-lg">Return</nuxt-link>
		<p class="mt-3 text-warning">{{ message }}</p>
	</div>
</template>

<script setup>
const statusForm = reactive({
	status: null
})
const route = useRoute()
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const { data: statuses } = await useFetch(`${api}/orderstatus`)
const id = route.params.id

async function update() {
	const requestOptions = {
		method: 'PUT',
		headers: { 'Content-Type': 'application/json' },
		body: statusForm,
		onResponse({ request, response, options }) {
			messages.value.push({
				method: options.method,
				request: request,
				status: response.status,
				statusText: response.statusText,
				payload: response._data
			})
		}
	}
	try {
		const { error } = await $fetch(`${api}/order/${id}`, requestOptions)
	} catch (error) {
		console.log(error)
		if (!error.value) navigateTo('/orders')
		message.value = error.value
	}
}
</script>