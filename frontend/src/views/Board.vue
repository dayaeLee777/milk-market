<template>
  <div>
    <h-nav></h-nav>
    <h2>게시판 리스트</h2>

    <div
      id="main-overview"
      class="container"
    >
      <!-- <div class="searchWrap">
			  <input type="text" v-model="keyword" @keyup.enter="fnSearch" />
        <a href="javascript:;" @click="fnSearch" class="btnSearch btn">검색</a>
		  </div> -->

      <!-- <form class="card card-sm"> -->
      <div class="card-body row no-gutters align-items-center">
        <div class="col-auto">
          <i class="fas fa-search h4 text-body"></i>
        </div>
        <div class="col">
          <input
            class="form-control form-control-lg form-control-borderless"
            type="search"
            v-model="keyword"
            @keyup.enter="fnSearch"
            placeholder="Search topics or keywords"
          >
        </div>
        <div class="col-auto">
          <button
            class="btn btn-lg btn-success"
            type="submit"
            @click="fnSearch"
          >Search</button>
        </div>
      </div>
      <!-- </form> -->

      <table class="table">
        <thead>
          <tr>
            <th scope="col">no</th>
            <th scope="col">제목</th>
            <th scope="col">아이디</th>
            <th scope="col">날짜</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(content, idx) in contents"
            :key="idx"
          >
            <th scope="row">{{idx}}</th>
            <td @click="fnBoardDetail(content.communityId)">{{content.title}}</td>
            <td>{{content.userNickname}}</td>
            <td>{{content.regTime}}</td>
            <td>{{content.communityId}}</td>
            <td>{{typeof(content.communityId)}}</td>
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
          <li class="page-item active"><a
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

    <f-nav></f-nav>
  </div>
</template>

<script>
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
    fnBoardDetail (communityId) {
      // this.$router.push({path : "/community/detail", params: communityId})
      // this.$router.push({path : "/community/detail", params: communityId})
      this.$router.push({ name: 'communityDetail', params: { id: communityId } })
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
        url: `http://localhost:8080/api/community/list/1`,
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
        url: `http://localhost:8080/api/community/list/${i}`,
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
</style>