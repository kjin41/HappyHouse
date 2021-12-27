<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit">
        <!-- <b-form-group id="no-group" label="no." label-for="no" v-if="false">
          <b-form-input id="no" :disabled="true"
          v-model="getter.no" type="text" required></b-form-input>
        </b-form-group>

        <b-form-group id="userId-group" label="아이디" label-for="userId" v-if="false">
          <b-form-input id="userId" :disabled="isNo || isView" 
          v-model="getter.userId" type="text" required></b-form-input>
        </b-form-group> -->

        <b-form-group id="title-group" label-for="title">
          <b-form-input id="title" :disabled="isView" placeholder="제목"
          v-model="getter.title" type="text" required></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label-for="content">
          <b-form-textarea id="content" :disabled="isView" v-model="getter.content" required placeholder="내용"
            rows="5" max-rows="15"></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="info" class="m-1" v-if="this.type === 'register'">등록</b-button>
        <b-button type="submit" variant="primary" class="m-1" v-if="this.type==='modify'">수정</b-button>
        <b-button @click="mvModify()" variant="primary" class="m-1" v-if="this.type==='view'">수정</b-button>
        <b-button @click="mvDelete()" variant="danger" class="m-1" v-if="this.type === 'view'">삭제</b-button>
        <b-button @click="mvList()" variant="success" class="m-1">목록</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/util/http-common";
import { mapActions, mapGetters, mapState } from "vuex";
const qnaStore="qnaStore";
const userStore="userStore";

export default {
  name: "QnaWriteForm",
  data() {
    return {
      isNo: false,
      isView: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    console.log(this.getQna());
    if (this.type === "modify") {
      this.isNo = true;
    } else if(this.type === "view"){
      this.isView = true;
      console.log(this.isView);
    } else if (this.type==="register"){
      this.resetQna;
      console.log("register");
    }
  },
  computed:{
    ...mapState(qnaStore, ["qna"]),
    ...mapState(userStore, ["userInfo"]),
    ...mapGetters(qnaStore, ["getQna"]),
    ...mapActions(qnaStore, ["resetQna"]),
    getter() {
      return this.getQna(this.$route.params.no);
    },
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      console.log(this.type);
      if (this.type==="register") this.register();
      else if (this.type==="modify")  this.modify();
      else if (this.type==="delete")  this.delete();
      else this.mvList();
    },
    register() {
      http
        .post(`/qna`, {
          userId: this.userInfo.userId,
          title: this.getter.title,
          content: this.getter.content,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data != "success") {
            alert(msg);
          }
          this.mvList();
        });
    },
    mvModify(){
      this.$router.push({name:'QnaModify'});
    },
    modify() {
      http
        .put(`/qna`, {
          no: this.getter.no,
          userId: this.getter.userId,
          title: this.getter.title,
          content: this.getter.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data != "success") {
            alert(msg);
          }
          // 현재 route를 /list로 변경.
          this.mvList();
        });
    },
    mvDelete(){
      console.log("delete");
      if (confirm("질문을 삭제하시겠습니까?")){
        this.$router.push({ name: "QnaDelete" });
      }
    },
    mvList() {
      this.$router.push({ name: "QnaList" });
    },
    // mvView() {
    //   this.$router.push({ name: "QnaView" , params:{no:this.getter.no}});
    // },
  },
};
</script>

<style></style>





