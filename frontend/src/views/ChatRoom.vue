<template>
  <div class="a123">
    <div class="chat_window">
      <div class="top_menu">
        <div
          style="margin-left: 750px;"
          class="buttons"
        >
          <div
            class="button close"
            @click="goHome()"
          ></div>
        </div>
        <div class="title">Chat</div>
      </div>
      <div class="messages">
        <div
          v-for="(message, index) in messages"
          :key="index"
          :class="[message.author === $store.state.user.userNickname ? 'chat chat-left' : 'chat']"
        >
          <div class="chat-avatar">
            <div class="avatar">
              <img
                v-if="user.profileImage"
                :src="[message.author === $store.state.user.userNickname ? user.profileImage : $route.params.profileImage]"
              >
              <img
                v-else
                :src="[message.author === $store.state.user.userNickname ? 'https://www.kindpng.com/picc/m/24-248253_user-profile-default-image-png-clipart-png-download.png' : $route.params.profileImage]"
              >
            </div>
          </div>
          <div class="chat-body">
            <div class="chat-content">
              <div>{{ message.message }}</div>
              <div class="chat-time">{{ message.author }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="bottom_wrapper clearfix">
        <div class="message_input_wrapper">
          <input
            @keyup.enter="saveMessage"
            v-model="message"
            class="message_input"
            placeholder="Type your message here..."
          />
        </div>
        <div
          @click="saveMessage"
          class="send_message"
        >
          <div class="icon"></div>
          <div class="text">Send</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import firebase from 'firebase'

export default {
  name: 'PrivateChat',

  data () {
    return {
      message: null,
      messages: [],
      authUser: {},
    }
  },

  methods: {
    scrollToBottom () {
      let box = document.querySelector('.messages')
      box.scrollTop = box.scrollHeight
    },
    saveMessage () {
      db.collection('chat').doc(this.$route.params.sessionId).collection('messages').add({
        message: this.message,
        author: this.$store.state.user.userNickname,
        createdAt: new Date()
      }).then(() => {
        this.scrollToBottom()
      })
      this.message = null
    },

    fetchMessages () {
      db.collection('chat').doc(this.$route.params.sessionId).collection('messages').orderBy('createdAt').onSnapshot((querySnapshot) => {
        let allMessages = []
        querySnapshot.forEach(doc => {
          allMessages.push(doc.data())
        })
        this.messages = allMessages
        setTimeout(() => {
          this.scrollToBottom()
        }, 1000)
      })
    },
    goHome () {
      this.resetFirebaseUserStatus(this.$route.params.sessionId)
      this.$router.push('/')
      this.$store.dispatch('setInterval', this.user.userNickname)
    },
    resetFirebaseUserStatus (sessionId) {
      this.chatRooms.forEach((obj) => {
        if (obj.sessionId === sessionId) {
          db.collection('user').doc(this.user.userNickname).update({
            chatRooms: firebase.firestore.FieldValue.arrayRemove(obj)
          }).then(() => {
            console.log('remove')
          })
        }
      })
    },
  },
  computed: {
    ...mapState([
      'user',
      'chatRooms',
    ]),
  },

  created () {
    this.fetchMessages()
  },
}
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.a123 {
  height: 1000px;
}

body {
  background-color: #edeff2;
  font-family: "Calibri", "Roboto", sans-serif;
}

.chat_window {
  position: absolute;
  width: calc(100% - 20px);
  max-width: 800px;
  height: 500px;
  border-radius: 10px;
  background-color: #fff;
  left: 50%;
  top: 50%;
  transform: translateX(-50%) translateY(-50%);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
  background-color: #f8f8f8;
  overflow: hidden;
}

.top_menu {
  background-color: #fff;
  width: 100%;
  padding: 20px 0 15px;
  box-shadow: 0 1px 30px rgba(0, 0, 0, 0.1);
}
.top_menu .buttons {
  margin: 0px 0 0 20px;
  position: absolute;
}
.top_menu .buttons .button {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: inline-block;
  margin-right: 10px;
  position: relative;
}
.top_menu .buttons .button.close {
  background-color: #f5886e;
}
.top_menu .buttons .button.minimize {
  background-color: #fdbf68;
}
.top_menu .buttons .button.maximize {
  background-color: #a3d063;
}
.top_menu .title {
  text-align: center;
  color: #bcbdc0;
  font-size: 20px;
}

.messages {
  position: relative;
  list-style: none;
  padding: 20px;
  margin: 0;
  height: 360px;
  overflow: scroll;
}

.bottom_wrapper {
  position: relative;
  width: 100%;
  background-color: #fff;
  padding: 20px 20px;
  position: absolute;
  bottom: 0;
}
.bottom_wrapper .message_input_wrapper {
  display: inline-block;
  height: 50px;
  border-radius: 25px;
  border: 1px solid #bcbdc0;
  width: calc(100% - 160px);
  position: relative;
  padding: 0 20px;
}
.bottom_wrapper .message_input_wrapper .message_input {
  border: none;
  height: 100%;
  box-sizing: border-box;
  width: calc(100% - 40px);
  position: absolute;
  outline-width: 0;
  color: gray;
}
.bottom_wrapper .send_message {
  width: 140px;
  height: 50px;
  display: inline-block;
  border-radius: 50px;
  background-color: #a3d063;
  border: 2px solid #a3d063;
  color: #fff;
  cursor: pointer;
  transition: all 0.2s linear;
  text-align: center;
  float: right;
}
.bottom_wrapper .send_message:hover {
  color: #a3d063;
  background-color: #fff;
}
.bottom_wrapper .send_message .text {
  font-size: 18px;
  font-weight: 300;
  display: inline-block;
  line-height: 48px;
}

.message_template {
  display: none;
}

.chats {
  padding: 30px 15px;
}

.chat-avatar {
  float: right;
}

.chat-body {
  display: block;
  margin: 10px 30px 0 0;
  overflow: hidden;
}

.chat-body:first-child {
  margin-top: 0;
}

.chat-content {
  max-width: 300px;
  position: relative;
  display: block;
  float: right;
  padding: 8px 15px;
  margin: 0 20px 10px 0;
  clear: both;
  color: #76838f;
  background-color: #dfe9ef;
  border-radius: 4px;
  -webkit-box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.37);
  box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.37);
}

