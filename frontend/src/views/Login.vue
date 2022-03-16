<template>
  <div class="container">
    <div class="row">
      <div
        id="login-form"
        class="col-md-6 mx-auto bg-white"
      >
        <router-link to="/">Escrow | STARMIX</router-link>
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
            />
          </div>
          <button
            type="submit"
            class="btn btn-primary"
            v-on:click="login"
          >
            로그인
          </button>
        </div>
      </div>
    </div>
    <section>
      <div v-on:click="kakaoLoginBtn">카카오 연동</div>
    </section>
  </div>
</template>

<script>
import { login } from "../api/user.js";
import { findByUserId as findWallet } from "../api/wallet.js";

export default {
  data () {
    return {
      user: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    login () {
      const scope = this;

      login(
        this.user.email,
        this.user.password,
        function (response) {
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.id);

          findWallet(
            response.data.id,
            function (response) {
              if (response.status == 200) {
                scope.$store.commit("setWalletAddress", response.data.address);
              } else {
                alert("Unexpected status code: " + response.status);
              }
            },
            function (err) {
              if (err.response != 404) {
                console.error(err);
                //alert("지갑 정보를 찾지 못했습니다.");
              }
            }
          );

          scope.$router.push("/");
        },
        function (error) {
          console.error(error);
          alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
        }
      );
    },
    /////
    kakaoLoginBtn: function () {

      window.Kakao.init('700fe3eb25ade19a26c18b71610a4b34') // Kakao Developers에서 요약 정보 -> JavaScript 키

      if (window.Kakao.Auth.getAccessToken()) {
        window.Kakao.API.request({
          url: '/v1/user/unlink',
          success: function (response) {
            console.log(response)
          },
          fail: function (error) {
            console.log(error)
          },
        })
        window.Kakao.Auth.setAccessToken(undefined)
      }


      window.Kakao.Auth.login({
        success: function () {
          window.Kakao.API.request({
            url: '/v2/user/me',
            data: {
              property_keys: ["kakao_account.email"]
            },
            success: async function (response) {
              console.log(response);
            },
            fail: function (error) {
              console.log(error)
            },
          })
        },
        fail: function (error) {
          console.log(error)
        },
      })
    }
  }
}
</script>

<style>
</style>
