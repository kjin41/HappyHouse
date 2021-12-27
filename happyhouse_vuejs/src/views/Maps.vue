<template>
  <div>
    <kakao-map></kakao-map>
    <router-view></router-view>
  </div>
</template>
<script>
import KakaoMap from "@/components/Map/KakaoMap.vue";
export default {
  components: {
    KakaoMap
  },
  methods: {},
  mounted() {}
};
</script>
