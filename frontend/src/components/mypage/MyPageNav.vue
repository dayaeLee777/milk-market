<template>
  <div class="row sub-nav mt-2">
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
          >프로필</router-link
        >
        <router-link
          class="nav-item nav-link"
          v-bind:class="{ active: viewName == 'mypage.items' }"
          :to="{ name: 'mypage.items' }"
          >거래내역</router-link
        >
        <!-- <router-link
        class="nav-item nav-link"
        v-bind:class="{ active: viewName == 'mypage.password' }"
        :to="{ name: 'mypage.password'}"
        >비밀번호 변경</router-link> -->
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
