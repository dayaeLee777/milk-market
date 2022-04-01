<template>
  <div class="myPage">
    <h-nav></h-nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {};
</script>

<style>
  .myPage {
    height: 100vh;
    background-color: aliceblue;
    /* background-image: linear-gradient(
      rgba(147, 124, 154, 0.3),
      rgba(117, 76, 121, 0.3)),
      url(../../public/images/profile.jpg);
    background-size: cover; */
  }

</style>
