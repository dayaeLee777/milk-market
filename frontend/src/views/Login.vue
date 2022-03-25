<template>
  <div class="container">
    <div class="row">
      <h-nav></h-nav>
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <router-link to="/">우유 마켓 | STARMIX</router-link>
        <div class="mt-4">
          <div class="form-group">
            <label for="email">아이디</label>
            <input
              type="text"
              class="form-control"
              id="email"
              v-model="user.email"
              placeholder="이메일"
            />
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="password"
              v-model="user.password"
              placeholder="비밀번호"
              @keydown.enter="login"
            />
          </div>
          <button type="submit" class="btn btn-primary" @click="login">
            로그인
          </button>
          <hr />
          <img
            src="https://blog.kakaocdn.net/dn/bYZZHh/btrfibui4Cj/DofAXcdzmQGCKkhTNUUAHk/img.png"
            @click="kakaoLogin"
            style="width: 200px"
          />
          <!-- <img
            src="https://static.nid.naver.com/oauth/big_g.PNG?version=js-2.0.1"
            @click="naverLogin"
            style="width : 200px;"
          > -->
        </div>
      </div>
    </div>
    <f-nav></f-nav>
  </div>
</template>

<script>
import { login, sendAccessToken } from "../api/user.js";
import { findByUserId as findWallet } from "../api/wallet.js";

export default {
  data() {
    return {
      user: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      const scope = this;
      //함수 안에 함수를 쓸 때 this쓰면 바뀐다.
      login(
        this.user.email,
        this.user.password,
        function (response) {
          console.log(response.data);
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.email);
          scope.$store.commit("setWalletAddress", response.data.address);
          scope.$store.commit("setJWTToken", response.data.token);
          console.log("여기까지 넘어갑니다." + response.data.token);

          scope.$router.push("/");
        },
        function (error) {
          console.error(error);
          alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
        }
      );
    },
    kakaoLogin() {
      window.Kakao.Auth.login({
        success: this.sendAccessToken,
      });
    },
    // getProfile (authObj) {
    //   console.log(authObj)
    //   window.Kakao.API.request({
    //     url: '/v2/user/me',
    //     success: res => {
    //       const kakao_account = res.kakao_account
    //       console.log(kakao_account)
    //     }
    //   })
    // },
    sendAccessToken(authObj) {
      let scope = this;
      sendAccessToken(
        authObj.access_token,
        function (res) {
          console.log(res);
          scope.$store.commit("setIsSigned", true);
          scope.$router.push("/").catch(() => {});
        },
        function (err) {
          console.log(err);
        }
      );
    },
    naverLogin() {
      var client_id = "QvNWqPgM7ebAubiDGxe8";
      var callbackUrl = "http://localhost:8080/api/oauth/naver";
      var url =
        "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" +
        client_id +
        "&redirect_uri=" +
        callbackUrl +
        "&state=1234";
      window.location.replace(url);
    },
  },
};
</script>

<style>
</style>
