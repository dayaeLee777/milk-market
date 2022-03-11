<template>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
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
      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >

        <div class="dropdown">
          <button
            class="btn btn-secondary dropdown-toggle"
            type="button"
            id="dropdownMenuButton"
            data-toggle="dropdown"
            aria-expanded="false"
          >
            세부사항
          </button>
          <div
            class="dropdown-menu"
            aria-labelledby="dropdownMenuButton"
          >

            <router-link
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
            </li>

          </div>
        </div>

      </div>
    </div>
  </nav>
</template>

<script>

export default {

};
</script>

<style>
#nav-icon {
  height: 40px;
  padding-right: 0.5rem;
}
</style>