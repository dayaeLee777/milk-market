<template>
  <div class="item-body">
    <header></header>

    <!-- <item-list style="margin-top: 100px"></item-list> -->
    <shop-item></shop-item>
    <step-flow></step-flow>
    <!-- <f-nav></f-nav> -->
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from "@/components/common/StepFlow";
import ItemList from "@/components/item/ItemList";
import FNav from "../components/common/FNav.vue";
import ShopItemList from "../components/shop/ShopItemList.vue";
import ShopItem from "../components/shop/ShopItem.vue";
export default {
  components: {
    HNav,
    StepFlow,
    FNav,
    ItemList,
    ShopItemList,
    ShopItem,
  },
};
</script>

<style></style>
