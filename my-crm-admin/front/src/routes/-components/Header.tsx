import {
  AppBar,
  IconButton,
  IconButtonProps,
  Toolbar,
  Typography,
} from '@mui/material'
import { useColorScheme } from '../../hooks/useColorScheme'
import {
  Brightness4,
  Brightness7,
  Notifications,
  Person,
} from '@mui/icons-material'

const HeaderIconButton = (props: IconButtonProps) => {
  return (
    <IconButton
      size="small"
      sx={(theme) => ({
        marginLeft: 1,
        borderWidth: '1px',
        borderStyle: 'solid',
        borderRadius: '10px',
        borderColor: theme.palette.divider,
      })}
      onClick={props.onClick}
    >
      {props.children}
    </IconButton>
  )
}

export const Header = () => {
  const [colorScheme, setColorScheme] = useColorScheme()

  const handleThemeButtonClick = () => {
    setColorScheme(colorScheme === 'light' ? 'dark' : 'light')
  }

  return (
    <AppBar
      sx={(theme) => ({
        background: theme.palette.background.default,
        zIndex: theme.zIndex.drawer + 1,
        borderBottomWidth: '1px',
        borderBottomStyle: 'solid',
        borderBottomColor: theme.palette.divider,
      })}
      elevation={0}
      position="static"
    >
      <Toolbar variant="dense">
        <Typography
          sx={(theme) => ({ color: theme.palette.text.primary, flexGrow: 1 })}
        >
          My CRM Service Admin
        </Typography>
        <HeaderIconButton onClick={handleThemeButtonClick}>
          {colorScheme === 'dark' ? (
            <Brightness7 fontSize="small" />
          ) : (
            <Brightness4 fontSize="small" />
          )}
        </HeaderIconButton>
        <HeaderIconButton>
          <Notifications fontSize="small" />
        </HeaderIconButton>
        <HeaderIconButton>
          <Person fontSize="small" />
        </HeaderIconButton>
      </Toolbar>
    </AppBar>
  )
}
