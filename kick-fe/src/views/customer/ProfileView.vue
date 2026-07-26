<script setup>
import { ref } from 'vue'
import customersJson from '@/data/json/customers.json'

const user = customersJson[0]

const profileForm = ref({
  fullName: user.fullName || 'Nguyễn Văn A',
  email: user.email || 'nguyenvana@gmail.com',
  phone: user.phone || '0987654321',
  dob: '1998-05-15',
  gender: 'male',
  avatar: user.avatar,
})

const passwordForm = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const notifPrefs = ref({
  orderUpdates: true,
  promotions: true,
  vipDrops: true,
})

const profileSaved = ref(false)
const passwordSaved = ref(false)

function saveProfile() {
  profileSaved.value = true
  setTimeout(() => (profileSaved.value = false), 3000)
}

function changePassword() {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    alert('Mật khẩu xác nhận không trùng khớp!')
    return
  }
  passwordSaved.value = true
  passwordForm.value = { currentPassword: '', newPassword: '', confirmPassword: '' }
  setTimeout(() => (passwordSaved.value = false), 3000)
}
</script>

<template>
  <div class="flex flex-col gap-8">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Hồ Sơ Cá Nhân</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Quản lý thông tin tài khoản, mật khẩu bảo mật và tùy chọn thông báo</p>
      </div>
    </div>

    <!-- 1. PERSONAL INFORMATION & AVATAR (PURE WHITE LIGHT THEME) -->
    <form @submit.prevent="saveProfile" class="bg-white border border-slate-200 rounded-3xl p-6 md:p-8 flex flex-col gap-6 shadow-sm">
      <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">1. Thông Tin Thành Viên</h2>

      <!-- Avatar Change Row -->
      <div class="flex items-center gap-6">
        <img :src="profileForm.avatar" :alt="profileForm.fullName" class="w-20 h-20 rounded-full border-4 border-amber-600/30 object-cover shadow" />
        <div class="flex flex-col gap-2">
          <span class="text-xs font-extrabold text-slate-900">Ảnh Đại Diện Tài Khoản</span>
          <span class="text-[11px] text-slate-500">Định dạng JPG, PNG tối đa 5MB</span>
          <button type="button" class="px-4 py-2 bg-slate-100 hover:bg-slate-900 hover:text-white border border-slate-200 text-slate-800 text-xs font-extrabold rounded-xl transition-all cursor-pointer w-fit shadow-sm">
            📷 Đổi Ảnh Đại Diện
          </button>
        </div>
      </div>

      <!-- Inputs Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Họ và tên *</label>
          <input v-model="profileForm.fullName" type="text" required class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium focus:outline-none focus:border-amber-600" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Địa chỉ Email (Đã xác minh)</label>
          <input v-model="profileForm.email" type="email" disabled class="h-11 px-4 text-xs bg-slate-100 border border-slate-200 rounded-xl text-slate-500 font-medium cursor-not-allowed" />
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Số điện thoại *</label>
          <input v-model="profileForm.phone" type="text" required class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium focus:outline-none focus:border-amber-600" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Ngày sinh</label>
          <input v-model="profileForm.dob" type="date" class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium focus:outline-none focus:border-amber-600" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Giới tính</label>
          <select v-model="profileForm.gender" class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-bold focus:outline-none focus:border-amber-600">
            <option value="male">Nam</option>
            <option value="female">Nữ</option>
            <option value="other">Khác</option>
          </select>
        </div>
      </div>

      <div class="flex items-center gap-4 pt-2">
        <button type="submit" class="px-6 py-3 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
          💾 LƯU THAY ĐỔI HỒ SƠ
        </button>
        <span v-if="profileSaved" class="text-xs text-emerald-600 font-extrabold animate-fade-in">
          ✓ Đã cập nhật hồ sơ cá nhân thành công!
        </span>
      </div>
    </form>

    <!-- 2. CHANGE PASSWORD & SECURITY -->
    <form @submit.prevent="changePassword" class="bg-white border border-slate-200 rounded-3xl p-6 md:p-8 flex flex-col gap-6 shadow-sm">
      <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">2. Đổi Mật Khẩu Bảo Mật</h2>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Mật khẩu hiện tại</label>
          <input v-model="passwordForm.currentPassword" type="password" placeholder="••••••••" class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium focus:outline-none focus:border-amber-600" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Mật khẩu mới (Tối thiểu 8 ký tự)</label>
          <input v-model="passwordForm.newPassword" type="password" placeholder="••••••••" class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium focus:outline-none focus:border-amber-600" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Xác nhận mật khẩu mới</label>
          <input v-model="passwordForm.confirmPassword" type="password" placeholder="••••••••" class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium focus:outline-none focus:border-amber-600" />
        </div>
      </div>

      <div class="flex items-center gap-4 pt-2">
        <button type="submit" class="px-6 py-3 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
          🔒 ĐỔI MẬT KHẨU
        </button>
        <span v-if="passwordSaved" class="text-xs text-emerald-600 font-extrabold animate-fade-in">
          ✓ Đổi mật khẩu tài khoản thành công!
        </span>
      </div>
    </form>

    <!-- 3. NOTIFICATION PREFERENCES -->
    <div class="bg-white border border-slate-200 rounded-3xl p-6 md:p-8 flex flex-col gap-4 shadow-sm">
      <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">3. Tùy Chọn Nhận Thông Báo</h2>

      <div class="flex flex-col gap-3 text-xs">
        <label class="flex items-center justify-between p-3.5 bg-slate-50 border border-slate-200 rounded-2xl cursor-pointer">
          <div>
            <strong class="text-slate-900 block font-extrabold">Cập nhật tiến trình đơn hàng</strong>
            <span class="text-slate-500 font-medium">Nhận SMS & Email khi đơn hàng được duyệt, đang giao hoặc hoàn tất.</span>
          </div>
          <input type="checkbox" v-model="notifPrefs.orderUpdates" class="accent-amber-600 w-4 h-4 rounded" />
        </label>

        <label class="flex items-center justify-between p-3.5 bg-slate-50 border border-slate-200 rounded-2xl cursor-pointer">
          <div>
            <strong class="text-slate-900 block font-extrabold">Thông báo khuyến mãi & Flash Sale</strong>
            <span class="text-slate-500 font-medium">Nhận tin tức ưu đãi giảm giá độc quyền dành cho thành viên VIP.</span>
          </div>
          <input type="checkbox" v-model="notifPrefs.promotions" class="accent-amber-600 w-4 h-4 rounded" />
        </label>

        <label class="flex items-center justify-between p-3.5 bg-slate-50 border border-slate-200 rounded-2xl cursor-pointer">
          <div>
            <strong class="text-slate-900 block font-extrabold">Thông báo đợt phát hành Limited Drop</strong>
            <span class="text-slate-500 font-medium">Nhắc nhở trước 15 phút khi các mẫu giày phiên bản giới hạn lên sàn.</span>
          </div>
          <input type="checkbox" v-model="notifPrefs.vipDrops" class="accent-amber-600 w-4 h-4 rounded" />
        </label>
      </div>
    </div>
  </div>
</template>
