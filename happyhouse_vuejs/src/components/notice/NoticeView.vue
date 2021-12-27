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
      <b-row class="mb-1">
        <b-col>
          <b-card
            :header-html="`<h1>${article.title}</h1>
            <div style='text-align:right'><h3>${article.userId} [ ${article.date} ]</h3></div>`"
            class="mb-2"
            border-variant="dark"
            no-body
          >
            <b-card-body class="text-left" style="font-size:20px">
              <div v-html="message"></div>
            </b-card-body>
            <b-card-body class="text-left">
              <ul v-for="(file, index) in article.fileInfoDtos"
                :key="index">
                <li >
                  <a href="#" @click="download(file.saveFolder, file.saveFile, file.originFile)" id="filedown"
                  >{{file.originFile}} [다운로드]</a>
                </li>  
              </ul>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-left">
          <b-button variant="outline-primary" @click="listArticle">목록</b-button>
        </b-col>
        
        <b-col class="text-right" v-if="userInfo&&userInfo.userId=='admin'">
          <b-button
            variant="outline-info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정</b-button
          >
          <b-button variant="outline-danger" size="sm" @click="removeArticle"
            >글삭제</b-button
          >
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// import moment from "moment";
import { getArticle, downloadFile } from "@/api/notice";
import {mapState, } from "vuex";
const userStore="userStore";

export default {
  data() {
    return {
      article: {},
    };
  },
  computed: {
    ...mapState(userStore, ['userInfo']),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    getArticle(
      this.$route.params.no,
      (response) => {
        this.article = response.data;
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "NoticeList" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "NoticeUpdate",
        params: { no: this.article.no },
      });
      //   this.$router.push({ path: `/notice/modify/${this.article.no}` });
    },
    removeArticle() {
      // if (confirm("공지를 삭제하겠습니까?")) {
        // deleteArticle(this.article.no, () => {
          this.$router.push({ name: "NoticeDelete",
        params: { no: this.article.no } });
        // });
      // }
    },
    download(savefolder, savefile, originfile){
      // console.log(this.originfile+ " "+this.savefile+" "+this.savefolder);
      console.log(savefolder+" // "+savefile+" // "+originfile);
      downloadFile(
        {
          saveFolder: savefolder,
          saveFile: savefile,
          originFile: originfile,
        }, 
        ({ data }) => {
          var fileURL = window.URL.createObjectURL(new Blob([data]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', originfile);
          document.body.appendChild(fileLink);
          fileLink.click();
          fileLink.remove();
          // console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  },
};
</script>

<style></style>
