<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

const activeTab = ref('order-guide')
const searchQuery = ref('')

const sizeChartData = [
  { eu: 38, us: 5.5, cm: '23.5 cm' },
  { eu: 39, us: 6.5, cm: '24.5 cm' },
  { eu: 40, us: 7.0, cm: '25.0 cm' },
  { eu: 41, us: 8.0, cm: '25.5 cm' },
  { eu: 42, us: 8.5, cm: '26.0 cm' },
  { eu: 43, us: 9.5, cm: '27.0 cm' },
  { eu: 44, us: 10.0, cm: '28.0 cm' },
]

const consultationForm = ref({ name: '', phone: '', question: '' })
const consultationSent = ref(false)

function submitConsultation() {
  if (!consultationForm.value.name || !consultationForm.value.phone) return
  consultationSent.value = true
  setTimeout(() => {
    consultationSent.value = false
    consultationForm.value = { name: '', phone: '', question: '' }
  }, 4000)
}
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen">
    <!-- HERO HEADER BANNER -->
    <section class="bg-gradient-to-br from-slate-900 via-slate-800 to-slate-950 text-white py-16 px-4">
      <div class="kv-container max-w-4xl text-center flex flex-col items-center gap-4">
        <span class="px-3.5 py-1 bg-amber-500/20 border border-amber-500/40 text-amber-400 font-extrabold text-xs tracking-wider uppercase rounded-full">
          Trung Tâm Trợ Giúp KickVerse
        </span>
        <h1 class="font-display text-4xl sm:text-5xl md:text-6xl font-extrabold tracking-tight">
          HƯỚNG DẪN MUA SẮM & TƯ VẤN KHÁCH HÀNG
        </h1>
        <p class="text-xs sm:text-sm text-slate-300 max-w-2xl leading-relaxed">
          Giải đáp mọi thắc mắc về cách đặt hàng, bảng hướng dẫn chọn size chuẩn 100%, quy trình đổi trả hàng miễn phí 14 ngày & kênh tư vấn trực tuyến 24/7.
        </p>

        <!-- Search Bar -->
        <div class="relative w-full max-w-xl mt-4">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Nhập nội dung cần trợ giúp (VD: Đổi size, Cách mua hàng, Freeship...)"
            class="w-full h-13 pl-12 pr-6 text-xs bg-white text-slate-900 rounded-2xl border-2 border-amber-500 focus:outline-none shadow-xl font-medium"
          />
          <span class="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-lg">🔍</span>
        </div>

        <!-- Quick Contacts Badges -->
        <div class="flex flex-wrap items-center justify-center gap-4 mt-2 text-xs">
          <a href="tel:19006789" class="px-4 py-2 bg-white/10 border border-white/15 rounded-xl hover:bg-amber-600 hover:border-amber-600 transition-all text-white font-bold no-underline">
            📞 Hotline: 1900 6789 (8:00 - 22:00)
          </a>
          <RouterLink to="/staff/support" class="px-4 py-2 bg-amber-600 text-white rounded-xl hover:bg-amber-500 transition-all font-extrabold no-underline shadow">
            💬 Chat CSKH Trực Tuyến
          </RouterLink>
          <a href="mailto:cskh@kickverse.vn" class="px-4 py-2 bg-white/10 border border-white/15 rounded-xl hover:bg-white hover:text-slate-900 transition-all text-white font-bold no-underline">
            ✉️ cskh@kickverse.vn
          </a>
        </div>
      </div>
    </section>

    <!-- NAVIGATION TABS FOR SUPPORT MODULES -->
    <section class="sticky top-16 z-30 bg-white border-b border-slate-200 shadow-sm">
      <div class="kv-container flex items-center justify-center gap-2 md:gap-4 overflow-x-auto py-3 no-scrollbar">
        <button
          v-for="tab in [
            { id: 'order-guide', label: '🛍️ Cách Đặt Hàng' },
            { id: 'size-guide', label: '📏 Hướng Dẫn Chọn Size' },
            { id: 'return-policy', label: '🔄 Quy Trình Đổi Trả 14 Ngày' },
            { id: 'live-consultation', label: '💬 Hỗ Trợ Tư Vấn 24/7' },
          ]"
          :key="tab.id"
          type="button"
          class="px-5 py-3 text-xs font-extrabold rounded-2xl transition-all cursor-pointer shrink-0 border"
          :class="[activeTab === tab.id ? 'bg-slate-900 text-white border-slate-900 shadow-md' : 'bg-slate-50 text-slate-700 border-slate-200 hover:border-slate-300']"
          @click="activeTab = tab.id"
        >
          {{ tab.label }}
        </button>
      </div>
    </section>

    <!-- CONTENT SECTIONS CONTAINER -->
    <div class="kv-container py-12 max-w-5xl">
      <!-- 1. CÁCH ĐẶT HÀNG STEP-BY-STEP -->
      <section v-if="activeTab === 'order-guide'" class="flex flex-col gap-8">
        <div class="text-center max-w-xl mx-auto">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1">Quy Trình 4 Bước</span>
          <h2 class="text-3xl font-extrabold text-slate-900">Hướng Dẫn Mua Hàng Nhanh Chóng</h2>
          <p class="text-xs text-slate-500 mt-2">Chỉ mất 2 phút để hoàn tất đơn hàng sneaker chính hãng tại KickVerse</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
          <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
            <span class="w-10 h-10 rounded-2xl bg-amber-500/10 text-amber-700 font-extrabold text-sm flex items-center justify-center">01</span>
            <h3 class="text-sm font-extrabold text-slate-900">Tìm Kiếm & Chọn Giày</h3>
            <p class="text-xs text-slate-500 leading-relaxed font-medium">Truy cập thanh tìm kiếm hoặc danh mục Nike, Adidas, Jordan... để chọn đôi giày yêu thích.</p>
          </div>

          <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
            <span class="w-10 h-10 rounded-2xl bg-amber-500/10 text-amber-700 font-extrabold text-sm flex items-center justify-center">02</span>
            <h3 class="text-sm font-extrabold text-slate-900">Chọn Size & Màu</h3>
            <p class="text-xs text-slate-500 leading-relaxed font-medium">Tham khảo bảng hướng dẫn chọn size chuẩn EU và bấm nút "Thêm vào giỏ hàng".</p>
          </div>

          <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
            <span class="w-10 h-10 rounded-2xl bg-amber-500/10 text-amber-700 font-extrabold text-sm flex items-center justify-center">03</span>
            <h3 class="text-sm font-extrabold text-slate-900">Điền Địa Chỉ & Voucher</h3>
            <p class="text-xs text-slate-500 leading-relaxed font-medium">Nhập địa chỉ nhận hàng 3 cấp (Tỉnh/Quận/Phường) và áp mã Voucher 100k nếu có.</p>
          </div>

          <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
            <span class="w-10 h-10 rounded-2xl bg-amber-500/10 text-amber-700 font-extrabold text-sm flex items-center justify-center">04</span>
            <h3 class="text-sm font-extrabold text-slate-900">Thanh Toán & Xóa Đơn</h3>
            <p class="text-xs text-slate-500 leading-relaxed font-medium">Chọn VietQR Chuyển khoản hoặc COD. Hệ thống tự động tạo mã theo dõi tiến trình.</p>
          </div>
        </div>
      </section>

      <!-- 2. HƯỚNG DẪN CHỌN SIZE GIÀY CHUẨN 100% -->
      <section v-else-if="activeTab === 'size-guide'" class="flex flex-col gap-8">
        <div class="text-center max-w-xl mx-auto">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1">Chuẩn Xác 100%</span>
          <h2 class="text-3xl font-extrabold text-slate-900">Hướng Dẫn Đo Bàn Chân & Chọn Size</h2>
          <p class="text-xs text-slate-500 mt-2">Bảng quy đổi size giày chuẩn quốc tế EU - US - CM dành cho cả Nam & Nữ</p>
        </div>

        <!-- 3-STEP MEASURING GUIDE -->
        <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow-sm flex flex-col gap-6">
          <h3 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Cách Đo Chiều Dài Bàn Chân Tận Nhà</h3>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div class="p-4 bg-slate-50 rounded-2xl border border-slate-200 flex flex-col gap-2 text-xs">
              <span class="font-extrabold text-amber-600">Bước 1: Chuẩn bị</span>
              <p class="text-slate-600">Đặt một tờ giấy A4 trên sàn nhà sát bức tường phẳng. Đặt bàn chân vuông góc sát tường.</p>
            </div>

            <div class="p-4 bg-slate-50 rounded-2xl border border-slate-200 flex flex-col gap-2 text-xs">
              <span class="font-extrabold text-amber-600">Bước 2: Đánh dấu</span>
              <p class="text-slate-600">Dùng bút chì vạch một đường tại đầu ngón chân dài nhất và điểm gót chân sát tường.</p>
            </div>

            <div class="p-4 bg-slate-50 rounded-2xl border border-slate-200 flex flex-col gap-2 text-xs">
              <span class="font-extrabold text-amber-600">Bước 3: Tra bảng</span>
              <p class="text-slate-600">Dùng thước đo khoảng cách bằng cm và đối chiếu với bảng quy đổi bên dưới.</p>
            </div>
          </div>

          <!-- PRO TIP BOX -->
          <div class="p-4 bg-amber-500/10 border border-amber-500/30 rounded-2xl text-xs font-bold text-amber-800 flex items-center gap-3">
            <span class="text-2xl">💡</span>
            <span>Mẹo nhỏ: Nếu chân bạn có độ bè ngang hoặc thích mang vớ dày, hãy cộng thêm 0.5 size (Ví dụ: Chân 25.0cm chuẩn 40 hãy chọn size 41).</span>
          </div>
        </div>

        <!-- SIZE CONVERSION TABLE -->
        <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow-sm overflow-x-auto">
          <h3 class="text-base font-extrabold text-slate-900 mb-4">Bảng Quy Đổi Size Giày EU / US / CM</h3>
          <table class="w-full text-xs text-center border-collapse">
            <thead>
              <tr class="bg-slate-900 text-white font-extrabold">
                <th class="py-3 px-4 border border-slate-200 rounded-tl-xl">Size EU</th>
                <th class="py-3 px-4 border border-slate-200">Size US (Nam/Nữ)</th>
                <th class="py-3 px-4 border border-slate-200 rounded-tr-xl">Chiều dài chân (cm)</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="row in sizeChartData" :key="row.eu" class="border-b border-slate-100 hover:bg-slate-50">
                <td class="py-3 px-4 font-extrabold text-amber-600">{{ row.eu }}</td>
                <td class="py-3 px-4 font-bold text-slate-700">{{ row.us }}</td>
                <td class="py-3 px-4 font-semibold text-slate-900">{{ row.cm }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>

      <!-- 3. CHÍNH SÁCH & QUY TRÌNH ĐỔI TRẢ 14 NGÀY -->
      <section v-else-if="activeTab === 'return-policy'" class="flex flex-col gap-8">
        <div class="text-center max-w-xl mx-auto">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1">Cam Kết 100% An Tâm</span>
          <h2 class="text-3xl font-extrabold text-slate-900">Chính Sách Đổi Trả Miễn Phí 14 Ngày</h2>
          <p class="text-xs text-slate-500 mt-1">Đổi size tận nhà tiện lợi nếu không vừa chân</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow-sm flex flex-col gap-4">
            <h3 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Điều Kiện Đổi Hàng</h3>
            <ul class="list-disc pl-5 text-xs text-slate-600 flex flex-col gap-2.5 font-medium">
              <li>Sản phẩm được đổi trong vòng <strong>14 ngày</strong> kể từ ngày nhận kiện hàng.</li>
              <li>Sản phẩm còn nguyên vẹn tem tag, fullbox chính hãng và hoá đơn mua hàng.</li>
              <li>Đế giày và thân giày chưa qua sử dụng ngoài đường (không bị dính bẩn hay trầy xước).</li>
              <li>KickVerse hỗ trợ phí vận chuyển 2 chiều nếu phát sinh lỗi từ nhà sản xuất hoặc nhầm size từ cửa hàng.</li>
            </ul>
          </div>

          <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow-sm flex flex-col gap-4">
            <h3 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Quy Trình 3 Bước Đổi Trả</h3>
            <div class="flex flex-col gap-4 text-xs">
              <div class="flex items-start gap-3">
                <span class="w-7 h-7 rounded-full bg-slate-900 text-white font-extrabold flex items-center justify-center shrink-0">1</span>
                <div>
                  <strong class="text-slate-900 block">Liên hệ CSKH:</strong>
                  <span class="text-slate-600">Gọi Hotline 1900 6789 hoặc bấm yêu cầu đổi hàng trong trang Đơn hàng của tôi.</span>
                </div>
              </div>

              <div class="flex items-start gap-3">
                <span class="w-7 h-7 rounded-full bg-slate-900 text-white font-extrabold flex items-center justify-center shrink-0">2</span>
                <div>
                  <strong class="text-slate-900 block">Shipper đến tận nhà thu hàng:</strong>
                  <span class="text-slate-600">Shipper của KickVerse sẽ đến tận nhà mang đôi mới đến đổi và nhận đôi cũ về.</span>
                </div>
              </div>

              <div class="flex items-start gap-3">
                <span class="w-7 h-7 rounded-full bg-slate-900 text-white font-extrabold flex items-center justify-center shrink-0">3</span>
                <div>
                  <strong class="text-slate-900 block">Hoàn tất đổi sản phẩm:</strong>
                  <span class="text-slate-600">Bạn kiểm tra thử đôi giày mới và ký nhận với shipper.</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 4. HỖ TRỢ TƯ VẤN TRỰC TUYẾN 24/7 -->
      <section v-else-if="activeTab === 'live-consultation'" class="flex flex-col gap-8">
        <div class="text-center max-w-xl mx-auto">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1">Chăm Sóc 24/7</span>
          <h2 class="text-3xl font-extrabold text-slate-900">Tư Vấn Trực Tuyến Với Chuyên Viên</h2>
          <p class="text-xs text-slate-500 mt-1">Đội ngũ Stylist & CSKH của KickVerse sẵn sàng hỗ trợ bạn</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8 items-start">
          <!-- LIVE CHAT BOX TRIGGER CARD -->
          <div class="bg-slate-900 text-white border border-slate-800 rounded-3xl p-8 shadow-xl flex flex-col gap-6">
            <div class="flex items-center gap-3">
              <div class="w-12 h-12 rounded-2xl bg-amber-500/20 text-amber-400 font-extrabold text-2xl flex items-center justify-center">
                💬
              </div>
              <div>
                <h3 class="text-lg font-extrabold text-white">Chat Trực Tiếp Ngay</h3>
                <span class="text-xs text-emerald-400 font-bold">● Chuyên viên tư vấn đang Online</span>
              </div>
            </div>

            <p class="text-xs text-slate-300 leading-relaxed font-medium">
              Bạn phân vân giữa 2 dòng giày? Cần tư vấn cách phối đồ Streetwear hoặc hỏi tồn kho thực tế tại cửa hàng? Bấm nút bên dưới để trò chuyện trực tiếp!
            </p>

            <RouterLink
              to="/staff/support"
              class="h-13 bg-amber-600 text-white font-extrabold text-xs uppercase tracking-wider rounded-2xl flex items-center justify-center gap-2 hover:bg-amber-500 no-underline transition-all shadow-lg"
            >
              🚀 MỞ KHUNG CHAT TƯ VẤN TRỰC TUYẾN →
            </RouterLink>
          </div>

          <!-- QUICK ADVICE REQUEST FORM -->
          <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow-sm flex flex-col gap-4">
            <h3 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Gửi Yêu Cầu Gọi Lai Tư Vấn</h3>
            
            <form @submit.prevent="submitConsultation" class="flex flex-col gap-3">
              <div class="flex flex-col gap-1">
                <label class="text-xs font-bold text-slate-700">Họ và tên của bạn *</label>
                <input v-model="consultationForm.name" type="text" required placeholder="Nguyễn Văn A" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900" />
              </div>

              <div class="flex flex-col gap-1">
                <label class="text-xs font-bold text-slate-700">Số điện thoại *</label>
                <input v-model="consultationForm.phone" type="text" required placeholder="0987654321" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900" />
              </div>

              <div class="flex flex-col gap-1">
                <label class="text-xs font-bold text-slate-700">Nội dung cần tư vấn (VD: Chọn size Air Force 1, Phối đồ...)</label>
                <textarea v-model="consultationForm.question" rows="3" placeholder="Nhập câu hỏi của bạn..." class="p-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900"></textarea>
              </div>

              <button type="submit" class="h-11 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow mt-1">
                GỬI YÊU CẦU TƯ VẤN →
              </button>

              <p v-if="consultationSent" class="text-xs text-emerald-600 font-extrabold text-center mt-2">
                ✓ Cảm ơn bạn! Chuyên viên KickVerse sẽ gọi lại tư vấn trong 5 phút.
              </p>
            </form>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>
