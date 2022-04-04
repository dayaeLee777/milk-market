<template>
  <div class="sect sect--padding-bottom">
    <div class="container">
      <carousel-3d :width="500" :height="500">
        <slide v-for="(slide, i) in slides" :index="i" :key="i">
          <my-item-slide />
        </slide>
      </carousel-3d>
    </div>
  </div>
</template>

<script>
import { Carousel3d, Slide } from "vue-carousel-3d";
import MyItemSlide from "./MyItemSlide.vue";
import Swal from 'sweetalert2/dist/sweetalert2.js'

export default {
  name: "MyEscrow",
  components: {
    Carousel3d,
    Slide,
    MyItemSlide,
  },
  data() {
    return {
      slides: 5,
    };
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
