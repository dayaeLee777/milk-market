import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

// const store = new Vuex.Store({
//   plugins: [createPersistedStanpmte()],
// });

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isSigned: false, // 로그인 여부
    user: {
      id: 0, // 사용자 아이디 저장
      walletAddress: null,
      JWTToken: null,
      userNickname: null,
      bcode: 0,
    },
  },
  mutations: {
    setBcode(state, bcode) {
      state.user.bcode = bcode;
      console.log("여기는 세팅 : " + state.user.bcode);
    },
    setIsSigned(state, isSigned) {
      state.isSigned = isSigned;
    },
    setUserId(state, id) {
      state.user.id = id;
    },
    setWalletAddress(state, address) {
      state.user.walletAddress = address;
    },
    setJWTToken(state, token) {
      state.user.JWTToken = token;
    },
    setUserNickname(state, userNickname) {
      state.user.userNickname = userNickname;
    },
    logout(state) {
      state.isSigned = false;
      state.user.id = 0;
      state.user.walletAddress = null;
    },
  },
  getters: {
    getJWTToken: function (state) {
      return state.user.JWTToken;
    },
    getUserId: function (state) {
      return state.user.id;
    },
    getBcode: function (state) {
      return state.user.bcode;
    },
  },
  actions: {},
  modules: {},
  plugins: [createPersistedState()],
});
