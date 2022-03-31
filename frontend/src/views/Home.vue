<template>
  <div>
    <landing />
    <hot-item style="margin-top: 100px"></hot-item>
    <step-flow></step-flow>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from "@/components/common/StepFlow";
import HotItem from "../components/mainpage/HotItem.vue";
import Hearder from "../components/common/Hearder.vue";
import Landing from "../components/mainpage/Landing.vue";

export default {
  components: {
    HNav,
    StepFlow,
    HotItem,
    Hearder,
    Landing,
  },
};
</script>

<style></style>
