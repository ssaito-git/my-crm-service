import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/users/')({
  component: UsersComponent,
})

function UsersComponent() {
  return <div>users</div>
}
