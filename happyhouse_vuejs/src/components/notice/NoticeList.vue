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
      <b-col class="text-right" v-if="userInfo!=null && this.userInfo.userId=='admin'">
        <b-button variant="primary" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="articles.length">
          <vue-good-table :columns="columns" :rows="articles" :search-options="{ enabled: true }" 
           @on-row-click="moveView" :pagination-options="{enabled: true}">
        </vue-good-table>
      </b-col>
      <b-col v-else class="text-center">도서 목록이 없습니다.</b-col>
    </b-row>
  </b-container>
  </div>
</template>

<script>
import NoticeListRow from "@/components/notice/child/NoticeListRow";
import { listArticle } from "@/api/notice.js";

import {mapState} from "vuex";
const userStore="userStore";

export default {
  name: "NoticeList",
  components: {
    NoticeListRow,
  },
  data() {
    return {
      articles: [],
      // navigation: Object,
      columns: [
        {label: 'No', field: 'no', thClass: 'custom-th-class',},
        {label: '작성자', field: 'userId', thClass: 'custom-th-class'},
        {label: '제목', field: 'title', width: '600px', thClass: 'custom-th-class'},
        {label: '조회수', field: 'hit', thClass: 'custom-th-class'},
        {label: '작성일', field: 'date', thClass: 'custom-th-class'},
      ]
    };
  },
  computed:{
    ...mapState(userStore, ['userInfo'])
  },
  created() {
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    listArticle(
      (response) => {
        this.articles = response.data['noticeList'];
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "NoticeWrite" });
    },
    moveView(article){
      this.$router.push({name:'NoticeView', params:{no:article.row.no}});
    },
  },
};
</script>

<style scope>
</style>
