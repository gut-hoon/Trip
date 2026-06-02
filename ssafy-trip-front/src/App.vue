<template>
  <v-app>
    <TheNavbar />
    <v-main>
      <router-view />
    </v-main>
    <TheFooter />
  </v-app>
</template>

<script setup>
  import TheNavbar from '@/components/TheNavbar.vue'
  import TheFooter from '@/components/TheFooter.vue'
</script>
