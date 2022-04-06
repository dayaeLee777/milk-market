<template>
  <v-app id="app">
    <hearder />
    <v-main>
      <router-view />
    </v-main>
    <f-nav />
    <invitation-modal></invitation-modal>
  </v-app>
</template>
<script>
import FNav from "./components/common/FNav.vue";
import Hearder from "./components/common/Hearder.vue";
import InvitationModal from "./components/common/InvitationModal.vue";

export default {
  components: { Hearder, FNav, InvitationModal },
  name: "App",
};
</script>