<template>
  <div id="main-overview" class="container">
    <div class="row row-cols-2 row-cols-md-4 g-5">
      <div class="col" v-for="(content, index) in contents" :key="index">
        <item-each :content="content"></item-each>
      </div>
    </div>
    <div id="footer">
      <button
        type="button"
        class="btn btn-primary"
        @click="itemWrite"
        style="float: right"
      >
        글작성
      </button>

      <ul id="pagination" class="pagination" style="justify-content: center">
        <li class="page-item" v-bind:class="{ disabled: prev }">
          <a class="page-link" @click="prevPage(currentPageNum)" href="#"
            >Previous</a
          >
        </li>
        <li class="page-item" v-for="number in totalPages" :key="number">
          <a class="page-link" @click="getItemByPage(number)" href="#">{{
            number
          }}</a>
        </li>
        <li class="page-item" v-bind:class="{ disabled: next }">
          <a class="page-link" @click="nextPage(currentPageNum)" href="#"
            >Next</a
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import ItemEach from "@/components/item/ItemEach";
import { findItemListByPage } from "@/api/item.js";
export default {
  components: {
    ItemEach,
  },
  data: () => ({
    contents: [
      // { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
    ],
    totalPages: 0,
    prevPageNum: 1,
    currentPageNum: 1,
    prev: true,
    next: false,
  }),
  mounted() {
    this.getItemList();

    console.log("마운트 되자마자 보여주는 콘솔");
  },
  updated() {
    this.currentPageActive(this.prevPageNum, this.currentPageNum);
  },

  methods: {
    getItemList() {
      const token = this.$store.state.user.JWTToken;
      console.log("아이템 리스트 가져오기" + token);
      const headers = {
        Authorization: `Bearer ${token}`,
      };
      axios({
        url: `http://localhost:8080/api/item/list/1`,
        method: "get",
        headers,
      })
        .then((res) => {
          console.log(res);
          this.contents = res.data.list;
          this.totalPages = res.data.totalPages;
          console.log(" 아이템 리스트 로드 성공");
          console.log(res);
          console.log(this.contents);
          console.log("total page : " + this.totalPages);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    itemWrite() {
      // this.$router.push({
      //   name: "item.write",
      // });
      console.log("itemWrite호출");
    },
    currentPageActive(prevPageNum, currentPageNum) {
      var page = $("#pagination").children("li");
      console.log(page);
      page[prevPageNum].classList.remove("active");
      page[currentPageNum].classList.add("active");
    },
    getItemByPage(pageNumber) {
      console.log(pageNumber);
      this.currentPageNum = pageNumber;
      if (pageNumber !== 1) {
        this.prev = false;
      } else {
        this.prev = true;
      }
      if (pageNumber === this.totalPages) {
        this.next = true;
      } else {
        this.next = false;
      }
      const scope = this;
      findItemListByPage(pageNumber, function (response) {
        console.log(response.data);
        scope.contents = response.data.list;
        scope.totalPages = response.data.totalPages;
        console.log("페이지 클릭시 매개변수" + pageNumber);
      });
      this.currentPageActive(this.prevPageNum, this.currentPageNum);
      this.prevPageNum = pageNumber;
      console.log("이전 페이지 등록" + this.prevPageNum);
    },
    prevPage(pageNumber) {
      this.getItemByPage(pageNumber - 1);
    },
    nextPage(pageNumber) {
      this.getItemByPage(pageNumber + 1);
    },
  },
};
</script>

<style>
div #footer {
  margin: 20px 0px;
}
</style>