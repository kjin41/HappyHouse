<template>
  <div class="m-3">
    <div>카카오 로그인중...</div>
  </div>
</template>

<script>
// import { getKakaoToken } from "@/api/user.js";

import { mapState, mapActions } from "vuex";

const userStore = "userStore";
export default {
  name:"KakaoLogin",
  data(){
    return{
      code:""
    }
  },
  created(){
    this.code=this.$route.query.code;
    this.getToken();
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError"]),
  },
  methods:{
    ...mapActions(userStore, ["kakaoUserConfirm", "getKakaoUserInfo"]),
      
    async getToken() {
      await this.kakaoUserConfirm(this.code);

      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getKakaoUserInfo(token);
        console.log("로그인 됨.");
        this.$router.push({ name: "dashboard" });
      } else{
        console.log("로그인 안됨.");
      }
      
    },


  }
}
</script>

<style>

</style>