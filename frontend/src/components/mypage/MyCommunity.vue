<template>
  <div class="profile-content">
    <h3 class="text-center my-5">내가 작성한 글</h3>
    <div class="my-content-body">
      <div v-if="!communities.length">
        <h4 class="text-center">아직 작성한 글이 없습니다!!</h4>
      </div>
      <div v-else>
        <div 
          v-for="community in paginatedData"
          :key="community.communityId"
          class="my-content">
          <div class="d-flex justify-content-between align-items-center px-5"
            @click="moveToCommunity(community.communityId, community.userNickname)">
            <span style="font-size: 18px">
              제목: {{ community.title }}
            </span>
            <span style="font-size: 10px">
              {{ community.regTime }}
            </span>
          </div>
        </div>
    </div>
    <div class="text-center">
      <button :disabled="pageNum === 0" @click="prevPage" class="btn">
        이전
      </button>
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="btn">
        다음
      </button>
    </div>        
    </div>
  </div>  
</template>

<script>
import Swal from 'sweetalert2/dist/sweetalert2.js'
import axios from "axios";
import {
  BLOCKCHAIN_URL,
  CASH_CONTRACT_ADDRESS,
  API_BASE_URL,
} from "@/config/index.js";


export default {
  name: "MyCommunity",
  props: {  
    communities: {
      type: Array,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 8
    }
  },
  data() {
    return {
      pageNum: 0,
    }
  },
  methods: {
    moveToCommunity(communityId, userNickname) {
      this.$emit('move-to-community', communityId, userNickname)
    },    
    nextPage () {
      this.pageNum += 1;
    },
    prevPage () {
      this.pageNum -= 1;
    },
  },
  computed: {
    pageCount () {
      let listLeng = this.communities.length
      let listSize = this.pageSize
      let page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;
      return page;
    },
    paginatedData () {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;
      return this.communities.slice(start, end);
    }
  },
}
</script>

<style>

</style>