import { RouterProvider, createRouter } from '@tanstack/react-router'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { routeTree } from './routeTree.gen'
import { queryClient } from './lib/react-query'
import { QueryClientProvider } from '@tanstack/react-query'
import { MuiCustomThemeProvdier } from './provider/MuiCustomThemeProvider'
import { CssBaseline } from '@mui/material'
import axios from 'axios'

axios.defaults.baseURL = 'http://127.0.0.1:5173/api'
axios.defaults.withCredentials = true

const router = createRouter({
  routeTree,
  context: {
    queryClient,
  },
  notFoundMode: 'fuzzy',
})

declare module '@tanstack/react-router' {
  interface Register {
    router: typeof router
  }
}

const rootElement = document.getElementById('root')!

if (!rootElement.innerHTML) {
  const root = createRoot(rootElement)
  root.render(
    <StrictMode>
      <QueryClientProvider client={queryClient}>
        <MuiCustomThemeProvdier>
          <CssBaseline enableColorScheme={true} />
          <RouterProvider router={router} />
        </MuiCustomThemeProvdier>
      </QueryClientProvider>
    </StrictMode>,
  )
}
