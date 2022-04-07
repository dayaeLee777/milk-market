<template>
  <div class="a">
    <!-- <h-nav></h-nav>
    <h2>게시판 상세정보</h2> -->

    <div id="main-overview" class="container">
      <!-- <a>{{$route.params.coId}}</a>
      <div></div>
      <a>{{$store.state.user.profileImage}}</a> -->

      <fieldset :disabled="$route.params.userN !== $store.state.user.userNickname">
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label"><h4>제목</h4></label>
          <input
            type="text"
            class="form-control"
            v-model="title"
            id="exampleFormControlInput1"
            placeholder="글 제목을 입력해주세요"
          />
        </div>
        <div class="mb-3">
          <label for="exampleFormControlTextarea1" class="form-label"><h5>내용</h5></label>
          <textarea
            class="form-control"
            v-model="content"
            id="exampleFormControlTextarea1"
            rows="10"
            placeholder="글 내용을 입력해주세요"
          ></textarea>
        </div>
      </fieldset>
      <div class="row_buttons mb-4">
        <button type="button" class="btn btn--width" @click="goCommunity">목록</button>
        <button
          type="button"
          class="btn btn--width btn--pink"
          style="float: right"
          @click="deleteCommunity($route.params.coId)"
          v-if="$route.params.userN === $store.state.user.userNickname"
        >
          삭제
        </button>
        <button
          type="button"
          class="btn btn--width mr-3 btn--purple"
          style="float: right"
          @click="modifyCommunity"
          v-if="$route.params.userN === $store.state.user.userNickname"
        >
          수정
        </button>
      </div>
      <div class="comment_section">
        <section class="mb-5 mt-5">
          <div class="card bg-light">
            <div class="card-body">
              <div class="mb-4">
                <div class="form-group">
                  <textarea
                    type="text"
                    class="form__field form__textarea"
                    placeholder="댓글를 작성해주세요"
                    v-model="commentContent"
                  >
                  </textarea>
                  <button class="btn btn--revert" style="float: right" @click="uploadComment">
                    댓글 등록
                  </button>
                </div>
              </div>
              <div class="comment row">
                <div
                  v-for="(comment, idx) in comments"
                  :key="idx"
                  class="comment-area row d-flex mb-4 ml-4 mt-4"
                >
                  <div class="row" style="display: inline-block">
                    <img
                      class="rounded-circle"
                      :src="comment.profileImage"
                      alt="..."
                      style="width: 80px"
                    />
                    <span class="username">{{ comment.userNickname }}</span>
                    <span class="regTime">{{ comment.regTime }}</span>
                    <button
                      @click="deleteComment(comment.commentId, comment.userNickname)"
                      v-if="comment.userNickname === $store.state.user.userNickname"
                      type="button"
                      class="btn btn--revert btn-commentdelete"
                      aria-label="Close"
                      style="float: right"
                    >
                      삭제
                    </button>
                    <div class="comment-text">
                      {{ comment.content }}
                    </div>
                    <hr class="comment-divider p-0 mt-2" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>

    <!-- <f-nav></f-nav> -->
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2/dist/sweetalert2.js";
import { API_BASE_URL } from "@/config/index.js";

