import { defineConfig } from 'orval'

export default defineConfig({
  todo: {
    input: '../../my-crm-service/webapi/build/openapi-open.json',
    output: {
      target: 'src/api/my-crm-service-api.ts',
      schemas: 'src/api/model',
      client: 'react-query',
      mode: 'tags-split',
      override: {
        query: {
          useSuspenseQuery: true,
          version: 5,
        },
      },
    },
  },
})