.chat-content:before {
  position: absolute;
  top: 10px;
  right: -10px;
  width: 0;
  height: 0;
  content: "";
  border: 5px solid transparent;
  border-left-color: #dfe9ef;
}

.chat-content + .chat-content:before {
  border-color: transparent;
}

.chat-time {
  display: block;
  margin-top: 8px;
  color: #a3afb7;
}

.chat-left .chat-avatar {
  float: left;
}

.chat-left .chat-body {
  margin-right: 0;
  margin-left: 30px;
}

.chat-left .chat-content {
  float: left;
  margin: 0 0 10px 20px;
  color: #fff;
  background-color: #62a8ea;
}

.chat-left .chat-content:before {
  right: auto;
  left: -10px;
  border-right-color: #62a8ea;
  border-left-color: transparent;
}

.chat-left .chat-content + .chat-content:before {
  border-color: transparent;
}

.chat-left .chat-time {
  color: rgba(255, 255, 255, 0.6);
}

.panel-footer {
  padding: 0 30px 15px;
  background-color: transparent;
  border-top: 1px solid transparent;
  border-bottom-right-radius: 3px;
  border-bottom-left-radius: 3px;
}
.avatar img {
  width: 100%;
  max-width: 100%;
  height: auto;
  border: 0 none;
  border-radius: 1000px;
}
.chat-avatar .avatar {
  width: 30px;
}
.avatar {
  position: relative;
  display: inline-block;
  width: 40px;
  white-space: nowrap;
  border-radius: 1000px;
  vertical-align: bottom;
}
</style>