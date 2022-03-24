<template>
  <div
    id="main-overview"
    class="container"
  >
    <div class="row row-cols-2 row-cols-md-4 g-5">
      <div
        class="col"
        v-for="(content, index) in contents"
        :key="index"
      >
        <item-each :content="content"></item-each>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ItemEach from '@/components/item/ItemEach';

export default {

  components: {
    ItemEach
  },
  data: () => ({
    contents: [
      { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
      { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
      { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
      { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
      { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
      { item_img: 'https://www.ktong.kr/wp-content/uploads/2020/08/%EC%82%AC%EA%B3%BC-%EB%8B%A4%EC%9D%B4%EC%96%B4%ED%8A%B8-5%EC%9D%BC%EA%B0%84%EC%9D%98-%EA%B8%B0%EC%A0%81%EC%9D%84-%EA%B2%BD%ED%97%98%ED%95%98%EC%84%B8%EC%9A%94.jpg', item_name: '사과', item_introduce: '맛있는 사과 팔아요' },
    ]
  })
}
</script>

<style>
</style>