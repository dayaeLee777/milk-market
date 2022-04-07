<template>
  <div class="profile-content">
    <h3 class="text-center my-5">내가 작성한 글</h3>
    <div v-if="!communities.length">
      <h4 class="text-center">아직 작성한 글이 없습니다!!</h4>
    </div>
    <div v-else>
      <div 
        v-for="community in communities"
        :key="community.communityId"
        class="my-content">
        <div class="d-flex"
          @click="moveToCommunity(community.communityId, community.userNickname)">
          제목: {{ community.title }}
        </div>
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
  method: {
    moveToCommunity(communityId, userNickname) {
      this.$router.push({name: 'communityDetail', params: { coId: communityId, userN: userNickname }})
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
      let listLeng = this.comments.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;
      return page;
    },
    paginatedData () {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;
      return this.comments.slice(start, end);
    }
  },
}
</script>

<style>

</style>