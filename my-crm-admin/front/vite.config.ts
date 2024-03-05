import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { TanStackRouterVite } from '@tanstack/router-vite-plugin'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), TanStackRouterVite()],
  server: {
    host: '127.0.0.1',
    proxy: {
      '/login/oauth2': {
        target: 'http://127.0.0.1:8092',
        xfwd: true,
      },
      '/login/status': {
        target: 'http://127.0.0.1:8092',
      },
      '/api': {
        target: 'http://127.0.0.1:8092',
      },
    },
  },
})
