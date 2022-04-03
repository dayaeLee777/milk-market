<template>
  <div class="sect sect--padding-top">
    <div class="container">
    <h3 class="text-center">회원 가입</h3>
      <div class="row">
        <div  class="col-6 mx-auto bg-white">
          <div class="">
            <div class="form-group">
              <label for="email">이메일</label>
              <input
                type="text"
                class="form-control"
                id="email"
                v-model="user.email"
                placeholder="이메일"
              />
            </div>
            <div class="form-group">
              <label for="name">닉네임</label>
              <input
                type="text"
                class="form-control"
                id="name"
                v-model="user.name"
                placeholder="닉네임"
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
            <div class="form-group">
              <label for="password-confirm">비밀번호 확인</label>
              <input
                type="password"
                class="form-control"
                id="password-confirm"
                v-model="user.passwordConfirm"
                placeholder="비밀번호 확인"
                @keydown.enter="register"
              />
            </div>
            <label for="location">주소</label>
            <div class="d-flex form-group">
              <input
                type="text"
                class="col-9 form-control"
                id="location"
                placeholder="주소"
              />
              <input
                type="button"
                class="col-3 form-control"
                value="찾기"
                @click="execDaumPostcode()"
              />
            </div>
            <button
              type="submit"
              class="btn btn-primary mt-3"
              v-on:click="register"
            >
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { signup } from "../api/user.js";
import Swal from 'sweetalert2/dist/sweetalert2.js'

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

      if (this.user.password === this.user.passwordConfirm) {
        signup(
          this.user.email,
          this.user.name,
          this.user.password,
          this.user.bname,
          this.user.bcode,
          this.user.sigungu,
          function (success) {
            console.log(success)
            vm.$router.push("/");
            Swal.fire({
              position: "center",
              icon: "success",
              title: "회원가입 성공",
              showConfirmButton: false,
              timer: 2000,
            });
          },
          function (error) {
            console.error(error);
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
#register-form {
  /* display: flex; */
  /* padding-top: 0px; */
  align-content: center;
  margin-bottom: 50px;
  display: inline-block;
}
.row {
  /* display: flex; */
  /* padding-top: 0px; */
  padding-block-start: 5px;
  /* display: inline-block; */
}
</style>
