<template>
  <div>
    <h-breadcrumb
      title="나의 정보 수정"
      description="나의 정보를 수정할 수 있습니다."
    ></h-breadcrumb>    
    <my-page-nav></my-page-nav>
    <div class="container">
      <input type="text" id="sample4_postcode" placeholder="우편번호">
      <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
      <input type="text" id="sample4_roadAddress" placeholder="도로명주소">
      <input type="text" id="sample4_jibunAddress" placeholder="지번주소">
      <span id="guide" style="color:#999;display:none"></span>
      <input type="text" id="sample4_detailAddress" placeholder="상세주소">
      <input type="text" id="sample4_extraAddress" placeholder="참고항목">
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
  }
}
</script>

<style>

</style>