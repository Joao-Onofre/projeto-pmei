<template>
  <div class="wrapper">
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
        <tr v-for="order in orders" :key="order.id">
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
      // Mock API data
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
      ],
    }
  },
}
</script>

<style scoped>
.wrapper {
  padding: 50px;
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
