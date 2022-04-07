<template>
  <div class="item-body">
    <header></header>
    <!-- <h2>게시판 리스트</h2> -->

    <div class="sect sect-community">
      <div id="main-overview" class="container community">
        <div class="row row--center">
          <h1 class="row__title">우유마켓 커뮤니티</h1>
          <h2 class="row__sub">유아용품 관련 정보를 공유하며 소통의 창구로 활용하세요</h2>
        </div>
        <div class="row">
          <div class="mb-4">
            <button
              type="button"
              class="btn btn--width"
              @click="communityWrite"
              style="float: right"
            >
              글 작성
            </button>
          </div>
        </div>
        <b-table
          style="text-align: center"
          id="my-table"
          :items="items"
          :per-page="perPage"
          :current-page="currentPage"
          :fields="fields"
          @row-clicked="fnBoardDetailTable"
          small
        >
          <template #cell(index)="data">
            {{ rows - perPage * (currentPage - 1) - data.index }}
          </template>
        </b-table>
        <div class="paging">
          <b-pagination
            v-model="currentPage"
            :total-rows="rows"
            :per-page="perPage"
            style="justify-content: center"
          ></b-pagination>
        </div>
      </div>
    </div>
    <!-- <f-nav></f-nav> -->
  </div>
</template>

<script>
import { API_BASE_URL } from "@/config/index.js";
import axios from "axios";

export default {
  data() {
    return {
      items: [],
      keyword: "",
      perPage: 10,
      currentPage: 1,
      prev: true,
      next: false,
      fields: [
        { key: "index", label: "글번호" },
        { key: "title", label: "제목" },
        { key: "userNickname", label: "닉네임" },
        { key: "regTime", label: "등록일시" },
        { key: "hit", label: "조회순" },
      ],
    };
  },
  mounted() {
    this.fnGetList();
    console("마운트 되자마자 보여주는 콘솔");
  },
  computed: {
    rows() {
      console.log("computed : " + this.items.length);
      return this.items.length;
    },
  },
  methods: {
    fnBoardDetailTable(item) {
      this.fnBoardDetail(item.communityId, item.userNickname);
    },
    goChatting(userNickname) {
      console.log("채팅방으로 가는 버튼을 눌럿음");
      const A =
        userNickname > this.$store.state.user.userNickname
          ? this.$store.state.user.userNickname
          : userNickname;
      const B =
        userNickname > this.$store.state.user.userNickname
          ? userNickname
          : this.$store.state.user.userNickname;
      this.$router.push({
        name: "room",
        params: { userNickname: userNickname, sessionId: A + "1" + B },
      });
    },
    deleteCommunity(communityId, userNickname) {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "community 삭제 시 백엔드로 보내는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`,
      };
      if (this.$store.state.user.userNickname === userNickname) {
        console.log("삭제가능");
        axios({
          url: `${API_BASE_URL}/api/community/delete/${communityId}`,
          method: "put",
          headers,
        })
          .then((res) => {
            console.log("글 삭제 성공");
            console.log(res);
            // this.$router.go('/community');
            const idx = this.items.findIndex((content) => content.communityId === communityId);
            this.items.splice(idx, 1);
          })
          .catch((err) => {
            console.log(err);
            console.log("글 삭제 실패");
          });
      } else {
        alert("본인이 아닙니다. 삭제 불가능");
      }
    },
    fnBoardDetail(communityId, userNickname) {
      this.$router.push({
        name: "communityDetail",
        params: { coId: communityId, userN: userNickname },
      });
    },
    communityWrite() {
      this.$router.push("/commnunity/write");
    },
    fnGetList() {
      const token = this.$store.state.user.JWTToken;
      console.log(token + "백엔드로 넘어가는 토큰입니다.");

      const headers = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        url: `${API_BASE_URL}/api/community/list`,
        method: "get",
        headers,
      })
        .then((res) => {
          this.items = res.data.communityGetResponselist;
          console.log(" 글 리스트 로드 성공");
          console.log(res);
          console.log(this.items);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fnGetPageList(i) {
      const token = this.$store.state.user.JWTToken;

      const headers = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        url: `${API_BASE_URL}/api/community/list/${i}`,
        method: "get",
        headers,
      })
        .then((res) => {
          this.items = res.data.communityGetResponselist;
          console.log("페이지 이동 성공");
          console.log(res);
          console.log(this.items);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fnSearch() {
      this.paging.page = 1;
      this.fnGetList();
    },
    fnPage(n) {
      if (this.page != n) {
        this.page = n;
        this.fnGetList();
      }
    },
  },
};
</script>

<style scoped>
.community {
  height: 100vh;
}

thead,
tbody {
  text-align: center;
}
.sect-community {
  padding-top: 20px;
}

.row--center {
  max-width: 100% !important;
}

.row__title {
  font-size: 30px;
  margin-bottom: 20px !important;
}

.row__sub {
  font-size: 20px;
  margin-bottom: 30px !important;
}

.paging {
  align-items: baseline;
}
</style>
