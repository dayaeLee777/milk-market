<template>
  <div>
    <!-- <h-breadcrumb
    ></h-breadcrumb> -->
    <div class="container">
      <my-page-nav></my-page-nav>
      <div class="my-items mt-5">
        <div class="item-body">
          <h4 class="mt-5 item-title">판매 중인 상품</h4>
          <div v-if="saleItems.length">
            <div class="caros-box">
              <carousel-3d :width="400" :height="280" :controls-visible="true">
                <slide v-for="(item, i) in saleItems" :index="i" :key="i">
                  <my-item-slide
                    :slide="item"
                    :contract="contract"
                    :coinbase="coinbaseAddress"
                    :walletAddress="user.walletAddress"
                    @end-escrow="receiveMilk"
                  />
                </slide>
              </carousel-3d>
            </div>
          </div>
          <div v-else>
            <h5 class="text-center">현재 판매중인 상품이 없어요!</h5>
          </div>
        </div>
        <div class="item-body">
          <h4 class="mt-5 item-title">내가 구매중인 상품</h4>
          <div v-if="purchaseTx.length">
            <div class="caros-box">
              <carousel-3d :width="400" :height="280" :controls-visible="true">
                <slide v-for="(item, i) in purchaseTx" :index="i" :key="i">
                  <my-purchase-slide
                    :slide="item"
                    :contract="contract"
                    :coinbase="coinbaseAddress"
                    :walletAddress="user.walletAddress"
                    @confirm-purchase="confirmPurchase"
                  />
                  <!-- @받을땐 케밥케이스, 실행하는 메서드는 카멜케이스 -->
                </slide>
              </carousel-3d>
            </div>
          </div>
          <div v-else>
            <h5 class="text-center">아직 구매한 상품이 없어요!</h5>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MyPageNav from "./MyPageNav.vue";
import axios from "axios";
import Swal from "sweetalert2/dist/sweetalert2.js";
import MilkToken from "@/config/contract/MilkToken.json";
import MyItemSlide from "./MyItemSlide.vue";
import MyPurchaseSlide from "./MyPurchaseSlide.vue";
import { Carousel3d, Slide } from "vue-carousel-3d";
import { ITEM_STATUS, ESCROW_STATE } from "@/config/constants.js";
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js";

export default {
  name: "MyItems",
  components: {
    MyPageNav,
    MyItemSlide,
    MyPurchaseSlide,
    Carousel3d,
    Slide,
  },
  data() {
    return {
      eState: ESCROW_STATE,
      user: {
        id: this.$store.state.user.id,
        walletAddress: this.$store.state.user.walletAddress,
        token: this.$store.state.user.JWTToken,
      },
      saleTx: [], // 내가 판매한 거래
      purchaseTx: [], // 내가 구매한 거래
      saleItems: [], // 판매 상품
      itemStatus: "",
      contract: "",
      coinbaseAddress: "",
    };
  },
  methods: {
    receiveMilk(itemId, price) {
      this.refund(price);
      this.deleteItem(itemId);
    },
    deleteItem(itemId) {
      const token = this.user.token;

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/delete/${itemId}`,
        method: "put",
        headers,
      })
        .then((res) => {
          console.log(res);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "수령 완료!",
            showConfirmButton: false,
            timer: 1500,
          });
          setTimeout(() => {
            this.$router.go();
          }, 1500);
        })
        .catch((err) => {
          console.log(err);
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
    confirmPurchase(itemId) {
      const token = this.user.token;

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/purchase/confirm/${itemId}`,
        method: "post",
        headers,
      })
        .then((res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "구매 확정 완료!",
            showConfirmButton: false,
            timer: 1500,
          });
          setTimeout(() => {
            this.$router.go();
          }, 1500);
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
    fetchMyProduct() {
      const token = this.user.token;

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/list/myItem`,
        method: "get",
        headers,
      })
        .then((res) => {
          console.log(res.data.list);
          this.saleItems = res.data.list;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchMyPurchase() {
      const token = this.user.token;
      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/purchase/list`,
        method: "get",
        headers,
      })
        .then((res) => {
          console.log(res.data.list);
          this.purchaseTx = res.data.list;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    moveToDetail(itemId) {
      this.$router.push({ name: "item.detail", params: { id: itemId } });
    },
    async refund(price) {
      const Web3 = require("web3");
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const from = this.coinbaseAddress;
      const to = this.user.walletAddress;
      const amount = price * 10 ** 15;
      const amountToBn = web3.utils.toBN(`${amount}`);

      const approve = await this.contract.methods.approve(from, amountToBn).send({ from: from });
      const transfer = await this.contract.methods
        .transferFrom(from, to, amountToBn)
        .send({ from: from });
    },
    cancelPurchase(price, itemId) {
      const token = this.user.token;
      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/cancel/${itemId}`,
        method: "delete",
        headers,
      })
        .then((res) => {
          this.refund(price);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "구매 취소 완료!",
            showConfirmButton: false,
            timer: 1500,
          });
          setTimeout(() => {
            this.$router.go();
          }, 1500);
        })
        .catch((err) => {
          Swal.fire({
            position: "center",
            icon: "fail",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
  },
  mounted() {
    this.makeContract();
    this.fetchMyProduct();
    this.fetchMyPurchase();
    this.itemStatus = ITEM_STATUS;
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
@import url("https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

.caros-box {
  height: 32vh;
}
.item-body {
  height: 400px;
  text-align: center;
}
.item-title {
  color: rgb(48, 47, 50);
  display: inline-block;
  /* justify-content: center; */
  /* align-items: center; */
  width: 240px;
  height: 38px;
  padding-top: 5px;
  text-align: center;

  background-color: #7975f0;
  border-radius: 20px;
  box-shadow: 1.2px 1.2px 2px 2px rgba(53, 52, 52, 0.844);
}
.table td,
.table tr {
  text-align: center;
}
.item-img {
  height: 80px;
  cursor: pointer;
}
.my-items {
  font-family: "Black Han Sans", sans-serif;
  position: relative;
}

.modal .overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.4;
  background-color: black;
}
.modal-card {
  position: relative;
  margin: auto;
  margin-top: 30px;
  padding: 20px;
  background-color: white;
  z-index: 10;
  opacity: 1;
}
</style>
