<template>
  <div>
    <h-nav></h-nav>

    <!-- <item-list style="margin-top: 100px"></item-list> -->
<<<<<<< HEAD
    <router-view></router-view>
=======
    <shop-item-list></shop-item-list>
>>>>>>> f037ecdfec16693fa63538626ad2b1398ac83fb2
    <step-flow></step-flow>
    <f-nav></f-nav>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from "@/components/common/StepFlow";
// import ItemList from "@/components/item/ItemList";
import FNav from "../components/common/FNav.vue";
import ShopItemList from "../components/shop/ShopItemList.vue";

export default {
  components: {
    HNav,
    StepFlow,
    FNav,
<<<<<<< HEAD
    // ItemList,
=======
    ItemList,
    ShopItemList,
>>>>>>> f037ecdfec16693fa63538626ad2b1398ac83fb2
  },
};
</script>

<style></style>
