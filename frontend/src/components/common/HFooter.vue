<template>
  <div class="container fixed-bottom">
    <div class="row justify-content-around text-center">
      <div class="col">
        <router-link to="/login">1</router-link>
      </div>
      <div class="col">
        <router-link to="/login">2</router-link>
      </div>
      <div class="col">
        <router-link to="/login">3</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.navbar {
  background-color: rgb(80, 220, 100);
}

ul {
  font-size: 20px;
}
</style>