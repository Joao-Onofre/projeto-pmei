<template>
  <div class="orders-container">
    <div class="order-list">
      <div class="order-item" v-for="order in orders" :key="order.id">
        <h3>{{ order.title }}</h3>
        <p>{{ order.description }}</p>
        <span>Status: {{ order.status }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Hardcoded list of orders
      orders: [
        {
          id: 1,
          title: 'Order 1',
          description: 'This is the description for Order 1',
          status: 'Pending',
        },
        {
          id: 2,
          title: 'Order 2',
          description: 'This is the description for Order 2',
          status: 'Shipped',
        },
        {
          id: 3,
          title: 'Order 3',
          description: 'This is the description for Order 3',
          status: 'Delivered',
        },
        {
          id: 4,
          title: 'Order 4',
          description: 'This is the description for Order 4',
          status: 'Processing',
        },
      ],
    }
  },
}
</script>

<style scoped>
.orders-container {
  margin-top: 50px;
  padding: 20px;
  background-color: #f9f9f9;
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.order-item {
  padding: 15px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.order-item h3 {
  margin: 0 0 10px;
  font-size: 18px;
  font-weight: bold;
}

.order-item p {
  margin: 0 0 10px;
  font-size: 14px;
}

.order-item span {
  font-size: 14px;
  color: #666;
}
</style>
