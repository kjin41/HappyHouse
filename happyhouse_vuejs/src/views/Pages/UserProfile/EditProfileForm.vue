<template>
  <card>
    <b-row align-v="center" slot="header" >
      <b-col cols="8">
        <h3 class="mb-0">Edit profile </h3>
      </b-col>
      <b-col cols="4" class="text-right">
        <!-- <div class="btn btn-sm btn-primary" @click="mvModify()" v-if="isView">설정</div> -->
        
        <b-button @click="mvModify()" variant="primary" class="m-1" v-if="isView && !this.isKakaoLogin">설정</b-button>
        <b-button @click="modify()" variant="success" class="m-1" v-if="!isView">수정</b-button>
        <b-button @click="mvDelete()" variant="danger" class="m-1" v-if="!isView">삭제</b-button>
      </b-col>
    </b-row>

    <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
      <h6 class="heading-small text-muted mb-4">User information</h6>

      <div class="pl-lg-4">
        <b-row>
          <b-col lg="6">
            <base-input
              type="text"
              label="ID"
              placeholder="ID"
              v-model="userInfo.userId" :disabled="true"
            >
            </base-input>
          </b-col>
          <b-col lg="6">
            <base-input
              type="text"
              label="Name"
              placeholder="Name" name="Name" :rules="{required: true}"
              v-model="userInfo.userName" :disabled="isView"
            >
            </base-input>
          </b-col>
        </b-row>
        <b-row v-if="!isView">
          <b-col lg="6">
            <base-input
              type="password"
              label="Password" name="password" :rules="{required: true}"
              v-model="userInfo.password"
            >
            </base-input>
          </b-col>
          <b-col lg="6">
            <base-input
              type="password"
              label="Password Again" name="password" :rules="{required: true}"
              v-model="password2"
            >
            </base-input>
          </b-col>
        </b-row>
        <b-row >
          <b-col lg="6">
            <base-input
              type="email"
              label="Email address"
              placeholder="Email"
              v-model="userInfo.email" :disabled="isView"
            >
            </base-input>
          </b-col>
          <b-col lg="6">
            <base-input
              type="text"
              label="Phone Number"
              placeholder="Phone Number"
              v-model="userInfo.phone" :disabled="isView"
            >
            </base-input>
          </b-col>
        </b-row>
      </div>
      <hr class="my-4">

      <!-- <hr class="my-4"> -->
      <!-- Description -->
      <!-- <h6 class="heading-small text-muted mb-4">About me</h6>
      <div class="pl-lg-4">
        <b-form-group label="About Me" label-class="form-control-label" class="mb-0" label-for="about-form-textaria">
          <label class="form-control-label">About Me</label>
          <b-form-textarea rows="4" value="A beautiful premium dashboard for BootstrapVue." id="about-form-textaria" placeholder="A few words about you ..."></b-form-textarea>
        </b-form-group>
      </div> -->

    </b-form>
  </card>
</template>
<script>

import { userModify, userDelete } from "@/api/user";

import {mapState, mapMutations} from "vuex";
const userStore="userStore";
export default {
  // name:"UserWriteForm",
  data() {
    return {
      isView: false,
      password2:""
    };
  },
  props: {
    type: { type: String },
  },
  computed:{
    ...mapState(userStore, ['userInfo', 'isKakaoLogin'])
  },
  created(){
    if(this.type === "view"){
      this.isView = true;
      console.log(this.isView);
    } else{
      this.isView = false;
      console.log(this.isView);
      this.password2=this.userInfo.password;
    }
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    modify() {
      if (!this.userInfo.userName){
        alert("이름을 입력하세요.");
      } else if (!this.userInfo.password || this.userInfo.password!=this.password2){
        alert("비밀번호를 확인하세요.");
      } else{
        userModify(this.userInfo,
          ({ data }) => {
            let msg = "수정 처리시 문제가 발생했습니다.";
            if (data == "success") {
              msg="회원정보가 수정되었습니다."
            }
            alert(msg);
            console.log("logout");
            this.SET_IS_LOGIN(false);
            this.SET_USER_INFO(null);
            sessionStorage.removeItem("access-token");
            // if (this.$route.path != "/") this.$router.push({ name: "dashboard" });
            this.$router.push({ name: "SignIn" });
          },
          (error) => {
            console.log(error);
          }
        );

      }
    },
    updateProfile() {
      alert('Your data: ' + JSON.stringify(this.user));
    },
    mvModify(){
      // this.$router.push({name:'UserModify'});
      this.$router.push({
        name: "UserModify",
        // params: { userId: this.article.no },
      });
    },
    mvDelete(){
      this.$router.push({
        name: "UserDelete",
        params: { userId: this.userInfo.userId },
      });
    }
    // modifyArticle(
    //     {
    //       no: this.article.no,
    //       userId: this.article.userId,
    //       title: this.article.title,
    //       content: this.article.content,
    //     },
    //     ({ data }) => {
    //       let msg = "수정 처리시 문제가 발생했습니다.";
    //       if (data != "success") {
    //         alert(msg);
    //       }
    //       // 현재 route를 /list로 변경.
    //       this.$router.push({ name: "NoticeList" });
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
  }
};
</script>
<style></style>
