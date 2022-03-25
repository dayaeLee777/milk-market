<template>
  <div class="row sub-nav mt-5">
    <div class="col-md-6">
      <nav class="nav nav-pills nav-fill">
        <router-link
          class="nav-item nav-link"
          v-bind:class="{ active: viewName == 'mypage.wallet.create' }"
          :to="{ name: 'mypage.wallet.create' }"
          v-if="!hasWallet"
          >지갑</router-link
        >
        <router-link
          class="nav-item nav-link"
          v-bind:class="{ active: viewName == 'mypage.wallet.info' }"
          :to="{ name: 'mypage.wallet.info' }"
          v-if="hasWallet"
          >My 지갑</router-link
        >
        <router-link
          class="nav-item nav-link"
          v-bind:class="{ active: viewName == 'mypage.update' }"
          :to="{ name: 'mypage.update' }"
          >내정보 확인</router-link
        >
        <router-link
          class="nav-item nav-link"
          v-bind:class="{ active: viewName == 'mypage.items' }"
          :to="{ name: 'mypage.items' }"
          >상품 관리</router-link
        >
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      hasWallet: this.$store.state.user.walletAddress != null,
      viewName: ""
    };
  },
  mounted: function() {
    this.viewName = this.$route.name;
  }
};
</script>

<style></style>
