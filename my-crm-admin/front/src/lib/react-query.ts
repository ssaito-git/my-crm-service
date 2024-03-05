import { DefaultOptions, QueryClient } from "@tanstack/react-query";

const defaultOptions: DefaultOptions = {
  queries: {
    retry: false,
  },
};

export const queryClient = new QueryClient({
  defaultOptions,
});
