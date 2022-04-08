<template>
  <div>
    <!-- <h-breadcrumb
    ></h-breadcrumb> -->
    <div class="container">
      <my-page-nav></my-page-nav>
      <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">PrivateKey 인증</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <p>지갑 비밀번호 입력</p>
              <input type="password" class="form-control" v-model="wallet.privateKey">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" @click="checkPrivateKey" data-bs-dismiss="modal">확인</button>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
          </div>
        </div>
      </div>        
      <div id="mywallet-info" class="row">
        <div class="col-8 mt-5">
          <div class="card">
            <table class="table table-bordered">
              <tr>
                <th>회원 정보</th>
                <td class="text-right">{{ user.name }}({{ user.email }})</td>
                <div v-if="isAuthorized">
                  <p class="text-primary fw-bold">PrivateKey 인증 완료</p>
                </div>
                <div v-else class="d-flex align-items-center">
                  <button type="button" class="btn btn-primary btn-sm"
                  data-bs-toggle="modal" data-bs-target="#exampleModal"
                  >privateKey 인증</button>
                  <span class="text-danger fw-bold" id="warn-wallet">※ 개인키 인증이 필요합니다.</span>
                </div>
              </tr>
              <tr>
                <th>보유 ETH</th>
                <td class="text-right">{{ wallet.balance }} ETH</td>
                <td colspan="2" class="text-left">
                  <div v-if="isAuthorized">
                    <button
                      type="button"
                      class="btn btn-primary"
                      data-bs-toggle="modal" 
                      data-bs-target="#ethModal"
                    > 
                      {{ "ETH 충전" }}
                    </button> 
                  <!-- 
                    <button class="btn btn-warning" @click="kakaoPay">카카오페이</button> -->
                  </div>
                </td>
              </tr>
              <tr>
                <th>보유 MILK</th>
                <td class="text-right">{{ wallet.milkBalance }} MILK
                </td>
                <td colspan="2" class="text-left">
                  <div class="input-group d-flex align-items-center">
                    <input
                      v-model="cashChargeAmount"
                      type="number"
                      step="0.001"
                      class="form-control"
                    />
                    <span class="input-group-text">ETH</span>
                    <div v-if="isAuthorized">
                      <button
                        type="button"
                        class="btn btn-primary ms-3"
                        @click="chargeCash()"
                      >
                        {{ "MILK 충전" }}
                      </button>                    
                    </div>
                  </div>
                  <p class="text-primary mb-0 eth-font">* 1 ETH = 1000 MILK / 최소 충전 금액: 0.001 ETH</p>
                </td>
              </tr>
              <tr>
                <th>지갑주소</th>
                <td class="text-right">{{ walletAddress }}</td>
                <!-- <th>ETH 충전 횟수</th>
                <td class="text-right">{{ wallet["receivingCount"] }}회</td> -->
              </tr>
            </table>
            <!-- 모달 -->
            <div class="modal fade" id="ethModal" tabindex="-1" aria-labelledby="ethModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="ethModalLabel">ETH 충전하기</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body d-flex flex-column">
                    <p class="eth-font">현재 환율: 1ETH = {{ ethPrice.toLocaleString() }}원</p>
                    <div class="d-flex align-items-center">
                      <label for="won">충전할 ETH: </label>
                      <input type="number" step="0.001" class="form-control col-3 ms-3" id="won" v-model="amountCharge">
                      <span>ETH</span>
                      <!-- <button class="btn btn-secondary btn-sm ms-2" @click="addCharge(0.1)">0.1</button>
                      <button class="btn btn-secondary btn-sm ms-2" @click="addCharge(0.01)">0.01</button> -->
                    </div>
                    <p class="mt-3 fw-bold">결제 금액: {{ (amountCharge * ethPrice).toLocaleString() }}원</p>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal"
                      @click="chargeETH()">충전</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
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
import MyPageNav from "./MyPageNav.vue";
import BN from "bn.js";
import MilkToken from "@/config/contract/MilkToken.json"
import axios from 'axios'
import jwt_decode from "jwt-decode";
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js";
import Swal from 'sweetalert2/dist/sweetalert2.js'

