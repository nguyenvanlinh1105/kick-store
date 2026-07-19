<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const route = useRoute()
const orderId = computed(() => route.params.id)

const steps = [
  { key: 'CONFIRMED', label: 'Xác nhận' },
  { key: 'PREPARING', label: 'Chuẩn bị' },
  { key: 'SHIPPING', label: 'Đang giao' },
  { key: 'DELIVERED', label: 'Đã giao' },
]
const current = 'SHIPPING'
</script>

<template>
  <div>
    <KvPageHeader :title="`Đơn #${orderId}`" description="Chi tiết và trạng thái vận chuyển">
      <KvBadge variant="info">Đang giao</KvBadge>
    </KvPageHeader>

    <div class="mb-xl rounded-md bg-surface-card p-lg">
      <p class="mb-md text-heading-md">Tiến trình</p>
      <ol class="flex flex-col gap-md kv:flex-row kv:justify-between">
        <li
          v-for="(s, i) in steps"
          :key="s.key"
          class="flex items-center gap-sm"
          :class="
            steps.findIndex((x) => x.key === current) >= i
              ? 'text-ink'
              : 'text-ash-light'
          "
        >
          <span
            class="flex h-8 w-8 items-center justify-center rounded-full text-caption-sm font-bold"
            :class="
              steps.findIndex((x) => x.key === current) >= i
                ? 'bg-primary text-on-primary'
                : 'bg-surface-soft'
            "
          >
            {{ i + 1 }}
          </span>
          <span class="text-body-sm">{{ s.label }}</span>
        </li>
      </ol>
    </div>

    <div class="rounded-md border border-hairline-light p-lg">
      <p class="text-heading-md">Sản phẩm</p>
      <div class="mt-md flex gap-md border-b border-hairline-light pb-md">
        <div class="h-20 w-20 rounded-md bg-surface-soft" />
        <div>
          <p class="text-body-sm">Air Force Kinetic</p>
          <p class="text-caption-md text-mute-light">Size 42 · Trắng</p>
          <p class="text-body-strong">2.890.000đ</p>
        </div>
      </div>
      <div class="mt-md flex justify-between">
        <span class="text-body-sm text-body-light">Tổng thanh toán</span>
        <span class="text-heading-md">5.780.000đ</span>
      </div>
      <KvButton to="/support" variant="secondary" class="mt-lg">Liên hệ hỗ trợ</KvButton>
    </div>
  </div>
</template>
