<template>
	<div class="layout-wrapper">
		<div class="main-content">
			<div class="container">
				<div class="d-flex justify-content-center">
					<div class="box m-1" v-for="item in boxes" :key="item.id" :class="{ hoverable: true }"
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
				{ id: 4, title: 'Sensors', link: '/sensors', icon: 'sensors-icon.png' },
				{ id: 5, title: 'Products', link: '/products', icon: 'dairy-products.png' },
				{ id: 6, title: 'Product Types', link: '/product_types', icon: 'products.png' },
				{ id: 7, title: 'Cart', link: '/cart', icon: 'trolley.png' }
				{ id: 6, title: 'Product Types', link: '/product_types', icon: 'products.png' },
				{ id: 7, title: 'Package Types', link: '/package_types', icon: 'products.png' }
			],
		}
	},
	methods: {
		navigate(link) {
			this.$router.push(link)
		},
	},
}
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
	/* Allow scrolling within the main content */
}

/* Center the dashboard */
.dashboard {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
	/* Automatically adjust the number of columns based on container width */
	gap: 20px;
	justify-content: center;
	/* Center the grid content */
	padding: 20px;
	width: 100%;
	max-width: 800px;
	/* Adjust the max width for a better layout */
	margin: 0 auto;
}


/* Style individual boxes */
.box {
	display: flex;
	flex-direction: column;
	/* Stack the icon and title vertically */
	justify-content: center;
	align-items: center;
	background-color: #ffffff;
	border: 1px solid #ddd;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	cursor: pointer;
	transition:
		background-color 0.3s ease,
		transform 0.2s ease;
	width: 150px;
	/* Box width */
	height: 150px;
	/* Box height */
	padding: 10px;
	/* Padding inside each box */
}

/* Hover effect for boxes */
.box:hover {
	background-color: #e6e6e6;
	transform: translateY(-5px);
}

/* Style the icons inside the boxes */
.box-icon {
	width: 40px;
	height: 40px;
	margin-bottom: 5px;
	/* Space between icon and text */
}

/* Style the titles */
h3 {
	font-size: 14px;
	margin-top: 5px;
	/* Space between icon and title */
	text-align: center;
	font-weight: bold;
}
</style>
