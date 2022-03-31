<template>
  <carousel-3d :width="700" :height="500" >
    <slide v-for="(slide, i) in slides" :index="i" :key="i">
         <hot-item-slide /> 
    </slide> 
    <!-- <slide :index="1">
        <hot-item-slide />
    </slide> -->
</carousel-3d>
</template>

<script>
import { Carousel3d, Slide } from 'vue-carousel-3d';
import HotItemSlide from './HotItemSlide.vue';

export default {
    name: 'HotItem',
    components: {
        Carousel3d,
        Slide,
        HotItemSlide
    },
    data() {
        return {
            slides: 5
        }
    }
}
</script>

<style>
.carousel-3d-container .carousel-3d-slide {
    border: 0;
    background: transparent;
    align-content: center;
}
</style>