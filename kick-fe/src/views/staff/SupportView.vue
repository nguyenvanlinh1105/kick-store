<script setup>
import { ref } from 'vue'
import customersJson from '@/data/json/customers.json'
import ordersJson from '@/data/json/orders.json'

const customers = customersJson
const selectedCustomerIndex = ref(0)

const activeChat = ref({
  customer: customers[0],
  messages: [
    { sender: 'customer', text: 'Chào shop, đôi Air Force Kinetic size 41 này form có chật không ạ?', time: '14:20' },
    { sender: 'staff', text: 'Dạ chào bạn! Đôi này form chuẩn EU ạ, nếu bạn chân bè có thể nhích lên 0.5 size nha.', time: '14:22' },
    { sender: 'customer', text: 'Shop còn sẵn màu Trắng ở cửa hàng Hà Nội không?', time: '14:25' },
    { sender: 'staff', text: 'Dạ cửa hàng Hà Nội còn sẵn 3 đôi size 41 màu Trắng ạ. Bạn có thể ghé thử trực tiếp hoặc đặt giao hỏa tốc trên web nhé!', time: '14:27' },
  ],
})

const newMessage = ref('')

function switchCustomer(index) {
  selectedCustomerIndex.value = index
  activeChat.value.customer = customers[index]
}

function sendMessage() {
  if (!newMessage.value.trim()) return
  activeChat.value.messages.push({
    sender: 'staff',
    text: newMessage.value.trim(),
    time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
  })
  newMessage.value = ''
}
</script>

