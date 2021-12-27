<template>
  <div class="regist">
    <div v-if="this.modifyComment != null" class="regist_form">
      <textarea id="comment" name="comment" v-model="modifyComment.comment" cols="35" rows="2"></textarea>
      <b-button class="m-1" @click="updateComment" variant="primary">수정</b-button>
      <b-button class="m-1" @click="updateCommentCancel" variant="danger">취소</b-button>
    </div>
    <div v-else class="regist_form">
      <textarea id="comment" name="comment" v-model="comment" cols="35" rows="2"></textarea>
      <b-button class="m-3" @click="registComment" variant="info">등록</b-button>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapActions, mapState } from "vuex";
const qnaStore="qnaStore";
const userStore="userStore";
export default {
  name: "commentwrite",
  data() {
    return {
      // userId: "ssafy",
      comment: ""
    };
  },
  props: {
    qnano: { type: Number },
    modifyComment: { type: Object }
  },
  computed:{
    ...mapState(userStore, ["userInfo"])
  },
  methods: {
    ...mapActions(qnaStore,["getQnaComments"]),
    registComment() {
      http
        .post("/qna/comment/", {
          userId: this.userInfo.userId,
          comment: this.comment,
          qnano: this.qnano
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "fail") {
            alert(msg);
          }

          // 작성글 지우기
          this.comment = "";

          // 댓글 얻기.
          this.getQnaComments(`/qna/comment/${this.qnano}`);
        });
    },
    updateComment() {
      http
        .put(`/qna/comment`, {
          no: this.modifyComment.no,
          comment: this.modifyComment.comment
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "fail") {
            alert(msg);
          }

          // 댓글 얻기.
          this.getQnaComments(`/qna/comment/${this.modifyComment.qnano}`);
          this.$emit("modify-comment-cancel", false);
        });
    },
    updateCommentCancel() {
      this.$emit("modify-comment-cancel", false);
    },
  }
};
</script>

<style scoped>
.regist {
  padding: 10px;
}
.regist_form {
  text-align: left;
  border-radius: 5px;
  background-color: #d6e7fa;
  padding: 20px;
}

textarea {
  width: 80%;
  padding: 10px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #787878;
  font-size: large;
}

/* button {
  float: right;
}

button.small {
  width: 60px;
  font-size: small;
  font-weight: bold;
} */
</style>
