<template>
  <div class="container-fluid bg-secondary fixed-bottom">
    <div class="row p-2 justify-content-center">
      <div>
        <img
          @click="returnHome()"
          class="navbar"
          src="../../../public/images/home.png"
          alt="home"
        />
      </div>
      <div>
        <img
          @click="openBoard()"
          class="navbar"
          src="../../../public/images/board.png"
          alt="board"
        />
      </div>
      <div>
        <img
          @click="openMap()"
          class="navbar"
          src="../../../public/images/location.png"
          alt="location"
        />
      </div>
      <div>
        <img
          class="navbar"
          src="../../../public/images/chat.png"
          alt="chat"
        />
      </div>
      <div>
        <img
          class="navbar"
          src="../../../public/images/info.png"
          alt="info"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    openMap () {
      this.$router.push('/map').catch(() => { })
    },
    openBoard () {
      this.$router.push('/board').catch(() => { })
    },
    returnHome () {
      this.$router.push('/').catch(() => { })
    }
  }
}
</script>

<style scoped>
.navbar {
  height: 60px;
  margin: 0 auto;
}
</style>