<template>
  <div>
    <landing />
    <hot-item style="margin-top: 100px"></hot-item>
  </div>
</template>

<script>
import HotItem from "../components/mainpage/HotItem.vue";
import Landing from "../components/mainpage/Landing.vue";

export default {
  components: {
    HotItem,
    Landing,
  },
};
</script>

<style></style>
