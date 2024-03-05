import { queryOptions } from '@tanstack/react-query'
import { HttpStatusCode } from 'axios'

export const fetchSignInStatus = async (): Promise<boolean> => {
  const response = await fetch('/login/status')

  switch (response.status) {
    case HttpStatusCode.NoContent:
      return true
    case HttpStatusCode.Unauthorized:
      throw new UnauthenticatedError()
    default:
      throw new UnknownError()
  }
}

export class UnauthenticatedError extends Error {
  static {
    this.prototype.name = 'UnauthenticatedError'
  }
}

export class UnknownError extends Error {
  static {
    this.prototype.name = 'UnknownError'
  }
}

export const signInStatusQueryOptions = queryOptions({
  queryKey: ['signinstatus'],
  queryFn: () => fetchSignInStatus(),
})
