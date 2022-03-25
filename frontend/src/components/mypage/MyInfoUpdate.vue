<template>
  <div>
    <h-breadcrumb
    ></h-breadcrumb>    
    <div class="container">
      <my-page-nav></my-page-nav>
      <div class="d-flex mt-5">
        <div class="d-flex flex-column col-2">
          <img id="profile-img" src="../../../public/images/ompang2.png" alt="profile-img" class="img-thumbnail"> 
          <button class="btn btn-secondary">프로필 사진 변경</button>
        </div>
        <div class="ms-3">
          <p>이메일: </p>
          <p>닉네임: </p>
          <div class="d-flex align-items-center">
            <p>현재 주소: </p>
            <button class="btn btn-secondary ms-3">주소 변경</button>
          </div>
          <p>보유 ETH: </p>
          <p>보유 MILK: </p>
        </div>
      </div>
      <div class="mt-3">
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
      </div>
    </div>
  </div>
</template>
<script>
import MyPageNav from "./MyPageNav.vue";

export default {
  components: {
    MyPageNav,
  },
  data() {
    return {
      
    }
  },
  methods: {
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
  }
}
</script>

<style>
  #profile-img{
    height: 150px;
    border-radius: 80px;
  }
</style>