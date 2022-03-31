<template>
  <div>
    <h-nav></h-nav>
    <item-list style="margin-top: 100px"></item-list>
    <step-flow></step-flow>
    <f-nav></f-nav>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from "@/components/common/StepFlow";
import ItemList from "@/components/item/ItemList";
import FNav from "../components/common/FNav.vue";

export default {
  components: {
    HNav,
    StepFlow,
    FNav,
    ItemList,
  },
};
</script>

<style></style>
