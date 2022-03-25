<template>
  <div>
    <h-breadcrumb
      title="상품 상세 보기"
      description="등록된 상품의 상세 내역을 볼 수 있습니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card">
            <div class="card-body">
              <div class="form-group">
                <h3>
                  <a href="">{{ item.category }}</a> >
                  {{ item.itemName }}
                </h3>
              </div>
              <img
                class="center"
                :src="getImg(item.files[item.keys[0]])"
                style="max-height: 500px"
              />
              <div class="form-group">
                <h4 class="alert alert-primary">{{ item.price }} CASH</h4>
              </div>
              <div class="form-group">
                <label id="user" class="text-secondary">판매자</label>
                <p>
                  {{ item.userNickname }}
                  <!-- ({{ item.seller.email }}) -->
                </p>
              </div>
              <div class="form-group">
                <label class="text-secondary">상품 등록일</label>
                <p>{{ item.regDate }}</p>
              </div>
              <div class="form-group">
                <label id="explanation" class="text-secondary">상품 설명</label>
                <p v-if="item.description.length > 0">{{ item.description }}</p>
                <p v-else>-</p>
              </div>
              <div class="form-group">
                <label id="state" class="text-secondary">상태</label><br />
                <!-- <p>{{ item.available ? "판매중" : "판매 종료" }}</p> -->
              </div>
              <!-- <div
                class="row"
                v-if="userId !== item.seller.id"
              >
                <div class="col-md-12 text-right">
                  <router-link
                    :to="{
                      name: 'item.purchase',
                      params: { id: item.id, seller: item.seller, image: item.image, name: item.name, price: item.price },
                    }"
                    class="btn btn-lg btn-primary"
                  >구매하기</router-link>
                </div>
              </div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getLocalImg } from "@/utils/imgLoader.js";
import { findById } from "@/api/item.js";
const vm = this;
export default {
  name: "ItemDetail",
  data() {
    return {
      item: {
        id: "",
        userId: "",
        userNickname: "",
        division: "",
        itemName: "",
        category: "",
        price: "",
        description: "",
        regDate: "",
        position: "",
        rentStartDate: "",
        rentEndDate: "",
        files: [],
        keys: [],
      },
      // userId: this.$store.state.user.id,
    };
  },
  methods: {
    getImg(name) {
      if (name) {
        return getLocalImg(name);
      }
      return null;
    },
  },
  created() {
    console.log("created");
    this.item.id = this.$route.params.id;
    console.log(this.item.id);
  },
  mounted: function () {
    const vm = this;
    // [DB] 상품 상세 정보 조회
    findById(
      this.item.id,
      function (res) {
        console.log(res);
        const result = res.data;
        vm.item.itemName = result.itemName;
        vm.item.category = result.category;
        vm.item.description = result.description ? result.description : "";
        vm.item.price = result.price;
        vm.item.userId = result.userId;
        vm.item.userNickname = result.userNickname;
        vm.item.regDate = result.regDate;
        vm.item.rentStartDate = result.rentStartDate
          ? result.rentStartDate
          : "";
        vm.item.rentEndDate = result.rentendDate ? result.rentEndDate : "";
        vm.item.files = result.files;
        vm.item.keys = Object.keys(vm.item.files);
        console.log(vm.item.keys);
      },
      function (error) {
        console.error(error);
        console.log("실패");
        alert("DB에서 상품 상세 정보 조회를 가져올 수 없습니다.");
      }
    );
  },
};
</script>

<style>
img.center {
  display: block;
  margin: 2rem auto;
}
</style>
