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
      profileImage: '',
    },
    interval: null,
    showModal: false,
    chatRooms: [],
  },
  mutations: {
    setBcode (state, bcode) {
      state.user.bcode = bcode;
      console.log("여기는 세팅 : " + state.user.bcode);
    },
    setIsSigned (state, isSigned) {
      state.isSigned = isSigned;
    },
    setUserId (state, id) {
      state.user.id = id;
    },
    setWalletAddress (state, address) {
      state.user.walletAddress = address;
    },
    setJWTToken (state, token) {
      state.user.JWTToken = token;
    },
    setUserNickname (state, userNickname) {
      state.user.userNickname = userNickname;
    },
    setUserProfileImage (state, profileImage) {
      state.user.profileImage = profileImage;
    },
    logout (state) {
      state.isSigned = false;
      state.showModal = false;
      state.chatRooms = [];
      state.interval = null;
      state.user = {
        id: null,
        walletAddress: null,
        JWTToken: null,
        userNickname: null,
        profileImage: '',
        bcode: null,
      };
    },
    TURN_ON_NOTIFICATION (state, data) {
      state.showModal = data
    },
    SET_CHATROOMS (state, data) {
      state.chatRooms = data
    },
    TURN_OFF_NOTIFICATION (state) {
      state.showModal = false
    },
    SET_INTERVAL (state, data) {
      state.interval = data
    },
    CLEAR_INTERVAL (state) {
      clearInterval(state.interval)
    }
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
  actions: {
    turnOnNotification: function ({ commit }, notification) {
      commit('TURN_ON_NOTIFICATION', notification)
    },
    turnOFFNotification: function ({ commit }) {
      commit('TURN_OFF_NOTIFICATION')
    },
    getChatRooms: function ({ commit }, chatRooms) {
      commit('SET_CHATROOMS', chatRooms)
    },
    setInterval: function ({ commit }, userNickname) {
      commit('SET_INTERVAL', setInterval(() => {
        db.collection('user').doc(userNickname).get().then((doc) => {
          if (doc.exists) {
            console.log('firebase!!!')
            const notification = doc.data().chatRooms.some((obj) => {
              return obj.notification
            })
            this.dispatch('turnOnNotification', notification)
            this.dispatch('getChatRooms', doc.data().chatRooms)
          }
        })
      }, 10000))
    },
    clearInterval: function ({ commit }) {
      commit('CLEAR_INTERVAL')
    }
  },
  modules: {},
  plugins: [createPersistedState()],
});
