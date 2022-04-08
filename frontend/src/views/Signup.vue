<template>
  <div class="sect sect--padding-top">
    <div class="container">
      <div class="row">
        <div class="site">
          <h1 class="row__title">Sign Up</h1>
          <h2 class="row__sub">회원이 되어 우유마켓의 서비스를 이용하세요</h2>
          <div class="row row--center">
            <div class="col-md-2"></div>
            <div class="col-md-8">
              <div class="signup-form">
                <div class="form-group">
                  <div class="form__field--half">
                    <input
                      type="text"
                      class="form-control form__field form__text"
                      id="email"
                      v-model="user.email"
                      placeholder="Email address"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <div class="form__field--half">
                    <input
                      type="text"
                      class="form-control form__field form__text"
                      id="name"
                      v-model="user.name"
                      placeholder="Nickname"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <div class="form__field--half">
                    <input
                      type="password"
                      class="form-control form__field form__text"
                      id="password"
                      v-model="user.password"
                      placeholder="Password"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <div class="form__field--half">
                    <input
                      type="password"
                      class="form-control form__field form__text"
                      id="password-confirm"
                      v-model="user.passwordConfirm"
                      placeholder="Password Confirm"
                      @keydown.enter="register"
                    />
                  </div>
                </div>
                <div class="form-group">
                  <!-- <div class="form__field--half"> -->
                  <div class="row signup_row">
                    <label for="location_btn">
                      <div class="address_text">
                        <input
                          type="text"
                          class="form__field form__text form-control"
                          id="location"
                          disabled="true"
                          placeholder="동네인증을 위해 찾기 버튼을 눌러주세요"
                        />
                      </div>
                      <div class="address_btn">
                        <input
                          type="button"
                          class="form__field form__text form-control"
                          id="location_btn"
                          value="찾기"
                          @click="execDaumPostcode()"
                        />
                      </div>
                    </label>
                  </div>
                  <!-- </div> -->
                </div>

                <div class="form-group">
                  <button type="submit" class="btn btn-width signup_btn" v-on:click="register">
                    회원가입
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
import { signup } from "../api/user.js";
import Swal from "sweetalert2/dist/sweetalert2.js";

//여기서 변수 선언.

export default {
  data() {
    return {
      user: {
        email: "",
        name: "",
        bcode: "",
        bname: "",
        sigungu: "",
        password: "",
        passwordConfirm: "",
      },
    };
  },
  methods: {
    register() {
      var vm = this;
      db.collection("user")
        .doc(this.user.name)
        .set({
          chatRooms: [],
        })
        .then(() => {
          console.log("sucess");
        });
      if (this.user.password === this.user.passwordConfirm) {
        signup(
          this.user.email,
          this.user.name,
          this.user.password,
          this.user.bname,
          this.user.bcode,
          this.user.sigungu,
          (success) => {
            console.log(success);
            Swal.fire({
              position: "center",
              icon: "success",
              title: "회원가입 성공",
              showConfirmButton: false,
              timer: 2000,
            });
            this.$router.push("/login");
          },
          (error) => {
            console.error(error);
            Swal.fire({
              position: "center",
              icon: "error",
              title: "회원가입 실패",
              text: "필수 입력사항을 입력해주세요",
              showConfirmButton: false,
              timer: 2000,
            });
          }
        );
      } else {
        Swal.fire({
          position: "center",
          icon: "error",
          title: "회원가입 실패",
          text: "비밀번호가 일치하지 않습니다",
          showConfirmButton: false,
          timer: 2000,
        });
      }
    },
    execDaumPostcode() {
      const vm = this;
      new daum.Postcode({
        oncomplete: function (data) {
          const addr = data.address;
          document.getElementById("location").value = addr;
          vm.user.bcode = data.bcode;
          vm.user.bname = data.bname;
          vm.user.sigungu = data.sigungu;
          console.log(vm.user);
        },
      }).open();
    },
  },
};
</script>

<style>
.address_text {
  width: 70%;
  float: left;
  padding-right: 5%;
}

.address_btn {
  width: 30%;
  float: left;
}

.signup_btn {
  width: 50%;
  margin-top: 20px;
}
</style>
