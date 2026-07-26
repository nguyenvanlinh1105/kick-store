<script setup>
import StaticPageLayout from '@/layouts/StaticPageLayout.vue'
</script>

<template>
  <StaticPageLayout title="Về Chúng Tôi — KickVerse Store" subtitle="Giới thiệu thương hiệu">
    <div class="flex flex-col gap-4">
      <p class="text-base text-white font-semibold">KickVerse là điểm đến uy tín dành cho cộng đồng đam mê sneaker và thời trang streetwear tại Việt Nam.</p>
      <p>Thành lập từ năm 2024, chúng tôi cam kết mang tới cho khách hàng 100% sản phẩm sneaker chính hãng chọn lọc từ các đại gia thể thao toàn cầu như Nike, Adidas, Jordan, New Balance, Puma, Converse.</p>
      <div class="grid grid-cols-2 md:grid-cols-4 gap-4 py-4 text-center">
        <div class="p-4 bg-black/40 border border-white/10 rounded-xl">
          <span class="text-2xl font-extrabold text-primary block">100%</span>
          <span class="text-[11px] text-neutral-400">Chính hãng Auth</span>
        </div>
        <div class="p-4 bg-black/40 border border-white/10 rounded-xl">
          <span class="text-2xl font-extrabold text-primary block">24h</span>
          <span class="text-[11px] text-neutral-400">Giao nhanh nội thành</span>
        </div>
        <div class="p-4 bg-black/40 border border-white/10 rounded-xl">
          <span class="text-2xl font-extrabold text-primary block">14 Ngày</span>
          <span class="text-[11px] text-neutral-400">Đổi trả linh hoạt</span>
        </div>
        <div class="p-4 bg-black/40 border border-white/10 rounded-xl">
          <span class="text-2xl font-extrabold text-primary block">10K+</span>
          <span class="text-[11px] text-neutral-400">Khách hàng tin tưởng</span>
        </div>
      </div>
    </div>
  </StaticPageLayout>
</template>
