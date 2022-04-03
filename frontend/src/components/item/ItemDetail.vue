<template>
  <div>   
    <div class="container mt-5">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card" id="item-detail"> 
            <div class="card-body">
              <div class="form-group">
                <h3>
                  <!--search 완성 되면 실행해보기-->
                  <a href="">{{ item.category }}</a> >
                  {{ item.itemName }}
                </h3>
              </div>
              <div
                v-for="imgName in item.keys"
                :key="imgName.id"
              >
                <img
                  class="center"
                  :src="getImg(item.files[imgName])"
                  id="item-detail-img"
                  style="max-width: 100%;"
                />
              </div>
              <!--v-for-->
              <div class="form-group">
                <h4 class="alert alert-primary">{{ item.price }} MILK</h4>
              </div>
              <div class="form-group">
                <label
                  id="user"
                  class="text-secondary"
                >판매자</label>
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
                <label
                  id="explanation"
                  class="text-secondary"
                >상품 설명</label>
                <p v-if="item.description.length > 0">{{ item.description }}</p>
                <p v-else>-</p>
              </div>

              <!-- 사용자와 판매자가 다르면 구매하기 버튼 생성! 대여 A01, 구매 A02-->
              <div
                class="d-flex justify-content-between"
                v-if="userId !== item.userId"
              >
                <div>
                  <button
                    @click="goChatting"
                    class="btn btn-lg btn-primary"
                  >채팅하기</button>
                </div>
                <div v-if="item.division === 'A01'">
                  <button class="btn btn-lg btn-primary"
                     data-bs-toggle="modal" data-bs-target="#rentModal">
                     대여하기
                     </button>
                </div>
                <div v-else>
                  <button class="btn btn-lg btn-primary" 
                    data-bs-toggle="modal" data-bs-target="#purchaseModal">
                    구매하기
                    </button>
                </div>
                <div class="modal fade" id="rentModal" tabindex="-1" aria-labelledby="rentModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="rentModalLabel">Modal title</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        ...
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="modal fade" id="purchaseModal" tabindex="-1" aria-labelledby="purchaseModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="purchaseModalLabel">Modal title</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        ...
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Code } from "@/utils/enum.js";
import { findById } from "@/api/item.js";
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js";

const vm = this;
export default {
  name: "ItemDetail",
  data () {
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
      userId: this.$store.state.user.id,
      walletAddress: this.$store.state.walletAddress,
    };
  },
  methods: {
    getImg (name) {
      if (name) {
        return name;
      }
      return null;
    },
    doPay() {
      
    },
    goChatting () {
      console.log("채팅방으로 가는 버튼을 눌럿음")
      const A = this.item.userNickname > this.$store.state.user.userNickname ? this.$store.state.user.userNickname : this.item.userNickname;
      const B = this.item.userNickname > this.$store.state.user.userNickname ? this.item.userNickname : this.$store.state.user.userNickname;
      this.$router.push({ name: "room", params: { userNickname: this.item.userNickname, sessionId: A + '1' + B } });
    },
  },
  created () {
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
        console.log(res.data);
        const result = res.data;
        vm.item.itemName = result.itemName;
        vm.item.category = result.category;
        //console.log("res: " + Code[vm.item.category]);
        vm.item.category = Code[vm.item.category];
        vm.item.description = result.description ? result.description : "";
        vm.item.price = result.price;
        vm.item.userId = result.userId;
        vm.item.userNickname = result.userNickname;
        vm.item.division = result.division;
        vm.item.regDate = result.regDate;
        vm.item.rentStartDate = result.rentStartDate
          ? result.rentStartDate
          : "";
        vm.item.rentEndDate = result.rentendDate ? result.rentEndDate : "";
        vm.item.files = result.files;
        vm.item.keys = Object.keys(vm.item.files);
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
#item-detail-img {
  height: 200px;
}
</style>
