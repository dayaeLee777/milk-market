<template>
  <div>
    <h-nav></h-nav>
    <!-- <div
      class="p-3 bg-white rounded shadow-sm"
      style="margin-top: 76px;"
    > -->

    <!-- <div class="media text-muted pt-3">
        <svg
          class="bd-placeholder-img mr-2 rounded"
          width="32"
          height="32"
          xmlns="http://www.w3.org/2000/svg"
          role="img"
          aria-label="Placeholder: 32x32"
          preserveAspectRatio="xMidYMid slice"
          focusable="false"
        >
          <title>Placeholder</title>
          <rect
            width="100%"
            height="100%"
            fill="#007bff"
          /><text
            x="50%"
            y="50%"
            fill="#007bff"
            dy=".3em"
          >32x32</text>
        </svg>

        <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
          <strong class="d-block text-gray-dark">@username</strong>
          Playing ping pong all night long, everything's all neon and hazy. Yeah, she's so in demand. She's sweet as pie but if you break her heart. But down to earth. It's time to face the music I'm no longer your muse. I guess that I forgot I had a choice.
        </p>
      </div>
      <div class="media text-muted pt-3">
        <svg
          class="bd-placeholder-img mr-2 rounded"
          width="32"
          height="32"
          xmlns="http://www.w3.org/2000/svg"
          role="img"
          aria-label="Placeholder: 32x32"
          preserveAspectRatio="xMidYMid slice"
          focusable="false"
        >
          <title>Placeholder</title>
          <rect
            width="100%"
            height="100%"
            fill="#e83e8c"
          /><text
            x="50%"
            y="50%"
            fill="#e83e8c"
            dy=".3em"
          >32x32</text>
        </svg>

        <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
          <strong class="d-block text-gray-dark">@username</strong>
          Standing on the frontline when the bombs start to fall. Heaven is jealous of our love, angels are crying from up above. Can't replace you with a million rings. Boy, when you're with me I'll give you a taste. There’s no going back. Before you met me I was alright but things were kinda heavy. Heavy is the head that wears the crown.
        </p>
      </div>
      <div class="media text-muted pt-3">
        <svg
          class="bd-placeholder-img mr-2 rounded"
          width="32"
          height="32"
          xmlns="http://www.w3.org/2000/svg"
          role="img"
          aria-label="Placeholder: 32x32"
          preserveAspectRatio="xMidYMid slice"
          focusable="false"
        >
          <title>Placeholder</title>
          <rect
            width="100%"
            height="100%"
            fill="#6f42c1"
          /><text
            x="50%"
            y="50%"
            fill="#6f42c1"
            dy=".3em"
          >32x32</text>
        </svg>

        <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
          <strong class="d-block text-gray-dark">@username</strong>
          Will you do the same for me? It's time to face the music I'm no longer your muse. Heard it's beautiful, be the judge and my girls gonna take a vote. I can feel a phoenix inside of me. Heaven is jealous of our love, angels are crying from up above. Yeah, you take me to utopia.
        </p>
      </div> -->
    <!-- </div> -->
    <h2>게시판 리스트</h2>

    <div class="searchWrap">
      <input
        type="text"
        v-model="keyword"
        @keyup.enter="fnSearch"
      /><a
        href="javascript:;"
        @click="fnSearch"
        class="btnSearch btn"
      >검색</a>
    </div>

    <div class="listWrap">
      <table class="tbList">
        <colgroup>
          <col width="6%" />
          <col width="*" />
          <col width="10%" />
          <col width="15%" />
        </colgroup>
        <tr>
          <th>no</th>
          <th>제목</th>
          <th>아이디</th>
          <th>날짜</th>
        </tr>
        <tr
          v-for="(content, idx) in contents"
          :key="idx"
        >
          <td>{{idx}}</td>
          <td class="txt_left"><a href="javascript:;">{{content.title}}</a></td>
          <td>{{content.userNickname}}</td>
          <td>{{content.regdate}}</td>
        </tr>
        <tr v-if="contents.length == 0">
          <td colspan="4">데이터가 없습니다.</td>
        </tr>
      </table>
    </div>

    <f-nav></f-nav>
  </div>
</template>

<script>
import axios from 'axios'

export default {

  components: {

  },

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
    fnGetList () {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "백엔드로 넘어가는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`
      }
      // axios.get("http://localhost:8080/api/community/list", {'token':token})
      // .then((res)=>{
      //   if(res.data.success){
      //     this.contents = res.data;
      //     // this.row.title = res.data.title;
      //     // this.row.content = res.data.content;
      //     // this.row.regTime = res.data.regTime;
      //     // this.row.userNickname = res.data.userNickname;
      //   }
      // })
      axios({
        url: `http://localhost:8080/api/community/list`,
        method: 'get',
        headers,
      })
        .then((res) => {
          this.contents = res.data.communityGetResponselist;
          console.log("성공성공성공");
          console.log(res);
          console.log(this.contents);
        })
        .catch((err) => {
          console.log(err)
        })

    },
    // getList(){
    //   const token = store.state.token;
    //   console.log(store.state.token);
    //   this.$axios.get("http://localhost:8080/api/community/list", {'token':token})
    //   .then((res)=>{
    //     console.log(res);
    //   })
    //   .then((err)=>{
    //     console.log(err);
    //   })
    // },
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
/* .searchWrap{border:1px solid #888; border-radius:5px; text-align:center; padding:20px 0; margin-bottom:40px;}
	.searchWrap input{width:60%; height:36px; border-radius:3px; padding:0 10px; border:1px solid #888;}
	.searchWrap .btnSearch{display:inline-block; margin-left:10px;}
	.tbList th{border-top:1px solid #888;}
	.tbList th, .tbList td{border-bottom:1px solid #eee; padding:5px 0;}
	.tbList td.txt_left{text-align:left;}
	.btnRightWrap{text-align:right; margin:10px 0 0 0;}

	.pagination{margin:20px 0 0 0; text-align:center;}
	.first, .prev, .next, .last{border:1px solid #666; margin:0 5px;}
	.pagination span{display:inline-block; padding:0 5px; color:#333;}
	.pagination a{text-decoration:none; display:inline-blcok; padding:0 5px; color:#666;} */
</style>