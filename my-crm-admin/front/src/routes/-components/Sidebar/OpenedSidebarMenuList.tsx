import { ExpandMore, ExpandLess } from '@mui/icons-material'
import {
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Collapse,
  List,
} from '@mui/material'
import { useState } from 'react'
import { SidebarMenuItem } from './Sidebar'

type OpenedSidebarMenuProps = {
  menuItem: SidebarMenuItem
}

const OpenedSidebarMenu = ({ menuItem }: OpenedSidebarMenuProps) => {
  return (
    <ListItemButton key={menuItem.name}>
      <ListItemIcon>
        <menuItem.icon />
      </ListItemIcon>
      <ListItemText>{menuItem.name}</ListItemText>
    </ListItemButton>
  )
}

type OpenedSidebarGropuMenuProps = {
  menuItem: SidebarMenuItem
}

const OpenedSidebarGroupMenu = ({ menuItem }: OpenedSidebarGropuMenuProps) => {
  const [open, setOpen] = useState(true)

  const handleClick = () => {
    setOpen((value) => !value)
  }

  return (
    <>
      <ListItemButton onClick={handleClick}>
        <ListItemIcon>
          <menuItem.icon />
        </ListItemIcon>
        <ListItemText>{menuItem.name}</ListItemText>
        {open ? <ExpandMore /> : <ExpandLess />}
      </ListItemButton>

      <Collapse in={open} timeout="auto" unmountOnExit>
        <List component="div" disablePadding>
          {menuItem.children.map((child) => (
            <ListItemButton key={child.id}>
              <ListItemIcon></ListItemIcon>
              <ListItemText>{child.name}</ListItemText>
            </ListItemButton>
          ))}
        </List>
      </Collapse>
    </>
  )
}

type OpenedSidebarMenuListProps = {
  menuItems: SidebarMenuItem[]
}

export const OpenedSidebarMenuList = ({
  menuItems,
}: OpenedSidebarMenuListProps) => {
  return (
    <List>
      {menuItems.map((menuItem) =>
        menuItem.children.length === 0 ? (
          <OpenedSidebarMenu key={menuItem.id} menuItem={menuItem} />
        ) : (
          <OpenedSidebarGroupMenu key={menuItem.id} menuItem={menuItem} />
        ),
      )}
    </List>
  )
}
