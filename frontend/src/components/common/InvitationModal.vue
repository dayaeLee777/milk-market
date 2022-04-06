<template>
  <div v-if="showModal">
    <transition name="modal">
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div
            class="modal-dialog"
            role="document"
          >
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title">채팅룸 입장</h5>
              </div>
              <div
                v-for="(chatRoom, index) in chatRooms"
                :key="index"
                class="modal-footer p-0 mx-auto"
              >
                <h5>채팅 요청 : {{ chatRoom.userNickname }}</h5>
                <button
                  type="button"
                  class="btn btn-primary"
                  @click="goChatting(chatRoom)"
                >Accept</button>
                <button
                  type="button"
                  class="btn btn-primary"
                  @click="closeModal(chatRoom)"
                >Reject</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import firebase from 'firebase'
import { mapState } from 'vuex'

export default {

  methods: {
    closeModal (chatRoom) {
      this.$store.dispatch('turnOFFNotification')
      this.resetFirebaseUserStatus(chatRoom)
    },
    goChatting (chatRoom) {
      this.$store.dispatch('turnOFFNotification')
      this.$store.dispatch('clearInterval')
      this.$router.push({ name: "room", params: { sessionId: chatRoom.sessionId, profileImage: chatRoom.profileImage } });
    },
    resetFirebaseUserStatus (chatRoom) {
      db.collection('user').doc(this.user.userNickname).update({
        chatRooms: firebase.firestore.FieldValue.arrayRemove(chatRoom)
      }).then(() => {
        console.log('remove')
      })
    }
  },
  computed: {
    ...mapState([
      'user',
      'showModal',
      'isSigned',
      'chatRooms',
    ]),
  },
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
</style>