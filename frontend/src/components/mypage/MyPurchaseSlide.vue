<template>
  <div class="hotitemslide">
    <section class="cards">
      <article class="card card--2">
        <img class="card__img" :src="slide.files[Object.keys(slide.files)[0]]" />
        <div class="card_link" @click="itemDetail(slide.itemId)">
          <img class="card__img--hover" :src="slide.files[Object.keys(slide.files)[0]]" />
        </div>
        <div class="card__info">
          <h3 class="card__title">{{ slide.itemName }}</h3>
          <div class="badges">
            <!-- <span class="badge badge-error">대여</span> -->
            <span class="badge badge-inverse">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="13"
                height="13"
                fill="currentColor"
                class="bi bi-bookmark-heart"
                viewBox="0 0 16 16"
              >
                <path
                  fill-rule="evenodd"
                  d="M8 4.41c1.387-1.425 4.854 1.07 0 4.277C3.146 5.48 6.613 2.986 8 4.412z"
                />
                <path
                  d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"
                />
              </svg>
            <span v-if="slide.status === `C01`">
              판매 중
            </span>
            <span v-if="slide.status === `C02`">
              결제 완료
            </span>
            </span>
            <div class="badges">
              <span 
                class="badge badge-info"
                @click="confirm(slide.itemId)">
                구매 확정
              </span>
              <span class="badge badge-error"
                @click="cancelPurchase(slide.price, slide.itemId)"
                >
                구매 취소
              </span>
            </div>
          </div>       
        </div>
      </article>
    </section>
  </div>
</template>

<script>
import axios from "axios"
import Swal from 'sweetalert2/dist/sweetalert2.js'
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js"

export default {
  name: "MyPurchaseSlide",
  props: {
    slide: {},
    coinbase: String,
    walletAddress: String,
    contract: Object,    
  },
  data() {
    return {};
  },
  methods: {
    confirm(itemId) {
      this.$emit("confirm-purchase", itemId)
    },
    itemDetail(itemId) {
      this.$router.push({
        name: "item.detail",
        params: { id: this.slide.itemId },
      });
    },
    async refund(price) {
      const Web3 = require('web3');
      const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
      const from = this.coinbase;
      const to = this.walletAddress;
      const amount = price * (10**15);
      const amountToBn = web3.utils.toBN(`${amount}`)
      const approve = await this.contract.methods.approve(from, amountToBn).send({from: from});
      const transfer = await this.contract.methods.transferFrom(from, to, amountToBn).send({from: from});

      this.$router.go();

    },
    cancelPurchase(price, itemId) {
      const token = this.$store.state.user.JWTToken;

      const headers = {
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `${API_BASE_URL}/api/item/cancel/${itemId}`,
        method: 'delete',
        headers,
      })
      .then( res => {
          Swal.fire({
          position: "center",
          icon: "success",
          title: "구매 취소 완료!",
          showConfirmButton: false,
          timer: 1500,
        });   
          this.refund(price);
      })
      .catch( err => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
      })
    }
  },
};
</script>

<style scoped>
</style>
