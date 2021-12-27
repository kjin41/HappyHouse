<template>
  <div class="m-3">
    <h1>QnA 삭제</h1>
    <div>삭제중...</div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
export default {
  created() {
    http.delete(`/qna/${this.$route.params.no}`).then(({data})=>{
      let msg="삭제시 문제 발생";
      if (data!="success"){
        alert(msg);
      }
      this.$router.push({name:"QnaList"});
    })
  }
}
</script>

<style>

</style>