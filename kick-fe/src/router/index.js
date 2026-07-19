import { createRouter, createWebHistory } from 'vue-router'
import PublicLayout from '@/layouts/PublicLayout.vue'
import CustomerLayout from '@/layouts/CustomerLayout.vue'
import AdminLayout from '@/layouts/AdminLayout.vue'
import StaffLayout from '@/layouts/StaffLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior() {
    return { top: 0 }
  },
  routes: [
    {
      path: '/',
      component: PublicLayout,
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('@/views/public/HomeView.vue'),
        },
        {
          path: 'shop',
          name: 'shop',
          component: () => import('@/views/public/ShopView.vue'),
        },
        {
          path: 'shop/:slug',
          name: 'product-detail',
          component: () => import('@/views/public/ProductDetailView.vue'),
        },
        {
          path: 'login',
          name: 'login',
          component: () => import('@/views/public/LoginView.vue'),
        },
        {
          path: 'register',
          name: 'register',
          component: () => import('@/views/public/RegisterView.vue'),
        },
        {
          path: 'support',
          name: 'support',
          component: () => import('@/views/public/SupportView.vue'),
        },
        {
          path: 'cart',
          name: 'cart',
          component: () => import('@/views/customer/CartView.vue'),
        },
        {
          path: 'checkout',
          name: 'checkout',
          component: () => import('@/views/customer/CheckoutView.vue'),
        },
      ],
    },
    {
      path: '/',
      component: CustomerLayout,
      children: [
        {
          path: 'account',
          name: 'account',
          component: () => import('@/views/customer/AccountView.vue'),
        },
        {
          path: 'account/profile',
          name: 'account-profile',
          component: () => import('@/views/customer/ProfileView.vue'),
        },
        {
          path: 'account/addresses',
          name: 'account-addresses',
          component: () => import('@/views/customer/AddressesView.vue'),
        },
        {
          path: 'account/orders',
          name: 'account-orders',
          component: () => import('@/views/customer/OrdersView.vue'),
        },
        {
          path: 'account/orders/:id',
          name: 'account-order-detail',
          component: () => import('@/views/customer/OrderDetailView.vue'),
        },
        {
          path: 'wishlist',
          name: 'wishlist',
          component: () => import('@/views/customer/WishlistView.vue'),
        },
        {
          path: 'notifications',
          name: 'notifications',
          component: () => import('@/views/customer/NotificationsView.vue'),
        },
      ],
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        {
          path: '',
          name: 'admin-dashboard',
          component: () => import('@/views/admin/DashboardView.vue'),
        },
        {
          path: 'orders',
          name: 'admin-orders',
          component: () => import('@/views/admin/OrdersView.vue'),
        },
        {
          path: 'products',
          name: 'admin-products',
          component: () => import('@/views/admin/ProductsView.vue'),
        },
        {
          path: 'products/:id',
          name: 'admin-product-form',
          component: () => import('@/views/admin/ProductFormView.vue'),
        },
        {
          path: 'inventory',
          name: 'admin-inventory',
          component: () => import('@/views/admin/InventoryView.vue'),
        },
        {
          path: 'customers',
          name: 'admin-customers',
          component: () => import('@/views/admin/CustomersView.vue'),
        },
        {
          path: 'reviews',
          name: 'admin-reviews',
          component: () => import('@/views/admin/ReviewsView.vue'),
        },
        {
          path: 'coupons',
          name: 'admin-coupons',
          component: () => import('@/views/admin/CouponsView.vue'),
        },
        {
          path: 'brands',
          name: 'admin-brands',
          component: () => import('@/views/admin/BrandsView.vue'),
        },
        {
          path: 'categories',
          name: 'admin-categories',
          component: () => import('@/views/admin/CategoriesView.vue'),
        },
        {
          path: 'users',
          name: 'admin-users',
          component: () => import('@/views/admin/UsersView.vue'),
        },
      ],
    },
    {
      path: '/staff',
      component: StaffLayout,
      children: [
        {
          path: '',
          name: 'staff-dashboard',
          component: () => import('@/views/staff/DashboardView.vue'),
        },
        {
          path: 'orders',
          name: 'staff-orders',
          component: () => import('@/views/staff/OrdersView.vue'),
        },
        {
          path: 'inventory',
          name: 'staff-inventory',
          component: () => import('@/views/staff/InventoryView.vue'),
        },
        {
          path: 'customers',
          name: 'staff-customers',
          component: () => import('@/views/staff/CustomersView.vue'),
        },
        {
          path: 'support',
          name: 'staff-support',
          component: () => import('@/views/staff/SupportView.vue'),
        },
      ],
    },
  ],
})

export default router
