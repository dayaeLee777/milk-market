<template>
  <div>
    <h-nav></h-nav>
    <product-list></product-list>
    <step-flow></step-flow>
    <f-nav></f-nav>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from '@/components/common/StepFlow';
import ProductList from '@/components/ProductList';
import FNav from '../components/common/FNav.vue';



export default {
  components: {
    HNav,
    StepFlow,
    FNav,
    ProductList
  }
};
</script>

<style>
</style>
