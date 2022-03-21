<template>
  <div class="card">
    <img
      src=""
      class="card-img-top"
      alt=""
    >
    <div class="card-body">
      <h5 class="card-title">{{ content.product_name }}</h5>
      <p class="card-text">{{ content.product_introduce }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    content: Object,
  }
}
</script>

<style>
</style>