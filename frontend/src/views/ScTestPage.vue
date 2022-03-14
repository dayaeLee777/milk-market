<template>
  <div>
    <div>
      <button @click="testMeta">클릭</button>
      <button @click="testAcc">확인용</button>
    </div>
    <div class="d-flex-row">
      <p>지갑 주소 {{ tx.walletAdress }}</p>
      <p>지갑 생성</p>
      <input type="text" v-model=tx.password>
      <button @click="createWallet">지갑 생성</button>
    </div>

    <div>
      <p>잔액 확인</p>
      <span>{{ balance }}ETH</span>
      <button @click="checkBalance">잔액 확인</button>
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
      contractAdress: "",
      MilkToken: MilkToken,
      tx: {
        password: "",
        walletAdress: [],
      },
      balance: 0,
    }
  },
  methods: {
    // 메타마스크 설치 후 프로바이더 사용
    testMeta() {   
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';      

      let contract = web3.eth.contract(MilkToken.abi).at(this.contractAdress)

      contract.balanceOf(this.tx.walletAdress[2], res => {
        console.log(res)
      })
    },
    testAcc() {
  
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
      web3.eth.getBalance(this.tx.walletAdress[6]).then(res => {
        this.balance = res / 10**18
      })
    }
  },
  mounted() {
    const contractAdress = "0xb3112448189B3d4737DABD06Ed2e574Ed51A9174"
    this.contractAdress = contractAdress 
    const Web3 = require('web3');
    const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
    const ENDPOINT = 'http://localhost:8545';      
    web3.eth.getAccounts().then(res => {
      this.tx.walletAdress = res
    })
  }
}
</script>

<style scoped>
</style>