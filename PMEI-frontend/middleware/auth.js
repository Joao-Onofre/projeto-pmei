// middleware/auth.js
export default defineNuxtRouteMiddleware((to, from) => {
    if (process.client) {
        const token = localStorage.getItem('jwt_token');
        // Redirect to /auth if no token and the user is not on the login page
        if (!token && to.path !== '/auth') {
            return navigateTo('/auth');
        }
    }
});
