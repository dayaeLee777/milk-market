<template>
  <div>
    <div
      v-for="(content, index) in contents"
      :key="index"
    >
      <product :content="content"></product>
    </div>
  </div>
</template>

<script>
import Product from '@/components/Product';
export default {
  components: {
    Product
  },
  data: () => ({
    contents: [{ product_name: "사과", product_introduce: '맛있는' }, { product_name: "사과", product_introduce: '맛있는' }, { product_name: "사과", product_introduce: '맛있는' }]
  })
}
</script>

<style>
</style>