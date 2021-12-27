<template>
  <div class="m-3">
    <h1>사용자 계정 삭제</h1>
    <div>삭제중...</div>
  </div>
</template>

<script>

import {mapState, mapMutations} from "vuex";
const userStore="userStore";
import { userDelete, } from "@/api/user";
export default {
  data(){
    return{

    }
  },
  computed:{
    ...mapState(userStore, ['userInfo'])
  },
  created() {
    if(confirm("사용자 계정을 탈퇴하시겠습니까?")){
      userDelete(this.$route.params.userId,
        ({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data == "success") {
            msg="회원정보가 삭제되었습니다."
          }
          alert(msg);
          console.log("logout");
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          sessionStorage.removeItem("access-token");
          // if (this.$route.path != "/") this.$router.push({ name: "dashboard" });
          this.$router.push({ name: "dashboard" });
        },
        (error) => {
          console.log(error);
        }
      );

    } else{
      this.$router.push({ name: "UserModify" });
    }
  }, 
  methods:{
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
  }
}
</script>

<style>

</style>