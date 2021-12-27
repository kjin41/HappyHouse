<template>
  <div class="m-3">
    <div>삭제중...</div>
  </div>
</template>

<script>
// import http from "@/util/http-common.js";

import { deleteArticle } from "@/api/notice";
export default {
  data(){
    return{

    }
  },
  created() {
    if (confirm("공지를 삭제하겠습니까?")) {
        deleteArticle(this.$route.params.no, () => {
          this.$router.push({ name: "NoticeList" });
        });
      }
  }
}
</script>

<style>

</style>