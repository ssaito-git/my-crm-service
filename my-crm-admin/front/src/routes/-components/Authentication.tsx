import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from '@mui/material'
import { useQuery } from '@tanstack/react-query'
import { useRouterState } from '@tanstack/react-router'
import {
  signInStatusQueryOptions,
  UnauthenticatedError,
} from '../-api/fetchSignInStatus'

export const Authentication = () => {
  const query = useQuery(signInStatusQueryOptions)
  const routerState = useRouterState()
  const signInPath = `/login/oauth2?return_url=${routerState.location.href}`

  if (query.isError) {
    if (query.error instanceof UnauthenticatedError) {
      return (
        <Dialog open={true} fullWidth>
          <DialogTitle>Please sign in.</DialogTitle>
          <DialogContent>
            You are signed out. Press 'Start sign in' to sign in.
          </DialogContent>
          <DialogActions>
            <Button href={signInPath}>Start sign in</Button>
          </DialogActions>
        </Dialog>
      )
    }

    throw query.error
  }

  return null
}
