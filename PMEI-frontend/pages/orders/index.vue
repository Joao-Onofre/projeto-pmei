<template>
  <div class="wrapper">
    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Order ID, Status or Customer ID" />
    </div>

    <!-- Order Table -->
    <table class="order-table table table-striped table-hover">
      <thead class="thead-dark text-center">
        <tr>
          <th>ID</th>
          <th>Status</th>
          <th>Customer ID</th>
          <th>Creation Date</th>
          <th>Delivery Date</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody class text-center>
        <!-- Display filtered orders -->
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.status }}</td>
          <td>{{ order.customerId }}</td>
          <td class="d-flex gap-2 justify-content-center">
            <button @click.prevent="deleteOrder(order.id)" class="btn btn-outline-danger btn-sm">Delete</button>
            <nuxt-link :to="`/orders/${order.id}`" class="btn btn-outline-light btn-sm">Details</nuxt-link>
            <!--<nuxt-link :to="`/orders/update/${order.id}`" class="btn btn-outline-info btn-sm">Update</nuxt-link>-->
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
console.log(api)
const { data: orders, error, refresh } = await useFetch(`${api}/order`)

async function deleteOrder(id) {
  try {
    const requestOptions = {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
    }
    const response = await fetch(`${api}/order/${id}`, requestOptions)
    console.log(response)

    if (!response.ok) {
      throw new Error('Failed to delete the order.')
    }

    // Refresh the data after successful deletion
    refresh()

  } catch (error) {
    console.error('Error deleting order:', error.message)
    // Optionally display the error in the UI
  }
}

</script>
