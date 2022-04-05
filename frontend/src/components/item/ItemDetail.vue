<template>
  <div>
    <div class="container mt-5">
      <div class="row">
        <div class="col-md-8 mx-auto">
<<<<<<< HEAD
          <div class="card detail-box" id="item-detail"> 
=======
          <div class="card" id="item-detail">
>>>>>>> feature/fe-category-click
            <div class="card-body">
              <div class="form-group">
                <h5>
                  <!--search 완성 되면 실행해보기-->
                  <router-link
                    :to="{
                      name: 'shop',
                      query: { category: `${item.category}` },
                    }"
                    >{{ item.category }}</router-link
                  >
                  >
                  {{ item.itemName }}
                </h5>
              </div>
              <div v-for="imgName in item.keys" :key="imgName.id">
                <img
                  class="center"
                  :src="getImg(item.files[imgName])"
                  id="item-detail-img"
                  style="max-width: 100%"
                />
              </div>
              <!--v-for-->
              <div class="form-group">
                <h4 class="alert alert-primary">{{ item.price }} MILK</h4>
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

              <!-- 사용자와 판매자가 다르면 구매하기 버튼 생성! 대여 A01, 구매 A02-->
              <div
                class="d-flex justify-content-between"
                v-if="userId !== item.userId"
              >
                <div>
<<<<<<< HEAD
                  <button
                    @click="goChatting"
                    class="btn btn-sm btn-primary"
                  >채팅하기</button>
                </div>
                <div v-if="item.division === 'A01'">
                  <button class="btn btn-sm btn-primary" @click="registInterest">관심상품 등록</button>
                  <button class="btn btn-sm btn-primary"
                     data-bs-toggle="modal" data-bs-target="#rentModal">
                     대여하기
                     </button>
                </div>
                <div v-else>
                  <button class="btn btn-sm btn-primary" @click="registInterest">관심상품 등록</button>
                  <button class="btn btn-sm  btn-primary"
                    @click="checkMilk()" 
                    data-bs-toggle="modal" data-bs-target="#purchaseModal">
=======
                  <button @click="goChatting" class="btn btn-lg btn-primary">
                    채팅하기
                  </button>
                </div>
                <div v-if="item.division === 'A01'">
                  <button
                    class="btn btn-lg btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#rentModal"
                  >
                    대여하기
                  </button>
                </div>
                <div v-else>
                  <button
                    class="btn btn-lg btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#purchaseModal"
                  >
>>>>>>> feature/fe-category-click
                    구매하기
                  </button>
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
                        <h5 class="modal-title" id="rentModalLabel">
                          Modal title
                        </h5>
                        <button
                          type="button"
                          class="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        ></button>
                      </div>
                      <div class="modal-body">...</div>
                      <div class="modal-footer">
                        <button
                          type="button"
                          class="btn btn-secondary"
                          data-bs-dismiss="modal"
                        >
                          Close
                        </button>
                        <button type="button" class="btn btn-primary">
                          Save changes
                        </button>
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
<<<<<<< HEAD
                        <h5 class="modal-title" id="purchaseModalLabel">Modal title</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <div class="d-flex">
                          <div class="text-primary">현재 잔액: {{ milkBalance }}MILK</div>
                        </div>
                        <div v-if="(milkBalance - item.price) >= 0">
                          <div></div>
                          <div class="mt-2">현재 상품 가격: {{ item.price }}MILK</div>
                          <div class="mt-2 fw-bold">구매 후 잔액: {{ milkBalance - item.price }}MILK</div>
                        </div>
                        <div v-else>
                          <p calss="text-danger">MILK 잔액이 부족합니다!</p>
                          <button class="btn btn-secondary btn-sm ms-2" 
                            data-bs-dismiss="modal"
                            @click="moveToWallet">충전하기!
                          </button>
                        </div>
=======
                        <h5 class="modal-title" id="purchaseModalLabel">
                          Modal title
                        </h5>
                        <button
                          type="button"
                          class="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        ></button>
>>>>>>> feature/fe-category-click
                      </div>
                      <div class="modal-body">...</div>
                      <div class="modal-footer">
