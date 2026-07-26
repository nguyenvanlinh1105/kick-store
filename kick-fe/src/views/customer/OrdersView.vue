<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import KvTabs from '@/components/ui/KvTabs.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import ordersJson from '@/data/json/orders.json'
import { formatVnd } from '@/data/demo'

const activeTab = ref('ALL')

const tabs = [
  { key: 'ALL', label: 'Tất cả đơn' },
  { key: 'PENDING', label: 'Chờ xác nhận' },
  { key: 'DELIVERING', label: 'Đang giao' },
  { key: 'COMPLETED', label: 'Đã hoàn thành' },
]

const filteredOrders = computed(() => {
  if (activeTab.value === 'ALL') return ordersJson
  return ordersJson.filter((o) => o.status === activeTab.value)
})
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-extrabold text-white">Đơn Hàng Của Tôi</h1>
      <span class="text-xs text-neutral-400">Tổng {{ ordersJson.length }} đơn hàng</span>
    </div>

    <!-- Status Tabs -->
    <KvTabs v-model="activeTab" :tabs="tabs" :dark="true" />

    <!-- Empty State -->
    <div v-if="filteredOrders.length === 0" class="py-12">
      <KvEmptyState
        title="Chưa có đơn hàng nào"
        description="Không tìm thấy đơn hàng thuộc trạng thái bạn đã chọn."
        action-text="Khám phá sản phẩm ngay"
        @action="$router.push('/shop')"
      />
    </div>

    <!-- Card-Based Order List -->
    <div v-else class="flex flex-col gap-4">
      <div
        v-for="ord in filteredOrders"
        :key="ord.id"
        class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-4"
      >
        <!-- Header Row -->
        <div class="flex items-center justify-between border-b border-white/10 pb-3">
          <div class="flex items-center gap-3">
            <span class="text-sm font-extrabold text-primary font-mono">{{ ord.id }}</span>
            <span class="text-[11px] text-neutral-400">Ngày đặt: {{ ord.createdAt }}</span>
          </div>
          <span
            class="px-3 py-1 rounded-full text-xs font-bold uppercase"
            :class="[
              ord.status === 'COMPLETED' && 'bg-emerald-500/20 text-emerald-400',
              ord.status === 'DELIVERING' && 'bg-blue-500/20 text-blue-400',
              ord.status === 'PENDING' && 'bg-amber-500/20 text-amber-400',
            ]"
          >
            {{ ord.statusLabel }}
          </span>
        </div>

        <!-- Items Row -->
        <div class="flex flex-col gap-3">
          <div v-for="item in ord.items" :key="item.id" class="flex items-center justify-between text-xs">
            <div class="flex items-center gap-3">
              <img :src="item.image" :alt="item.name" class="w-12 h-12 object-cover rounded-lg" />
              <div>
                <span class="font-bold text-white block">{{ item.name }}</span>
                <span class="text-[11px] text-neutral-400">Size: {{ item.size }} | Màu: {{ item.color }} | x{{ item.qty }}</span>
              </div>
            </div>
            <span class="font-bold text-white">{{ formatVnd(item.price * item.qty) }}</span>
          </div>
        </div>

        <!-- Summary & Actions Row -->
        <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between border-t border-white/10 pt-4 gap-4">
          <div class="text-xs text-neutral-400">
            Tổng tiền đơn hàng: <strong class="text-primary text-base font-extrabold ml-1">{{ formatVnd(ord.total) }}</strong>
          </div>

          <div class="flex items-center gap-2">
            <RouterLink
              :to="`/account/orders/${ord.id}`"
              class="px-4 py-2 bg-neutral-800 border border-white/15 text-xs font-bold text-white rounded-xl hover:border-primary no-underline"
            >
              Chi tiết đơn hàng
            </RouterLink>
            <button
              type="button"
              class="px-4 py-2 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer"
              @click="$router.push('/cart')"
            >
              Mua lại đơn này
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
