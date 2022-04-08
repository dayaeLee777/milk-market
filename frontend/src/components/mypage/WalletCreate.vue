<template>
  <div>
    <!-- <h-breadcrumb
    ></h-breadcrumb> -->
    <div class="container">
      <my-page-nav></my-page-nav>
      <div id="create-wallet-form" class="row">
        <div class="col-7 mt-5">
          <div class="card">
            <div class="card-body">
              <div v-if="walletAddress">
                <div class="alert alert-warning" role="alert">
                  <strong>경고!</strong>
                  <p>
                    1. 지갑 비밀키를 잃어버리지 마세요! 한 번 잃어버리면 복구 할
                    수 없습니다.<br />
                    2. 공유하지 마세요! 비밀키가 악위적인 사이트에 노출되면
                    당신의 자산이 유실될 수 있습니다.<br />
                    3. 백업을 만들어 두세요! 종이에 적어서 오프라인으로
                    관리하세요.
                  </p>
                </div>
                <hr />
                <div class="form-group">
                  <label><b>지갑 비밀키를 저장하세요!</b></label>
                  <p>{{ privateKey }}</p>
                </div>
                <hr />
                <div class="form-group">
                  <label><b>지갑 주소</b></label>
                  <p>{{ walletAddress }}</p>
                </div>
                <hr />
                <a href="#" v-on:click="saveWallet" class="btn btn-primary"
                  >지갑 정보를 저장하겠습니다.</a
                >
              </div>
              <div v-else>
                <h5 class="card-title">
                  아직 지갑이 없네요! 지갑을 생성하세요.
                </h5>
                <p>비밀번호 입력</p>
                <input type="password" class="form-control" v-model="pw">
                <button
                  @click="createWallet"
                  class="btn btn-lg btn-outline-primary mt-3"
                  >지갑생성하기</button
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Web3 from "web3";
import axios from 'axios'
import MyPageNav from "./MyPageNav.vue";
import MilkToken from "@/config/contract/MilkToken.json"
import { BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS, API_BASE_URL } from "@/config/index.js";
import Swal from 'sweetalert2/dist/sweetalert2.js'

export default {
  components: {
    MyPageNav
  },
  data() {
    return {
      step: 0,
      pw: "",
      privateKey: "",
      walletAddress: "",
      sharedState: this.$store.state,
      userId: this.$store.state.user.id,
      contract: "",
    };
  },
  methods: {
    // 추후 메타마스크 연동으로 변경
    makeContract() {   
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));   

      let contract =  new web3.eth.Contract(MilkToken.abi, CASH_CONTRACT_ADDRESS);
      this.contract = contract
      
    },
    createWallet() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      this.privateKey = this.pw;
      console.log(this.pw);
      web3.eth.personal.newAccount(this.pw).then(address => {
        console.log(address);
        this.$store.commit("setWalletAddress", address);
        this.walletAddress = address;
      })
      this.pw = "";
      // this.step += 1;
    },
    saveWallet() {
      const token = this.$store.state.user.JWTToken;
      const headers = {
        Authorization: `Bearer ${token}`
      }
      axios({
        url: `${API_BASE_URL}/api/wallets/`,
        method: 'post',
        headers,
        data: {
          ownerId: this.userId,
          address: this.walletAddress,
          privateKey: this.privateKey,
        }
      })
      .then(res => {
        // console.log(res.data.data)
        this.$store.commit("setWalletAddress", res.data.data);
        this.$router.push("/mypage/wallet_info")
        Swal.fire({
          position: "center",
          icon: "success",
          title: "지갑이 생성 됬어요!",
          showConfirmButton: false,
          timer: 1500,
        });        
      })
      .catch(err => {
        Swal.fire({
          position: "center",
          icon: "fail",
          title: "다시 시도해주세요",
          showConfirmButton: false,
          timer: 1500,
        });
      })
      
    }
  },
  mounted() {
    this.makeContract();
    this.walletAddress = this.$store.state.user.walletAddress;
  }
};
</script>

<style></style>
