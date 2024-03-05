import { createFileRoute } from '@tanstack/react-router'
import {
  getGetProductListQueryOptions,
  useGetProductListSuspense,
} from '../../api/product/product'
import {
  Box,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'

export const Route = createFileRoute('/products/')({
  loader: ({ context: { queryClient } }) =>
    queryClient.ensureQueryData(getGetProductListQueryOptions()),
  component: ProductsComponent,
})

function ProductsComponent() {
  const query = useGetProductListSuspense()

  return (
    <Box sx={{ padding: 2 }}>
      <Box sx={{ marginBottom: 2 }}>
        <Typography variant="h5">Products</Typography>
      </Box>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>SKU</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Description</TableCell>
              <TableCell>Active</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {query.data.data.products.map((product) => (
              <TableRow key={product.id}>
                <TableCell>{product.sku}</TableCell>
                <TableCell>{product.name}</TableCell>
                <TableCell>{product.description}</TableCell>
                <TableCell>{`${product.active}`}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  )
}
