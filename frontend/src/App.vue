<template>
  <v-app>
    <hearder />
    <v-main>
      <router-view />
    </v-main>
    <f-nav />
  </v-app>
</template>

<script>
import FNav from "./components/common/FNav.vue";
import Hearder from "./components/common/Hearder.vue";

export default {
  components: { FNav, Hearder },
  name: "App",

  data: () => ({
    //
  }),
};
</script>
<style>
  body {
    font-family: 'Dongle', sans-serif;
  }
</style>