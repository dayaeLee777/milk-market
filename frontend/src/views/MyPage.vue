<template>
  <div class="myPage">
    <header></header>
    <router-view></router-view>
  </div>
</template>

<script>
export default {};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
@import url("https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

.myPage {
  font-family: "Noto Sans KR", sans-serif;
  height: 118vh;
  /* background-image: linear-gradient(
    rgba(147, 124, 154, 0.3),
    rgba(117, 76, 121, 0.3)),
    url(../../public/images/profile.jpg);
  background-size: cover; */
}

</style>
