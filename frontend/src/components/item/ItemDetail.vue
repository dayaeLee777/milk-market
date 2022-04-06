<template>
  <div class="container" style="margin-top: 80px">
    <div class="col-lg-8 border p-3 main-section bg-white">
      <div class="row m-0">
        <div class="col-lg-4 left-side-product-box pb-3">
          <img
            :src="getImg(item.files[item.keys[0]])"
            id="item-detail-img"
            style="max-width: 100%"
            class="border p-3"
          />
          <span class="sub-img" v-if="item.keys.length > 1">
            <img
              v-for="imgName in item.keys.slice(1)"
              :key="imgName"
              :src="getImg(item.files[imgName])"
              class="border p-2"
            />
          </span>
        </div>
        <div class="col-lg-8">
          <div class="right-side-pro-detail border p-3 m-0">
            <div class="row">
              <div class="col-lg-12">
                <span>Title</span>
                <p class="m-0 p-0">{{ item.itemName }}</p>
              </div>
              <div class="col-lg-12">
                <span class="m-0 p-0 price-pro">MILK</span>
                <p class="m-0 p-0">{{ item.price }}</p>
                <hr class="p-0 m-0" />
              </div>
              <div class="col-lg-12 pt-2">
                <span>Product Detail</span>
                <span>
                  <p v-if="item.description.length > 0">
                    {{ item.description }}
                  </p>
                  <p v-else>-</p>
                </span>
                <hr class="m-0 pt-2 mt-2" />
              </div>
              <div class="col-lg-12">
                <p class="tag-section">
                  <span>Category : </span>
                  <router-link
                    :to="{
                      name: 'shop',
                      query: { category: `${item.category}` },
                    }"
                    ><p>{{ item.category }}</p></router-link
                  >
                </p>
              </div>
              <div class="col-lg-12">
                <span>판매자 :</span>
                <p>
                  {{ item.userNickname }}
                </p>
              </div>
              <div class="col-lg-12 mt-3" v-if="userId !== item.userId">
                <div class="row">
                  <div class="pb-2">
                    <button @click="goChatting" class="btn btn-sm btn-primary">채팅하기</button>

                    <span v-if="item.division === 'A01'">
                      <button class="btn btn-sm btn-primary" @click="registInterest">
                        관심상품 등록
                      </button>
                      <button
                        class="btn btn-sm btn-primary"
                        data-bs-toggle="modal"
                        data-bs-target="#rentModal"
                      >
                        대여하기
                      </button>
                    </span>
                    <span v-else>
                      <button class="btn btn-sm btn-primary" @click="registInterest">
                        관심상품 등록
                      </button>
                      <button
                        class="btn btn-sm btn-primary"
                        @click="checkMilk()"
                        data-bs-toggle="modal"
                        data-bs-target="#purchaseModal"
                      >
                        구매하기
                      </button>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class=".right-side-pro-detail col-lg-12 text-center pt-3">
          <span>More Product</span>
        </div>
      </div>
      <div class="row mt-3 p-0 text-center pro-box-section">
        <div class="col-lg-3 pb-2" v-for="(sitem, index) in suggest" :key="index">
          <div class="pro-box border p-0 m-0" @click="itemDetail(sitem.id)">
            <img :src="sitem.files[sitem.keys[0]]" />
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      id="rentModal"
      tabindex="-1"
      aria-labelledby="rentModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="rentModalLabel">결제</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div class="d-flex">
              <div class="text-primary">현재 잔액: {{ milkBalance }}MILK</div>
            </div>
            <div v-if="milkBalance - item.price >= 0">
              <div></div>
              <div class="mt-2">현재 상품 가격: {{ item.price }}MILK</div>
              <div class="mt-2 fw-bold">구매 후 잔액: {{ milkBalance - item.price }}MILK</div>
            </div>
            <div v-else>
              <p calss="text-danger">MILK 잔액이 부족합니다!</p>
              <button
                class="btn btn-secondary btn-sm ms-2"
                data-bs-dismiss="modal"
                @click="moveToWallet"
              >
                충전하기!
              </button>
            </div>
          </div>
          <div class="modal-body"></div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="doPay">
              결제하기
            </button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      id="purchaseModal"
      tabindex="-1"
      aria-labelledby="purchaseModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="purchaseModalLabel">결제</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div class="d-flex">
              <div class="text-primary">현재 잔액: {{ milkBalance }}MILK</div>
            </div>
            <div v-if="milkBalance - item.price >= 0">
              <div></div>
              <div class="mt-2">현재 상품 가격: {{ item.price }}MILK</div>
              <div class="mt-2 fw-bold">구매 후 잔액: {{ milkBalance - item.price }}MILK</div>
            </div>
            <div v-else>
              <p calss="text-danger">MILK 잔액이 부족합니다!</p>
              <button
                class="btn btn-secondary btn-sm ms-2"
                data-bs-dismiss="modal"
                @click="moveToWallet"
              >
                충전하기!
              </button>
            </div>
          </div>
          <div class="modal-body"></div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="doPay">
              결제하기
            </button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import firebase from "firebase";
