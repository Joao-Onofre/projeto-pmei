<template>
  <div>
    <h1>Order Details</h1>
    <p><strong>Order ID:</strong> {{ order.id }}</p>
    <p><strong>Status:</strong> {{ order.status }}</p>
    <p><strong>Customer ID:</strong> {{ order.customerId }}</p>
    <p>
      <strong>Creation Date:</strong>
      {{ new Date(order.creationDate).toLocaleString() }}
    </p>
    <p>
      <strong>Delivery Date:</strong>
      {{ new Date(order.deliveryDate).toLocaleString() }}
    </p>

    <h2>Items</h2>
    <ul>
      <li v-for="item in order.items" :key="item.productId">
        Product ID: {{ item.productId }}, Quantity: {{ item.quantity }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Mock data for a single order
      order: {
        id: '12345',
        status: 'pending',
        customerId: '67890',
        creationDate: '2023-10-01T12:34:56Z',
        deliveryDate: '2023-10-10T10:00:00Z',
        items: [
          { productId: 'abc123', quantity: 2 },
          { productId: 'xyz789', quantity: 1 },
        ],
      },
    }
  },
}
</script>
