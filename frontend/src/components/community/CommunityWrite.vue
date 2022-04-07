<template>
  <!-- <h-nav></h-nav>
    <h2>게시판 작성</h2> -->

  <div class="sect sect-community">
    <div class="container">
      <div class="row row--center">
        <h1 class="row__title">우유마켓 커뮤니티</h1>
        <h2 class="row__sub">유아용품 관련 정보를 공유하며 소통의 창구로 활용하세요</h2>
      </div>
      <div class="row row--center">
        <!-- <label for="exampleFormControlInput1" class="form-label">글 제목</label> -->
        <div id="community-form">
          <div class="form-group">
            <div class="form__field--half">
              <input
                type="text"
                class="form-control"
                v-model="title"
                id="exampleFormControlInput1"
                placeholder="글 제목을 입력해주세요"
              />
            </div>
          </div>
          <div class="form-group">
            <div class="form__field--half">
              <!-- <label for="exampleFormControlTextarea1" class="form-label">글 내용</label> -->
              <textarea
                class="form-control"
                v-model="content"
                id="exampleFormControlTextarea1"
                rows="10"
                placeholder="글 내용을 입력해주세요"
              ></textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <button
          type="button"
          class="btn btn--width btn-blue"
          style="float: right"
          @click="fnWriteCommunity"
        >
          글 등록
        </button>
      </div>
    </div>
  </div>
  <!-- <f-nav></f-nav> -->
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2/dist/sweetalert2.js";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: "commnunityWrite",
  data() {
    return {
      title: "",
      content: "",
    };
  },
  methods: {
    fnWriteCommunity() {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community를 등록할때 백엔드로 보내는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      const data = {
        title: this.title,
        content: this.content,
      };

      axios({
        url: `${API_BASE_URL}/api/community`,
        method: "post",
        headers,
        data,
      })
        .then((res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "게시글 작성 완료",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.push("/community");
        })
        .catch((err) => {
          console.log(err);
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
  },
};
</script>
<style scoped>
.sect-community {
  padding-top: 20px;
}

.row__title {
  font-size: 30px;
  margin-bottom: 20px !important;
}

.row__sub {
  font-size: 20px;
  margin-bottom: 30px !important;
}
</style>
