<template>
  <div class="sect sect--padding-top">
    <div class="container">
      <div class="row">
        <div class="site">
          <div id="login-form" class="col-md-6 mx-auto bg-white">
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
            <button type="submit" class="btn btn-primary" @click="login">로그인</button>
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
    </div>
  </div>
</template>

<script>
import { login, sendAccessToken, findUser } from "../api/user.js";
import { findByUserId as findWallet } from "../api/wallet.js";
import Swal from "sweetalert2/dist/sweetalert2.js";

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

      const Swal = require("sweetalert2");
      login(
        this.user.email,
        this.user.password,
        function (response) {
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.email);
          scope.$store.commit("setWalletAddress", response.data.address);
          scope.$store.commit("setJWTToken", response.data.token);
          scope.$store.commit("setUserNickname", response.data.nickname);
          console.log("여기까지 넘어갑니다." + response.data.token);
          findUser(
            function (success) {
              console.log("findBy User " + success.data.bcode);
              Swal.fire({
                position: "center",
                icon: "success",
                title: "로그인 성공",
                showConfirmButton: false,
                timer: 1500,
              });
              scope.$store.commit("setBcode", success.data.bcode);
            },
            function (error) {
              console.log(error);
              Swal.fire({
                position: "center",
                icon: "fail",
                title: "로그인 실패",
                showConfirmButton: false,
                timer: 1500,
              });
            }
          );
          scope.$router.push("/");
        },
        function (error) {
          console.error(error);
          // alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
          Swal.fire({
            position: "center",
            icon: "error",
            title: "로그인 실패",
            text: "이메일 혹은 비밀번호가 일치하지 않습니다",
            showConfirmButton: false,
            timer: 2000,
          });
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
.site {
  align-content: center;
  align-self: center;
}
</style>
