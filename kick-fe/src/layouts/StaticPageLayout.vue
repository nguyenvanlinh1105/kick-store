<script setup>
import PublicNav from '@/components/layout/PublicNav.vue'
import AppFooter from '@/components/layout/AppFooter.vue'

defineProps({
  title: {
    type: String,
    required: true,
  },
  subtitle: {
    type: String,
    default: '',
  },
})
</script>

<template>
  <div class="min-h-screen bg-slate-50 text-slate-900 flex flex-col">
    <PublicNav />

    <div class="flex-1 py-12">
      <div class="kv-container max-w-4xl">
        <!-- Page Title Header -->
        <div class="border-b border-slate-200 pb-6 mb-8">
          <span v-if="subtitle" class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1">{{ subtitle }}</span>
          <h1 class="text-3xl md:text-4xl font-extrabold text-slate-900">{{ title }}</h1>
        </div>

        <!-- Rich Content Slot -->
        <div class="bg-white border border-slate-200 rounded-3xl p-8 md:p-10 shadow-xl text-slate-700 text-sm leading-relaxed">
          <slot />
        </div>
      </div>
    </div>

    <AppFooter />
  </div>
</template>
