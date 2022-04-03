<template>
  <div id="headernav">
    <header class="header">
      <div class="container header__container">
        <router-link class="navbar-brand" to="/">
          <div class="header__logo">
            <img class="header__img" src="https://image.ibb.co/kcVou6/path3000.png" />
            <!-- <img class="header__img" src="../../../public/images/favicon.png" /> -->
            <h1 class="header__title">Milk<span class="header__light">Market</span></h1>
          </div>
        </router-link>
        <button
          type="button"
          class="navbar-toggle collapsed"
          data-toggle="collapse"
          data-target="#navbar"
          aria-expanded="false"
          aria-controls="#navbar"
        >
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>

        <div class="header__menu">
          <nav id="navbar" class="header__nav collapse">
            <ul class="header__elenco">
              <li class="header__el">
                <router-link class="nav-link" to="/">
                  <a class="header__link">Home</a>
                </router-link>
              </li>
              <li class="header__el">
                <router-link class="nav-link" to="/shop">
                  <a class="header__link">Shop</a>
                </router-link>
              </li>

              <li class="header__el">
                <router-link class="nav-link" to="/community"
                  ><a class="header__link">Community</a>
                </router-link>
              </li>
              <li class="header__el" v-if="$store.state.isSigned">
                <router-link
                  class="nav-link"
                  to="/mypage/wallet_create"
                  v-if="!$store.state.user.walletAddress"
                >
                  <a class="header__link">MyPape</a></router-link
                >
                <router-link
                  class="nav-link"
                  to="/mypage/wallet_info"
                  v-if="$store.state.user.walletAddress"
                >
                  <a class="header__link">MyPape</a></router-link
                >
              </li>
              <!-- <li class="header__el" v-if="$store.state.isSigned">
                <div v-if="profileImage">
                  <img
                    :src="profileImage"
                    alt="profile-img"
                    class="my-img"
                    @click="moveToMypage()"
                  />
                </div>
                <div v-else>
                  <img
                    src="../../../public/images/ompang2.png"
                    alt="profile-img"
                    class="my-img"
                    @click="moveToMypage()"
                  />
                </div>
              </li> -->
              <li class="header__el header__el--white">
                <router-link class="nav-link" to="/login" v-if="!$store.state.isSigned">
                  <a class="btn btn--white">Sign In</a>
                </router-link>
                <router-link class="nav-link" to="/logout" v-else>
                  <a class="btn btn--white">Log out</a>
                </router-link>
              </li>
              <li class="header__el header__el--blue" v-if="!$store.state.isSigned">
                <router-link class="nav-link" to="/register">
                  <a class="btn btn--blue">Sign Up</a>
                </router-link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </header>
  </div>
</template>

<script>
import axios from "axios";
import jwt_decode from "jwt-decode";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: "Header",

  data() {
    return {
      profileImage: "",
    };
  },

  methods: {
    moveToMypage() {
      this.$router.push("/mypage/update");
    },
    UserInfo() {
      const token = this.$store.state.user.JWTToken;
      const decode = jwt_decode(token);
      console.log(decode.sub);

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      axios({
        url: `${API_BASE_URL}/api/users/`,
        method: "get",
        headers,
      })
        .then((res) => {
          this.profileImage = res.data.profileImage;
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created() {
    this.UserInfo();
  },
};
</script>

<style scope>
@import url("https://fonts.googleapis.com/css?family=Lato:100,100i,300,300i,400,400i,700,700i,900,900i");

#headernav .collapse {
  display: block;
}

#headernav .btn {
  background-color: #3a9fff;
  border-radius: 20px;
  padding: 5px 20px;
  color: #fff;
  box-shadow: 0px 18px 13px -6px rgba(0, 0, 0, 0.2);
  transition: all 0.4s;
  -webkit-transition: all 0.4s;
  -moz-transition: all 0.4s;
  -ms-transition: all 0.4s;
  -o-transition: all 0.4s;
}

#headernav .btn:hover {
  box-shadow: 0px 25px 18px -6px rgba(0, 0, 0, 0.2);
  background-color: #63b2fc;
  color: #fff;
}

#headernav .btn--width {
  width: 120px;
}

#headernav .btn--white {
  background-color: #fff;
  border: 1px solid #ededed;
  color: #8198ae;
}

#headernav .btn--white:hover {
  background-color: #fff;
  color: #8198ae;
}

/*menu header*/
#headernav .header {
  padding: 30px 25px;
}
#headernav .header__elenco {
  padding: 0;
  margin: 0;
  list-style: none;
}
#headernav .header__menu {
  float: right t;
}
#headernav .header__el {
  padding: 0 0px;
  display: inline-block;
}
#headernav .header__img {
  display: inline-block;
  vertical-align: middle;
}

#headernav .header__logo,
#headernav .header__menu {
  display: inline-block;
  vertical-align: middle;
}
#headernav .header__title {
  font-size: 18px;
  margin: 0;
  margin-left: 10px;
  display: inline-block;
  line-height: 18px;
  vertical-align: middle;
}

#headernav .header__light {
  color: #8198ae;
}
#headernav .header__link {
  color: #1f4568;
  font-size: 16px;
  text-decoration-line: none;
}
#headernav .header__link:hover {
  text-decoration: none;
  color: #3a9fff;
  text-decoration-line: none;
}
/* #headernav .navbar-toggle .icon-bar {
  background-color: #8198ae;
}
#headernav .navbar-toggle {
  margin: 5px 0;
} */

#headernav .btn--up {
  position: absolute;
  bottom: -15px;
  right: 25px;
  z-index: 3;
}

#headernav .btn--blue {
  background-color: #3a9fff;
}
#headernav .btn--blue:hover {
  background-color: #63b2fc;
}
</style>

<style>
/* body {
  margin: 0;
  padding: 0;
  font-family: "Lato", sans-serif;
  color: #1f4568;
}

body {
  background-image: url("https://image.ibb.co/de6JzG/bitmap_1_1.png");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
} */

@media screen and (max-width: 1024px) {
  #headernav .collapse {
    display: none;
  }
  /* #headernav .navbar-toggle {
    margin: 0;
    display: inline-block;
    margin-right: 10px;
    margin-top: 10px;
  } */
  #headernav .header__container {
    width: 100%;
    padding: 0;
  }
  #headernav .header__menu {
    width: 100%;
  }
  #headernav .header {
    background-color: #fff;
    padding: 0;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 4;
  }
  #headernav .header__logo {
    padding: 12px 20px 12px 20px;
  }
  #headernav .header__el {
    display: block;
    padding: 10px 20px;
    border-top: 1px solid #ededed;
  }

  #headernav .header .btn--blue,
  #headernav .header .btn--blue:hover {
    padding: 0;
    border: 0;
    box-shadow: none;
    color: #fff;
    background-color: transparent;
  }
  #headernav .header__el--blue {
    background-color: #3a9fff;
  }

  #headernav .header .btn--white,
  #headernav .header .btn--white:hover {
    padding: 0;
    border: 0;
    box-shadow: none;
    color: #1f4568;
    background-color: transparent;
  }
  #headernav .header__el--white {
    background-color: white;
  }
}

@media screen and (max-width: 991px) {
}

@media screen and (max-width: 767px) {
}

@media screen and (max-width: 480px) {
  #headernav .btn {
    font-size: 12px;
    padding: 8px 8px;
  }
  #headernav .btn--width {
    width: 100px;
  }
}
</style>
