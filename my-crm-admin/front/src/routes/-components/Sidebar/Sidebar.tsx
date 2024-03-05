import {
  HomeOutlined,
  PeopleOutlineRounded,
  ShoppingCartOutlined,
  AssessmentOutlined,
  SettingsOutlined,
  KeyboardDoubleArrowLeft,
  KeyboardDoubleArrowRight,
} from '@mui/icons-material'
import {
  Box,
  CSSObject,
  Divider,
  Drawer,
  ListItemButton,
  ListItemIcon,
  Theme,
  Toolbar,
  Tooltip,
} from '@mui/material'
import { useNavigate } from '@tanstack/react-router'
import { useState } from 'react'
import { OpenedSidebarMenuList } from './OpenedSidebarMenuList'
import { ClosedSidebarMenuList } from './ClosedSidebarMenuList'

export type SidebarMenuItemId =
  | 'Home'
  | 'Users'
  | 'Products'
  | 'Reports'
  | 'Settings'
  | 'One-time Products'
  | 'Subscription Products'
  | 'System Users'
  | 'Shops'

export type SidebarMenuItem = {
  id: SidebarMenuItemId
  name: string
  icon: React.ComponentType
  children: SidebarChildMenuItem[]
}

export type SidebarChildMenuItem = {
  id: SidebarMenuItemId
  name: string
}

const menuItems: SidebarMenuItem[] = [
  {
    id: 'Home',
    name: 'Home',
    icon: HomeOutlined,
    children: [],
  },
  {
    id: 'Users',
    name: 'Users',
    icon: PeopleOutlineRounded,
    children: [],
  },
  {
    id: 'Products',
    name: 'Products',
    icon: ShoppingCartOutlined,
    children: [
      { id: 'One-time Products', name: 'One-time products' },
      { id: 'Subscription Products', name: 'Subscription Products' },
    ],
  },
  {
    id: 'Reports',
    name: 'Reports',
    icon: AssessmentOutlined,
    children: [],
  },
  {
    id: 'Settings',
    name: 'Settings',
    icon: SettingsOutlined,
    children: [
      { id: 'System Users', name: 'System Users' },
      { id: 'Shops', name: 'Shops' },
    ],
  },
]

const sidebarWidth = 240

const opendMixin = (theme: Theme): CSSObject => ({
  display: 'flex',
  width: sidebarWidth,
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
})

const closedMixin = (theme: Theme): CSSObject => ({
  display: 'flex',
  width: `calc(${theme.spacing(7)} + 1px)`,
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
})

export const Sidebar = () => {
  const [open, setOpen] = useState(true)
  const navigate = useNavigate()

  const handleOpenClose = () => {
    setOpen((value) => !value)
  }

  const handleNavigation = (menuItemId: SidebarMenuItemId) => {
    switch (menuItemId) {
      case 'Home':
        navigate({ to: '/' })
        break
      case 'Users':
        // navigate({ to: '/users' })
        break
      case 'Reports':
        // navigate({ to: '/reports' })
        break
      case 'Products':
        break
      case 'One-time Products':
        // navigate({ to: '/one-time-products' })
        break
      case 'Subscription Products':
        // navigate({ to: '/subscription-products' })
        break
      case 'Settings':
        break
      case 'System Users':
        // navigate({ to: '/system-users' })
        break
      case 'Shops':
        // navigate({ to: '/shops' })
        break
      default:
        throw new Error(menuItemId satisfies never)
    }
  }

  handleNavigation

  return (
    <Drawer
      variant="permanent"
      sx={
        open
          ? (theme) => ({
              whiteSpace: 'nowrap',
              ...opendMixin(theme),
              '& .MuiDrawer-paper': opendMixin(theme),
            })
          : (theme) => ({
              whiteSpace: 'nowrap',
              ...closedMixin(theme),
              '& .MuiDrawer-paper': closedMixin(theme),
            })
      }
      open={open}
    >
      <Toolbar variant="dense" />
      <Box sx={{ overflowY: 'auto', overflowX: 'hidden', flex: '1' }}>
        {open && <OpenedSidebarMenuList menuItems={menuItems} />}
        {!open && <ClosedSidebarMenuList menuItems={menuItems} />}
      </Box>
      <Divider />
      <Box>
        <Tooltip
          title={open ? 'Show less information' : 'Show more information'}
          followCursor
          placement="right-start"
        >
          <ListItemButton onClick={handleOpenClose}>
            <ListItemIcon
              sx={{
                minWidth: 0,
                justifyContent: 'center',
                marginRight: 'auto',
              }}
            >
              {open ? (
                <KeyboardDoubleArrowLeft />
              ) : (
                <KeyboardDoubleArrowRight />
              )}
            </ListItemIcon>
          </ListItemButton>
        </Tooltip>
      </Box>
    </Drawer>
  )
}
