import { useQuery, useSuspenseQuery } from '@tanstack/react-query'
import { fetchSignInStatus } from '../-api/fetchSignInStatus'

export const useSignInStatusSuspense = () => {
  return useSuspenseQuery({
    queryKey: ['signinstatus'],
    queryFn: () => fetchSignInStatus(),
  })
}

export const useSignInStatus = () => {
  return useQuery({
    queryKey: ['signinstatus'],
    queryFn: () => fetchSignInStatus(),
  })
}
