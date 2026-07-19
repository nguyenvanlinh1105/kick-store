<script setup>
import { RouterView } from 'vue-router'
import PublicNav from '@/components/layout/PublicNav.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
import CartDrawer from '@/components/layout/CartDrawer.vue'
import NotificationPanel from '@/components/layout/NotificationPanel.vue'
</script>

<template>
  <div class="flex min-h-screen flex-col bg-canvas-light">
    <PublicNav />
    <main class="flex-1">
      <RouterView />
    </main>
    <AppFooter />
    <CartDrawer />
    <NotificationPanel />
  </div>
</template>
