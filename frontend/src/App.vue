<template>
  <v-app>
    <hearder />
    <v-main>
      <router-view />
    </v-main>
    <invitation-modal></invitation-modal>
  </v-app>
</template>
<script>
import Hearder from "./components/common/Hearder.vue";
import InvitationModal from './components/common/InvitationModal.vue';

export default {
  components: { Hearder, InvitationModal },
  name: "App",
};
</script>
<style>
body {
  font-family: "Dongle", sans-serif;
}
</style>