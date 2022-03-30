<template>
  <div>
    <h-nav></h-nav>
    <h2>게시판 상세정보</h2>

    <div
      id="main-overview"
      class="container"
    >
        <div class="chatting-container">
  
            <div class="view chat" style="margin-bottom: 130px" >
                <header>
                <!-- <button class="logout" @click="Logout">채팅방 나가기</button> -->
                <!-- <h1>Welcome, {{ state.nickName }}</h1> -->
                </header>

                <section class="chat-box">
                <div
                    v-for="message in state.messages"
                    :key="message.key"
                    :class="(message.username == 'pan' ? 'message current-user' : 'message')">
                    <div class="message-inner">
                    <div class="username">{{ message.username }}</div>
                    <div class="content">{{ message.content }}</div>
                    </div>
                </div>
                </section>

                <footer>
                <form @submit.prevent="SendMessage">
                    <input
                    type="text"
                    v-model="inputMessage"
                    placeholder="Write a message..." />
                    <input
                    type="submit"
                    value="Send" />
                </form>
                </footer>
            </div>
        </div>

      

    </div>

    <f-nav></f-nav>
  </div>
</template>

<script>

import db from '../../db';

export default {
    data () {
        return {
            chatId : '1a1',
            username: 'pan',
            messages: [],
            nickName: 'pp',
            inputMessage : '',
        };
    },
    mounted () {
        const messagesRef = db.database().ref('1a1');

        messagesRef.on('value', snapshot => {
        const data = snapshot.val();
        let messages = [];

        Object.keys(data).forEach(key => {
          messages.push({
            id: key,
            username: data[key].username,
            content: data[key].content
          });
        });

        state.messages = messages;
      });
    },
    methods: {
        SendMessage(){
            const messagesRef = db.database().ref('1a1');

            if (inputMessage.value === '' || inputMessage.value === null) {
                return;
            }

            const message = {
                username: 'pp',
                content: inputMessage.value
            }

            messagesRef.push(message);
            inputMessage.value = '';
            const height = document.querySelector('.chatting-container').clientHeight;

            window.scrollTo(0, height);
        }

    },

};
</script>

<style>
#nav-icon {
  height: 60px;
  padding-right: 0.5rem;
}
</style>