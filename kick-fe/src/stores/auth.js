import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null)
  const accessToken = ref(localStorage.getItem('accessToken') || '')
  const refreshToken = ref(localStorage.getItem('refreshToken') || '')

  const isAuthenticated = computed(() => !!user.value)
  
  const roles = computed(() => {
    if (!user.value?.roles) return []
    // Support backend Role entity structure [{name: 'ADMIN'}]
    return user.value.roles.map((r) => (typeof r === 'object' && r !== null ? r.name : r))
  })

  const isAdmin = computed(() => roles.value.includes('ADMIN') || roles.value.includes('SALES_MANAGER'))
  const isStaff = computed(() => roles.value.includes('STAFF'))
  const isCustomer = computed(() => roles.value.includes('CUSTOMER') || isAuthenticated.value)

  function login(authData) {
    if (!authData || !authData.token) {
      throw new Error('Invalid authentication response')
    }
    // Backend LoginResponse format
    accessToken.value = authData.token
    refreshToken.value = authData.refreshToken || ''
    user.value = authData.user

    localStorage.setItem('accessToken', authData.token)
    if (authData.refreshToken) {
      localStorage.setItem('refreshToken', authData.refreshToken)
    } else {
      localStorage.removeItem('refreshToken')
    }
    localStorage.setItem('user', JSON.stringify(authData.user))
  }

  function logout() {
    user.value = null
    accessToken.value = ''
    refreshToken.value = ''
    localStorage.removeItem('user')
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
  }

  return {
    user,
    accessToken,
    refreshToken,
    isAuthenticated,
    roles,
    isAdmin,
    isStaff,
    isCustomer,
    login,
    logout,
  }
})
