<template>
  <div class="profile-content">
  <h3 class="text-center my-5">나의 wish 리스트</h3>
  <div class="my-content-body">
    <div v-if="!mywishList.length">
      <h4 class="text-center">아직 찜한 아이템이 없어요!</h4>
    </div>
    <div v-else>
      <div 
        v-for="wish in wishpaginatedData"
        :key="wish.itemId"
        @click="moveToItemDetail(wish.itemId)"
        class="my-content px-3 d-flex justify-content-between align-items-center">
        <span>상품 명: {{ wish.itemName }}</span>
        <span style="font-size: 10px">카테고리: {{ wish.category}}</span>
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
</template>

<script>
export default {
  name: "MyWish",
  data() {
    return {
      pageNum: 0,
    }
  },
  props: {
    mywishList: {
      type: Array,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 8
    }    
  },
  methods: {
    moveToItemDetail(itemId) {
      this.$emit("move-to-item-detail", itemId)
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
      let listLeng = this.mywishList.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;
      return page;
    },
    wishpaginatedData () {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;
      return this.mywishList.slice(start, end);
    }
  }
}
</script>

<style>
.my-content-body {
  height: 330px;
}
</style>
