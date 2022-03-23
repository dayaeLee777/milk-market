<template>
  <div>
    <h-nav></h-nav>
    <h2>게시판 상세정보</h2>

    <div
      id="main-overview"
      class="container"
    >

    <!-- <a>{{$router.query.coId}}</a> -->

      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">글 제목</label>
        <input type="text" class="form-control" v-model="title" id="exampleFormControlInput1" placeholder="글 제목을 입력해주세요">
        </div>
        <div class="mb-3">
        <label for="exampleFormControlTextarea1" class="form-label">글 내용</label>
        <textarea class="form-control" v-model="content" id="exampleFormControlTextarea1" rows="10" placeholder="글 내용을 입력해주세요"></textarea>
      </div>

    <button type="button" class="btn btn-primary" style="justify-content : center" >
      글 수정 버튼
    </button>

    </div>
    
    <f-nav></f-nav>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title:'',
      content:'',
    };
  },
  mounted(){
    this.getCommunityDetail();
  },
  methods: {
    getCommunityDetail(){
      const token = this.$store.state.user.JWTToken;
      console.log(token+"community 세부정보를 조회할때 백엔드로 보내는 토큰입니다.");
      console.log(this.$router.query.coId);

      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url : `http://localhost:8080/api/community/${this.$router.query.coId}`,
        method: 'get',
        headers,
      })
      .then((res)=>{
        console.log("글 조회 성공");
        this.title = res.data.title;
        this.content = res.data.content;
      })
      .catch((err)=>{
        console.log(err);
        console.log("글 조회 실패");
      })

      
    }
  },
};
</script>