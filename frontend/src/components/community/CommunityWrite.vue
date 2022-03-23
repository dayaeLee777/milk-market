<template>
  <div>
    <h-nav></h-nav>
    <h2>게시판 작성</h2>

    <div
      id="main-overview"
      class="container"
    >

      <div class="mb-3">
        <label for="exampleFormControlInput1" class="form-label">글 제목</label>
        <input type="text" class="form-control" v-model="title" id="exampleFormControlInput1" placeholder="글 제목을 입력해주세요">
        </div>
        <div class="mb-3">
        <label for="exampleFormControlTextarea1" class="form-label">글 내용</label>
        <textarea class="form-control" v-model="content" id="exampleFormControlTextarea1" rows="10" placeholder="글 내용을 입력해주세요"></textarea>
      </div>

    <button type="button" class="btn btn-primary" style="justify-content : center" @click="fnWriteCommunity">
      글 등록 버튼
    </button>

    </div>

    
    
    
    
    
    <f-nav></f-nav>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "commnunityWrite",
  data() {
    return {
      title:'',
      content:'',
    };
  },
  methods: {
    fnWriteCommunity(){
      const token = this.$store.state.user.JWTToken;
      console.log(token+"community를 등록할때 백엔드로 보내는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`
      }

      const data = {
        title : this.title,
        content : this.content,
      }

      axios({
        url : `http://localhost:8080/api/community`,
        method: 'post',
        headers,
        data,
      })
      .then((res)=>{
        console.log("글 등록 성공");
        this.$router.push("/board")
      })
      .catch((err)=>{
        console.log(err);
        console.log("글 등록 실패");
      })

      
    }
  },
};
</script>