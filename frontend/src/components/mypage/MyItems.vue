<template>
  <div>
    <h-breadcrumb
    ></h-breadcrumb>
    <div class="container">
      <my-page-nav></my-page-nav>
      <div id="my-item" class="row">
        <h4 class="col-8 mt-5">판매 중인 My 아이템</h4>
        <div 
          v-for="item in saleItems"
          :key="item.itemId"
          class="d-flex justify-content-start mt-3"
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
            <p>상품가격: {{ item.price }}MILK</p>
            <button class="btn btn-secondary">하하</button>
          </div>
          <div>
          </div>
        </div>
        <h4 class="col-12 mt-5">내가 판매한 거래</h4>
        


        <h4 class="col-8 mt-5">내가 구매한 거래</h4>
        

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
import { IITEM_STATUS, ESCROW_STATE } from "@/config/constants.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { getPrice, deregisterItem } from "@/utils/itemInventory.js";
import axios from 'axios'
import { API_BASE_URL, BLOCKCHAIN_URL } from "@/config/index.js"

export default {
  name: "MyItems",
  components: {
    MyPageNav
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
      saleItems: [] // 판매 상품
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
        // console.log(res.data.list)
        this.saleItems = res.data.list

        
      })
      .catch( err => {
        console.log(err)
      })
    },
    moveToDetail(itemId) {
      this.$router.push({name: 'item.detail', params: { id: itemId }})
    }

  },
  mounted() {
    this.fetchMyProduct();
  }
};
</script>

<style scoped>
.table td,
.table tr {
  text-align: center;
}
.item-img {
  height: 200px;
  width: 250px;
  cursor: pointer;
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
