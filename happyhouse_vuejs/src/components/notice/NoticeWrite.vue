<template>
  <div>
    <base-header class="bg-gradient-success py-3 py-lg-7">
        <b-container class="container">
          <div class="header-body text-center mb-7">
            <h1 class="text-white" style="font-size:40px;">공지사항</h1>
          </div>
        </b-container>
      </base-header>
    <b-container class="mt--8 pb-5">
      <notice-write-form type="register" />
    </b-container>
  </div>
</template>

<script>
import NoticeWriteForm from "./child/NoticeWriteForm.vue";

export default {
  name: "NoticeWrite",
  components: {
    NoticeWriteForm,
  },
};
</script>

<style></style>
