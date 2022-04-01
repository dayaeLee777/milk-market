<template>
  <div class="hotitemslide">
    <section class="cards">
      <article class="card card--2">
        <div class="card__info-hover">
          <svg class="card__like" viewBox="0 0 24 24">
            <path
              fill="#000000"
              d="M12.1,18.55L12,18.65L11.89,18.55C7.14,14.24 4,11.39 4,8.5C4,6.5 5.5,5 7.5,5C9.04,5 10.54,6 11.07,7.36H12.93C13.46,6 14.96,5 16.5,5C18.5,5 20,6.5 20,8.5C20,11.39 16.86,14.24 12.1,18.55M16.5,3C14.76,3 13.09,3.81 12,5.08C10.91,3.81 9.24,3 7.5,3C4.42,3 2,5.41 2,8.5C2,12.27 5.4,15.36 10.55,20.03L12,21.35L13.45,20.03C18.6,15.36 22,12.27 22,8.5C22,5.41 19.58,3 16.5,3Z"
            />
          </svg>
          <div class="card__clock-info">
            <svg class="card__clock" viewBox="0 0 24 24">
              <path
                d="M12,20A7,7 0 0,1 5,13A7,7 0 0,1 12,6A7,7 0 0,1 19,13A7,7 0 0,1 12,20M19.03,7.39L20.45,5.97C20,5.46 19.55,5 19.04,4.56L17.62,6C16.07,4.74 14.12,4 12,4A9,9 0 0,0 3,13A9,9 0 0,0 12,22C17,22 21,17.97 21,13C21,10.88 20.26,8.93 19.03,7.39M11,14H13V8H11M15,1H9V3H15V1Z"
              /></svg
            ><span class="card__time">5 min</span>
          </div>
        </div>
        <div class="card__img"></div>
        <a href="#" class="card_link">
          <div class="card__img--hover"></div>
        </a>
        <div class="card__info">
          <span class="card__category">역삼동</span>
          <h3 class="card__title">카시트 A급</h3>
          <div class="badges">
            <!-- <span class="badge badge-pill badge-primary">판매</span>
            <span class="badge badge-pill badge-info">유모차</span>
            <span class="badge badge-pill badge-success">판매중</span> -->
            <!-- <span class="badge">1</span>
            <span class="badge badge-success">2</span>-->
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
              대여</span
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
              카시트</span
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
              판매중</span
            >
          </div>
          <span class="card__by"><a href="#" class="card__author" title="author">100</a> Milk</span>
        </div>
      </article>
    </section>
  </div>
</template>

<script>
export default {
  name: "HotItemSlide",
};
</script>

<style scope>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
@import url("https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.googleapis.com/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

.hotitemslide * {
  box-sizing: border-box;
}

/* body,
html {
  font-family: "Roboto Slab", serif;
  margin: 0;
  width: 100%;
  height: 100%;
  padding: 0;
}

body {
  background-color: #d2dbdd;
  display: flex;
  display: -webkit-flex;
  -webkit-justify-content: center;
  -webkit-align-items: center;
  justify-content: center;
} */
</style>

<style>
.hotitemslide .cards {
  color: black;
  font-family: "Roboto Slab", serif;
  width: 80%;
  display: flex;
  display: -webkit-flex;
  justify-content: center;
  -webkit-justify-content: center;
  /* max-width: 820px; */
  margin-left: 70px;
}

.hotitemslide .card--1 .card__img,
.hotitemslide .card--1 .card__img--hover {
  background-image: url("https://images.pexels.com/photos/45202/brownie-dessert-cake-sweet-45202.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
}

.hotitemslide .card--2 .card__img,
.hotitemslide .card--2 .card__img--hover {
  background-image: url("https://images.pexels.com/photos/307008/pexels-photo-307008.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
}

.hotitemslide .card__like {
  width: 18px;
}

.hotitemslide .card__clock {
  width: 15px;
  vertical-align: middle;
  fill: #ad7d52;
}
.hotitemslide .card__time {
  font-size: 12px;
  color: #ad7d52;
  vertical-align: middle;
  margin-left: 5px;
}

.hotitemslide .card__clock-info {
  float: right;
}

.hotitemslide .card__img {
  visibility: hidden;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 235px;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
}

.hotitemslide .card__info-hover {
  position: absolute;
  padding: 16px;
  width: 100%;
  opacity: 0;
  top: 0;
}

.hotitemslide .card__img--hover {
  transition: 0.2s all ease-out;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  position: absolute;
  height: 235px;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  top: 0;
}
.hotitemslide .card {
  margin: 25px;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0, 1);
  background-color: #fff;
  width: 80%;
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0px 13px 10px -7px rgba(0, 0, 0, 0.1);
}
.hotitemslide .card:hover {
  box-shadow: 0px 30px 18px -8px rgba(0, 0, 0, 0.1);
  transform: scale(1.1, 1.1);
}

.hotitemslide .card__info {
  z-index: 2;
  background-color: #fff;
  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
  padding: 16px 24px 24px 24px;
}

.hotitemslide .card__category {
  /* font-family: "Noto Sans KR", sans-serif; */
  font-family: "Jua", sans-serif;
  font-size: 13px;
  letter-spacing: 2px;
  font-weight: 500;
  color: #868686;
}

.hotitemslide .card__title {
  margin-top: 5px;
  margin-bottom: 10px;
  font-family: "Black Han Sans", sans-serif;
}

.hotitemslide .card__by {
  font-size: 12px;
  font-family: "Raleway", sans-serif;
  font-weight: 500;
}

.hotitemslide .card__author {
  font-weight: 600;
  text-decoration: none;
  color: #ad7d52;
}

.hotitemslide .card:hover .card__img--hover {
  height: 100%;
  opacity: 0.3;
}

.hotitemslide .card:hover .card__info {
  background-color: transparent;
  position: relative;
}

.hotitemslide .card:hover .card__info-hover {
  opacity: 1;
}

.badges .badge {
  margin-right: 5px;
  padding: 4px 10px 2px;
  font-family: "Noto Sans KR", sans-serif;
  font-size: 12px;
  font-weight: bold;
  white-space: nowrap;
  color: #ffffff;
  background-color: #999999;
  -webkit-border-radius: 9px;
  -moz-border-radius: 9px;
  border-radius: 9px;
}
.badges .badge:hover {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
}
.badges .badge-error {
  background-color: #f0423f;
}
.badges .badge-error:hover {
  background-color: #953b39;
}
.badges .badge-warning {
  background-color: #8a3aff;
}
.badges .badge-warning:hover {
  background-color: #692bc5;
}
.badges .badge-success {
  background-color: #468847;
}
.badges .badge-success:hover {
  background-color: #356635;
}
.badges .badge-info {
  background-color: #6f79ff;
}
.badges .badge-info:hover {
  background-color: #454cac;
}
.badges .badge-inverse {
  background-color: #3a9fff;
}
.badges .badge-inverse:hover {
  background-color: #2974bb;
}
</style>