export default {
  data() {
    return {
      title: "",
      content: "",
      comments: [],
      commentContent: "",
      disabled: false,
      profileImage: "",
    };
  },
  created() {
    this.getCommunityDetail();
    this.getCommunityComment();
  },
  mounted() {
    // this.getCommunityDetail();
    // this.getCommunityComment();
  },
  methods: {
    //글 삭제하는 함수
    deleteCommunity(coId) {
      const token = this.$store.state.user.JWTToken;
      const headers = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        url: `${API_BASE_URL}/api/community/delete/${coId}`,
        method: "put",
        headers,
      })
        .then((res) => {
          console.log("글 삭제 성공");
          Swal.fire({
            position: "center",
            icon: "success",
            title: "게시글 삭제 완료",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.push("/community");
        })
        .catch((err) => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
    //댓글 지우는 함수
    deleteComment(commentId, userNickname) {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 내부의 댓글을 삭제할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      const data1 = {
        //이건 데이터 새로고침할때 사용하는 데이터
        userNickname: this.$store.state.user.userNickname, //이 부분을 스토어에 닉네임을 담아서 끌어오면됨
        content: this.commentContent,
      };
      if (userNickname > this.$store.state.user.userNickname) {
        console.log("글작성자가 숫자로 치면 더 크다");
        console.log(userNickname + this.$store.state.user.userNickname);
      } else {
        console.log("지금 사용자가 더크다.");
      }

      if (userNickname === this.$store.state.user.userNickname) {
        console.log("일치");
        axios({
          url: `${API_BASE_URL}/api/comment/delete/${commentId}`,
          method: "put",
          headers,
        })
          .then((res) => {
            Swal.fire({
              position: "center",
              icon: "success",
              title: "댓글 삭제 성공",
              showConfirmButton: false,
              timer: 1500,
            });
            console.log("댓글 삭제 성공");
            // this.comments.push(data1);
            const idx = this.comments.findIndex((comment) => comment.commentId === commentId);
            this.comments.splice(idx, 1);
          })
          .catch((err) => {
            console.log(err);
            console.log("댓글 등록 실패");
          });
      } else {
        Swal.fire({
          position: "center",
          icon: "info",
          title: "사용자가 작성한 글이 아닙니다",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    },
    goCommunity() {
      this.$router.push("/community");
    },
    uploadComment() {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 내부의 댓글을 등록할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      const data1 = {
        //이건 데이터 새로고침할때 사용하는 데이터
        // userNickname : this.comments.userNickname,
        userNickname: this.$store.state.user.userNickname, //이 부분을 스토어에 닉네임을 담아서 끌어오면됨
        content: this.commentContent,
        profileImage: this.$store.state.user.profileImage,
      };

      const data = {
        communityId: this.$route.params.coId, //스토어
        content: this.commentContent,
        profileImage: this.$store.state.user.profileImage, //이미지
      };

      axios({
        url: `${API_BASE_URL}/api/comment`,
        method: "post",
        headers,
        data,
      })
        .then((res) => {
          console.log("댓글 등록 성공");
          Swal.fire({
            position: "center",
            icon: "success",
            title: "댓글 등록 성공",
            showConfirmButton: false,
            timer: 1500,
          });
          this.comments.push(data1);
        })
        .catch((err) => {
          console.log(err);
          console.log("댓글 등록 실패");
        });
    },
    //댓글 가져오는 함수
    getCommunityComment() {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 내부의 댓글을 조회할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/comment/list/${this.$route.params.coId}`,
        method: "get",
        headers,
      })
        .then((res) => {
          console.log("댓글 조회 성공");
          console.log(res);
          this.comments = res.data.commentGetListResponseDto;
          console.log(res.data.commentGetListResponseDto);
        })
        .catch((err) => {
          console.log(err);
          console.log("댓글 조회 실패");
        });
    },
    //게시글 내용 가져오는 함수
    getCommunityDetail() {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 세부정보를 조회할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/community/${this.$route.params.coId}`,
        method: "get",
        headers,
      })
        .then((res) => {
          console.log("글 조회 성공");
          this.title = res.data.title;
          this.content = res.data.content;
        })
        .catch((err) => {
          console.log(err);
          console.log("글 조회 실패");
        });
    },
    //게시글 수정 시 작동하는 함수
    modifyCommunity() {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 세부정보 수정하기 버튼을 누르면 나오는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      const data = {
        communityId: this.$route.params.coId,
        title: this.title,
        content: this.content,
      };

      if (this.$route.params.userN === this.$store.state.user.userNickname) {
        console.log("수정가능한 아이디");

        axios({
          url: `${API_BASE_URL}/api/community`,
          method: "put",
          headers,
          data,
        })
          .then((res) => {
            console.log("글 수정 성공");
            Swal.fire({
              position: "center",
              icon: "success",
              title: "글 수정 성공",
              showConfirmButton: false,
              timer: 1500,
            });
            this.$router.push("/community");
            // this.title = res.data.title;
            // this.content = res.data.content;
          })
          .catch((err) => {
            Swal.fire({
              position: "center",
              icon: "error",
              title: "글 수정 실패",
              showConfirmButton: false,
              timer: 1500,
            });
            console.log(err);
            console.log("글 수정 실패");
          });
      } else {
        console.log("수정불가능 아이디");
        alert("작성자가 아니라 수정 불가.");
      }
    },
  },
};
</script>
<style scoped>
.btn--purple {
  background-color: #8a3aff !important;
}

.btn--purple:hover {
  background-color: #b284f6 !important;
}

.btn--pink {
  background-color: #d942b6 !important;
}
.btn--pink:hover {
  background-color: #de59bf !important;
}

.row_buttons {
  margin-bottom: 10px;
  display: inline;
}
.form__textarea {
  height: 100px !important;
}

.comment-area {
  min-height: 80px;
}
.comment-text {
  width: 100%;
}
.btn-commentdelete {
  width: 60px;
}

.username {
  font-size: 18px;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: bold;
}

.regTime {
  font-size: 15px;
  color: #868686;
}
</style>
