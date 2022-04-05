<template>
  <div class="item-body">
    <header></header>
    <!-- <h2>게시판 리스트</h2> -->
    <div
      id="main-overview"
      class="container community"
    >

      <table class="table">
        <thead>
          <tr>
            <!-- <th scope="col">no</th> -->
            <th scope="col">제목</th>
            <th scope="col">아이디</th>
            <th scope="col">날짜</th>
            <th scope="col">조회수</th>
            <th scope="col">삭제</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(content, idx) in contents"
            :key="idx"
          >
            <td @click="fnBoardDetail(content.communityId, content.userNickname)">{{content.title}}</td>
            <td>{{content.userNickname}}</td>
            <td>{{content.regTime}}</td>
            <td>{{content.hit}}</td>
            <td>
              <!-- <button type="button" class="btn btn-danger" @click ="communityWrite">
                삭제
              </button> -->
              <button
                @click="deleteCommunity(content.communityId, content.userNickname)"
                v-if="$store.state.user.userNickname === content.userNickname"
                type="button"
                class="btn-close"
                aria-label="Close"
              ></button>
            </td>
          </tr>
          <tr v-if="contents.length == 0">
            <td colspan="4">데이터가 없습니다.</td>
          </tr>
        </tbody>
      </table>

      <div>
        <button
          type="button"
          class="btn btn-primary"
          @click="communityWrite"
          style="float:right"
        >
          글작성
        </button>

        <ul
          class="pagination"
          style="justify-content : center"
        >
          <li class="page-item disabled"><a
              class="page-link"
              href="#"
            >Previous</a></li>
          <li class="page-item"><a
              class="page-link"
              @click="fnGetPageList(1)"
              href="#"
            >1</a></li>
          <li class="page-item"><a
              class="page-link"
              @click="fnGetPageList(2)"
              href="#"
            >2</a></li>
          <li class="page-item"><a
              class="page-link"
              @click="fnGetPageList(3)"
              href="#"
            >3</a></li>
          <li class="page-item"><a
              class="page-link"
              @click="fnGetPageList(4)"
              href="#"
            >4</a></li>
          <li class="page-item"><a
              class="page-link"
              @click="fnGetPageList(5)"
              href="#"
            >5</a></li>
          <li class="page-item"><a
              class="page-link"
              href="#"
            >Next</a></li>
        </ul>

      </div>

    </div>
    

    <!-- <f-nav></f-nav> -->
  </div>
</template>

<script>
import { API_BASE_URL } from "@/config/index.js";
import axios from 'axios'


export default {
  data () {
    return {
      contents: [],
      keyword: "",
    };
  },
  mounted () {
    this.fnGetList();
    console("마운트 되자마자 보여주는 콘솔")
  },
  methods: {
    goChatting (userNickname) {
      console.log("채팅방으로 가는 버튼을 눌럿음")
      const A = userNickname > this.$store.state.user.userNickname ? this.$store.state.user.userNickname : userNickname;
      const B = userNickname > this.$store.state.user.userNickname ? userNickname : this.$store.state.user.userNickname;
      this.$router.push({ name: "room", params: { userNickname: userNickname, sessionId: A + '1' + B } });
    },
    deleteCommunity (communityId, userNickname) {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 삭제 시 백엔드로 보내는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`
      }
      if (this.$store.state.user.userNickname === userNickname) {
        console.log('삭제가능');
        axios({
          url: `${API_BASE_URL}/api/community/delete/${communityId}`,
          method: 'put',
          headers,
        })
          .then((res) => {
            console.log("글 삭제 성공");
            console.log(res);
            // this.$router.go('/community');
            const idx = this.contents.findIndex(content => content.communityId === communityId)
            this.contents.splice(idx, 1)
          })
          .catch((err) => {
            console.log(err);
            console.log("글 삭제 실패");
          })
      } else {
        alert("본인이 아닙니다. 삭제 불가능")
      }


    },
    fnBoardDetail (communityId, userNickname) {
      this.$router.push({ name: "communityDetail", params: { coId: communityId, userN: userNickname } })

    },
    communityWrite () {
      this.$router.push("/commnunity/write");
    },
    fnGetList () {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "백엔드로 넘어가는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`
      }
      axios({
        url: `${API_BASE_URL}/api/community/list/1`,
        method: 'get',
        headers,
      })
        .then((res) => {
          this.contents = res.data.communityGetResponselist;
          console.log(" 글 리스트 로드 성공");
          console.log(res);
          console.log(this.contents);
        })
        .catch((err) => {
          console.log(err)
        })

    },
    fnGetPageList (i) {
      const token = this.$store.state.user.JWTToken;

      const headers = {
        Authorization: `Bearer ${token}`
      }
      axios({
        url: `${API_BASE_URL}/api/community/list/${i}`,
        method: 'get',
        headers,
      })
        .then((res) => {
          this.contents = res.data.communityGetResponselist;
          console.log("페이지 이동 성공");
          console.log(res);
          console.log(this.contents);
        })
        .catch((err) => {
          console.log(err)
        })

    },
    fnSearch () {
      this.paging.page = 1;
      this.fnGetList();
    },
    fnPage (n) {
      if (this.page != n) {
        this.page = n;
        this.fnGetList();
      }
    }
  }
}
</script>

<style scoped>
.community {
  height: 100vh;
}
</style>