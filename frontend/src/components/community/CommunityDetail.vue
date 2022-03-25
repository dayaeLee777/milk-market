<template>
  <div>
    <h-nav></h-nav>
    <h2>게시판 상세정보</h2>

    <div
      id="main-overview"
      class="container"
    >

      <a>{{$route.params.coId}}</a>

      <div class="mb-3">
        <label
          for="exampleFormControlInput1"
          class="form-label"
        >글 제목</label>
        <input
          type="text"
          class="form-control"
          v-model="title"
          id="exampleFormControlInput1"
          placeholder="글 제목을 입력해주세요"
        >
      </div>
      <div class="mb-3">
        <label
          for="exampleFormControlTextarea1"
          class="form-label"
        >글 내용</label>
        <textarea
          class="form-control"
          v-model="content"
          id="exampleFormControlTextarea1"
          rows="10"
          placeholder="글 내용을 입력해주세요"
        ></textarea>
      </div>

      <button
        type="button"
        class="btn btn-primary"
        @click="modifyCommunity"
        style="justify-content : center"
      >
        글 수정 버튼
      </button>

      <button
        type="button"
        class="btn btn-primary"
        @click="goCommunity"
        style="float:right"
      >
        글 목록
      </button>


      

      <section class="mb-5 mt-5">
        <div class="card bg-light">
          <div class="card-body">
            <from class="mb-4">
              <textarea class="form-control" rows="3" placeholder="코멘트를 작성해주세요" v-model="commentContent">
              </textarea>
              <button style="float:right" @click="uploadComment">
                댓글 등록
              </button>
            </from>
              <div class="d-flex mb-4 ml-4 mt-4">
                <div class="flex-shrink-0">
                  <img class="rounded-circle" src = "https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="...">
                </div>
                <div class="ms-3">
                  <div class="fw-bold">
                    Commenter Name
                  </div>
                    if youre going to lead a space if

                    <!--child commnet1 -->
                    <div class = "d-flex mt-4">
                      <div class="flex-shrink-0">
                        <img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="...">
                        </div>
                      <div class="ms-3">
                        <div class="fw-bold">대댓글 단 사람 이름</div>
                        And under those comditions, you cannot establish
                      </div>
                    </div>
                </div>
                
              </div>


              <div 
              v-for="(comment, idx) in comments" :key="idx" class="d-flex mb-4 ml-4">
                <div class="flex-shrink-0">
                  <img class="rounded-circle" src = "https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="...">
                </div>
                <div class="ms-3">
                  <div class="fw-bold">
                    {{comment.userNickname}}
                  </div>
                    {{comment.content}}
                </div>
              </div>

              
              
          </div>
        </div>

      </section>

    </div>

    <f-nav></f-nav>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data () {
    return {
      title: '',
      content: '',
      comments:[],
      commentContent:'',
    };
  },
  mounted () {
    this.getCommunityDetail();
    this.getCommunityComment();
  },
  methods: {
    goCommunity(){
      this.$router.push("/community");
    },
    uploadComment(){
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 내부의 댓글을 등록할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`
      }

      const data1 = { //이건 데이터 새로고침할때 사용하는 데이터
        // userNickname : this.comments.userNickname,
        userNickname : this.$store.state.user.id, //이 부분을 스토어에 닉네임을 담아서 끌어오면됨
        content : this.commentContent,
      }

      const data = {
        communityId : this.$route.params.coId, //스토어
        content : this.commentContent,
      }

      axios({
        url: `http://localhost:8080/api/comment`,
        method: 'post',
        headers,
        data,
      })
        .then((res) => {
          console.log("댓글 등록 성공");
          alert("댓글 등록 성공");
          this.comments.push(data1);
        })
        .catch((err) => {
          console.log(err);
          console.log("댓글 등록 실패");
        })

      
    },
    //댓글 가져오는 함수
    getCommunityComment(){
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 내부의 댓글을 조회할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`
      }

       axios({
        url: `http://localhost:8080/api/comment/list/${this.$route.params.coId}`,
        method: 'get',
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
        })

    },
    //게시글 내용 가져오는 함수
    getCommunityDetail () {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 세부정보를 조회할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$route.params.coId);

      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `http://localhost:8080/api/community/${this.$route.params.coId}`,
        method: 'get',
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
        })
    },
    //게시글 수정 시 작동하는 함수
    modifyCommunity () {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 세부정보 수정하기 버튼을 누르면 나오는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`
      }

      const data = {
        communityId: this.$route.params.coId,
        title: this.title,
        content: this.content,
      }

      axios({
        url: `http://localhost:8080/api/community`,
        method: 'put',
        headers,
        data,
      })
        .then((res) => {
          console.log("글 수정 성공");
          alert("글 수정 성공")
          this.$router.push("/community")
          // this.title = res.data.title;
          // this.content = res.data.content;
        })
        .catch((err) => {
          alert("글 수정 실패")
          console.log(err);
          console.log("글 수정 실패");
        })
    }
  },
};
</script>