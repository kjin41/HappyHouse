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
      <!-- <h1 class="underline">QnA 정보</h1> -->
      <qna-write-form type="view"></qna-write-form>
      <qna-comment-write :qnano="this.qnano" />
      <qna-comment-write
        v-if="isModifyShow && this.modifyComment != null"
        :modifyComment="this.modifyComment"
        @modify-comment-cancel="onModifyCommentCancel"
      />
      <qna-comment v-for="(comment, index) in qnaComments" :key="index" :comment="comment" @modify-comment="onModifyComment" />
    </b-container>
  </div>
</template>

<script>
// import http from "@/util/http-common.js";
const qnaStore="qnaStore";
import {mapActions, mapState} from "vuex";
import QnaWriteForm from "@/components/qna/child/QnaWriteForm.vue";
import QnaComment from "@/components/qna/child/comment/QnaComment.vue";
import QnaCommentWrite from "@/components/qna/child/comment/QnaCommentWrite.vue";
export default {
  name: "QnaView",
  data(){
    return {
      // qna: {},
      // qnano:0,
      isModifyShow: false,
      modifyComment: Object
    }
  },
  computed:{
    ...mapState(qnaStore, ["qnaComments"])
  },
  components:{
    QnaWriteForm,
    QnaComment,
    QnaCommentWrite,

  },
  created() {
    // console.log(this.$route.params.no);
    this.qnano=this.$route.params.no;
    this.getQnaComments(`/qna/comment/${this.qnano}`);
    
  },
  methods:{
    ...mapActions(qnaStore, ["getQnaComments"]),
    onModifyComment(comment) {
      this.modifyComment = comment;
      this.isModifyShow = true;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    }

  }
}
</script>

<style>

</style>