import { Code } from "@/utils/enum.js";
import { findById, getSearchItemByCategory } from "@/api/item.js";
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js";
import axios from "axios";
import Swal from "sweetalert2/dist/sweetalert2.js";
import MilkToken from "@/config/contract/MilkToken.json";
import { mapState } from "vuex";
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
        profileImage: "",
        description: "",
        regDate: "",
        position: "",
        rentStartDate: "",
        rentEndDate: "",
        files: [],
        keys: [],
      },
      userId: this.$store.state.user.id,
      suggest: [],
      contract: "",
      coinbaseAddress: "",
      milkBalance: 0,
    };
  },
  methods: {
    itemDetail(itemId) {
      console.log("이동 : ");
      console.log("sitem.id : " + itemId);
      this.$router
        .push({
          // name: "item.detail",
          name: "item.detail",
          params: { id: itemId },
        })
        .catch(() => {});
    },
    makeContract() {
      const Web3 = require("web3");
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));

      let contract = new web3.eth.Contract(MilkToken.abi, CASH_CONTRACT_ADDRESS);
      this.contract = contract;

      web3.eth.getAccounts().then((res) => {
        console.log(res);
        this.coinbaseAddress = res[0];
      });
    },
    async checkMilk() {
      const milk = await this.contract.methods
        .balanceOf(this.$store.state.user.walletAddress)
        .call();

      this.milkBalance = milk / 10 ** 15;
    },
    moveToWallet() {
      this.$router.push("/mypage/wallet_info");
    },
    getImg(name) {
      if (name) {
        return name;
      }
      return null;
    },
    registInterest() {
      const token = this.$store.state.user.JWTToken;

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/interest/${this.item.id}`,
        method: "post",
        headers,
      })
        .then((res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "관심상품 등록 성공",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.push({ name: "shop" });
        })
        .catch((err) => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
    purchase() {
      const token = this.$store.state.user.JWTToken;

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/purchase/${this.item.id}`,
        method: "post",
        headers,
      })
        .then((res) => {
          console.log(res);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "구매 성공",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.push({ name: "shop" });
        })
        .catch((err) => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
    // 코인 베이스로 이동
    async doPay() {
      const Web3 = require("web3");
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const from = this.$store.state.user.walletAddress;
      const to = this.coinbaseAddress;
      const amount = this.item.price * 10 ** 15;
      const amountToBn = web3.utils.toBN(`${amount}`);
      const approve = await this.contract.methods.approve(from, amountToBn).send({ from: from });
      const transfer = await this.contract.methods
        .transferFrom(from, to, amountToBn)
        .send({ from: from });

      this.purchase();
      this.$router.push({ name: "shop" });
    },
    goChatting() {
      const A =
        this.item.userNickname > this.$store.state.user.userNickname
          ? this.$store.state.user.userNickname
          : this.item.userNickname;
      const B =
        this.item.userNickname > this.$store.state.user.userNickname
          ? this.item.userNickname
          : this.$store.state.user.userNickname;

      db.collection("user")
        .doc(this.item.userNickname)
        .update({
          chatRooms: firebase.firestore.FieldValue.arrayUnion({
            notification: true,
            itemName: this.item.itemName,
            userNickname: this.user.userNickname,
            sessionId: A + "1" + B,
            profileImage: this.user.profileImage,
          }),
        })
        .then(() => {
          console.log("sucess");
        });

      this.$router.push({
        name: "room",
        params: {
          sessionId: A + "1" + B,
          profileImage: this.item.profileImage,
        },
      });
    },
  },
  computed: {
    ...mapState(["user"]),
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
        console.log(res.data);
        const result = res.data;
        vm.item.itemName = result.itemName;
        vm.item.category = result.category;
        //console.log("res: " + Code[vm.item.category]);
        vm.item.category = Code[vm.item.category];
        vm.item.description = result.description ? result.description : "";
        vm.item.price = result.price;
        vm.item.profileImage = result.profileImage;
        vm.item.userId = result.userId;
        vm.item.userNickname = result.userNickname;
        vm.item.division = result.division;
        vm.item.regDate = result.regDate;
        vm.item.rentStartDate = result.rentStartDate ? result.rentStartDate : "";
        vm.item.rentEndDate = result.rentendDate ? result.rentEndDate : "";
        vm.item.files = result.files;
        vm.item.keys = Object.keys(vm.item.files);
        console.log(vm.item.keys);
        return result;
      },
      (result) => {
        console.log("추천상품 data", result);
        getSearchItemByCategory(
          result.category,
          result.division,
          result.bcode,
          "regDateTime",
          null,
          null,
          4,
          function (success) {
            console.log("success", success);
            var result = success.data; //배열
            console.log(result);
            console.log(result[0].id);
            result.forEach((value, index, array) => {
              var object = new Object();
              object.id = value.id;
              object.files = value.fileNameList;
              object.keys = Object.keys(object.files);
              vm.suggest.push(object);
              console.log("suggest[]: " + index, vm.suggest);
              console.log("object:", object);
              //   console.log(value.id);
              //   console.log(value.fileNameList);
              //   console.log(Object.keys(value.fileNameList));
              //   vm.suggest[index].id = value.id;
              //   console.log("vm.suggest[]" + index, vm.suggest[index].id);
              //   vm.suggest[index].files = value.fileNameList;
              //   vm.suggest[index].keys = Object.keys(vm.suggest[index].files);
            });

            console.log("추천", vm.suggest);
          },
          function (fail) {
            console.log("추천상품 오류 : ", fail);
          }
        );
      },
      function (error) {
        console.error(error);
        console.log("실패");
        alert("DB에서 상품 상세 정보 조회를 가져올 수 없습니다.");
      }
    );
    this.makeContract();
  },
};
</script>

<style scoped>
body {
  font-family: "Roboto Condensed", sans-serif;
  /* font-family: "Black Han Sans", sans-serif; */
  /* font-family: "Noto Sans KR", sans-serif; */
  background-color: #f5f5f5;
}
button .chatting {
  float: left;
  margin-right: 0px;
}
.hedding {
  font-size: 20px;
  color: #ab8181;
}

.main-section {
  position: absolute;
  left: 50%;
  right: 50%;
  transform: translate(-50%, 5%);
}

.left-side-product-box img {
  width: 100%;
}

.left-side-product-box .sub-img img {
  margin-top: 5px;
  width: 83px;
  height: 100px;
}

.right-side-pro-detail span {
  font-family: "Black Han Sans", sans-serif;
  font-size: 15px;
}

.right-side-pro-detail p {
  font-size: 25px;
  color: #070707;
}

.right-side-pro-detail .price-pro {
  color: #e45641;
}

.right-side-pro-detail .tag-section {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 18px;
  color: #5d4c46;
}

.pro-box-section .pro-box img {
  width: 100%;
  height: 200px;
}

@media (min-width: 360px) and (max-width: 640px) {
  .pro-box-section .pro-box img {
    height: auto;
  }
}
</style>
