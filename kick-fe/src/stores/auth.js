import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)

  const isAuthenticated = computed(() => !!user.value)
  const roles = computed(() => user.value?.roles ?? [])
  const isAdmin = computed(() => roles.value.includes('ADMIN') || roles.value.includes('SALES_MANAGER'))
  const isStaff = computed(() => roles.value.includes('STAFF'))
  const isCustomer = computed(() => roles.value.includes('CUSTOMER') || isAuthenticated.value)

  function login({ email, fullName, roles: nextRoles = ['CUSTOMER'] }) {
    user.value = {
      id: 1,
      email,
      fullName: fullName || email.split('@')[0],
      avatarUrl: '',
      roles: nextRoles,
    }
  }

  function logout() {
    user.value = null
  }

  function demoAs(role) {
    const map = {
      customer: { email: 'customer@kickverse.vn', fullName: 'Nguyễn Minh An', roles: ['CUSTOMER'] },
      admin: { email: 'admin@kickverse.vn', fullName: 'Admin KickVerse', roles: ['ADMIN'] },
      manager: {
        email: 'manager@kickverse.vn',
        fullName: 'Quản lý bán hàng',
        roles: ['SALES_MANAGER'],
      },
      staff: { email: 'staff@kickverse.vn', fullName: 'Nhân viên kho', roles: ['STAFF'] },
    }
    login(map[role] || map.customer)
  }

  return {
    user,
    isAuthenticated,
    roles,
    isAdmin,
    isStaff,
    isCustomer,
    login,
    logout,
    demoAs,
  }
})
