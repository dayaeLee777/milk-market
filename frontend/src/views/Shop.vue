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
import ShopItemList from "../components/shop/ShopItemList.vue";
import ShopItem from "../components/shop/ShopItem.vue";
export default {
  components: {
    ShopItemList,
    ShopItem,
  },
  mounted () {
    // this.$router.go("/shop/list");
  },
};

</script>

<style></style>
