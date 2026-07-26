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
        { path: '', name: 'home', component: () => import('@/views/public/HomeView.vue') },
        { path: 'shop', name: 'shop', component: () => import('@/views/public/ProductCatalogView.vue') },
        { path: 'shop/:slug', name: 'product-detail', component: () => import('@/views/public/ProductDetailView.vue') },
        { path: 'search', name: 'search', component: () => import('@/views/public/SearchResultsView.vue') },
        { path: 'login', name: 'login', component: () => import('@/views/public/LoginView.vue') },
        { path: 'register', name: 'register', component: () => import('@/views/public/RegisterView.vue') },
        { path: 'forgot-password', name: 'forgot-password', component: () => import('@/views/public/ForgotPasswordView.vue') },
        { path: 'reset-password', name: 'reset-password', component: () => import('@/views/public/ResetPasswordView.vue') },
        { path: 'cart', name: 'cart', component: () => import('@/views/customer/CartView.vue') },
        { path: 'checkout', name: 'checkout', component: () => import('@/views/customer/CheckoutView.vue') },
        { path: 'order-success', name: 'order-success', component: () => import('@/views/public/OrderSuccessView.vue') },
        
        // Blogs & Static Policy Views
        { path: 'blog', name: 'blog', component: () => import('@/views/public/BlogListView.vue') },
        { path: 'blog/:slug', name: 'blog-detail', component: () => import('@/views/public/BlogDetailView.vue') },
        { path: 'about', name: 'about', component: () => import('@/views/public/AboutUsView.vue') },
        { path: 'contact', name: 'contact', component: () => import('@/views/public/ContactView.vue') },
        { path: 'terms', name: 'terms', component: () => import('@/views/public/TermsView.vue') },
        { path: 'privacy', name: 'privacy', component: () => import('@/views/public/PrivacyPolicyView.vue') },
        { path: 'return-policy', name: 'return-policy', component: () => import('@/views/public/ReturnPolicyView.vue') },
        { path: 'shipping-policy', name: 'shipping-policy', component: () => import('@/views/public/ShippingPolicyView.vue') },
        { path: 'faq', name: 'faq', component: () => import('@/views/public/FAQView.vue') },
      ],
    },
    {
      path: '/',
      component: CustomerLayout,
      children: [
        { path: 'account', name: 'account', component: () => import('@/views/customer/AccountView.vue') },
        { path: 'account/addresses', name: 'account-addresses', component: () => import('@/views/customer/AddressesView.vue') },
        { path: 'account/orders', name: 'account-orders', component: () => import('@/views/customer/OrdersView.vue') },
        { path: 'account/orders/:id', name: 'account-order-detail', component: () => import('@/views/customer/OrderDetailView.vue') },
        { path: 'account/vouchers', name: 'account-vouchers', component: () => import('@/views/customer/VouchersView.vue') },
        { path: 'wishlist', name: 'wishlist', component: () => import('@/views/customer/WishlistView.vue') },
        { path: 'notifications', name: 'notifications', component: () => import('@/views/customer/NotificationsView.vue') },
      ],
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        { path: '', name: 'admin-dashboard', component: () => import('@/views/admin/DashboardView.vue') },
        { path: 'orders', name: 'admin-orders', component: () => import('@/views/admin/OrdersView.vue') },
        { path: 'returns', name: 'admin-returns', component: () => import('@/views/admin/ReturnsView.vue') },
        { path: 'products', name: 'admin-products', component: () => import('@/views/admin/ProductsView.vue') },
        { path: 'products/:id', name: 'admin-product-form', component: () => import('@/views/admin/ProductFormView.vue') },
        { path: 'inventory', name: 'admin-inventory', component: () => import('@/views/admin/InventoryView.vue') },
        { path: 'customers', name: 'admin-customers', component: () => import('@/views/admin/CustomersView.vue') },
        { path: 'reviews', name: 'admin-reviews', component: () => import('@/views/admin/ReviewsView.vue') },
        { path: 'coupons', name: 'admin-coupons', component: () => import('@/views/admin/CouponsView.vue') },
        { path: 'flash-sale', name: 'admin-flash-sale', component: () => import('@/views/admin/FlashSaleView.vue') },
        { path: 'banners', name: 'admin-banners', component: () => import('@/views/admin/BannersView.vue') },
        { path: 'brands', name: 'admin-brands', component: () => import('@/views/admin/BrandsView.vue') },
        { path: 'categories', name: 'admin-categories', component: () => import('@/views/admin/CategoriesView.vue') },
        { path: 'attributes', name: 'admin-attributes', component: () => import('@/views/admin/CategoriesView.vue') },
        { path: 'loyalty', name: 'admin-loyalty', component: () => import('@/views/admin/LoyaltyView.vue') },
        { path: 'users', name: 'admin-users', component: () => import('@/views/admin/UsersView.vue') },
        { path: 'settings', name: 'admin-settings', component: () => import('@/views/admin/SettingsView.vue') },
      ],
    },
    {
      path: '/staff',
      component: StaffLayout,
      children: [
        { path: '', name: 'staff-dashboard', component: () => import('@/views/staff/POSView.vue') },
        { path: 'pos', name: 'staff-pos', component: () => import('@/views/staff/POSView.vue') },
        { path: 'support', name: 'staff-support', component: () => import('@/views/staff/SupportView.vue') },
      ],
    },
    // Standalone System Views
    { path: '/404', name: 'not-found', component: () => import('@/views/public/NotFoundView.vue') },
    { path: '/500', name: 'server-error', component: () => import('@/views/public/ServerErrorView.vue') },
    { path: '/maintenance', name: 'maintenance', component: () => import('@/views/public/MaintenanceView.vue') },
    { path: '/:pathMatch(.*)*', redirect: '/404' },
  ],
})

export default router
