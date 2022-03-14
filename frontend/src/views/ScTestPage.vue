<template>
  <div>
    <div class="d-flex-row">
      <p>지갑 주소 {{ tx.walletAdress }}</p>
      <p>지갑 생성</p>
      <input type="text" v-model=tx.password>
      <button @click="createWallet">지갑 생성</button>
    </div>

    <div>
      <p>Coinbase 잔액 확인</p>
      <div>
      <span>{{ ethBalance }}ETH</span>
      <button @click="checkBalance">ETH 잔액 확인</button>
      </div>
      <div>
        <span>{{ milkBalance }}MILK</span>
        <button @click="checkMilk">MILK 잔액 확인</button>
      </div>
    </div>

    <div>
      <p>MILK 전송</p>
      <p>From</p>
      <input type="text" v-model="from">
      <p>To</p>
      <input type="text" v-model="to">
      <p>전송 MILK</p>
      <input type="text" v-model="amount">
      <button @click="sendMilk">MILK 전송</button>
    </div>
  </div>

</template>

<script>
import Web3 from "web3";
import MilkToken from "../config/contract/MilkToken.json"


export default {
  data() {
    return {
      web3: "",
      coinbaseAdress: "",
      contractAdress: "",
      MilkToken: MilkToken,
      from: "",
      to: "",
      amount: 0,
      tx: {
        password: "",
        walletAdress: [],
      },
      ethBalance: 0,
      milkBalance: 0,
      contract: "",
    }
  },
  methods: {
    // 메타마스크 설치 후 프로바이더 사용
    makeContract() {   
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';      


      let contract =  new web3.eth.Contract(MilkToken.abi, this.contractAdress);
      this.contract = contract
      
    },
  	createWallet() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';      
      const pw = this.tx.password
      console.log(pw)
      // 위의줄 까지는 web3 명령어를 사용하기위해 전처리 해줘야하는 내용,
      // 이걸 계속사용해야하면 mounted 단계에서 위에까지만 선언해서 변수로 저장해 두자.
      // web3.eth.personal.newAccounts() 지갑생성하는 게스 명령어
      web3.eth.personal.newAccount(pw).then(address => {
        console.log(address);
        this.tx.walletAdress.push(address)
      })
      this.tx.password = ""
   },
    checkBalance() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';      
      web3.eth.getBalance(this.coinbaseAdress).then(res => {
        this.ethBalance = (res / 10**18).toLocaleString();
      })
    },
    // 1000MILK = 1ETH (1MILK = 10*15 wei)
    async checkMilk() {
      const milkBalance = await this.contract.methods.balanceOf(this.coinbaseAdress).call();
      this.milkBalance = (milkBalance / 10**15).toLocaleString();
      const result = await this.contract.methods.approve(this.coinbaseAdress, milkBalance).call();
    },
    async sendMilk() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';       
      const from = this.from;
      const to = this.to;
      const amount = Number(this.amount);
      const is_allowed = await this.contract.methods.allowance(to, amount).call();
      console.log(is_allowed)
      // let result;
      // if (is_allowed) {
      //   console.log(is_allowed)
      //   result = await this.contract.methods.transferFrom(fromAddress, toAddress, amount).call();
      // }
      // console.log(result)
      this.from = "";
      this.to = "";
      this.amount = 0;
    }
  },
  mounted() {
    const contractAdress = "0x36Bb0950E8c2B909bB9AD77573b5fF3982c98Bb8"
    this.contractAdress = contractAdress 
    const Web3 = require('web3');
    const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
    const ENDPOINT = 'http://localhost:8545';      
    web3.eth.getAccounts().then(res => {
      this.tx.walletAdress = res
      this.coinbaseAdress = res[0]
    });
    this.makeContract();
  }
}
</script>

<style scoped>
</style>