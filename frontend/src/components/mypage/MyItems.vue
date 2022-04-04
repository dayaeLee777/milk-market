<template>
  <div>
    <!-- <h-breadcrumb
    ></h-breadcrumb> -->
    <div class="container">
      <my-page-nav></my-page-nav>
      <div id="my-item" class="row">
        <h4 class="col-8 mt-5">판매 중인 My 아이템</h4>
        <div v-if="saleItems.length">
          <div 
            v-for="item in saleItems"
            :key="item.itemId"
            class="d-flex justify-content-start align-items-center mt-2 my-items"
            >
            <div>
              <div
                v-for="(imgName, idx) in item.files"
                :key="idx"
                class="d-flex">
                <img :src="imgName" alt="itemImg" @click="moveToDetail(item.itemId)" class="item-img">
              </div>  
            </div>
            <div class="ms-5">
              <h5>상품명: {{ item.itemName }}</h5>
              <div v-if="item.status === `C01`">
                <p class="escrow-state">판매 중</p>
              </div>
              <div v-if="item.status === `C02`">
                <p class="escrow-state">결제 완료</p>
              </div>
            </div>
            <div>
            </div>
          </div>
        </div>
        <div v-else>
          <h5>현재 판매중인 상품이 없어요!</h5>
        </div>
        <h4 class="col-12 mt-5">내가 판매한 거래</h4>
          <div v-if="saleTx.length">
            
          </div>
          <div v-else>
            <h5>아직 판매한 상품이 없어요!</h5>
          </div>
        <h4 class="col-8 mt-5">내가 구매한 거래</h4>
          <div v-if="purchaseTx.length">
            <div 
              v-for="item in purchaseTx"
              :key="item.itemId"
              class="d-flex justify-content-start align-items-center mt-2 my-items"
              >
              <div>
                <div
                  v-for="(imgName, idx) in item.files"
                  :key="idx"
                  class="d-flex">
                  <img :src="imgName" alt="itemImg" @click="moveToDetail(item.itemId)" class="item-img">
                </div>  
              </div>
              <div class="ms-5">
                <h5>상품명: {{ item.itemName }}</h5>
                <div v-if="item.status === `C02`">
                  <p class="escrow-state">결재 완료</p>
                  <div class="d-flex">
                    <button class="escrow-btn">구매 확정</button>
                    <button class="close-btn">구매 취소</button>
                  </div>
                </div>
              </div>
              <div>
              </div>
            </div>        
          </div>
          <div v-else>
            <h5>아직 구매한 상품이 없어요!</h5>
          </div>
        

      </div>
    </div>  
  </div>  
</template>

<script>
import { findMySaleItems, remove } from "@/api/item.js";
import {
  findMySalePurchases as findSaleTx,
  findMyPurchases as findPurchaseTx,
  checkDeposit
} from "@/api/purchase.js";
import MyPageNav from "./MyPageNav.vue";
import { ITEM_STATUS, ESCROW_STATE } from "@/config/constants.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { getPrice, deregisterItem } from "@/utils/itemInventory.js";
import axios from 'axios'
import { API_BASE_URL, BLOCKCHAIN_URL } from "@/config/index.js"
import Swal from 'sweetalert2/dist/sweetalert2.js'
import MyEscrow from './MyEscrow.vue';

export default {
  name: "MyItems",
  components: {
    MyPageNav,
    MyEscrow,
  },
  data() {
    return {
      eState: ESCROW_STATE,
      user: {
        id: this.$store.state.user.id,
        walletAddress: this.$store.state.user.walletAddress,
        token: this.$store.state.user.JWTToken
      },
      saleTx: [], // 내가 판매한 거래
      purchaseTx: [], // 내가 구매한 거래
      saleItems: [], // 판매 상품
      itemStatus: "",
    };
  },
  methods: {
    fetchMyProduct() {
      const token = this.user.token;
      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `${API_BASE_URL}/api/item/list/myItem`,
        method: 'get',
        headers,
      })
      .then( res => {
        console.log(res.data.list)
        this.saleItems = res.data.list

        
      })
      .catch( err => {
        console.log(err)
      })
    },
    fetchMyPurchase() {
      const token = this.user.token;
      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `${API_BASE_URL}/api/item/purchase/list`,
        method: 'get',
        headers,
      })
      .then( res => {
        console.log(res.data.list);
        this.purchaseTx = res.data.list;
      })
      .catch( err => {
        console.log(err)
      })
    },
    moveToDetail(itemId) {
      this.$router.push({name: 'item.detail', params: { id: itemId }})
    },

  },
  mounted() {
    this.fetchMyProduct();
    this.fetchMyPurchase();
    this.itemStatus = ITEM_STATUS;
  }
};
</script>

<style scoped>
.table td,
.table tr {
  text-align: center;
}
.item-img {
  height: 80px;
}
.my-items {
  height: 120px;
  width: 450px;
  background-color: beige;
  border-style: solid;
  border-color: black;
  border-width: 1px;
  padding: 10px;
  border-radius: 20px;
  /* 각각 x축, y축, blur, spread */
  box-shadow: 1.5px 1.5px 2px 1.5px gray;
}
.escrow-btn {
  font-size: 12px;
  color: aliceblue;
  background-color: #4a4879;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 25px;
  border-color: #45436d;
  border-style: solid;
  border-radius: 20px;
  margin-right: 3px;
}
.close-btn {
  font-size: 12px;
  color: aliceblue;
  background-color: #eb459f;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 25px;
  border-color: #7f4264;
  border-style: solid;
  border-radius: 20px;
  margin-right: 3px;
}
.escrow-state {
  font-size: 12px;
  color: aliceblue;
  background-color: #5865f2;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 25px;
  border-color: #5865f2;
  border-style: solid;
  border-radius: 20px;
}
/* .badge-primary {
  color: #fff;
  background-color: #007bff;
}
.badge-info {
  color: #fff;
  background-color: #17a2b8;
}
.btn-show-history {
  background-color: #5c130e;
  color: white;
}
hr {
  border: 0;
  clear: both;
  display: block;
  width: 96%;
  background-color: black;
  height: 2px;
} */
</style>
