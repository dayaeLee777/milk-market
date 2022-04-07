<template>
  <div id="main-overview" class="container">
    <!-- 검색 -->

    <div class="row row--center">
      <h1 class="row__title">우유마켓 상품검색</h1>
      <h2 class="row__sub">다양한 조건과 키워드를 활용하여 검색하세요</h2>
    </div>
    <div class="card-body row no-gutters align-items-center justify-content-md-center">
      <div class="col-auto">
        <i class="fas fa-search h4 text-body"></i>
      </div>

      <div class="row row--center">
        <div class="search-form">
          <div class="form-group input_search">
            <input
              class="form-control form__field form__text"
              type="search"
              v-model="keyword"
              @keyup.enter="fnSearch"
              placeholder="원하는 상품을 검색해보세요"
            />
          </div>

          <div class="form-group btn_search">
            <button class="btn" type="submit" @click="fnSearch">Search</button>
          </div>
        </div>
      </div>
    </div>

    <div class="body">
      <!-- 탭 -->
      <div id="myBtnContainer">
        <button class="btn btn_filter btn--violet active" @click="init">Show all</button>
        <select @change="filterSelection2($event)" class="btn btn--violet btn_filter" id="division">
          <option value="0" selected>상태</option>
          <option value="A01">대여</option>
          <option value="A02">판매</option>
        </select>

        <select @change="filterSelection3($event)" class="btn btn--violet btn_filter" id="category">
          <option value="0" selected>카테고리</option>
          <option v-for="(name, value) in categorys" :key="value">
            {{ name }}
          </option>
        </select>

        <button class="btn btn--violet btn_filter" @click="filterSelection4">우리동네</button>

        <button type="button" class="btn btn_filter" @click="itemWrite" style="float: right">
          상품등록
        </button>
      </div>
      <div class="row">
        <div class="row row-cols-2 row-cols-md-4">
          <div v-if="contents.length === 0">등록된 상품이 없습니다.</div>
          <div class="col" v-for="(content, index) in lists" :key="index">
            <item-each :content="content"></item-each>
          </div>
        </div>
      </div>

      <!-- 페이지네이션 -->
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        style="justify-content: center"
      ></b-pagination>
    </div>
  </div>
</template>

<script>
import ItemEach from "@/components/item/ItemEach";
import {
  findItemListByPage,
  getItemList,
  getSearchItem,
  getSearchItemByDivision,
  getSearchItemByCategory,
  getSearchItemByBcode,
  getTotalPage,
} from "@/api/item.js";
import { allList } from "@/api/tap.js";

