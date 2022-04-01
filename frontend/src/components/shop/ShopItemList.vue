<template>
  <div id="main-overview" class="container">
    <!-- 검색 -->
    <div>안녕하세요 shop item</div>
    <div
      class="
        card-body
        row
        no-gutters
        align-items-center
        justify-content-md-center
      "
    >
      <div class="col-auto">
        <i class="fas fa-search h4 text-body"></i>
      </div>
      <div class="col-4 center">
        <input
          class="form-control form-control-lg form-control-borderless"
          type="search"
          v-model="keyword"
          @keyup.enter="fnSearch"
          placeholder="Search topics or keywords"
        />
      </div>
      <div class="col-auto">
        <button class="btn btn-lg btn-success" type="submit" @click="fnSearch">
          Search
        </button>
      </div>
    </div>

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
        상품등록
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
import ItemCreate from "@/components/item/ItemCreate";
import { findItemListByPage, getItemList, getSearchItem } from "@/api/shop.js";
export default {
  components: {
    ItemEach,
  },
  data: () => ({
    keyword: "",
    contents: [
      // { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
    ],
    totalPages: 0,
    prevPageNum: 1,
    currentPageNum: 1,
    prev: true,
    next: false,
    bcode: null,
    sortBy: "regDate",
    order: "ASC",
    page: 0,
    size: 12,
  }),
  mounted() {
    this.itemList();

    console.log("마운트 되자마자 보여주는 콘솔 shopitem");
  },
  updated() {
    this.currentPageActive(this.prevPageNum, this.currentPageNum);
  },

  methods: {
    itemList() {
      console.log("여기는 shoplist");
      var vm = this;
      getItemList(
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log(vm.contents);
        },
        function (error) {
          console.error("에러 : " + error);
        }
      );
    },
    fnSearch() {
      // this.paging.page = 1;
      var vm = this;
      getSearchItem(
        this.keyword,
        this.bcode,
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log(vm.contents);
        },
        function (error) {
          console.error("에러 : " + error);
        }
      );
    },
    itemWrite() {
      // this.$router.push({
      //   name: "item.write",
      // });
      this.$router.push({ name: "item.create" });
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