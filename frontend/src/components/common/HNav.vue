<template>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-secondary">
    <div class="container">
      <img
        id="nav-icon"
        src="../../../public/images/milk.png"
      >
      <!-- <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div> -->
      <router-link
        class="navbar-brand"
        to="/"
      >MILK</router-link>

      <router-link
        class="navbar-brand"
        to="/"
      >찾기</router-link>
      <router-link
        class="navbar-brand"
        to="/"
      >알람</router-link>

      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link
              class="nav-link"
              to="/community"
            >Community</router-link>
          </li>
          <li class="nav-item">
            <router-link
              class="nav-link"
              to="/explorer/dashboard"
            >Explorer</router-link>
          </li>
          <li class="nav-item">
            <router-link
              class="nav-link"
              to="/shop"
            >Shop</router-link>
          </li>
          <li
            class="nav-item"
            v-if="$store.state.isSigned"
          >
            <router-link
              class="nav-link"
              to="/mypage/wallet_create"
              v-if="!$store.state.user.walletAddress"
            >MyPage</router-link>
            <router-link
              class="nav-link"
              to="/mypage/wallet_info"
              v-if="$store.state.user.walletAddress"
            >MyPage</router-link>
          </li>
          <li
            class="nav-item"
            v-if="!$store.state.isSigned"
          >
            <router-link
              class="nav-link"
              to="/login"
            >Login</router-link>
          </li>
          <li
            class="nav-item"
            v-if="!$store.state.isSigned"
          >
            <router-link
              class="nav-link"
              to="/register"
            >Sign Up</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link class="nav-link" to="/logout">Log out</router-link>
          <li
            class="nav-item"
            v-if="$store.state.isSigned"
          >
            <router-link
              class="nav-link"
              to="/logout"
            >Sign out</router-link>
          </li>
        </ul>

      </div>

      <!-- <div class="dropdown">
        <button
          class="navbar-toggler"
          type="button"
          id="dropdownMenuButton"
          data-toggle="dropdown"
          aria-expanded="false"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div
          class="dropdown-menu"
          aria-labelledby="dropdownMenuButton"
        > -->

      <!-- <router-link
            class="dropdown-item"
            to="/explorer/dashboard"
          >Explorer
          </router-link>

          <router-link
            class="dropdown-item"
            to="/shop"
          >
            Shop
          </router-link>

          <li
            class="nav-item"
            v-if="$store.state.isSigned"
          >
            <router-link
              class="dropdown-item"
              to="/mypage/wallet_create"
              v-if="!$store.state.user.walletAddress"
            >MyPage</router-link>
            <router-link
              class="dropdown-item"
              to="/mypage/wallet_info"
              v-if="$store.state.user.walletAddress"
            >MyPage</router-link>
          </li>

          <li
            class="nav-item"
            v-if="!$store.state.isSigned"
          >
            <router-link
              class="dropdown-item"
              to="/login"
            >Sign In</router-link>
          </li>
          <li
            class="nav-item"
            v-if="!$store.state.isSigned"
          >
            <router-link
              class="dropdown-item"
              to="/register"
            >Sign Up</router-link>
          </li>
          <li
            class="nav-item"
            v-if="$store.state.isSigned"
          >
            <router-link
              class="dropdown-item"
              to="/logout"
            >Sign out</router-link>
          </li> -->

      <!-- </div>
      </div> -->

    </div>
  </nav>
</template>

<script>

export default {

};
</script>

<style>
#nav-icon {
  height: 60px;
  padding-right: 0.5rem;
}
</style>