const CommonMethodsPlugin = {}

CommonMethodsPlugin.install = function (Vue) {
  Vue.prototype.$getFirebaseUserStatus = function (userNickname) {
    db.collection('user').doc(userNickname).get().then((doc) => {
      if (doc.exists) {
        console.log('firebase!!!')
        this.$store.dispatch('turnOnNotification', doc.data().notification)
        this.$store.dispatch('getSessionId', doc.data().sessionId)
      }
    })
  }
  Vue.prototype.$resetFirebaseUserStatus = function (userNickname) {
    // db.collection('user').doc(userNickname).get().then((doc) => {
    //   if (doc.exists) {
    //     console.log('firebase!!!')
    //     console.log(doc.data().chatRooms)
    //     const notification = doc.data().chatRooms.some((obj) => {
    //       return obj.notification
    //     })
    //     this.dispatch('turnOnNotification', notification)
    //     this.dispatch('getChatRooms', doc.data().chatRooms)
    //   }
    // })
  }
}
export default CommonMethodsPlugin