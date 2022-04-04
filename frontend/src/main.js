import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import HNav from "./components/common/HNav.vue";
import FNav from "./components/common/FNav.vue";
import HBreadcumb from "./components/common/HBreadcrumb.vue";
import vuetify from "./plugins/vuetify";

import firebase from "firebase";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
require("firebase/firestore");

Vue.use(BootstrapVue);
const config = {
  apiKey: "AIzaSyAMpY-nVtLuHyM1GcnecPcZ8IBqIAShcvk",
  authDomain: "milk-91702.firebaseapp.com",
  projectId: "milk-91702",
  storageBucket: "milk-91702.appspot.com",
  messagingSenderId: "724283696048",
  appId: "1:724283696048:web:7fd7ff6d4f203f1fd2ec75",
  measurementId: "G-W5WLDZLQY2",
};

firebase.initializeApp(config);

const db = firebase.firestore();

window.db = db;

db.settings({
  timestampsInSnapshots: true,
});

Vue.config.productionTip = false;
Vue.filter("truncate", function (text, length, clamp) {
  if (text) {
    clamp = clamp || "...";
    return text.length > length ? text.slice(0, length) + clamp : text;
  }
});

window.Kakao.init("700fe3eb25ade19a26c18b71610a4b34");
new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");

Vue.component("HNav", HNav);
Vue.component("FNav", FNav);
Vue.component("HBreadcrumb", HBreadcumb);
Vue.use(IconsPlugin);
