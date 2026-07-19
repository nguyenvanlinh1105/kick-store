import apiClient from './apiClient'

const authService = {
  async register({ email, password, fullName, phone }) {
    const response = await apiClient.post('/pub/auth/register', {
      email,
      password,
      fullName,
      phone,
    })
    return response.data
  },

  async login({ email, password }) {
    const response = await apiClient.post('/pub/auth/login', {
      email,
      password,
    })
    return response.data
  },

  async logout() {
    const refreshToken = localStorage.getItem('refreshToken')
    if (refreshToken) {
      try {
        await apiClient.post('/pub/auth/logout', { refreshToken })
      } catch (error) {
        console.error('Error logging out from server:', error)
      }
    }
    // Always clear localStorage values
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('user')
    
    // Dispatch standard event
    window.dispatchEvent(new Event('auth-logout'))
  },

  async refresh(refreshToken) {
    const response = await apiClient.post('/pub/auth/refresh', {
      refreshToken,
    })
    return response.data
  },
}

export default authService
