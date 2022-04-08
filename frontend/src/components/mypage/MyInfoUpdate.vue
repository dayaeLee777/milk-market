<template>
  <div id="profile-body">
    <!-- <h-breadcrumb></h-breadcrumb> -->
    <div class="container">
      <my-page-nav></my-page-nav>
      <div class="d-flex flex-column">
        <div
          class="d-flex justify-content-start align-items-center mt-5"
          id="profile-tag"
        >
          <div
            class="
              d-flex
              flex-column
              col-2
              align-items-center
              ms-5
              image_outer_container
            "
          >
            <div v-if="user.profileImage">
              <div class="image_inner_container">
                <img
                  :src="user.profileImage"
                  alt="profile-image"
                  class="profile-img"
                />
              </div>
            </div>
            <div v-else>
              <div class="image_inner_container">
                <img
                  src="../../../public/images/default.png"
                  alt="profile-img"
                  class="profile-img"
                />
              </div>
            </div>
            <button
              class="btn btn-secondary mt-2"
              id="profile-btn"
              data-bs-toggle="modal"
              data-bs-target="#profileImage"
            >
              프로필 변경
            </button>
          </div>
          <div style="margin-left: 50px">
            <p class="profile-info">이메일 : {{ user.email }}</p>
            <p class="profile-info">닉네임: {{ user.nickName }}</p>
            <div class="d-flex align-items-center">
              <p class="profile-info">현재 주소: {{ user.sigungu }} {{ user.bname }}</p>
              <button class="btn btn-secondary ms-3" 
                data-bs-toggle="modal"
                data-bs-target="#addressModal">주소 변경</button>
            </div>
            <div class="modal fade" id="addressModal" tabindex="-1" aria-labelledby="addressModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="addressModalLabel">주소 변경</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="d-flex form-group">
                    <input type="text" class="col-9 form-control" id="location" placeholder="주소" />
                    <input
                      type="button"
                      class="col-3 form-control"
                      value="찾기"
                      @click="execDaumPostcode()"
                    />
                  </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateLocation">변경</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <hr>
        <div class="d-flex justify-content-around">
          <my-community
            :communities="communities"
            @move-to-community="moveToCommunity"
          ></my-community>
          <my-wish
            :mywish-list="mywishList"
            @move-to-item-detail="moveToItem"
          ></my-wish>
        </div>
      </div>
      <div class="d-flex justify-content-start"></div>
      <div
        class="modal fade"
        id="profileImage"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">프로필 변경</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <label for="profile">프로필</label>
              <input
                type="file"
                id="profile"
                class="form-control"
                ref="profileImage"
                @change="selectProfile"
              />
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-primary"
                @click="changeProfile"
                data-bs-dismiss="modal"
              >
                변경
              </button>
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                닫기
              </button>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import MyPageNav from "./MyPageNav.vue";
import MyWish from "./MyWish.vue";
import MyCommunity from "./MyCommunity.vue"
import jwt_decode from "jwt-decode";
import axios from "axios";
import Swal from 'sweetalert2/dist/sweetalert2.js'
import {
  BLOCKCHAIN_URL,
  CASH_CONTRACT_ADDRESS,
  API_BASE_URL,
} from "@/config/index.js";

