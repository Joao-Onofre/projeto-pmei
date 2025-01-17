<template>
	<div class="layout-wrapper">
		<div class="main-content">
			<div class="dashboard">
				<div class="box" v-for="item in filteredBoxes" :key="item.id" @click="navigate(item.link)">
					<img :src="`/images/${item.icon}`" alt="Icon" class="box-icon" />
					<h3>{{ item.title }}</h3>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { onMounted, onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';

export default {
	data() {
		return {
			boxes: [
				{ id: 1, title: 'Packages', link: '/packages', icon: 'packages-icon.png', restricted: true },
				{ id: 2, title: 'Orders', link: '/orders', icon: 'orders-icon.png' },
				{ id: 3, title: 'Alerts', link: '/alerts', icon: 'alerts-icon.png' },
				{ id: 4, title: 'Sensors', link: '/sensors', icon: 'sensors-icon.png', restricted: true },
				{ id: 5, title: 'Products', link: '/products', icon: 'dairy-products.png' },
				{ id: 6, title: 'Product Types', link: '/product_types', icon: 'products.png', restricted: true },
				{ id: 7, title: 'Cart', link: '/cart', icon: 'trolley.png' },
				{ id: 8, title: 'Package Types', link: '/package_types', icon: 'products.png', restricted: true },
			],
			userType: null,
		};
	},
	computed: {
		// Filter boxes based on user type
		filteredBoxes() {
			return this.boxes.filter((box) => {
				return !(box.restricted && this.userType === 'Customer');
			});
		},
	},
	mounted() {
		this.verifyAuthentication();
		this.refreshData();
	},
	methods: {
		navigate(link) {
			this.$router.push(link);
		},
		verifyAuthentication() {
			const token = localStorage.getItem('jwt_token');
			if (!token) {
				this.$router.push('/auth');
			}
		},
		refreshData() {
			this.userType = localStorage.getItem('user_type') || null;
			console.log('Page refreshed and data loaded!');
		},
	},
	setup() {
		const router = useRouter();

		// Refresh data whenever the route changes
		onBeforeMount(() => {
			const token = localStorage.getItem('jwt_token');
			if (!token) {
				router.push('/auth');
			}
			console.log('Data refreshed on route change');
		});
	},
};
</script>

<style scoped>
/* Prevent scrolling */
html,
body {
	margin: 0;
	padding: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.layout-wrapper {
	display: flex;
	flex-direction: column;
	width: 100%;
	height: 100%;
}

.main-content {
	display: flex;
	flex: 1;
	justify-content: center;
	align-items: flex-start;
	padding-top: 40px;
}

.dashboard {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
	gap: 20px;
	width: 100%;
	max-width: 900px;
	padding: 50px 10px 10px 10px;
	margin: 0 auto;
	justify-items: center;
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
	width: 100%;
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
