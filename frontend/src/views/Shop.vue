<template>
  <div>
    <shop-item></shop-item>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import ItemList from "@/components/item/ItemList";
import FNav from "../components/common/FNav.vue";
import ShopItem from "../components/shop/ShopItem.vue";
export default {
  components: {
    HNav,
    FNav,
    ItemList,
    ShopItem,
  },
  mounted() {
    // this.$router.go("/shop/list");
  },
};
</script>

<style></style>