export default {
  components: {
    MyPageNav,
    MyCommunity,
    MyWish,
  },
  data() {
    return {
      user: {
        email: "",
        nickName: "",
        address: "",
        eth: "",
        milk: "",
        profileImage: "",
        sigungu: "",
        bname: "",
        bcode: "",
      },
      image: "",
      communities: [],
      mywishList: [],
    };
  },
  methods: {
    // 회원 정보 가져온다.
    fetchUserInfo() {
      const token = this.$store.state.user.JWTToken;
      const decode = jwt_decode(token);
      console.log(decode.sub);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/users/`,
        method: "get",
        headers,
      })
        .then((res) => {
          this.user.nickName = res.data.nickname;
          this.user.email = res.data.email;
          this.user.profileImage = res.data.profileImage;
          // this.$route.push({ name: "communityDetail", params: { profileImage: res.data.profileImage} })
          //vuex 저장 코드를 넣는곳
          this.$store.commit('setUserProfileImage', res.data.profileImage);
          this.user.sigungu = res.data.sigungu;
          this.user.bname = res.data.bname;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchCommunity() {
      const token = this.$store.state.user.JWTToken;

      const headers  = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/community/list/myList`,
        method: 'get',
        headers,
      })
      .then( res => {
        // console.log(res.data.communityGetResponselist)
        this.communities = res.data.communityGetResponselist;
      })
      .catch( err => {
        console.log(err)
      })
    },
    getWishList() {
      const token = this.$store.state.user.JWTToken;

      const headers  = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/interest/list/`,
        method: 'get',
        headers,
      })
      .then( res => {
        // console.log(res.data.interestListResponseDto)
        this.mywishList = res.data.interestListResponseDto;
      })
      .catch( err => {
        console.log(err)
      })
    },
    selectProfile() {
      this.image = this.$refs.profileImage.files[0];
    },
    changeProfile() {
      const formdata = new FormData();
      console.log(this.image);
      formdata.append("multipartFile", this.image);

      const token = this.$store.state.user.JWTToken;
      const headers = {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/users/profileImage`,
        method: "post",
        headers,
        data: formdata,
      })
        .then((res) => {
          console.log(res);
          Swal.fire({
          position: "center",
          icon: "success",
          title: "프로필 변경 성공",
          showConfirmButton: false,
          timer: 1500,
          }); 
          // 새로고침할때 이거 쓰자.
          this.fetchUserInfo();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    execDaumPostcode() {
      const vm = this;
      new daum.Postcode({
        oncomplete: function (data) {
          const addr = data.address;
          document.getElementById("location").value = addr;
          vm.user.bname = data.bname;
          vm.user.sigungu = data.sigungu;
          vm.user.bcode = data.bcode;
          console.log(vm.user);
        },
      }).open();
    },    
    moveToItem(itemId) {
      this.$router.push({name: 'item.detail', params: {id: itemId}})
    },
    moveToCommunity(communityId, userNickname) {
      this.$router.push({name: 'communityDetail', params: { coId: communityId, userN: userNickname }})
    },    
    updateLocation() {
      const token = this.$store.state.user.JWTToken;

      const headers  = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/users/address/update`,
        method: 'put',
        headers,
        data: {
          bcode: this.user.bcode,
          bname: this.user.bname,
          sigungu: this.user.sigungu,
        }
      })
      .then( res => {
          Swal.fire({
          position: "center",
          icon: "success",
          title: "주소가 변경되었어요!",
          showConfirmButton: false,
          timer: 1500,
        }); 
      })
      .catch( err => {
        console.log(err)
      }) 
    }
  },
  mounted() {
    this.fetchUserInfo();
    this.fetchCommunity();
    this.getWishList();
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
@import url("https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

#profile-body {
  font-family: "Noto Sans KR", sans-serif;
}
.profile-content{
  width: 450px;
  height: 470px;
}
.profile-img {
  height: 200px;
  width: 180px;
  border-radius: 10px;
}
#profile-btn {
  width: 90px;
  font-size: 10px;
}
.my-content {
  margin: 15px;
  cursor: pointer;
  background-color: #e9ecf3;
  border-radius: 20px;
  box-shadow: 1px 1px 1.5px 1px gray;
}
.profile-info {
  font-size: 18px;
}
/* #profile-tag{
    border-style: solid;
    border-width: 3px;
    border-radius: 30px;
    border-color: #647ec0;
    width: 1000px;
    height: 300px;
    background-color: #fdefff;
  } */
.image_outer_container {
  margin-top: auto;
  margin-bottom: auto;
  border-radius: 50%;
  position: relative;
}

.image_inner_container {
  border-radius: 50%;
  padding: 5px;
  background: #833ab4;
  background: -webkit-linear-gradient(to bottom, #fcb045, #fd1d1d, #833ab4);
  background: linear-gradient(to bottom, #fcb045, #fd1d1d, #833ab4);
}
.image_inner_container img {
  height: 200px;
  width: 200px;
  border-radius: 50%;
  border: 5px solid white;
}

.image_outer_container .green_icon {
  background-color: #4cd137;
  position: absolute;
  right: 30px;
  bottom: 10px;
  height: 30px;
  width: 30px;
  border: 5px solid white;
  border-radius: 50%;
}
</style>
