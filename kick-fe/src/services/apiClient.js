import axios from 'axios'

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to attach JWT token
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

let isRefreshing = false
let failedQueue = []

const processQueue = (error, token = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error)
    } else {
      prom.resolve(token)
    }
  })
  failedQueue = []
}

// Response interceptor to handle auto token refresh on 401
apiClient.interceptors.response.use(
  (response) => {
    return response
  },
  async (error) => {
    const originalRequest = error.config

    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject })
        })
          .then((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`
            return apiClient(originalRequest)
          })
          .catch((err) => {
            return Promise.reject(err)
          })
      }

      originalRequest._retry = true
      isRefreshing = true

      const refreshToken = localStorage.getItem('refreshToken')
      if (!refreshToken) {
        isRefreshing = false
        handleLogout()
        return Promise.reject(error)
      }

      try {
        // Call backend refresh token endpoint directly (use axios directly to avoid interceptor headers)
        const response = await axios.post(
          `${apiClient.defaults.baseURL}/pub/auth/refresh`,
          { refreshToken }
        )
        const { token: newAccessToken, refreshToken: newRefreshToken } = response.data.data

        localStorage.setItem('accessToken', newAccessToken)
        if (newRefreshToken) {
          localStorage.setItem('refreshToken', newRefreshToken)
        }

        processQueue(null, newAccessToken)
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`
        isRefreshing = false

        return apiClient(originalRequest)
      } catch (refreshError) {
        processQueue(refreshError, null)
        isRefreshing = false
        handleLogout()
        return Promise.reject(refreshError)
      }
    }

    return Promise.reject(error)
  }
)

function handleLogout() {
  localStorage.removeItem('accessToken')
  localStorage.removeItem('refreshToken')
  localStorage.removeItem('user')
  
  // Custom event so that the store or window can react immediately
  window.dispatchEvent(new Event('auth-logout'))
  
  // Redirect to login page if we are not already on an auth page
  const currentPath = window.location.pathname
  if (currentPath !== '/login' && currentPath !== '/register') {
    window.location.href = `/login?redirect=${encodeURIComponent(currentPath)}`
  }
}

export default apiClient
