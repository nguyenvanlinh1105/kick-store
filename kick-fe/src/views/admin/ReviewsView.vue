<script setup>
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const reviews = [
  { product: 'Air Force Kinetic', user: 'An', rating: 5, status: 'PENDING', body: 'Form đẹp, đi êm.' },
  { product: 'Ultraboost City', user: 'Hà', rating: 4, status: 'APPROVED', body: 'Đáng tiền.' },
  { product: 'Chuck 70 Hi', user: 'Huy', rating: 2, status: 'REJECTED', body: 'Size hơi chật.' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Đánh giá" description="Duyệt review trước khi hiển thị" />
    <div class="flex flex-col gap-md">
      <article
        v-for="(r, i) in reviews"
        :key="i"
        class="rounded-md bg-canvas-light p-lg"
      >
        <div class="flex flex-wrap items-center justify-between gap-sm">
          <p class="text-heading-md">{{ r.product }}</p>
          <KvBadge variant="soft">{{ r.status }}</KvBadge>
        </div>
        <p class="mt-xs text-caption-md text-mute-light">{{ r.user }} · {{ r.rating }}/5</p>
        <p class="mt-sm text-body-sm text-body-light">{{ r.body }}</p>
        <div v-if="r.status === 'PENDING'" class="mt-md flex gap-sm">
          <KvButton size="md" variant="primary">Duyệt</KvButton>
          <KvButton size="md" variant="secondary">Từ chối</KvButton>
        </div>
      </article>
    </div>
  </div>
</template>
