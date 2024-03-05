import {
  ListItemButton,
  ListItemIcon,
  Popper,
  Fade,
  Paper,
  List,
  ListItem,
  ListItemText,
  Box,
} from '@mui/material'
import { useState } from 'react'
import { SidebarMenuItem } from './Sidebar'

type ClosedSidebarMenuProps = {
  menuItem: SidebarMenuItem
}

const ClosedSidebarMenu = ({ menuItem }: ClosedSidebarMenuProps) => {
  return (
    <ListItemButton key={menuItem.name}>
      <ListItemIcon>
        <menuItem.icon />
      </ListItemIcon>
    </ListItemButton>
  )
}

type ClosedSidebarGropuMenuProps = {
  menuItem: SidebarMenuItem
}

const ClosedSidebarGroupMenu = ({ menuItem }: ClosedSidebarGropuMenuProps) => {
  const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null)
  const [menuItemHover, setMenuItemHover] = useState(false)
  const [popperHover, setPopperHover] = useState(false)

  const handleMenuItemMouseEnter = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
    setMenuItemHover(true)
  }
  const handleMenuItemMouseLeave = () => {
    setMenuItemHover(false)
  }
  const handlePopperMouseEnter = () => {
    setPopperHover(true)
  }
  const handlePopperMouseLeave = () => {
    setPopperHover(false)
  }

  return (
    <>
      <Popper
        open={menuItemHover || popperHover}
        anchorEl={anchorEl}
        transition
        placement="right-start"
      >
        {({ TransitionProps }) => (
          <Fade {...TransitionProps} timeout={350}>
            <Paper
              sx={{ minWidth: 250 }}
              onMouseEnter={handlePopperMouseEnter}
              onMouseLeave={handlePopperMouseLeave}
            >
              <List dense>
                <ListItem>
                  <ListItemText sx={{ fontWeight: 'bold' }}>
                    <Box sx={{ fontWeight: 'bold' }}>{menuItem.name}</Box>
                  </ListItemText>
                </ListItem>
                {menuItem.children.map((child) => (
                  <ListItem key={child.id} disablePadding>
                    <ListItemButton>
                      <ListItemText>{child.name}</ListItemText>
                    </ListItemButton>
                  </ListItem>
                ))}
              </List>
            </Paper>
          </Fade>
        )}
      </Popper>
      <ListItemButton
        sx={{ minHeight: 36 }}
        onMouseEnter={handleMenuItemMouseEnter}
        onMouseLeave={handleMenuItemMouseLeave}
      >
        <ListItemIcon
          sx={{
            minWidth: 0,
            justifyContent: 'center',
            marginRight: 'auto',
          }}
        >
          <menuItem.icon />
        </ListItemIcon>
      </ListItemButton>
    </>
  )
}

type ClosedSidebarMenuListProps = {
  menuItems: SidebarMenuItem[]
}

export const ClosedSidebarMenuList = ({
  menuItems,
}: ClosedSidebarMenuListProps) => {
  return (
    <List>
      {menuItems.map((menuItem) =>
        menuItem.children.length === 0 ? (
          <ClosedSidebarMenu key={menuItem.id} menuItem={menuItem} />
        ) : (
          <ClosedSidebarGroupMenu key={menuItem.id} menuItem={menuItem} />
        ),
      )}
    </List>
  )
}
