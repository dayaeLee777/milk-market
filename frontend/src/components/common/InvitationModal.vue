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
              <div class="modal-body">

              </div>
              <div class="modal-footer">
                <button
                  type="button"
                  class="btn btn-danger"
                  @click="closeModal"
                >닫기</button>
                <button
                  type="button"
                  class="btn btn-primary"
                  @click="goChatting"
                >채팅룸 입장</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {

  methods: {
    closeModal () {
      this.$store.dispatch('turnOFFNotification')
    },
    goChatting () {
      this.closeModal()
      this.$router.push({ name: "room", params: { sessionId: this.sessionId } });
    }
  },
  computed: {
    ...mapState([
      'showModal',
      'isSigned',
      'sessionId',
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