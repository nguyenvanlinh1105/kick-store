<script setup>
import { ref, computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import KvModal from '@/components/ui/KvModal.vue'
import ordersJson from '@/data/json/orders.json'
import { formatVnd } from '@/data/demo'

const route = useRoute()
const orderId = route.params.id || 'KV-1042'
const order = computed(() => ordersJson.find((o) => o.id === orderId) || ordersJson[0])

const showReturnModal = ref(false)
const returnReason = ref('')
const returnNote = ref('')
const isReturnSubmitted = ref(false)

function submitReturnRequest() {
  if (!returnReason.value) return
  isReturnSubmitted.value = true
  setTimeout(() => {
    showReturnModal.value = false
    isReturnSubmitted.value = false
    alert('Yêu cầu Đổi/Trả hàng của bạn đã được gửi thành công. Nhân viên CSKH sẽ liên hệ trong 24h.')
  }, 800)
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div class="flex items-center gap-3">
        <RouterLink to="/account/orders" class="text-xs text-primary font-bold hover:underline no-underline">← Quay lại danh sách</RouterLink>
        <h1 class="text-2xl font-extrabold text-white">Đơn Hàng {{ order.id }}</h1>
      </div>
      <button
        type="button"
        class="px-4 py-2 bg-red-500/10 border border-red-500/30 text-red-400 font-bold text-xs rounded-xl hover:bg-red-500/20 cursor-pointer"
        @click="showReturnModal = true"
      >
        🔄 Yêu cầu Đổi / Trả hàng
      </button>
    </div>

    <!-- TIMELINE TRACKING STEPPER -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-4">
      <h2 class="text-sm font-bold text-white uppercase tracking-wider">Tiến Trình Đơn Hàng</h2>
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 relative pt-2">
        <div
          v-for="(step, idx) in order.timeline"
          :key="idx"
          class="flex flex-col gap-1 p-3 bg-black/40 border border-white/5 rounded-xl"
        >
          <span class="text-[10px] font-bold text-primary">{{ step.time }}</span>
          <span class="text-xs font-bold text-white">{{ step.title }}</span>
          <span class="text-[11px] text-neutral-400">{{ step.desc }}</span>
        </div>
      </div>
    </div>

    <!-- CUSTOMER & RECEIVER INFO -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-2">
        <h3 class="text-xs font-bold text-neutral-400 uppercase tracking-wider mb-1">Địa Chỉ Nhận Hàng</h3>
        <span class="text-sm font-bold text-white">{{ order.customer.name }} ({{ order.customer.phone }})</span>
        <span class="text-xs text-neutral-300 leading-relaxed">{{ order.customer.address }}</span>
      </div>

      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-2">
        <h3 class="text-xs font-bold text-neutral-400 uppercase tracking-wider mb-1">Thanh Toán & Vận Chuyển</h3>
        <span class="text-xs text-neutral-300">Phương thức thanh toán: <strong class="text-white font-bold">{{ order.paymentMethod }}</strong></span>
        <span class="text-xs text-neutral-300">Trạng thái thanh toán: <strong class="text-emerald-400 font-bold">{{ order.paymentStatus === 'PAID' ? 'Đã thanh toán' : 'Chưa thanh toán' }}</strong></span>
      </div>
    </div>

    <!-- ORDER ITEMS LIST -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-4">
      <h2 class="text-sm font-bold text-white uppercase tracking-wider border-b border-white/10 pb-3">Sản Phẩm Trong Đơn</h2>
      <div class="flex flex-col gap-4">
        <div v-for="item in order.items" :key="item.id" class="flex items-center justify-between text-xs">
          <div class="flex items-center gap-4">
            <img :src="item.image" :alt="item.name" class="w-14 h-14 object-cover rounded-xl" />
            <div>
              <span class="text-sm font-bold text-white block">{{ item.name }}</span>
              <span class="text-xs text-neutral-400">Size: {{ item.size }} | Màu: {{ item.color }} | Đơn giá: {{ formatVnd(item.price) }}</span>
            </div>
          </div>
          <span class="text-sm font-extrabold text-white">x{{ item.qty }} = {{ formatVnd(item.price * item.qty) }}</span>
        </div>
      </div>

      <div class="flex flex-col gap-2 border-t border-white/10 pt-4 text-xs text-neutral-300">
        <div class="flex justify-between">
          <span>Phí vận chuyển:</span>
          <span class="font-bold text-white">{{ order.shippingFee === 0 ? 'Miễn phí' : formatVnd(order.shippingFee) }}</span>
        </div>
        <div v-if="order.discount" class="flex justify-between text-emerald-400">
          <span>Giảm giá:</span>
          <span class="font-bold">-{{ formatVnd(order.discount) }}</span>
        </div>
        <div class="flex justify-between text-base font-extrabold text-white border-t border-white/10 pt-3">
          <span>Tổng thanh toán:</span>
          <span class="text-primary text-xl">{{ formatVnd(order.total) }}</span>
        </div>
      </div>
    </div>

    <!-- RETURN & REFUND MODAL -->
    <KvModal v-model="showReturnModal" title="Yêu Cầu Đổi / Trả Hàng & Hoàn Tiền" :dark="true">
      <form @submit.prevent="submitReturnRequest" class="flex flex-col gap-4 text-xs text-neutral-300">
        <p>Vui lòng chọn lý do đổi trả đơn hàng <strong>{{ order.id }}</strong>:</p>
        <select v-model="returnReason" required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white">
          <option value="" disabled selected>-- Chọn lý do --</option>
          <option value="SIZE_FIT">Không vừa size (muốn đổi size)</option>
          <option value="DEFECT">Sản phẩm bị lỗi nhà sản xuất / trầy xước</option>
          <option value="WRONG_ITEM">Giao sai mẫu hoặc màu sắc</option>
        </select>

        <textarea v-model="returnNote" rows="3" placeholder="Mô tả thêm chi tiết tình trạng sản phẩm..." class="p-3 bg-black border border-white/15 rounded-lg text-white"></textarea>

        <button type="submit" :disabled="isReturnSubmitted" class="h-11 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
          {{ isReturnSubmitted ? 'Đang gửi yêu cầu...' : 'GỬI YÊU CẦU ĐỔI TRẢ' }}
        </button>
      </form>
    </KvModal>
  </div>
</template>
