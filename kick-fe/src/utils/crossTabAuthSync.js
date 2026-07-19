/**
 * Cross-tab logout sync.
 *
 * The `storage` event is only fired by the browser in OTHER tabs (never
 * in the tab that made the change), so this is the standard way to
 * detect "another tab just logged out" without polling or requiring a
 * manual reload.
 *
 * Call initCrossTabAuthSync() once when the app starts (e.g. in main.js
 * or App.vue onMounted).
 */
export const initCrossTabAuthSync = () => {
  window.addEventListener('storage', (event) => {
    // Only care about the accessToken key being removed (logout)
    if (event.key !== 'accessToken') return

    const stillHasAccessToken = !!event.newValue
    if (stillHasAccessToken) return // this is a login/refresh case, not a logout

    const currentPath = window.location.pathname
    // Hard reload/redirect the page to fully clear the Pinia store and any other in-memory state in this tab.
    if (currentPath.startsWith('/admin')) {
      window.location.href = '/admin/login'
    } else {
      window.location.href = '/login'
    }
  })

  // Handle local tab logout event
  window.addEventListener('auth-logout', () => {
    const currentPath = window.location.pathname
    if (currentPath !== '/login' && currentPath !== '/register') {
      window.location.href = `/login?redirect=${encodeURIComponent(currentPath)}`
    }
  })
}
