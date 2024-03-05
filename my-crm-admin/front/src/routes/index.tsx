import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/')({
  component: IndexCompoent,
})

function IndexCompoent() {
  return <div>home</div>
}
