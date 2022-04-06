<template>
  <div class="item-body">
    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
export default {};
</script>

<style>
.item-body {
  height: 120vh;
}
</style>
