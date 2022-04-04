const CommonMethodsPlugin = {}

CommonMethodsPlugin.install = function (Vue) {
  Vue.prototype.$getFirebaseUserStatus = function (userNickname) {
    db.collection('user').doc(userNickname).get().then((doc) => {
      if (doc.exists) {
        this.$store.dispatch('turnOnNotification', doc.data().notification)
        this.$store.dispatch('getSessionId', doc.data().sessionId)
      }
    })
  }
  Vue.prototype.$resetFirebaseUserStatus = function (userNickname) {
    db.collection('user').doc(userNickname).set({
      notification: false,
      sessionId: '',
    }).then(() => {
      console.log('sucess')
    })
  }
}
export default CommonMethodsPlugin