<template>
  <div>
    <div class="d-flex-row">
      <p>지갑 주소 {{ tx.walletAdress }}</p>
      <hr>
      <p>지갑 생성</p>
      <div>
        <span>비밀번호 입력</span>
        <input type="text" class="form-control" v-model=tx.password>
        <button @click="createWallet" class="btn btn-primary my-2">지갑 생성</button>
      </div>
    </div>
    <hr>
    <div class="d-flex">
      <div>
        <p>Coinbase 잔액 확인</p>
        <span>계좌: {{ coinbaseAdress }}</span>
     <div>
        <span>{{ ethBalanceBase }}ETH</span>
        <button @click="checkBalance(0)" class="btn btn-primary my-2">ETH 잔액 확인</button>
        </div>
        <div>
          <span>{{ milkBalanceBase }}MILK</span>
          <button @click="checkMilk(0)" class="btn btn-primary my-2">MILK 잔액 확인</button>
        </div>
      </div>
      <div class="ms-5">
        <p>계좌 잔액 확인</p>
        <span>계좌: {{ address }}</span>
        <input type="text" class="form-control" v-model="idx">
        <div>
        <span>{{ ethBalance }}ETH</span>
        <button @click="checkBalance(idx)" class="btn btn-primary my-2">ETH 잔액 확인</button>
        </div>
        <div>
          <span>{{ milkBalance }}MILK</span>
          <button @click="checkMilk(idx)" class="btn btn-primary my-2">MILK 잔액 확인</button>
        </div>
      </div>      
    </div>
    <hr>
    <div class="d-flex">
      <div class="col-3">
        <p>MILK 전송</p>
        <p>From</p>
        <input type="text" class="form-control"  v-model="from">
        <p>To</p>
        <input type="text" class="form-control" v-model="to">
        <p>전송 MILK</p>
        <input type="text" class="form-control" v-model="amount">
        <button @click="sendMilk" class="btn btn-primary my-2">MILK 전송</button>
      </div>
      <div class="col-3">
        <p>ETH 전송</p>
        <p>From</p>
        <input type="text" class="form-control" v-model="from_eth">
        <p>To</p>
        <input type="text" class="form-control" v-model="to_eth">
        <p>전송 ETH</p>
        <input type="text" class="form-control" v-model="amount_eth">
        <button @click="sendETH" class="btn btn-primary my-2">ETH 전송</button>    
      </div>
    </div>
    <div>
      <button class="btn btn-secondary my-2" @click="testEscrow">테스트</button>
    </div>
  </div>

</template>

<script>
import Web3 from "web3";
import MilkToken from "../config/contract/MilkToken.json"
import Milk from "../config/contract/Milk.json"
import { BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS, PURCHASE_RECORD_CONTRACT_ADDRESS} from "@/config/index.js" 

export default {
  data() {
    return {
      web3: "",
      coinbaseAdress: "",
      contractAdress: "",
      address: "",
      MilkToken: MilkToken,
      from: "",
      to: "",
      amount: 0,
      from_eth: "",
      to_eth: "",
      idx: 0,
      amount_eth: 0,
      tx: {
        password: "",
        walletAdress: [],
      },
      ethBalance: 0,
      milkBalance: 0,
      ethBalanceBase: 0,
      milkBalanceBase: 0,
      contract: "",
      testContract: "",
    }
  },
  methods: {
    // 추후 메타마스크 사용으로 변경ㅇ
    makeContract() {   
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = BLOCKCHAIN_URL;      

      const contract =  new web3.eth.Contract(MilkToken.abi, CASH_CONTRACT_ADDRESS);
      this.contract = contract;

      const testContract = new web3.eth.Contract(Milk.abi, PURCHASE_RECORD_CONTRACT_ADDRESS);
      this.testContract = testContract;
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
    // ETH 남은 금액 확인
    checkBalance(i) {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';      
      web3.eth.getBalance(this.tx.walletAdress[i]).then(res => {
        if (i == 0) {
          this.ethBalanceBase = (res / 10**18).toLocaleString();
        }
        else{

          this.ethBalance = (res / 10**18).toLocaleString();
        }
      })
    },
    // 1000MILK = 1ETH (1MILK = 10*15 wei) (실제 연동은 아님...)
    // MILK 남은 금액 확인
    async checkMilk(i) {
      const milkBalance = await this.contract.methods.balanceOf(this.tx.walletAdress[i]).call();
      if (i === 0) {
        this.milkBalanceBase = (milkBalance / 10**15).toLocaleString();
      }
      else{
        this.milkBalance = (milkBalance / 10**15).toLocaleString();
      }
      const result = await this.contract.methods.approve(this.tx.walletAdress[i], milkBalance).call();
    },
    // eth 전송
    sendETH() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';      
      const from = this.from_eth;
      const to = this.to_eth;
      const amount = (this.amount_eth) * (10 ** 18); // wei 단위로 변환해 주어야 함
      const tx = {"from": from, "to": to, "value": amount}
      web3.eth.sendTransaction(tx).then(res => {
        console.log(res)
      })
    
    },
    // MILK 전송
    async sendMilk() {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));
      const ENDPOINT = 'http://localhost:8545';       
      const from = this.from;
      const to = this.to;
      const amount = Number(this.amount) * (10**15);

      // 10**15 이상의 숫자를 그냥 보내는것은 불가능 web3.utis.toBN() 메서드 활용
      const a = await this.contract.methods.approve(from, web3.utils.toBN(`${amount}`)).send({ from: from });
      const c = await this.contract.methods.transferFrom(from, to, web3.utils.toBN(`${amount}`)).send({from: from});
      console.log(a, c)
      this.from = "";
      this.to = "";
      this.amount = 0;
    },
    testEscrow() {
       const web3 = new Web3(Web3.givenProvider)
       this.web3 = web3
       reslove(web3);
    },
  },
  mounted() {
    const contractAdress = CASH_CONTRACT_ADDRESS
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