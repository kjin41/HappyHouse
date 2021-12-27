<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset" enctype="multipart/form-data">
        <b-form-group id="userId-group" label="작성자" label-for="userId" v-if="this.type==='view'" >
          <b-form-input id="userId" :disabled="isUserid" v-model="article.userId" type="text" required ></b-form-input>
        </b-form-group>

        <b-form-group id="title-group" placeholder="제목" label-for="title" >
          <b-form-input id="title" v-model="article.title" type="text" required></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" placeholder="내용" label-for="content">
          <b-form-textarea id="content" v-model="article.content" rows="10" max-rows="15" ></b-form-textarea>
        </b-form-group>

        <b-form-group id="file-group" label-for="file" v-if="this.type==='register'">
          <input type="file" id="files" ref="files" multiple="multiple">
        </b-form-group>
          <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'register'" >글작성</b-button>
          <b-button type="submit" variant="primary" class="m-1" v-else >글수정</b-button >
          <b-button @click="moveList" variant="success" class="m-1">목록</b-button> 
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { writeArticle, getArticle, modifyArticle } from "@/api/notice";

// import {mapState} from 'vuex';
// const userStore="userStore";

export default {
  name: "NoticeWriteForm",
  data() {
    return {
      article: {
        no: 0,
        userId: "",
        title: "",
        content: "",
        files:null
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  computed:{
    // ...mapState(storeStore, ['qnas'])
  },
  created() {
    if (this.type === "modify") {
      getArticle(
        this.$route.params.no,
        ({ data }) => {
          // this.article.no = data.article.no;
          // this.article.userId = data.article.userId;
          // this.article.title = data.article.title;
          // this.article.content = data.article.content;
          this.article = data;
        },
        (error) => {
          console.log(error);
        }
      );
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      // !this.article.userId &&
      //   ((msg = "작성자 입력해주세요"),
      //   (err = false),
      //   this.$refs.userId.focus());
      // err &&
        !this.article.title &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.updateArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.no = 0;
      this.article.title = "";
      this.article.content = "";
      this.$router.push({ name: "NoticeList" });
    },
    registArticle() {
      this.files=this.$refs.files.files;
      let formData=new FormData();
      for (let i=0; i<this.files.length; i++){
        formData.append("upfile", this.files[i]);
      }
      formData.append("userId", this.article.userId);
      formData.append("title", this.article.title);
      formData.append("content", this.article.content);
      console.log(formData);
      writeArticle(
        // {
        //   userId: this.article.userId,
        //   title: this.article.title,
        //   content: this.article.content,
        //   // file: this.article.file
        // }, 
        formData,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data == "success") {
            msg="공지사항이 등록됐습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateArticle() {
      modifyArticle(
        {
          no: this.article.no,
          userId: this.article.userId,
          title: this.article.title,
          content: this.article.content,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data != "success") {
            alert(msg);
          }
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "NoticeList" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
};
</script>

<style></style>
