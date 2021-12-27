<template>
  <div v-show="isShow" class="comment">
    <div class="head">{{ comment.userId }} ({{ comment.date }})</div>
    <div class="content" v-html="enterToBr(comment.comment)"></div>
    <!-- 로그인 기능 구현 후 로그인한 자신의 글에만 보이게 한다. -->
    <div class="cbtn">
      <b-button class="m-1" @click="modifyCommentView" variant="primary">수정</b-button>
      <b-button class="m-1" @click="deleteComment" variant="danger">삭제</b-button>
    <!-- <label @click="modifyCommentView">수정</label> | <label @click="deleteComment">삭제</label> -->
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapActions } from "vuex";
const qnaStore="qnaStore";

export default {
  name: "comment",
  data() {
    return {
      isShow: true
    };
  },
  props: {
    comment: Object
  },
  computed:{
  },
  methods: {
    ...mapActions(qnaStore, ["getQnaComments"]),
    modifyCommentView() {
      this.$emit("modify-comment", {
        no: this.comment.no,
        comment: this.comment.comment,
        qnano: this.comment.qnano
      });
    },
    deleteComment() {
      if (confirm("답변을 삭제하겠습니까?")) {
        http.delete(`/qna/comment/${this.comment.no}`).then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "fail") {
            alert(msg);
          }
          this.getQnaComments(`/qna/comment/${this.comment.qnano}`);
        });
      }
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    }
  }
};
</script>

<style>
.comment {
  text-align: left;
  border-radius: 5px;
  background-color: #d6e7fa;
  padding: 10px 20px;
  margin: 10px;
}
.head {
  font-weight: bold;
  margin-bottom: 5px;
}
.content {
  padding: 5px;
}
.cbtn {
  text-align: right;
  color: steelblue;
  margin: 5px 0px;
}
</style>
