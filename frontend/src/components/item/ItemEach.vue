<template>
  <div
    class="card col-sm-12 col-lg-6"
    @click="itemDetail(content.itemId)"
    style="cursor: pointer"
  >
    <img v-if="!key" :src="item_img" class="card-img-top" alt="사진없음" />
    <img v-else :src="content.files[key]" class="card-img-top" alt="" />
    <div class="card-body">
      <div>
        <h5 class="card-title">{{ content.itemName }}</h5>
        <hr />
        <p class="card-text" id="fix">{{ content.description }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    content: Object,
  },
  data() {
    return {
      key: "",
      item_img:
        "https://3.bp.blogspot.com/-ZKBbW7TmQD4/U6P_DTbE2MI/AAAAAAAADjg/wdhBRyLv5e8/s1600/noimg.gif",
    };
  },
  mounted() {
    this.key = Object.keys(this.content.files)[0];
    console.log("key 출력" + this.key);
  },
  methods: {
    itemDetail(itemId) {
      this.$router.push({
        name: "item.detail",
        params: { id: this.content.itemId },
      });
      console.log("경로 : " + this.content.files[this.key]);
    },
  },
};
</script>

<style scoped>
img.card-img-top {
  width: 286px;
  height: 150px;
  object-fit: cover;
}
</style>