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
      // userNickname: null,
    },
  },
  mutations: {
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
<<<<<<< HEAD
    // setUserNickname (state, userNickname) {
    //   state.user.userNickname = userNickname;
    // },
    logout (state) {
=======
    logout(state) {
>>>>>>> c0c6de7b24ce836b4a0135d594c1feff3960988b
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
  },
  actions: {},
  modules: {},
  plugins: [createPersistedState()],
});