<template>
  <div class="h-[calc(100vh-6.5rem)] bg-white border border-slate-200 rounded-3xl overflow-hidden flex shadow-lg text-slate-900">
    <!-- LEFT: CHAT LIST SIDEBAR -->
    <div class="w-80 border-r border-slate-200 p-4 flex flex-col gap-3 shrink-0 bg-slate-50">
      <div class="flex items-center justify-between border-b border-slate-200 pb-3">
        <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider">Hội Thoại Đang Tư Vấn</h2>
        <span class="px-2 py-0.5 bg-emerald-500/10 text-emerald-700 font-extrabold text-[10px] rounded-full">
          {{ customers.length }} Khách hàng
        </span>
      </div>

      <div class="flex flex-col gap-2 overflow-y-auto flex-1 pr-1">
        <div
          v-for="(c, idx) in customers"
          :key="c.id"
          class="p-3 rounded-2xl border transition-all cursor-pointer flex items-center gap-3"
          :class="[selectedCustomerIndex === idx ? 'bg-white border-amber-600 shadow-md' : 'bg-slate-100 border-slate-200 hover:border-slate-300']"
          @click="switchCustomer(idx)"
        >
          <img :src="c.avatar" :alt="c.fullName" class="w-10 h-10 rounded-full border border-slate-200 shrink-0" />
          <div class="flex flex-col flex-1 min-w-0">
            <div class="flex items-center justify-between">
              <span class="text-xs font-extrabold text-slate-900 truncate">{{ c.fullName }}</span>
              <span class="text-[10px] font-bold text-amber-600">14:27</span>
            </div>
            <span class="text-[11px] text-slate-500 truncate">Hạng {{ c.tier }} · {{ c.points }} pts</span>
          </div>
        </div>
      </div>
    </div>

    <!-- CENTER: REALTIME CHAT MESSAGES BOX -->
    <div class="flex-1 flex flex-col justify-between border-r border-slate-200 bg-white">
      <!-- Chat Header -->
      <div class="p-4 border-b border-slate-200 flex items-center justify-between bg-slate-50">
        <div class="flex items-center gap-3">
          <img :src="activeChat.customer.avatar" :alt="activeChat.customer.fullName" class="w-9 h-9 rounded-full border border-slate-200" />
          <div class="flex flex-col">
            <span class="text-xs font-extrabold text-slate-900">{{ activeChat.customer.fullName }}</span>
            <span class="text-[10px] text-emerald-600 font-bold">● Đang trực tuyến (Website Storefront)</span>
          </div>
        </div>

        <span class="text-xs bg-slate-900 text-white font-extrabold px-3 py-1 rounded-xl">
          CSKH Trực Tuyến
        </span>
      </div>

      <!-- Messages Stream -->
      <div class="p-6 flex flex-col gap-4 overflow-y-auto flex-1 bg-slate-50/50">
        <div
          v-for="(msg, idx) in activeChat.messages"
          :key="idx"
          class="flex flex-col max-w-md text-xs"
          :class="[msg.sender === 'staff' ? 'self-end items-end' : 'self-start items-start']"
        >
          <div
            class="p-4 rounded-2xl leading-relaxed shadow-sm"
            :class="[
              msg.sender === 'staff'
                ? 'bg-slate-900 text-white font-medium rounded-br-none'
                : 'bg-white border border-slate-200 text-slate-900 rounded-bl-none'
            ]"
          >
            {{ msg.text }}
          </div>
          <span class="text-[10px] text-slate-400 font-bold mt-1 px-1">{{ msg.time }}</span>
        </div>
      </div>

      <!-- Message Input Form -->
      <form @submit.prevent="sendMessage" class="p-4 border-t border-slate-200 flex gap-2 bg-white">
        <input
          v-model="newMessage"
          type="text"
          placeholder="Nhập câu trả lời tư vấn cho khách hàng..."
          class="flex-1 h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 focus:outline-none focus:border-amber-600 font-medium"
        />
        <button type="submit" class="h-11 px-6 bg-amber-600 text-white font-extrabold text-xs rounded-xl hover:bg-amber-500 cursor-pointer shrink-0 shadow">
          Gửi (Enter)
        </button>
      </form>
    </div>

    <!-- RIGHT: CUSTOMER CONTEXT SIDEBAR -->
    <div class="w-72 p-6 flex flex-col gap-6 shrink-0 bg-slate-50">
      <h3 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-200 pb-3">Hồ Sơ Khách Hàng</h3>
      
      <div class="flex flex-col gap-3">
        <div class="flex items-center gap-3">
          <img :src="activeChat.customer.avatar" :alt="activeChat.customer.fullName" class="w-12 h-12 rounded-full border-2 border-amber-600" />
          <div class="flex flex-col">
            <span class="text-sm font-extrabold text-slate-900">{{ activeChat.customer.fullName }}</span>
            <span class="text-xs text-amber-600 font-extrabold">Thành viên Hạng {{ activeChat.customer.tier }}</span>
          </div>
        </div>

        <div class="p-4 bg-white border border-slate-200 rounded-2xl flex flex-col gap-2 text-xs">
          <div class="flex justify-between text-slate-600">
            <span>Số điện thoại:</span>
            <strong class="text-slate-900">{{ activeChat.customer.phone }}</strong>
          </div>
          <div class="flex justify-between text-slate-600">
            <span>Email:</span>
            <strong class="text-slate-900 truncate max-w-[120px]">{{ activeChat.customer.email }}</strong>
          </div>
          <div class="flex justify-between text-slate-600">
            <span>Điểm thưởng:</span>
            <strong class="text-amber-600 font-bold">{{ activeChat.customer.points }} pts</strong>
          </div>
        </div>
      </div>

      <div class="flex flex-col gap-2">
        <h4 class="text-xs font-extrabold text-slate-900">Đơn Hàng Gần Đây</h4>
        <div class="p-4 bg-white border border-slate-200 rounded-2xl flex flex-col gap-2 text-xs">
          <div class="flex justify-between font-bold text-slate-900">
            <span>Mã: {{ ordersJson[0].code }}</span>
            <span class="text-emerald-600">Đang giao</span>
          </div>
          <p class="text-[11px] text-slate-500 font-medium">1x Nike Air Force Kinetic (Size 41)</p>
          <span class="text-xs font-extrabold text-amber-600">2.890.000đ</span>
        </div>
      </div>
    </div>
  </div>
</template>
