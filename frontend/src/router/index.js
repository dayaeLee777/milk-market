import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
// import Login from "@/views/Login.vue";
import store from "@/store";
import Shop from "@/views/Shop.vue";
import MyPage from "@/views/MyPage.vue";
import Item from "@/views/Item.vue";
import Explorer from "@/views/Explorer.vue";
import Escrow from "@/views/Escrow.vue";
import ScTestPage from "@/views/ScTestPage.vue";
import Map from "@/views/Map.vue";
import Community from "@/views/Community.vue";
// import CommunityWrite from "@/components/community/CommunityWrite.vue"
import Swal from "sweetalert2/dist/sweetalert2.js";
// import 'sweetalert2/src/sweetalert2.scss';
import ShopTest from "@/components/item/BackUPItemList.vue";
Vue.use(VueRouter);

/**
 * 아래의 router를 변경하여 구현할 수 있습니다.
 */
const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/shoptest",
    name: "shoptest",
    component: ShopTest,
  },
  // 게시판
  {
    path: "/community",
    name: "community",
    component: Community,
  },
  {
    path: "/room",
    name: "room",
    component: () => import("@/views/ChatRoom.vue"),
  },
  {
    path: "/commnunity/write",
    name: "commnunityWrite",
    component: () => import("@/components/community/CommunityWrite.vue"),
  },
  {
    path: "/commnunity/detail",
    name: "communityDetail",
    component: () => import("@/components/community/CommunityDetail.vue"),
  },
  {
    name: "login",
    path: "/login",
    component: () => import("@/views/Login.vue"),
  },
  {
    path: "/register",
    name: "signup",
    component: () => import("@/views/Signup.vue"),
  },
  {
    path: "/test",
    name: "sctest",
    component: ScTestPage,
  },
  {
    path: "/logout",
    name: "logout",
    beforeEnter(to, from, next) {
      store.commit("logout");
      const Swal = require("sweetalert2");
      Swal.fire({
        position: "center",
        icon: "success",
        title: "로그아웃되었습니다.",
        showConfirmButton: false,
        timer: 1500,
      });
      next("/");
    },
  },
  // 지도
  {
    path: "/map",
    name: "map",
    component: Map,
  },
  {
    name: "shop",
    path: "/shop",
    component: Shop,
    props: true,
    children: [
      {
        path: "",
        component: () => import("@/components/shop/All.vue"),
      },
      {
        path: "digital",
        component: () => import("@/components/shop/Digital.vue"),
      },
      {
        path: "child",
        component: () => import("@/components/shop/Child.vue"),
      },
      {
        path: "hobby",
        component: () => import("@/components/shop/Hobby.vue"),
      },
      {
        path: "list",
        component: () => import("@/components/shop/ShopItem.vue"),
      },
    ],
    redirect: () => {
      return "/shop";
    },
  },
  {
    name: "mypage",
    path: "/mypage",
    component: MyPage,
    children: [
      {
        name: "mypage.wallet.create",
        path: "wallet_create",
        component: () => import("../components/mypage/WalletCreate.vue"),
      },
      {
        name: "mypage.wallet.info",
        path: "/mypage/wallet_info",
        component: () => import("../components/mypage/WalletInfo.vue"),
      },
      {
        name: "mypage.items",
        path: "/mypage/items",
        component: () => import("../components/mypage/MyItems.vue"),
      },
      {
        name: "mypage.password",
        path: "/mypage/password",
        component: () => import("../components/mypage/Password.vue"),
      },
      {
        name: "mypage.update",
        path: "/mypage/update",
        component: () => import("../components/mypage/MyInfoUpdate.vue"),
      },
    ],
    redirect: () => {
      return "/mypage/items";
    },
  },
  {
    name: "item",
    path: "/item",
    component: Item,
    children: [
      {
        name: "item.create",
        path: "create",
        component: () => import("../components/item/ItemCreate.vue"),
      },
      {
        name: "item.detail",
        path: "detail/:id",
        component: () => import("../components/item/ItemDetail.vue"),
      },
      {
        name: "item.purchase",
        path: "purchase/:id",
        component: () => import("../components/item/ItemPurchase.vue"),
      },
    ],
  },
  {
    name: "escrow",
    path: "/escrow",
    component: Escrow,
    children: [
      {
        name: "escrow.purchase.detail",
        path: "purchase/detail",
        component: () => import("@/components/escrow/PurchaseTxDetail.vue"),
      },
      {
        name: "escrow.sale.detail",
        path: "sale/detail",
        component: () => import("@/components/escrow/SaleTxDetail.vue"),
      },
      {
        name: "escrow.history",
        path: "history/:id",
        component: () => import("@/components/escrow/EscrowHistory.vue"),
      },
    ],
  },
  {
    name: "explorer",
    path: "/explorer",
    component: Explorer,
    children: [
      {
        name: "explorer.dashboard",
        path: "dashboard",
        component: () => import("../components/explorer/Dashboard.vue"),
      },
      {
        name: "explorer.block",
        path: "blocks",
        component: () => import("../components/explorer/BlockListView.vue"),
      },
      {
        name: "explorer.block.detail",
        path: "block/:blockNumber",
        component: () => import("../components/explorer/BlockDetail.vue"),
      },
      {
        name: "explorer.tx",
        path: "txes",
        component: () => import("../components/explorer/TxListView.vue"),
      },
      {
        name: "explorer.tx.detail",
        path: "tx/:hash",
        component: () => import("../components/explorer/TxDetail.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const Swal = require("sweetalert2");
  let isSigned = store.state.isSigned;
  let isAvailableToGuest =
    ["/", "/login", "/register", "/test"].includes(to.path) ||
    to.path.startsWith("/explorer");

  // 로그인도 하지 않았고 게스트에게 허용된 주소가 아니라면 로그인 화면으로 이동한다.
  if (!isSigned && !isAvailableToGuest) {
    // alert("로그인을 하신 뒤에 사용이 가능합니다.");
    Swal.fire(
      "로그인 후 이용 가능합니다!",
      "로그인 페이지로 이동합니다.",
      "info"
    );
    next("/login");
  } else {
    next();
  }
});

export default router;
