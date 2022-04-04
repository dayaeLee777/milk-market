<template>
  <div id="main-overview" class="container">
    <!-- 검색 -->

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

    <div class="body">
      <!-- 탭 -->
      <div id="myBtnContainer">
        <button class="btn active" @click="init">Show all</button>
        <select @change="filterSelection2($event)" class="btn">
          <option value="0" selected>상태</option>
          <option value="A01">대여</option>
          <option value="A02">판매</option>
        </select>

        <select @change="filterSelection3($event)" class="btn">
          <option value="0" selected>카테고리</option>
          <option v-for="(name, value) in categorys" :key="value">
            {{ name }}
          </option>
        </select>

        <button class="btn" @click="filterSelection4">우리동네</button>
      </div>
      <div class="row">
        <!-- <div class="column nature">
        <div class="content">
          <img
            src="https://3.bp.blogspot.com/-ZKBbW7TmQD4/U6P_DTbE2MI/AAAAAAAADjg/wdhBRyLv5e8/s1600/noimg.gif"
            alt="Mountains"
            style="width: 100%"
          />
          <h4>Mountains</h4>
          <p>Lorem ipsum dolor..</p>
        </div>
      </div> -->
        <div class="row row-cols-2 row-cols-md-4 g-5">
          <div v-if="contents.length === 0">등록된 상품이 없습니다.</div>
          <div class="col" v-for="(content, index) in contents" :key="index">
            <item-each :content="content"></item-each>
          </div>
        </div>
        <!-- <div class="column" v-for="(content, index) in contents" :key="index">
        <item-each :content="content"></item-each>
      </div> -->
      </div>

      <!-- 페이지네이션 -->
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="my-table"
      ></b-pagination>
    </div>

    <!--상품 등록-->
    <div class="row">
      <div id="footer">
        <button
          type="button"
          class="btn btn-primary"
          @click="itemWrite"
          style="float: right"
        >
          상품등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import ItemEach from "@/components/item/ItemEach";
import { findUser } from "@/api/user.js";
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

var btnContainer = document.getElementById("myBtnContainer");
var btns = document.getElementsByClassName("content");

console.log(btns);
export default {
  components: {
    ItemEach,
  },
  data: () => ({
    keyword: "",
    contents: [
      // { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
    ],
    perPage: 12,
    currentPage: 1,
    prev: true,
    next: false,
    division: null,
    bcode: null,
    changeCategory: null,
    sortBy: "regDate",
    order: "ASC",
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
    this.init();
    // this.findUser();
    this.getTotalPage();
    console.log("마운트", this.contents);
    console.log("categorys", this.categorys);
    console.log("BackUp 콘솔");
  },
  computed: {
    rows() {
      console.log("computed : " + this.contents.length);
      return this.contents.length;
    },
  },
  updated() {
    console.log("업데이트", this.contents);
  },

  methods: {
    //초기 목록 불러오기, 전체 목록 불러오기
    init() {
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
      // this.$router.push({
      //   name: "item.write",
      // });
      this.$router.push({ name: "item.create" });
    },
    // findUser() {
    //   var vm = this;
    //   findUser(
    //     function (res) {
    //       console.log("findUser" + res.data.bcode);
    //       vm.$store.commit("setBcode", res.data.bcode);
    //     },
    //     function (error) {
    //       console.log("findUser Error");
    //       console.error(error);
    //     }
    //   );
    // },
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

/* Add padding BETWEEN each column (if you want) */
.row,
.row > .column {
  padding: 8px;
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
}

/* The "show" class is added to the filtered elements */
.show {
  display: block;
}
</style>