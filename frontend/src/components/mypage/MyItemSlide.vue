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
          <div class="badges d-flex justify-content-between">
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
              <span v-if="slide.status === `C01`"> 판매 중 </span>
              <span v-if="slide.status === `C02`"> 결제 완료 </span>
              <span v-if="slide.status === `C03`"> 구매자 수령 완료 </span>
            </span>
            <span
              v-if="slide.status === `C01` || slide.status === `C02`"
              class="badge badge-error py-1"
              @click="deleteItem(slide.itemId)"
            >
              판매 취소
            </span>
            <span
              v-if="slide.status === `C03`"
              class="badge badge-success py-1"
              @click="endEscrow(slide.itemId, slide.price)"
            >
              판매자 확인
            </span>
          </div>
          <span class="badges" v-if="slide.status === `C03`">
            <span class="badge badge-warn text-danger">
              ※확인처리를 통해 판매금 수령이 가능합니다.
            </span>
          </span>
        </div>
      </article>
    </section>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2/dist/sweetalert2.js";
import { API_BASE_URL, BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS } from "@/config/index.js";

export default {
  name: "MyItemSlide",
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
    deleteItem(itemId) {
      const token = this.$store.state.user.JWTToken;

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/item/delete/${itemId}`,
        method: "put",
        headers,
      })
        .then((res) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "판매 취소 처리 완료",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.go();
        })
        .catch((err) => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "다시 시도해주세요",
            showConfirmButton: false,
            timer: 1500,
          });
        });
    },
    endEscrow(itemId, price) {
      this.$emit("end-escrow", itemId, price);
    },
    itemDetail(itemId) {
      this.$router.push({
        name: "item.detail",
        params: { id: this.slide.itemId },
      });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
@import url("https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

.hotitemslide * {
  box-sizing: border-box;
  cursor: pointer;
}
.hotitemslide .cards {
  color: black;
  font-family: "Roboto Slab", serif;
  width: 75%;
  display: flex;
  display: -webkit-flex;
  justify-content: center;
  -webkit-justify-content: center;
  /* max-width: 820px; */
  margin-left: 50px;
}

.hotitemslide .card__like {
  width: 18px;
  font-family: "Noto Sans KR", sans-serif;
  font-size: 8px;
  vertical-align: middle;
  margin-left: 5px;
}

.hotitemslide .card__clock {
  width: 15px;
  vertical-align: middle;
  fill: #ad7d52;
}
.hotitemslide .card__time {
  font-size: 8px;
  color: #ad7d52;
  vertical-align: middle;
  margin-left: 5px;
}

.hotitemslide .card__clock-info {
  font-family: "Noto Sans KR", sans-serif;
  float: right;
}

.hotitemslide .card__img {
  visibility: hidden;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 140px;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
}

.hotitemslide .card__info-hover {
  position: absolute;
  padding: 16px;
  width: 100%;
  opacity: 0;
  top: 0;
}

.hotitemslide .card__img--hover {
  transition: 0.2s all ease-out;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  position: absolute;
  height: 140px;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  top: 0;
}
.hotitemslide .card {
  margin: 25px;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0, 1);
  background-color: #fff;
  width: 80%;
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0px 13px 10px -7px rgba(0, 0, 0, 0.1);
}
.hotitemslide .card:hover {
  box-shadow: 0px 30px 18px -8px rgba(0, 0, 0, 0.1);
  transform: scale(1.1, 1.1);
}

.hotitemslide .card__info {
  z-index: 2;
  background-color: #fff;
  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
  padding: 16px 24px 24px 24px;
}

.hotitemslide .card__category {
  /* font-family: "Noto Sans KR", sans-serif; */
  font-family: "Jua", sans-serif;
  font-size: 4px;
  letter-spacing: 2px;
  font-weight: 500;
  color: #868686;
}

.hotitemslide .card__title {
  margin-top: 3px;
  margin-bottom: 6px;
  font-size: 14px;
  font-family: "Black Han Sans", sans-serif;
}

.hotitemslide .card__by {
  font-size: 8px;
  font-family: "Raleway", sans-serif;
  font-weight: 500;
}

.hotitemslide .card__author {
  font-weight: 600;
  text-decoration: none;
  color: #ad7d52;
}

.hotitemslide .card:hover .card__img--hover {
  height: 160px;
  opacity: 0.3;
}

.hotitemslide .card:hover .card__info {
  background-color: transparent;
  position: relative;
}

.hotitemslide .card:hover .card__info-hover {
  opacity: 1;
}

.badges .badge {
  margin-right: 3px;
  padding: 2px 6px 1px;
  font-family: "Noto Sans KR", sans-serif;
  font-size: 5px;
  font-weight: bold;
  white-space: nowrap;
  color: #ffffff;
  background-color: #999999;
  -webkit-border-radius: 9px;
  -moz-border-radius: 9px;
  border-radius: 9px;
}
.badges .badge:hover {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
}
.badges .badge-error {
  background-color: #f0423f;
}
.badges .badge-error:hover {
  background-color: #953b39;
}
.badges .badge-warning {
  background-color: #8a3aff;
}
.badges .badge-warning:hover {
  background-color: #692bc5;
}
.badges .badge-success {
  background-color: #468847;
}
.badges .badge-success:hover {
  background-color: #356635;
}
.badges .badge-info {
  background-color: #6f79ff;
}
.badges .badge-info:hover {
  background-color: #454cac;
}
.badges .badge-inverse {
  background-color: #3a9fff;
}
.badges .badge-inverse:hover {
  background-color: #2974bb;
}
.badges .badge-warn {
  background-color: #fff4b3;
}
.badges .badge-warn:hover {
  background-color: #d9c44e;
}
</style>
