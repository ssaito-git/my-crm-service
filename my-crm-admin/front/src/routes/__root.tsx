import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from '@mui/material'
import { QueryClient } from '@tanstack/react-query'
import {
  ErrorComponentProps,
  Outlet,
  createRootRouteWithContext,
} from '@tanstack/react-router'
import { Header } from './-components/Header'
import { Sidebar } from './-components/Sidebar'
import { Authentication } from './-components/Authentication'

export const Route = createRootRouteWithContext<{
  queryClient: QueryClient
}>()({
  component: RootComponent,
  errorComponent: ErrorComponent,
})

function RootComponent() {
  return (
    <>
      <Authentication />
      <Box sx={{ height: '100vh', display: 'flex', flexDirection: 'column' }}>
        <Header />
        <Box sx={{ display: 'flex', flex: '1 1 0', overflow: 'auto' }}>
          <Sidebar />
          <Box sx={{ flexGrow: 1 }}>
            <Outlet />
          </Box>
        </Box>
      </Box>
    </>
  )
}

function ErrorComponent({ error }: ErrorComponentProps) {
  console.error(error)

  const handleReloadClick = () => {
    window.location.assign(window.location.origin)
  }

  return (
    <Dialog open={true} fullWidth>
      <DialogTitle>Unknown error.</DialogTitle>
      <DialogContent>Unknown error occurred. Press 'Reload'.</DialogContent>
      <DialogActions>
        <Button onClick={handleReloadClick}>Reload</Button>
      </DialogActions>
    </Dialog>
  )
}
