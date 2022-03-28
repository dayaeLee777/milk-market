<template>
  <div>
    <h-breadcrumb
      title="상품 등록"
      description="새로운 상품을 등록합니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card">
            <div class="card-body">
              <div class="form-group">
                <label id="name">상품 이름</label>
                <input
                  type="text"
                  class="form-control"
                  id="name"
                  v-model="item.itemName"
                />
              </div>
              <div class="form-group">
                <label id="division">구분</label>
                <select
                  class="form-control"
                  id="division"
                  v-model="item.division"
                >
                  <option value="A01">대여</option>
                  <option value="A02">판매</option>
                </select>
              </div>
              <div class="form-group">
                <label id="category">카테고리</label>
                <select
                  class="form-control"
                  id="category"
                  v-model="item.category"
                >
                  <option value="B01">신생아</option>
                  <option value="B02">유모차</option>
                  <option value="B03">카시트</option>
                  <option value="B04">발육</option>
                  <option value="B05">수유</option>
                  <option value="B06">이유식</option>
                  <option value="B07">소독/살균</option>
                  <option value="B08">스킨/바디</option>
                  <option value="B09">유아가구</option>
                  <option value="B10">목욕</option>
                  <option value="B11">구강청결</option>
                  <option value="B12">세제</option>
                  <option value="B13">안전</option>
                  <option value="B14">위생/건강</option>
                  <option value="B15">임산부</option>
                  <option value="B16">유아침구</option>
                </select>
              </div>
              <div class="form-group">
                <label id="price">가격 <b>(CASH)</b></label>
                <input
                  type="number"
                  class="form-control"
                  id="price"
                  v-model="item.price"
                />
              </div>
              <div class="form-group">
                <label id="description">상품 설명</label>
                <textarea
                  class="form-control"
                  id="description"
                  v-model="item.description"
                  placeholder=""
                ></textarea>
              </div>
              <div
                v-if="item.division === 'A01'"
                class="form-group"
              >
                <label id="rentStartDate">대여 시작 날짜</label>
                <input
                  type="datetime-local"
                  class="form-control"
                  id="rentStartDate"
                  v-model="item.rentStartDate"
                />
              </div>
              <div
                v-if="item.division === 'A01'"
                class="form-group"
              >
                <label id="rentEndDate">대여 종료 날짜</label>
                <input
                  type="datetime-local"
                  class="form-control"
                  id="rentEndDate"
                  v-model="item.rentEndDate"
                />
              </div>
              <div class="form-group">
                <label id="image-upload">이미지 첨부</label>
                <input
                  type="file"
                  class="form-control"
                  ref="itemImage"
                  id="image-upload"
                  @change="selectFile()"
                />
              </div>
              <!-- <div class="form-group">
                <label id="image-upload">이미지 첨부</label>
                <input
                  id="upload"
                  type="file"
                  class="form-control"
                  style="height: auto;"
                  @change="onFileChange"
                />
                <div class="image-area mt-4">
                  <img
                    id="imageResult"
                    :src="imgLocalPath"
                    alt=""
                    class="img-fluid rounded shadow-sm mx-auto d-block"
                    style="max-height: 500px;"
                  />
                </div>
              </div> -->
              <button
                type="button"
                class="btn btn-primary"
                @click="save()"
              >
                등록
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { create as createItem } from "@/api/item.js";
import { registerItem } from "@/utils/itemInventory.js";
import axios from 'axios'


export default {
  name: "ItemCreate",
  data () {
    return {
      item: {
        itemName: "",
        division: "",
        category: "",
        price: null,
        description: "",
        rentStartDate: null,
        rentEndDate: null,
      },
      image: null,
      userId: this.$store.state.user.id,
    };
  },
  computed: {
    /**
     * public/images에 있는 로컬 이미지를 첨부해서 사용합니다.
     * DB에는 이미지 파일 이름만 저장되고
     * 화면에 보여줄 땐 'public/images/{파일이름}' 경로를 사용합니다.
     */
    // imgLocalPath () {
    //   if (this.item.imgName) {
    //     return process.env.BASE_URL + "images/" + this.item.imgName;
    //   }

    //   return null;
    // },
  },
  methods: {
    // 상품을 등록한다.
    save () {
      const vm = this;
      this.isCreating = true; // 아이템 등록 중임을 화면에 표시, 등록이 끝나면 false로 변경
      if (this.item.division === 'A01') {
        if (this.item.itemName.length <= 0 ||
          this.item.division.length <= 0 ||
          this.item.category.length <= 0 ||
          this.item.price === null ||
          this.item.price <= 0 ||
          this.item.rentStartDate === null ||
          this.item.rentStartDate.length <= 0 ||
          this.item.rentEndDate === null ||
          this.item.rentEndDate.legnth <= 0 ||
          this.image === null ||
          this.image.length <= 0
        ) {
          alert("입력폼을 모두 입력해주세요.");
          return;
        }
        this.sendData()
      }
      else {
        if (this.item.itemName.length <= 0 ||
          this.item.division.length <= 0 ||
          this.item.category.length <= 0 ||
          this.item.price === null ||
          this.item.price <= 0 ||
          this.image === null ||
          this.image.length <= 0
        ) {
          alert("입력폼을 모두 입력해주세요.");
          return;
        }
        this.sendData()
      }

      /**
       * TODO: PJTⅢ 과제3 Req.1-1 [상품 등록]
       * DB에 상품 등록 후 반환 받은 id를 이용해서 이더리움에 상품을 등록
       */
    },
    // onFileChange (input) {
    //   var files = input.target.files || input.dataTransfer.files;
    //   if (!files.length) {
    //     return;
    //   }
    //   this.item.imgName = files[0].name;
    // },

    selectFile () {
      this.image = this.$refs.itemImage.files
      console.log(this.image)
    },
    changeDateFormat (date) {
      if (date) {
        return date.substring(0, 10) + " " + date.substring(11, 16)
      }
      else {
        return null
      }
    },
    sendData () {
      const formdata = new FormData()

      if (this.image) {
        for (let i = 0; i < this.image.length; i++) {
          formdata.append("multipartFile", this.image[i]);
        }
      }
      formdata.append(
        "itemRegisterRequestDto",
        new Blob(
          [
            JSON.stringify({ ...this.item, rentStartDate: this.changeDateFormat(this.item.rentStartDate), rentEndDate: this.changeDateFormat(this.item.rentEndDate) })
          ],
          { type: "application/json" }
        )
      );
      const token = this.$store.state.user.JWTToken;

      const headers = {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${token}`
      }

      axios({
        url: `http://localhost:8080/api/item`,
        method: 'post',
        headers,
        data: formdata
      })
        .then((res) => {
          console.log('성공')
          console.log(res);
        })
        .catch((err) => {
          console.log('실패')
          console.log(err)
        })
    }
  },
};
</script>

<style>
.image-area {
  border: 2px dashed #ced4da;
  padding: 1rem;
  position: relative;
}

.image-area::before {
  content: "업로드 된 이미지";
  font-weight: bold;
  text-transform: uppercase;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 0.8rem;
  z-index: 1;
}

.image-area img {
  z-index: 2;
  position: relative;
}
</style>
