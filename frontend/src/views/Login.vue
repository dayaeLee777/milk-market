<template>
  <div class="sect sect--padding-top">
    <div class="container">
      <div class="row">
        <div class="site">
          <h1 class="row__title">Login</h1>
          <h2 class="row__sub">우유마켓 이용을 위해 로그인하세요</h2>
          <div class="row row--center">
            <div class="col-md-3"></div>

            <div class="col-md-6">
              <div id="login-form">
                <div class="form-group">
                  <div class="form__field--half">
                    <input
                      type="text"
                      class="form__field form__text form-control"
                      id="email"
                      v-model="user.email"
                      placeholder="Email address"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <div class="form__field--half">
                    <input
                      type="password"
                      class="form__field form__text form-control"
                      id="password"
                      v-model="user.password"
                      placeholder="Password"
                      @keydown.enter="login"
                    />
                  </div>
                </div>
                <div id="btn_span" />
                <div class="form-group">
                  <button type="submit" class="btn btn--width btn-blue login_btn" @click="login">
                    Login
                  </button>
                </div>
                <div class="form-group">
                  <button class="btn btn--width login_btn" id="kakaobtn" @click="kakaoLogin">
                    kakao Login
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login, sendAccessToken, findUser } from "../api/user.js";
import { API_BASE_URL } from "@/config/index.js";
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
        (response) => {
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.email);
          scope.$store.commit("setWalletAddress", response.data.address);
          scope.$store.commit("setJWTToken", response.data.token);
          scope.$store.commit("setUserNickname", response.data.nickname);
          console.log("여기까지 넘어갑니다." + response.data.token);
          return response.data;
        },
        function (response) {
          findUser(
            response.token,
            function (success) {
              console.log("findBy User " + success.data.bcode);
              Swal.fire({
                position: "center",
                icon: "success",
                title: "로그인 성공",
                showConfirmButton: false,
                timer: 1500,
              });
              scope.$store.commit("setUserProfileImage", success.data.profileImage);
              scope.$store.commit("setBcode", success.data.bcode);
            },
            (error) => {
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
          return response.nickname;
        },
        function (response) {
          scope.$store.dispatch("setInterval", response);
        },
        (error) => {
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
  },
};
</script>

<style scope>
.site {
  align-content: center;
  align-self: center;
}

#login-form {
  height: 450px;
}

.form-group {
  margin-bottom: 25px;
}

.login_btn {
  width: 100%;
}

#kakaobtn {
  color: black !important;
  background-color: #feec34 !important;
}

#kakaobtn:hover {
  color: rgb(80, 28, 28) !important;

  box-shadow: 0px 25px 18px -6px rgba(0, 0, 0, 0.2);
  background-color: #faed63;
}

#btn_span {
  height: 20px;
}
</style>
