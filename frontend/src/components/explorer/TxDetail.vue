<template>
  <div>
    <h-breadcrumb
      title="Transaction Explorer"
      description="블록체인에서 생성된 거래내역을 보여줍니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="card shadow-sm">
            <div class="card-header">
              <strong>{{ tx.hash }}</strong>
            </div>
            <table class="table">
              <tbody>
                <tr>
                  <th width="200">트랜잭션 해시</th>
                  <td>{{ tx.hash }}</td>
                </tr>
                <tr>
                  <th>블록 넘버</th>
                  <td>{{ tx.block }}</td>
                </tr>
                <tr>
                  <th>날짜</th>
                  <td>{{ tx.timestamp }}</td>
                </tr>
                <tr>
                  <th>송신자 주소</th>
                  <td>
                    <router-link
                      :to="{ name: 'address', params: { address: tx.from } }"
                      >{{ tx.from }}</router-link
                    >
                  </td>
                </tr>
                <tr>
                  <th>수신자 주소</th>
                  <td>
                    <router-link
                      :to="{ name: 'address', params: { address: tx.to } }"
                      >{{ tx.to }}</router-link
                    >
                  </td>
                </tr>
                <tr>
                  <th>전송한 이더</th>
                  <td>{{ tx.value }} Ether</td>
                </tr>
                <tr>
                  <th>Gas</th>
                  <td>{{ tx.gas }}</td>
                </tr>
                <tr>
                  <th>Gas Price</th>
                  <td>{{ tx.gasPrice }}</td>
                </tr>
                <tr>
                  <th>Input Data</th>
                  <td>
                    <textarea class="form-control" v-model="tx.input" readonly>
                    </textarea>
                  </td>
                </tr>
                <tr>
                  <th>지갑 생성</th>
                  <input v-model="tx.password">
                  <button @click="makeWallet">Click</button>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { createWeb3 } from "@/utils/itemInventory.js";
import Web3 from "web3";


export default {
  data() {
    return {
      isValid: true,
      web3: "",
      tx: {
        hash: "-",
        timestamp: "-",
        step: 0,
        privateKey: "",
        walletAdress: "",
        password: "",
      }
    };
  },
  mounted: function() {
    /**
     * 1. 특정 블록의 정보을 블록체인으로부터 직접 가져옵니다. 
     * 2. 특정 블록의 정보을 서버로부터 요청하여 가져옵니다. 
     */    
    const ENDPOINT = 'http://localhost:8545';
    const Web3 = require('web3');
    this.web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));

    var scope = this;
    var hash = this.$route.params.hash;
    const web3 = createWeb3();
    if (hash) {
      web3.eth.getTransaction(hash).then(function(tx) {
        scope.tx.hash = hash;
        scope.tx.block = tx.blockNumber;
        scope.tx.from = tx.from;
        scope.tx.to = tx.to;
        scope.tx.gas = Number(tx.gas).toLocaleString();
        scope.tx.gasPrice = Number(tx.gasPrice).toLocaleString();
        scope.tx.value = Math.ceil(Number(tx.value) / 10 ** 18);
        scope.tx.input = tx.input;

        web3.eth.getBlock(scope.tx.block).then(function(block) {
          scope.tx.timestamp = new Date(block.timestamp * 1000);
        });
      });
    } else {
      this.isValid = false;
    }
  },
  methods: {
    makeWallet() {
      const pw = this.tx.password;
      // web3.eth.personal.newAccounts() 지갑생성하는 게스 명령어
      this.web3.eth.personal.newAccount(pw).then(address => {
        console.log(address);
        this.tx.walletAdress;
      })
    }
  }
};
/**
 * 
 * 
 * 아래와 같이 그냥 web3 선언해놓고 필요한거 쓰면 geth는 끝이다!
 web3.eth.net.getId()
.then(id => console.log("Network Id: ", id));
web3.eth.net.getPeerCount()
.then(peerCount => console.log("No. of Peers: ", peerCount));

web3.eth.getBlockNumber()
.then(blockNo => console.log("Latest Block Number: ", blockNo));

 * 
 */
</script>

<style></style>
