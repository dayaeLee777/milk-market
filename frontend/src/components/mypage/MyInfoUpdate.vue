<template>
  <div>
    <h-breadcrumb
    ></h-breadcrumb>    
    <div class="container" id="profile-body">
      <my-page-nav></my-page-nav>      
      <div class="d-flex justify-content-start align-items-center mt-5" id="profile-tag">
        <div class="d-flex flex-column col-2 align-items-center ms-5 image_outer_container">
          <div v-if="user.profileImage">
            <div class="image_inner_container">
              <img :src="user.profileImage" alt="profile-image" class="profile-img">
            </div>
          </div>
          <div v-else>
            <div class="image_inner_container">
              <img src="../../../public/images/ompang2.png" alt="profile-img" class="profile-img"> 
            </div>
          </div>
          <button class="btn btn-secondary mt-2" id="profile-btn" data-bs-toggle="modal" data-bs-target="#profileImage">프로필 변경</button>
        </div>
        <div class="ms-3">
          <p>이메일 : {{ user.email }}</p>
          <p>닉네임: {{ user.nickName }} </p>
          <div class="d-flex align-items-center">
            <p>현재 주소: </p>
            <button class="btn btn-secondary ms-3">주소 변경</button>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-start">

      </div>
      <!-- 여기 모달창으로 변경 -->
      <!-- <div class="mt-3">
        <p>나의 주소 변경</p>
        <div class="d-flex">
          <input class="form-control col-2" type="text" id="sample4_postcode" placeholder="우편번호">
          <input type="button" class="btn btn-secondary mx-2" @click="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
        </div>
        <div class="d-flex my-2">
          <input type="text" class="form-control col-3 me-2" id="sample4_roadAddress" placeholder="도로명주소">
          <input type="text" id="sample4_jibunAddress" class="form-control col-3 me-2"  placeholder="지번주소">
        </div>
        <div class="d-flex my-2">
          <span id="guide" style="color:#999;display:none"></span>
          <input type="text" id="sample4_detailAddress" class="form-control col-3 me-2" placeholder="상세주소">
          <input type="text" id="sample4_extraAddress" class="form-control col-3 me-2" placeholder="참고항목">
        </div>
      </div> -->

<!-- Modal -->
<div class="modal fade" id="profileImage" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">프로필 변경</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <label for="profile">프로필</label>
        <input type="file" id="profile" class="form-control" ref="profileImage" @change="selectProfile">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" @click="changeProfile" data-bs-dismiss="modal">변경</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
      
    </div>
  </div>
</template>
<script>
import MyPageNav from "./MyPageNav.vue";
import axios from 'axios';
import jwt_decode from "jwt-decode";
import { BLOCKCHAIN_URL, CASH_CONTRACT_ADDRESS, API_BASE_URL } from "@/config/index.js";

export default {
  components: {
    MyPageNav,
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
      },
      image: "",
    }
  },
  methods: {
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
        this.user.nickName = res.data.nickname;
        this.user.email = res.data.email;
        this.user.profileImage = res.data.profileImage;
      })
      .catch( (err) => {
        console.log(err)
      });
    },    
    selectProfile() {
      this.image = this.$refs.profileImage.files[0];
    },
    changeProfile() {
      const formdata = new FormData();
      console.log(this.image);
      formdata.append('multipartFile', this.image);

      const token = this.$store.state.user.JWTToken;
      const headers = {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${token}`
      };

      axios({
        url: `${API_BASE_URL}/api/users/profileImage`,
        method: 'post',
        headers,
        data: formdata,
      })
      .then(res => {
        console.log(res);
        // 새로고침할때 이거 쓰자.
        this.$router.go();
      })
      .catch(err => {
        console.log(err);
      });

    },
    sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
  },
  mounted() {
    this.fetchUserInfo();
  }
}
</script>

<style>
  .profile-img{
    height: 200px;
    width: 180px;
    border-radius: 10px;
  }
  #profile-btn{
    width: 90px;
    font-size: 10px;
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
  .image_outer_container{
  margin-top: auto;
  margin-bottom: auto;
  border-radius: 50%;
  position: relative;
  }

  .image_inner_container{
  border-radius: 50%;
  padding: 5px;
  background: #833ab4; 
  background: -webkit-linear-gradient(to bottom, #fcb045, #fd1d1d, #833ab4); 
  background: linear-gradient(to bottom, #fcb045, #fd1d1d, #833ab4);
  }
  .image_inner_container img{
  height: 200px;
  width: 200px;
  border-radius: 50%;
  border: 5px solid white;
  }

  .image_outer_container .green_icon{
    background-color: #4cd137;
    position: absolute;
    right: 30px;
    bottom: 10px;
    height: 30px;
    width: 30px;
    border:5px solid white;
    border-radius: 50%;
  }  
</style>