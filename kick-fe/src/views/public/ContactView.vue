<script setup>
import { ref } from 'vue'
import StaticPageLayout from '@/layouts/StaticPageLayout.vue'

const contactForm = ref({ name: '', email: '', message: '' })
const isSent = ref(false)

function handleSendContact() {
  if (!contactForm.value.name || !contactForm.value.email) return
  isSent.value = true
}
</script>

<template>
  <StaticPageLayout title="Liên Hệ Với KickVerse" subtitle="Hỗ trợ CSKH 24/7">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- Contact Details & Google Maps -->
      <div class="flex flex-col gap-4">
        <h3 class="text-base font-bold text-white">Thông Tin Trụ Sở</h3>
        <p class="text-xs text-neutral-300">📍 Địa chỉ: Số 10 Phạm Hùng, Phường Dịch Vọng, Quận Cầu Giấy, Hà Nội</p>
        <p class="text-xs text-neutral-300">📞 Hotline: 1900 6789 (8:00 - 22:00 hàng ngày)</p>
        <p class="text-xs text-neutral-300">✉️ Email: cskh@kickverse.vn</p>

        <!-- Google Maps Iframe -->
        <div class="w-full h-48 rounded-xl overflow-hidden border border-white/10 mt-2">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.096814183571!2d105.7818!3d21.0287!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x0!2zMjHCsDAxJzIzLjMiTiAxMDXCsDQ2JzU0LjUiRQ!5e0!3m2!1svi!2s!4v1620000000000!5m2!1svi!2s"
            width="100%"
            height="100%"
            style="border:0;"
            allowfullscreen=""
            loading="lazy"
          ></iframe>
        </div>
      </div>

      <!-- Contact Message Form -->
      <div class="flex flex-col gap-4">
        <h3 class="text-base font-bold text-white">Gửi Tin Nhắn Cho Chúng Tôi</h3>
        <form v-if="!isSent" @submit.prevent="handleSendContact" class="flex flex-col gap-3">
          <input v-model="contactForm.name" placeholder="Họ và tên của bạn *" required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
          <input v-model="contactForm.email" type="email" placeholder="Địa chỉ email *" required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
          <textarea v-model="contactForm.message" rows="4" placeholder="Nội dung cần hỗ trợ tư vấn..." required class="p-3 bg-black border border-white/15 rounded-lg text-white text-xs"></textarea>
          <button type="submit" class="h-11 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer mt-1">
            GỬI TIN NHẮN →
          </button>
        </form>

        <div v-else class="p-4 bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 font-bold text-xs rounded-xl text-center">
          ✓ Cảm ơn bạn! Chúng tôi đã nhận được tin nhắn và sẽ phản hồi trong 2 giờ làm việc.
        </div>
      </div>
    </div>
  </StaticPageLayout>
</template>
