<template>
  <!-- <div>
    <header class="bg-image">
      <div class="container">
        <h1>우리동네 인기상품</h1>
      </div>
    </header>
    <carousel-3d :width="500" :height="500">
      <slide v-for="(slide, i) in slides" :index="i" :key="i">
        <hot-item-slide />
      </slide>
    </carousel-3d>
  </div> -->
  <div class="sect sect--padding-bottom">
    <div class="container">
      <div class="row row--center">
        <h1 class="row__title">우리동네 인기상품</h1>
        <h2 class="row__sub">관심등록이 많은 Top5 상품을 확인하세요</h2>
      </div>
      <carousel-3d :width="500" :height="500">
        <slide v-for="(slide, i) in slides" :index="i" :key="i">
          <hot-item-slide :slide="slide" />
        </slide>
      </carousel-3d>
    </div>
  </div>
</template>

<script>
import { Carousel3d, Slide } from "vue-carousel-3d";
import HotItemSlide from "./HotItemSlide.vue";
import { getHotItem } from "@/api/interest.js";

export default {
  name: "HotItem",
  components: {
    Carousel3d,
    Slide,
    HotItemSlide,
  },
  data: () => ({
    slides: 5,
  }),
  created() {
    const bcode = this.$store.state.user.bcode;
    this.getHotItemList(bcode);
  },
  computed: {},
  methods: {
    getHotItemList(bcode) {
      var vm = this;
      setTimeout(
        getHotItem(
          function (res) {
            vm.slides = res.data;
          },
          function (error) {
            console.error("에러 : " + error);
          }
        ),
        0
      );
    },
  },
};
</script>

<style scope>
@import url("https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap");
@import url("//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css");
@import url(//fonts.googleapis.com/css?family=Open+Sans:400,700,800,300);

.carousel-3d-container .carousel-3d-slide {
  border: 0;
  background: transparent;
  align-content: center;
  width: 90%;
}

.sect .container {
  margin: 0 auto;
  width: 90%;
  max-width: 900px;
}

.sect {
  padding: 90px 0;
  position: relative;
}

.sect--padding-bottom {
  padding-bottom: 115px;
}

.row--center {
  max-width: 1000px;
  margin: 0 auto;
}

.row__title {
  margin-top: 0px;
  text-align: center;
  font-family: "Black Han Sans", sans-serif;
  font-size: 30px;
  color: #1f4568;
}

.row__sub {
  text-align: center;
  font-size: 22px;
  color: #8198ae;
  margin: 15px 0 40px 0;
  font-family: "Noto Sans KR", sans-serif;
}

@media screen and (max-width: 1024px) {
}

@media screen and (max-width: 991px) {
}

@media screen and (max-width: 767px) {
}

@media screen and (max-width: 480px) {
  .sect {
    padding: 20px 0;
  }
  .row__sub {
    font-size: 16px;
  }
  .row__title {
    font-size: 20px;
    margin-bottom: 5px;
  }
}
</style>
