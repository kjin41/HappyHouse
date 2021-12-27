<template>
  <div id="qnalist-vue">
    
    <base-header class="bg-gradient-success py-3 py-lg-7">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <h1 class="text-white" style="font-size:40px;">QnA</h1>
        </div>
      </b-container>
    </base-header>
    <b-container class="mt--8 pb-5">
      <div style="text-align: right">
        <b-button @click="mvRegister()" variant="white">등록</b-button>
      </div>
      <br>
      <div v-if="qnas.length">
        <vue-good-table :columns="columns" :rows="qnas" :search-options="{ enabled: true }" 
            @on-row-click="moveView" :pagination-options="{enabled: true}"></vue-good-table>
        <!-- <b-table hover bordered style="text-align:center" :items="qnas" :fields="fields" @row-clicked="mvView" head-variant="dark">
        </b-table> -->
      </div>
      <div class="text-center" v-if="!qnas.length">질문 목록이 없습니다.</div>
    </b-container>
  </div>
</template>

<script>
import {mapState, mapActions} from 'vuex';
const qnaStore="qnaStore";
const userStore="userStore";

export default {
  name: 'QnaList',

  data(){
    return {
      // qnas: [],
      // fields:[
      //   {key:'no', label:'no'},
      //   {key:'title', label:'제목'},
      //   {key:'userId', label:'아이디'},
      // ]
      columns: [
        {label: 'No', field: 'no', thClass: 'custom-th-class',},
        {label: '제목', field: 'title', width: '600px', thClass: 'custom-th-class'},
        {label: '아이디', field: 'userId', thClass: 'custom-th-class'},
        {label: '답변수', field: 'ansCount', thClass: 'custom-th-class'},
      ]
    }
  },
  created() {
    this.getList(this.userInfo.userId);
    console.log(this.qnas);
  },
  methods: {
    mvRegister(){
      this.$router.push({name:'QnaRegister'});
    },
    moveView(params){
      this.$router.push({name:'QnaView', params:{no:params.row.no}});
    },
    ...mapActions(qnaStore, ['getList'])
  },
  computed:{
    ...mapState(qnaStore, ['qnas']),
    ...mapState(userStore, ['userInfo']),
  }
}

</script>

<style>
 a {
  text-decoration: none;
  color: black;
} 

#qnalist-vue{
  margin: 0 20px;
}
</style>