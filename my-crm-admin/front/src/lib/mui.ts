import { ThemeOptions } from '@mui/material'

export const MuiCustomThemeOptions: ThemeOptions = {
  palette: {
    mode: 'dark',
  },
  typography: {
    h1: { fontSize: '6rem' },
    h2: { fontSize: '3.75rem' },
    h3: { fontSize: '3rem' },
    h4: { fontSize: '2.125rem' },
    h5: { fontSize: '1.5rem' },
    h6: { fontSize: '1.25rem' },
    subtitle1: { fontSize: '0.875rem' },
    subtitle2: { fontSize: '0.8125rem' },
    body1: { fontSize: '0.875rem' },
    body2: { fontSize: '0.875rem' },
    button: { fontSize: '0.8125rem', textTransform: 'none' },
    caption: { fontSize: '0.75rem' },
    overline: { fontSize: '0.75rem' },
  },
  components: {
    MuiList: {
      defaultProps: {
        dense: true,
      },
    },
    MuiTable: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiButton: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiCheckbox: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiTextField: {
      defaultProps: {
        size: 'small',
      },
    },
  },
}
