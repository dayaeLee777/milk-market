<template>
  <div class="item-body">
    <header></header>

    <!-- <item-list style="margin-top: 100px"></item-list> -->
    <shop-item-list></shop-item-list>
    <step-flow></step-flow>
    <!-- <f-nav></f-nav> -->
  </div>
</template>

<script>
import ShopItemList from "../components/shop/ShopItemList.vue";

export default {
  components: {
    ShopItemList,
  },
};
</script>

<style></style>
