import { PaletteOptions, ThemeProvider, createTheme } from '@mui/material'
import { useMemo } from 'react'
import { MuiCustomThemeOptions } from '../lib/mui'
import { useColorScheme } from '../hooks/useColorScheme'

type MuiCustomThemeProvdierProps = { children: React.ReactNode }

export const MuiCustomThemeProvdier = ({
  children,
}: MuiCustomThemeProvdierProps) => {
  const [colorScheme] = useColorScheme()
  const theme = useMemo(() => {
    const paletteMode = colorScheme === 'light' ? 'light' : 'dark'
    const palette: PaletteOptions =
      paletteMode === 'light'
        ? {
            background: {
              default: '#f9f9f9',
              paper: '#f9f9f9',
            },
          }
        : {
            divider: 'rgba(255, 255, 255, 0.24)',
            background: {
              default: '#1d2025',
              paper: '#1d2025',
            },
          }
    return createTheme({
      ...MuiCustomThemeOptions,
      palette: { mode: paletteMode, ...palette },
    })
  }, [colorScheme])

  return <ThemeProvider theme={theme}>{children}</ThemeProvider>
}
