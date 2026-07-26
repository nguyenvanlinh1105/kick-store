<script setup>
import { ref } from 'vue'
import StaticPageLayout from '@/layouts/StaticPageLayout.vue'

const faqs = ref([
  { q: 'Làm thế nào để biết giày KickVerse là hàng chính hãng 100%?', a: 'Toàn bộ sản phẩm bán ra tại KickVerse đều đi kèm hóa đơn và tem chống hàng giả. Chúng tôi cam kết hoàn tiền 200% nếu phát hiện hàng giả.' },
  { q: 'Nếu mang không vừa size tôi có được đổi không?', a: 'Có! Bạn được hỗ trợ đổi size miễn phí tận nhà trong vòng 14 ngày.' },
  { q: 'Shop hỗ trợ các hình thức thanh toán nào?', a: 'KickVerse hỗ trợ COD (tiền mặt), VietQR chuyển khoản ngân hàng, VNPAY, Ví MoMo và Thẻ Visa/Mastercard.' },
])
</script>

<template>
  <StaticPageLayout title="Câu Hỏi Thường Gặp (FAQ)" subtitle="Giải đáp thắc mắc">
    <div class="flex flex-col gap-4">
      <div v-for="(item, idx) in faqs" :key="idx" class="p-4 bg-black/40 border border-white/10 rounded-xl flex flex-col gap-2">
        <h3 class="text-sm font-bold text-primary">Q: {{ item.q }}</h3>
        <p class="text-xs text-neutral-300 leading-relaxed font-medium">A: {{ item.a }}</p>
      </div>
    </div>
  </StaticPageLayout>
</template>
