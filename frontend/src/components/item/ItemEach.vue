<template>
  <div class="card">
    <img
      :src="content.item_img"
      class="card-img-top"
      alt=""
    >
    <div class="card-body">
      <h5 class="card-title">{{ content.item_name }}</h5>
      <p class="card-text">{{ content.item_introduce }}</p>
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