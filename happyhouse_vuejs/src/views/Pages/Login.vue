<template>
  <div>
    <!-- Header -->
      <base-header class="bg-gradient-success py-3 py-lg-7">
        <b-container class="container">
          <div class="header-body text-center mb-7">
            <h1 class="text-white" style="font-size:40px;">로그인</h1>
          </div>
        </b-container>
      </base-header>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            <b-card-header class="bg-transparent pb-5"  >
              <!-- <div class="text-muted text-center mt-2 mb-3"><small></small></div> -->
              <div class="btn-wrapper text-center">
                <a href="https://kauth.kakao.com/oauth/authorize?client_id=8105a536e7d755140d8ffa78260da754&redirect_uri=http://localhost:8080/kakaologin&response_type=code">
                  <img src="img/happyhouse/kakao_login_btn.png" class="m-2">
                  <!-- <span class="btn-inner--text">카카오</span> -->
                </a>
                <!-- <a href="#" class="btn btn-neutral btn-icon">
                  <span class="btn-inner--icon"><img src="img/icons/common/google.svg"></span>
                  <span class="btn-inner--text">구글</span>
                </a> -->
              </div>
            </b-card-header>
            <b-card-body class="px-lg-5 py-lg-5">
              <!-- <div class="text-center text-muted mb-4">
                <small>Or sign in with credentials</small>
              </div> -->
              <!-- <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <base-form @submit.prevent="handleSubmit(onSubmit)">
                <b-alert show variant="danger" v-if="isLoginError"
                  >아이디 또는 비밀번호를 확인하세요.</b-alert>
                  <base-input alternative
                              class="mb-3" required
                              prepend-icon="ni ni-circle-08"
                              placeholder="아이디"
                              @keyup.enter="confirm" v-model="user.userId">
                  </base-input>

                  <base-input alternative
                              class="mb-3" required
                              prepend-icon="ni ni-lock-circle-open"
                              type="password"
                              placeholder="비밀번호"
                  @keyup.enter="confirm" v-model="user.password">
                  </base-input>

                <b-form-checkbox v-model="user.reuserMe">Reuser me</b-form-checkbox>
                  <div class="text-center">
                    <b-button type="submit" @click="confirm" variant="primary" class="my-4">로그인</b-button>
                    <b-button @click="movePage" type="primary" class="my-4">회원가입</b-button>
                  </div>
                </base-form>
              </validation-observer> -->
              <b-form class="text-left" @submit="onSubmit">
                <b-alert show variant="danger" v-if="isLoginError"
                  >아이디 또는 비밀번호를 확인하세요.</b-alert>
                <b-input-group label-for="userId" class="mb-3">
                  <b-input-group-prepend is-text>
                      <b-icon icon="person-fill"></b-icon>
                    </b-input-group-prepend>
                  <b-form-input id="userId" v-model="user.userId" required placeholder="아이디"></b-form-input>
                </b-input-group>
                <b-input-group label-for="password" class="mb-3">
                  <b-input-group-prepend is-text>
                      <b-icon icon="lock-fill"></b-icon>
                    </b-input-group-prepend>
                  <b-form-input type="password" id="password" required v-model="user.password" placeholder="비밀번호"
                   @keyup.enter="confirm"></b-form-input>
                </b-input-group>
                <b-button type="submit" variant="primary" class="m-1" >로그인</b-button>
                <b-button variant="warning" class="m-1" @click="findPass">비밀번호 찾기</b-button>
                <b-button type="button" variant="success" class="m-1" @click="movePage">회원가입</b-button>
              </b-form>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { findPassoword } from "@/api/user.js";
import { mapState, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "UserLogin",
  data() {
    return {
      user: {
        userId: null,
        password: null,
      },
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    
    onSubmit(event) {
      event.preventDefault();
      this.confirm();
    },
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "dashboard" });
      } else{
        console.log("로그인 안됨.");
      }
    },
    movePage() {
      this.$router.push({ name: "SignUp" });
    },
    findPass(){
      let userId = prompt("아이디를 입력하세요.");
      findPassoword(userId,
      ({ data }) => {
        let msg = "해당 아이디는 존재하지 않습니다.";
        console.log(data);
        if (data.message == "success") {
          msg="해당 아이디의 비밀번호는 "+data.userInfo.password+"입니다.";
        }
        alert(msg);
        // 현재 route를 /list로 변경.
        // this.$router.push({ name: "SignIn" });
      },
      (error) => {
        console.log(error);
      })
    }
  },
};
</script>