export default {
  name: "WalletInfo",
  components: {
    MyPageNav
  },
  data() {
    return {
      wallet: {
        id: 0,
        ownerId: null,
        balance: 0,
        milkBalance: 0,
        cash: 0,
        receivingCount: 0,
        privateKey: "",
      },
      user: {
        name: "",
        email: ""
      },
      isAuthorized: false,
      cashChargeAmount: 0.001,
      tempCharge: 0,
      userId: this.$store.state.user.id,
      walletAddress: this.$store.state.user.walletAddress,
      coinbaseAddress: "",
      contract: "",
      amountCharge: 0,
      ethPrice: 4304000, // 크롤링으로 받아오는걸로
    };
  },
  computed: {
    canCashCharge() {
      return this.cashChargeAmount >= 0.1 && !this.isCashCharging;
    }
  },
  methods: {
    addCharge(n) {
      this.amountCharge += n
    },
    makeContract() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      let contract =  new web3.eth.Contract(MilkToken.abi, CASH_CONTRACT_ADDRESS);
      this.contract = contract;
    },
    fetchWalletInfo() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const myWallet = this.$store.state.user.walletAddress;
      web3.eth.getAccounts().then(res => {
        console.log(res)
        this.coinbaseAddress = res[0]
      });

      web3.eth.getBalance(myWallet).then(res => {
        const balanceWei = res
        this.wallet.balance = web3.utils.fromWei(
          balanceWei, "ether"
        )
      })
    },
    async milkBalnce() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      let contract =  new web3.eth.Contract(MilkToken.abi, CASH_CONTRACT_ADDRESS);
      const myWallet = this.$store.state.user.walletAddress;
      const milkBalance = await contract.methods.balanceOf(myWallet).call();
      this.wallet.cash = (milkBalance / 10**18).toFixed(3);
    },
    async checkMilk() {
      const milk = await this.contract.methods.balanceOf(this.$store.state.user.walletAddress).call();

      this.wallet.milkBalance = (milk / 10**15).toLocaleString();
      console.log(this.wallet.milkBalance)
    },
    async chargeMilk() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const from = this.coinbaseAddress;
      const to = this.walletAddress;
      const amount = this.tempCharge * (10**18);
      const amountToBn = web3.utils.toBN(`${amount}`)
      const approve = await this.contract.methods.approve(from, amountToBn).send({from: from});
      const transfer = await this.contract.methods.transferFrom(from, to, amountToBn).send({from: from});
      this.checkMilk();
   },
    chargeETH() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const value = (this.amountCharge) * (10**18) 
      const tx = {"from": this.coinbaseAddress, "to": this.walletAddress, "value": value}
      web3.eth.sendTransaction(tx).then(res => {
        console.log(res);
        this.fetchEtherBalance();
        Swal.fire({
          position: "center",
          icon: "success",
          title: "충전 되었습니다.",
          showConfirmButton: false,
          timer: 1500,
        }); 
        this.amountCharge = 0;
      })
    },
    chargeCash() {
      // const vm = this;
      // this.isCashCharging = true;
      // const privateKey = prompt("캐시 를 충전하시려면 개인키를 입력하세요.");
      // if (privateKey) {
      //   /**
      //    * TODO: PJTⅡ 과제3 Req.1-1 [토큰 구매]
      //    * 이더를 지불하고 캐시를 충전
      //    */
        
      // }
      if (this.cashChargeAmount > Number(this.wallet.balance)) {
        alert("이더리움을 충전해주세요")
      }
      else {
        const Web3 = require('web3');
        const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        this.tempCharge = this.cashChargeAmount;
        const amount = Number(this.cashChargeAmount) * (10**18);
        const amount_BN = web3.utils.toBN(`${amount}`);
        web3.eth.personal.unlockAccount(this.walletAddress, this.wallet.privateKey, 600);
        const tx = {"from": this.walletAddress, "to": this.coinbaseAddress, "value": amount_BN}   
        web3.eth.sendTransaction(tx).then( res => {
          console.log(res)
          this.fetchEtherBalance();
        })
        this.chargeMilk();
        Swal.fire({
            position: "center",
            icon: "success",
            title: "MILK 충전이 성공!",
            showConfirmButton: false,
            timer: 1500,
          });
           
     }


    },
    /**
     * TODO: PJTⅡ 과제3 Req.1-2 [토큰 잔액 조회]
     * 스마트 컨트랙트의 잔액 확인 함수 호출
     */
    fetchEtherBalance() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      web3.eth.getBalance(this.walletAddress).then(res =>{
        const balanceWei = res
        console.log(balanceWei)
        this.wallet.balance = web3.utils.fromWei(
          balanceWei, "ether"
        )
        console.log(this.wallet.balance)
      })
    },
    // 회원 정보 가져온다.
    fetchUserInfo() {
      const token = this.$store.state.user.JWTToken;
      const decode = jwt_decode(token)
      console.log(decode.sub)

      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `${API_BASE_URL}/api/users/`,
        method: 'get',
        headers,
      })
      .then( (res) => {
        this.user.name = res.data.nickname;
        this.user.email = res.data.email;
      })
      .catch( (err) => {
        console.log(err)
      });
    },
    // privateKey 확인
    checkPrivateKey() {
      const token = this.$store.state.user.JWTToken;
      const headers = {
        Authorization: `Bearer ${token}`
      } 
      axios({
        url: `${API_BASE_URL}/api/wallets/check`,
        method: 'post',
        headers,
        data: {
          ownerId: this.user.email,
          address: this.walletAddress,
          privateKey: this.wallet.privateKey,
        }
      })
      .then( res => {
        if (res.data.is_login === "true") {
          this.isAuthorized = true;
          Swal.fire({
            position: "center",
            icon: "success",
            title: "인증 성공",
            showConfirmButton: false,
            timer: 1500,
          });
        }
      })
      .catch( err => {
        console.log(err)
        Swal.fire({
          position: "center",
          icon: "fail",
          title: "개인키를 다시 확인해주세요",
          showConfirmButton: false,
          timer: 1500,
        });
      })
    },
  },
  mounted() {
    this.milkBalnce();
    this.makeContract();
    this.fetchWalletInfo();
    this.fetchUserInfo();
    this.fetchEtherBalance();
    this.checkMilk();
  }
};
</script>

<style>
th {
  width: 150px;
}
#mywallet-info th {
  text-align: left;
}
.wallet-btn {
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
#warn-wallet {
  font-size: 8px;
}
.eth-font {
  font-size: 12px;
}

</style>
