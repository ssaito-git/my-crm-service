import { useQuery, useQueryClient } from '@tanstack/react-query'

const colorSchemeQueryKey = 'color-scheme'

export type ColorScheme = 'light' | 'dark'

export const useColorScheme = (): [
  ColorScheme,
  (newData: ColorScheme) => void,
] => {
  const queryClient = useQueryClient()
  const data = useQuery<ColorScheme>({
    queryKey: [colorSchemeQueryKey],
    queryFn: () =>
      localStorage.getItem(colorSchemeQueryKey) === 'dark' ? 'dark' : 'light',
    staleTime: Infinity,
  })
  const setData = (newData: ColorScheme) => {
    localStorage.setItem(colorSchemeQueryKey, newData)
    queryClient.setQueryData([colorSchemeQueryKey], newData)
  }
  return [data.data ?? 'light', setData]
}
