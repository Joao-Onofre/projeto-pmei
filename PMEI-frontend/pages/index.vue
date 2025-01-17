<template>
	<div class="layout-wrapper">
		<div class="main-content">
			<div class="container">
				<div class="d-flex justify-content-center">
					<div class="box m-1" v-for="item in filteredBoxes" :key="item.id" :class="{ hoverable: true }"
						@click="navigate(item.link)">
						<img :src="`/images/${item.icon}`" alt="Icon" class="box-icon" />
						<h3>{{ item.title }}</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			boxes: [
				{ id: 1, title: 'Packages', link: '/packages', icon: 'packages-icon.png' },
				{ id: 2, title: 'Orders', link: '/orders', icon: 'orders-icon.png' },
				{ id: 3, title: 'Alerts', link: '/alerts', icon: 'alerts-icon.png' },
				{ id: 4, title: 'Sensors', link: '/sensors', icon: 'sensors-icon.png', restricted: true },
				{ id: 5, title: 'Products', link: '/products', icon: 'dairy-products.png' },
				{ id: 6, title: 'Product Types', link: '/product_types', icon: 'products.png', restricted: true },
				{ id: 7, title: 'Cart', link: '/cart', icon: 'trolley.png' },
				{ id: 7, title: 'Package Types', link: '/package_types', icon: 'products.png' }
			],
			userType: null,
		};
	},
	computed: {
		// Filter boxes based on user type
		filteredBoxes() {
			return this.boxes.filter((box) => {
				// If `restricted` is true, only show if userType is not "Customer"
				return !(box.restricted && this.userType === 'Customer');
			});
		},
	},
	mounted() {
		// Retrieve the user type from local storage
		if (typeof window !== 'undefined') {
			this.userType = localStorage.getItem('user_type') || null;
		}
	},
	methods: {
		navigate(link) {
			this.$router.push(link);
		},
	},
};
</script>

<style scoped>
.layout-wrapper {
	display: flex;
	flex-direction: column;
	min-height: 95vh;
}

.main-content {
	display: flex;
	flex: 1;
	justify-content: center;
	align-items: center;
	overflow-y: auto;
}

.dashboard {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
	gap: 20px;
	justify-content: center;
	padding: 20px;
	width: 100%;
	max-width: 800px;
	margin: 0 auto;
}

.box {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	background-color: #ffffff;
	border: 1px solid #ddd;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	cursor: pointer;
	transition: background-color 0.3s ease, transform 0.2s ease;
	width: 150px;
	height: 150px;
	padding: 10px;
}

.box:hover {
	background-color: #e6e6e6;
	transform: translateY(-5px);
}

.box-icon {
	width: 40px;
	height: 40px;
	margin-bottom: 5px;
}

h3 {
	font-size: 14px;
	margin-top: 5px;
	text-align: center;
	font-weight: bold;
}
</style>
