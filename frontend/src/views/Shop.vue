<template>
  <div>
    <shop-item></shop-item>
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
