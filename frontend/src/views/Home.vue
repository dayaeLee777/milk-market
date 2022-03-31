<template>
  <div>
    <h-nav></h-nav>
    <hot-item style="margin-top: 100px;"></hot-item>
    <step-flow></step-flow>
    <f-nav></f-nav>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from '@/components/common/StepFlow';
import FNav from '../components/common/FNav.vue';
import HotItem from '../components/mainpage/HotItem.vue';

export default {
  components: {
    HNav,
    StepFlow,
    FNav,
    HotItem
  }
};
</script>

<style>
</style>
