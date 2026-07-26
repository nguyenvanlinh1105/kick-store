<script setup>
import { ref } from 'vue'
import customersJson from '@/data/json/customers.json'
import ordersJson from '@/data/json/orders.json'

const activeChat = ref({
  customer: customersJson[0],
  messages: [
    { sender: 'customer', text: 'Chào shop, đôi Air Force Kinetic size 41 này form có chật không ạ?', time: '14:20' },
    { sender: 'staff', text: 'Dạ chào bạn! Đôi này form chuẩn EU ạ, nếu bạn chân bè có thể nhích lên 0.5 size nha.', time: '14:22' },
    { sender: 'customer', text: 'Shop còn sẵn màu Trắng ở cửa hàng Hà Nội không?', time: '14:25' },
  ],
})

const newMessage = ref('')

function sendMessage() {
  if (!newMessage.value) return
  activeChat.value.messages.push({
    sender: 'staff',
    text: newMessage.value,
    time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
  })
  newMessage.value = ''
}
</script>

<template>
  <div class="h-[calc(100vh-8rem)] bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden flex">
    <!-- LEFT: CHAT LIST -->
    <div class="w-72 border-r border-white/10 p-4 flex flex-col gap-3 shrink-0">
      <h2 class="text-xs font-bold text-neutral-400 uppercase tracking-wider">Hội Thoại Đang Tương Tác</h2>
      <div class="p-3 bg-primary/10 border border-primary/30 rounded-xl flex items-center gap-3 cursor-pointer">
        <img :src="activeChat.customer.avatar" :alt="activeChat.customer.fullName" class="w-10 h-10 rounded-full border border-primary" />
        <div class="flex flex-col">
          <span class="text-xs font-bold text-white">{{ activeChat.customer.fullName }}</span>
          <span class="text-[10px] text-primary">Đang tư vấn size...</span>
        </div>
      </div>
    </div>

    <!-- CENTER: REALTIME CHAT MESSAGES BOX -->
    <div class="flex-1 flex flex-col justify-between border-r border-white/10">
      <!-- Chat Header -->
      <div class="p-4 border-b border-white/10 flex items-center justify-between bg-black/40">
        <span class="text-xs font-bold text-white">Trò chuyện trực tiếp với {{ activeChat.customer.fullName }}</span>
        <span class="text-[10px] bg-emerald-500/20 text-emerald-400 font-bold px-2 py-0.5 rounded">Online</span>
      </div>

      <!-- Messages Stream -->
      <div class="p-4 flex flex-col gap-3 overflow-y-auto flex-1 bg-black/20">
        <div
          v-for="(msg, idx) in activeChat.messages"
          :key="idx"
          class="flex flex-col max-w-xs text-xs"
          :class="[msg.sender === 'staff' ? 'self-end items-end' : 'self-start items-start']"
        >
          <div
            class="p-3 rounded-2xl leading-relaxed"
            :class="[msg.sender === 'staff' ? 'bg-primary text-black font-medium' : 'bg-neutral-800 text-white']"
          >
            {{ msg.text }}
          </div>
          <span class="text-[9px] text-neutral-500 mt-1">{{ msg.time }}</span>
        </div>
      </div>

      <!-- Message Input Form -->
      <form @submit.prevent="sendMessage" class="p-4 border-t border-white/10 flex gap-2 bg-black/40">
        <input
          v-model="newMessage"
          type="text"
          placeholder="Nhập câu trả lời tư vấn cho khách hàng..."
          class="flex-1 h-10 px-4 text-xs bg-black border border-white/15 rounded-xl text-white focus:outline-none focus:border-primary"
        />
        <button type="submit" class="h-10 px-5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer shrink-0">
          Gửi (Enter)
        </button>
      </form>
    </div>

    <!-- RIGHT: CONTEXT SIDEBAR -->
    <div class="w-72 p-4 flex flex-col gap-4 shrink-0 bg-black/40">
      <h3 class="text-xs font-bold text-neutral-400 uppercase tracking-wider border-b border-white/10 pb-2">Thông Tin Khách Hàng</h3>
      <div class="flex flex-col gap-1 text-xs">
        <span class="text-white font-bold">{{ activeChat.customer.fullName }}</span>
        <span class="text-neutral-400">SĐT: {{ activeChat.customer.phone }}</span>
        <span class="text-neutral-400">Hạng: <strong class="text-amber-400">{{ activeChat.customer.tier }}</strong></span>
        <span class="text-neutral-400">Điểm: {{ activeChat.customer.points }} pts</span>
      </div>
    </div>
  </div>
</template>
