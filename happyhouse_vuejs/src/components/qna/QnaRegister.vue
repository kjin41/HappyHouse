<template>
  <div>
    <base-header class="bg-gradient-success py-3 py-lg-7">
        <b-container class="container">
          <div class="header-body text-center mb-7">
            <h1 class="text-white" style="font-size:40px;">QnA</h1>
          </div>
        </b-container>
      </base-header>
    <b-container class="mt--8 pb-5">
      <qna-write-form type="register"></qna-write-form>
    </b-container>
  </div>
</template>

<script>
import QnaWriteForm from "@/components/qna/child/QnaWriteForm.vue";
export default {
  data() {
    return {
      qna:{}
    };
  },
  components:{
    QnaWriteForm
  }
}
</script>

<style>

</style>