<<<<<<< HEAD
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="doPay">결재하기</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
=======
                        <button
                          type="button"
                          class="btn btn-secondary"
                          data-bs-dismiss="modal"
                        >
                          Close
                        </button>
                        <button type="button" class="btn btn-primary">
                          Save changes
                        </button>
>>>>>>> feature/fe-category-click
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
<<<<<<< HEAD
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js";
import axios from "axios";
import MilkToken from "@/config/contract/MilkToken.json";
import Swal from 'sweetalert2/dist/sweetalert2.js';
=======
import {
  API_BASE_URL,
  BLOCKCHAIN_URL,
  CASH_CONTRACT_ADDRESS,
} from "@/config/index.js";
>>>>>>> feature/fe-category-click

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
      userId: this.$store.state.user.id,
      contract: "",
      coinbaseAddress: "",
      milkBalance: 0,
    };
  },
  methods: {
<<<<<<< HEAD
    makeContract() {   
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));   

      let contract =  new web3.eth.Contract(MilkToken.abi, CASH_CONTRACT_ADDRESS);
      this.contract = contract

      web3.eth.getAccounts().then(res => {
        console.log(res)
        this.coinbaseAddress = res[0]
      });
    },    
    async checkMilk() {
      const milk = await this.contract.methods.balanceOf(this.$store.state.user.walletAddress).call();

      this.milkBalance = (milk / 10**15);
    },    
    moveToWallet() {
      this.$router.push("/mypage/wallet_info")
    },
    getImg (name) {
=======
    getImg(name) {
>>>>>>> feature/fe-category-click
      if (name) {
        return name;
      }
      return null;
    },
<<<<<<< HEAD
    registInterest() {
      const token = this.$store.state.user.JWTToken;
      
      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `${API_BASE_URL}/api/interest/${this.item.id}`,
        method: 'post',
        headers,
      })
      .then( res => {
        console.log(res)
      })
      .catch( err => {
        console.log(err)
      })
    },
    purchase() {
      const token = this.$store.state.user.JWTToken;
      
      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url:`${API_BASE_URL}/api/item/purchase/${this.item.id}`,
        method: 'post',
        headers,
      })
      .then(res => {
        console.log(res)
        console.log("성공!")
      })
      .catch(err => {
        console.log(err)
      })

    },
    // 코인 베이스로 이동
    async doPay() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const from = this.$store.state.user.walletAddress;
      const to = this.coinbaseAddress;
      const amount = this.item.price * (10**15);
      const amountToBn = web3.utils.toBN(`${amount}`)
      const approve = await this.contract.methods.approve(from, amountToBn).send({from: from});
      const transfer = await this.contract.methods.transferFrom(from, to, amountToBn).send({from: from});

      this.purchase();
      this.$router.push({ name: "mypage.items" })
    },
    goChatting () {
      console.log("채팅방으로 가는 버튼을 눌럿음")
      const A = this.item.userNickname > this.$store.state.user.userNickname ? this.$store.state.user.userNickname : this.item.userNickname;
      const B = this.item.userNickname > this.$store.state.user.userNickname ? this.item.userNickname : this.$store.state.user.userNickname;
      this.$router.push({ name: "room", params: { userNickname: this.item.userNickname, sessionId: A + '1' + B } });
=======
    doPay() {},
    goChatting() {
      console.log("채팅방으로 가는 버튼을 눌럿음");
      const A =
        this.item.userNickname > this.$store.state.user.userNickname
          ? this.$store.state.user.userNickname
          : this.item.userNickname;
      const B =
        this.item.userNickname > this.$store.state.user.userNickname
          ? this.item.userNickname
          : this.$store.state.user.userNickname;
      this.$router.push({
        name: "room",
        params: {
          userNickname: this.item.userNickname,
          sessionId: A + "1" + B,
        },
      });
>>>>>>> feature/fe-category-click
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
    this.makeContract();
  },
};
</script>

<style>
img.center {
  display: block;
  margin: 2rem auto;
}
.detail-box {
  height: 90vh;
}
#item-detail-img {
  height: 200px;
}
</style>
