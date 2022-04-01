<template>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-secondary">
    <div class="container">
      <img id="nav-icon" src="../../../public/images/milk.png" />
      <!-- <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div> -->
      <router-link class="navbar-brand" to="/">MILK</router-link>
      <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/community">Community</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/explorer/dashboard">Explorer</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/shop">Shop</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link
              class="nav-link"
              to="/mypage/wallet_create"
              v-if="!$store.state.user.walletAddress"
              >MyPage</router-link
            >
            <router-link
              class="nav-link"
              to="/mypage/wallet_info"
              v-if="$store.state.user.walletAddress"
              >MyPage</router-link
            >
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/login">Login</router-link>
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/register">Sign Up</router-link>
          </li>
          <li v-if="$store.state.isSigned">
            <router-link class="nav-link" to="/community">Community</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link class="nav-link" to="/logout">Log out</router-link>
          </li>
          <!-- <li
            class="nav-item"
            v-if="$store.state.isSigned"
          >
            <router-link
              class="nav-link"
              to="/logout"
            >Sign out</router-link>
          </li> -->
        </ul>
        <div v-if="$store.state.isSigned">
          <div v-if="profileImage">
            <img :src="profileImage" alt="profile-img" class="my-img" @click="moveToMypage()" />
          </div>
          <div v-else>
            <img
              src="../../../public/images/ompang2.png"
              alt="profile-img"
              class="my-img"
              @click="moveToMypage()"
            />
          </div>
        </div>
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
import axios from "axios";
import jwt_decode from "jwt-decode";
import { API_BASE_URL } from "@/config/index.js";

export default {
  data() {
    return {
      profileImage: "",
    };
  },
  methods: {
    moveToMypage() {
      this.$router.push("/mypage/update");
    },
    UserInfo() {
      const token = this.$store.state.user.JWTToken;
      const decode = jwt_decode(token);
      console.log(decode.sub);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/users/`,
        method: "get",
        headers,
      })
        .then((res) => {
          this.profileImage = res.data.profileImage;
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.UserInfo();
  },
};
</script>

<style>
#nav-icon {
  height: 60px;
  padding-right: 0.5rem;
}
.my-img {
  height: 45px;
  width: 45px;
  border-radius: 50px;
  margin-left: 5px;
  cursor: pointer;
}
</style>
