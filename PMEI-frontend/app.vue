<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>

<script>
export default {
  middleware: 'auth', // Apply auth middleware globally via default layout
};
</script>