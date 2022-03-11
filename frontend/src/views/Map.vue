<template>
  <div>
    <h-nav></h-nav>
    <div
      id="map"
      style="margin-top: 76px;"
    ></div>
    <h-footer></h-footer>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import HFooter from '../components/common/HFooter.vue';

export default {
  data () {
    return {
      markerPositions1: [

      ],
      markerPositions2: [

      ],
      markers: [],
      infowindow: null,
    }
  },
  components: {
    HNav,
    HFooter
  },
  mounted () {
    if (window.kakao && window.kakao.maps) {
      this.initMap()
    } else {
      const script = document.createElement("script")
      script.onload = () => kakao.maps.load(this.initMap)
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=3500ddf5f2e75e1f441483304d4c18f1"
      document.head.appendChild(script)
    }
  },
  methods: {
    initMap () {
      const container = document.getElementById("map")
      const options = {
        center: new kakao.maps.LatLng(37.5017237691485, 127.03963872680058),
        level: 3,
      }
      this.map = new kakao.maps.Map(container, options)
    }
  }
};
</script>

<style>
#map {
  width: 100%;
  height: 763px;
}
</style>
