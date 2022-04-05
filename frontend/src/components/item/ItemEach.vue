<template>
  <div class="hotitemslide" @click="itemDetail(content.id)">
    <section class="cards">
      <article class="card card--2">
        <img
          v-if="!contentImage()"
          :src="item_img"
          class="card__img"
          alt="사진없음"
        />
        <img v-else :src="content.fileNameList[key]" class="card__img" alt="" />

        <div class="card_link" @click="itemDetail(content.id)">
          <img class="card__img--hover" :src="content.fileNameList[key]" />
        </div>
        <div class="card__info">
          <span class="card__category">{{ content.bname }}</span>
          <h3 class="card__title">{{ content.itemName }}</h3>
          <div class="badges">
            <span class="badge badge-warning">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-bucket"
                viewBox="0 0 16 16"
              >
                <path
                  d="M2.522 5H2a.5.5 0 0 0-.494.574l1.372 9.149A1.5 1.5 0 0 0 4.36 16h7.278a1.5 1.5 0 0 0 1.483-1.277l1.373-9.149A.5.5 0 0 0 14 5h-.522A5.5 5.5 0 0 0 2.522 5zm1.005 0a4.5 4.5 0 0 1 8.945 0H3.527zm9.892 1-1.286 8.574a.5.5 0 0 1-.494.426H4.36a.5.5 0 0 1-.494-.426L2.58 6h10.838z"
                />
              </svg>
              {{ content.division }}</span
            >
            <!-- <span class="badge badge-error">대여</span> -->
            <span class="badge badge-info"
              ><svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-tag"
                viewBox="0 0 16 16"
              >
                <path
                  d="M6 4.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm-1 0a.5.5 0 1 0-1 0 .5.5 0 0 0 1 0z"
                />
                <path
                  d="M2 1h4.586a1 1 0 0 1 .707.293l7 7a1 1 0 0 1 0 1.414l-4.586 4.586a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 1 6.586V2a1 1 0 0 1 1-1zm0 5.586 7 7L13.586 9l-7-7H2v4.586z"
                />
              </svg>
              {{ content.category }}</span
            >
            <span class="badge badge-inverse">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-bookmark-heart"
                viewBox="0 0 16 16"
              >
                <path
                  fill-rule="evenodd"
                  d="M8 4.41c1.387-1.425 4.854 1.07 0 4.277C3.146 5.48 6.613 2.986 8 4.412z"
                />
                <path
                  d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"
                />
              </svg>
              {{ content.status }}</span
            >
          </div>
          <span class="card__by"
            ><a href="#" class="card__author" title="author">{{
              content.price
            }}</a>
            Milk</span
          >
        </div>
      </article>
    </section>
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
    this.key = Object.keys(this.content.fileNameList)[0];
  },
  methods: {
    itemDetail(itemId) {
      this.$router.push({
        name: "item.detail",
        params: { id: itemId },
      });
    },
    contentImage() {
      this.key = Object.keys(this.content.fileNameList)[0];

      if (this.key) return true;
      return false;
    },
  },
};
</script>

<style scoped>
/* img.card-img-top {
   width: 286px; 
  height: 150px;
  object-fit: cover;
} */

@import url("https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap");
@import url("//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css");
@import url(//fonts.googleapis.com/css?family=Open+Sans:400,700,800,300);

.carousel-3d-container .carousel-3d-slide {
  border: 0;
  background: transparent;
  align-content: center;
  width: 90%;
}

.sect .container {
  margin: 0 auto;
  width: 90%;
  max-width: 900px;
}

.sect {
  padding: 90px 0;
  position: relative;
}

.sect--padding-bottom {
  padding-bottom: 115px;
}

.row--center {
  max-width: 1000px;
  margin: 0 auto;
}

.row__title {
  margin-top: 0px;
  text-align: center;
  font-family: "Black Han Sans", sans-serif;
  font-size: 30px;
  color: #1f4568;
}

.row__sub {
  text-align: center;
  font-size: 22px;
  color: #8198ae;
  margin: 15px 0 40px 0;
  font-family: "Noto Sans KR", sans-serif;
}

@media screen and (max-width: 1024px) {
}

@media screen and (max-width: 991px) {
}

@media screen and (max-width: 767px) {
}

@media screen and (max-width: 480px) {
  .sect {
    padding: 20px 0;
  }
  .row__sub {
    font-size: 16px;
  }
  .row__title {
    font-size: 20px;
    margin-bottom: 5px;
  }
}
</style>