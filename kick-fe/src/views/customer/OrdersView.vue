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
      <h1 class="text-2xl font-extrabold text-slate-900">Đơn Hàng Của Tôi</h1>
      <span class="text-xs text-slate-500 font-bold">Tổng {{ ordersJson.length }} đơn hàng</span>
    </div>

    <!-- Status Tabs -->
    <KvTabs v-model="activeTab" :tabs="tabs" :dark="false" />

    <!-- Empty State -->
    <div v-if="filteredOrders.length === 0" class="py-12">
      <KvEmptyState
        title="Chưa có đơn hàng nào"
        description="Không tìm thấy đơn hàng thuộc trạng thái bạn đã chọn."
        action-text="Khám phá sản phẩm ngay"
        @action="$router.push('/shop')"
      />
    </div>

    <!-- Card-Based Order List (PURE WHITE LIGHT THEME) -->
    <div v-else class="flex flex-col gap-4">
      <div
        v-for="ord in filteredOrders"
        :key="ord.id"
        class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm hover:shadow-md transition-all text-slate-900"
      >
        <!-- Header Row -->
        <div class="flex items-center justify-between border-b border-slate-100 pb-3">
          <div class="flex items-center gap-3">
            <span class="text-sm font-extrabold text-amber-600 font-mono">{{ ord.id }}</span>
            <span class="text-[11px] text-slate-500 font-bold">Ngày đặt: {{ ord.createdAt }}</span>
          </div>
          <span
            class="px-3 py-1 rounded-full text-xs font-extrabold uppercase"
            :class="[
              ord.status === 'COMPLETED' && 'bg-emerald-500/10 text-emerald-700',
              ord.status === 'DELIVERING' && 'bg-blue-500/10 text-blue-700',
              ord.status === 'PENDING' && 'bg-amber-500/10 text-amber-700',
            ]"
          >
            {{ ord.statusLabel }}
          </span>
        </div>

        <!-- Items Row -->
        <div class="flex flex-col gap-3">
          <div v-for="item in ord.items" :key="item.id" class="flex items-center justify-between text-xs">
            <div class="flex items-center gap-3">
              <img :src="item.image" :alt="item.name" class="w-12 h-12 object-cover rounded-xl border border-slate-200" />
              <div>
                <span class="font-extrabold text-slate-900 block">{{ item.name }}</span>
                <span class="text-[11px] text-slate-500 font-medium">Size: {{ item.size }} | Màu: {{ item.color }} | x{{ item.qty }}</span>
              </div>
            </div>
            <span class="font-extrabold text-slate-900">{{ formatVnd(item.price * item.qty) }}</span>
          </div>
        </div>

        <!-- Summary & Actions Row -->
        <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between border-t border-slate-100 pt-4 gap-4">
          <div class="text-xs text-slate-500 font-medium">
            Tổng tiền đơn hàng: <strong class="text-amber-600 text-base font-extrabold ml-1">{{ formatVnd(ord.total) }}</strong>
          </div>

          <div class="flex items-center gap-2">
            <RouterLink
              :to="`/account/orders/${ord.id}`"
              class="px-4 py-2 bg-slate-100 border border-slate-200 text-xs font-extrabold text-slate-800 rounded-xl hover:bg-slate-900 hover:text-white no-underline transition-colors"
            >
              Chi tiết đơn hàng
            </RouterLink>
            <button
              type="button"
              class="px-4 py-2 bg-amber-600 text-white font-extrabold text-xs rounded-xl hover:bg-amber-500 cursor-pointer shadow transition-colors"
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
