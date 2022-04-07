<template>
  



  
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

  },
  method: {
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
  }
}
</script>

<style>

</style>