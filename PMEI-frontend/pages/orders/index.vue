<template>
  <div class="wrapper">
    <!-- Search Bar -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        class="search-bar"
        placeholder="Search by Order ID, Status or Customer ID"
      />
    </div>

    <!-- Order Table -->
    <table class="order-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Status</th>
          <th>Customer ID</th>
          <th>Creation Date</th>
          <th>Delivery Date</th>
        </tr>
      </thead>
      <tbody>
        <!-- Display filtered orders -->
        <tr v-for="order in filteredOrders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.status }}</td>
          <td>{{ order.customerId }}</td>
          <td>{{ new Date(order.creationDate).toLocaleString() }}</td>
          <td>{{ new Date(order.deliveryDate).toLocaleString() }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      // Mock order data
      orders: [
        {
          id: '12345',
          status: 'pending',
          customerId: '67890',
          creationDate: '2023-10-01T12:34:56Z',
          deliveryDate: '2023-10-10T10:00:00Z',
        },
        {
          id: '67890',
          status: 'shipped',
          customerId: '12345',
          creationDate: '2023-09-20T08:15:00Z',
          deliveryDate: '2023-09-30T18:00:00Z',
        },
        {
          id: '11223',
          status: 'delivered',
          customerId: '54321',
          creationDate: '2023-10-15T10:30:00Z',
          deliveryDate: '2023-10-20T12:00:00Z',
        },
      ],
    }
  },
  computed: {
    filteredOrders() {
      // Return filtered orders based on the search query for ID, Status, and Customer ID
      return this.orders.filter(
        (order) =>
          order.id.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          order.status.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          order.customerId
            .toLowerCase()
            .includes(this.searchQuery.toLowerCase()),
      )
    },
  },
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
  margin-top: 20px;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 100%;
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

.order-table th,
.order-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.order-table th {
  background-color: #f4f4f4;
  font-weight: bold;
}
</style>
