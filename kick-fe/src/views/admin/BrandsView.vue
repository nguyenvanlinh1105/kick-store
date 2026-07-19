<script setup>
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const brands = [
  { name: 'Nike', products: 24, status: 'ACTIVE' },
  { name: 'Adidas', products: 18, status: 'ACTIVE' },
  { name: 'Jordan', products: 9, status: 'ACTIVE' },
  { name: 'Puma', products: 7, status: 'INACTIVE' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Thương hiệu" description="Chỉ Admin đầy đủ">
      <KvButton size="md" variant="primary">Thêm brand</KvButton>
    </KvPageHeader>
    <div class="grid gap-md sm:grid-cols-2">
      <article
        v-for="b in brands"
        :key="b.name"
        class="rounded-md bg-canvas-light p-lg"
      >
        <p class="text-heading-md">{{ b.name }}</p>
        <p class="mt-xs text-caption-md text-mute-light">
          {{ b.products }} sản phẩm · {{ b.status }}
        </p>
      </article>
    </div>
  </div>
</template>