export default {
  components: {
    ItemEach,
  },
  data: () => ({
    keyword: "",
    contents: [
      // { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
    ],
    perPage: 8,
    currentPage: 1,
    prev: true,
    next: false,
    division: null,
    bcode: null,
    changeCategory: null,
    sortBy: "regDateTime",
    order: null,
    page: 0,
    size: 12,
    categorys: {
      B01: "신생아",
      B02: "유모차",
      B03: "카시트",
      B04: "발육",
      B05: "수유",
      B06: "이유식",
      B07: "소독/살균",
      B08: "스킨/바디",
      B09: "유아가구",
      B10: "목욕",
      B11: "구강청결",
      B12: "세제",
      B13: "안전",
      B14: "위생/건강",
      B15: "임산부",
      B16: "유아침구",
    },
  }),
  mounted() {
    var category = this.$route.query.category;
    console.log(category);
    if (category !== undefined) {
      this.filterSelection5(category);
    } else {
      this.init();
    }

    // this.findUser();
    this.getTotalPage();
  },
  computed: {
    rows() {
      console.log("computed : " + this.contents.length);
      return this.contents.length;
    },
    lists() {
      const items = this.contents;
      // Return just page of items needed
      return items.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
    },
  },
  updated() {
    console.log("업데이트", this.contents);
  },

  methods: {
    //초기 목록 불러오기, 전체 목록 불러오기
    init() {
      document.getElementById("division").value = "0";
      document.getElementById("category").value = "0";
      var vm = this;
      this.changeCategory = null;
      this.bcode = null;
      this.division = null;

      allList(function (res) {
        vm.contents = res.data;
        console.log("init", vm.contents);
      });
    },

    // 검색,
    fnSearch(arg) {
      getSearchItem(
        arg,
        this.bcode,
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log("필터 호출 함수", vm.contents);
        },
        function (error) {
          console.error("filter 에러 :" + error);
        }
      );
    },

    //판매유무
    filterSelection2(event) {
      var vm = this;
      this.division = event.target.value;
      if (this.division === "0") {
        this.division = null;
      }
      getSearchItemByDivision(
        this.division,
        this.changeCategory,
        this.bcode,
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log("필터 호출 함수", vm.contents);
        },
        function (error) {
          console.error("filter 에러 :" + error);
        }
      );
    },
    //카테고리
    filterSelection3(event) {
      var vm = this;
      var category = event.target.value;
      console.log(category);
      if (category === "0") {
        this.changeCategory = null;
      } else {
        this.changeCategory = Object.keys(vm.categorys).find(
          (key) => vm.categorys[key] === category
        );
      }

      console.log("카테고리 클릭시");
      console.log("상태 :" + this.division);
      console.log("카테고리 : " + this.changeCategory);
      getSearchItemByCategory(
        this.changeCategory,
        this.division,
        this.bcode,
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log("카테고리 함수", vm.contents);
        },
        function (error) {
          console.error("카테고리 에러 :" + error);
        }
      );
    },

    //우리동네 찾기
    filterSelection4() {
      var vm = this;
      console.log("우리동네찾기 호출합니다.");
      console.log(this.changeCategory);
      console.log(this.division);
      console.log(this.$store.state.user.bcode);
      getSearchItemByBcode(
        this.changeCategory,
        this.division,
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log("우리동네 : ", vm.bcode);

          vm.bcode = vm.$store.state.user.bcode;
          console.log("우리동네, state ", vm.bcode);
          console.log("우리동네 함수", vm.contents);
        },
        function (error) {
          console.error("우리동네 에러 :" + error);
        }
      );
    },
    filterSelection5(category) {
      var vm = this;
      console.log(category);
      this.changeCategory = Object.keys(vm.categorys).find((key) => vm.categorys[key] === category);
      console.log("카테고리 클릭시");
      console.log("카테고리 : " + this.changeCategory);
      getSearchItemByCategory(
        this.changeCategory,
        this.division,
        this.bcode,
        this.sortBy,
        this.order,
        this.page,
        this.size,
        function (res) {
          vm.contents = res.data;
          console.log("카테고리 함수", vm.contents);
        },
        function (error) {
          console.error("카테고리 에러 :" + error);
        }
      );
    },

    allList,
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
          console.error("검색에러 : " + error);
        }
      );
    },

    getTotalPage() {
      var vm = this;
      getTotalPage(
        function (success) {
          console.log("success", success);
          vm.getTotalPage = success.data;
          console.log("getTotalPage : ", vm.getTotalPage);
        },
        function (error) {
          console.log("전체페이지 불러오기 실패");
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        }
      );
    },

    //아이템 등록
    itemWrite() {
      this.$router.push({ name: "item.create" });
    },
  },
};
</script>

<style scoped>
div #footer {
  margin: 20px 0px;
}

* {
  box-sizing: border-box;
}

.body {
  background-color: #f1f1f1;
  padding: 20px;
  font-family: Arial;
  margin-bottom: 100px;
}

/* Center website */
.main {
  max-width: 1000px;
  margin: auto;
}

h1 {
  font-size: 50px;
  word-break: break-all;
}

.row {
  margin: 8px -16px;
}

.row--center {
  max-width: 100% !important;
}

/* Add padding BETWEEN each column (if you want) */
.row,
.row > .column {
  padding: 8px;
}

.row__title {
  font-size: 30px;
  margin-bottom: 20px !important;
}

.row__sub {
  font-size: 20px;
  margin-bottom: 10px !important;
}

/* Create three equal columns that floats next to each other */
.column {
  float: left;
  width: 33.33%;
  display: none; /* Hide columns by default */
}

/* Clear floats after rows */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Content */
.content {
  background-color: white;
  padding: 10px;
  margin: 10px;
}

/* The "show" class is added to the filtered elements */
.show {
  display: block;
}

.btn_filter {
  border-radius: 0px !important;
}

.btn--white {
  background-color: #fff !important;
  border: 1px solid #ededed;
  color: #8198ae !important;
}

.btn--white:hover {
  background-color: #fff !important;
  color: #8198ae !important;
}

.btn--violet {
  background-color: #6f79ff !important;
}
.btn--violet:hover {
  background-color: #878ef3 !important;
}

.form-group {
  text-align: center;
}

.input_search {
  width: 85%;
  float: left;
  margin-right: 10px;
}

.form__text {
  margin-left: 15px;
  border-radius: 20px;
}

.search_form {
  display: inline-block;
}

.btn_search {
  width: 120px;
}
</style>
