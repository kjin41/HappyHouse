<template>
  <div>
    <!-- Header -->
    <!-- <base-header class="pb-1 bg-gradient-success mb-2">
      <h1 class="pt-3" style="text-align:center; font-size:40px;">
        회원가입
      </h1>
    </base-header> -->
    <base-header class="bg-gradient-success py-3 py-lg-7">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <h1 class="text-white" style="font-size:40px;">회원가입</h1>
        </div>
      </b-container>
      <!-- <div class="separator separator-bottom separator-skew zindex-100">
        <svg x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1"
             xmlns="http://www.w3.org/2000/svg">
          <polygon class="fill-default" points="2560 0 2560 100 0 100"></polygon>
        </svg>
      </div> -->
    </base-header>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8" >
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 py-lg-5">
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <b-input-group class="mt-3">
                    <b-input-group-prepend is-text>
                      <b-icon icon="person-fill"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input placeholder="아이디" required v-model="user.userId" @keyup="idcheck"></b-form-input>
                  </b-input-group>
                  <div :v-bind="check" v-if="check&&usable" style="color:blue;">{{check}}</div>
                  <div :v-bind="check" v-if="check&&!usable" style="color:red;">{{check}}</div>
                  <b-input-group class="mt-3">
                    <b-input-group-prepend is-text>
                      <b-icon icon="lock-fill"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input type="password" placeholder="비밀번호" required v-model="user.password"></b-form-input>
                  </b-input-group>
                  <b-input-group class="mt-3">
                    <b-input-group-prepend is-text>
                      <b-icon icon="person-plus-fill"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input placeholder="이름" required v-model="user.userName"></b-form-input>
                  </b-input-group>
                  <b-input-group class="mt-3">
                    <b-input-group-prepend is-text>
                      <b-icon icon="envelope"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input placeholder="이메일" v-model="user.email"></b-form-input>
                  </b-input-group>
                  <b-input-group class="mt-3">
                    <b-input-group-prepend is-text>
                      <b-icon icon="phone"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input placeholder="전화번호" v-model="user.phone"></b-form-input>
                  </b-input-group>

                  <b-row class=" my-4">
                    <b-col cols="12">
                      <b-input-group required>
                        <b-form-checkbox v-model="user.agree">
                          <span class="text-muted">개인정보보호정책 및 이용약관에 동의합니다.</span>
                        </b-form-checkbox>
                      </b-input-group>
                    </b-col>
                  </b-row>
                  <div class="text-center">
                    <b-button type="submit" variant="primary" class="mt-4">회원가입하기</b-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { userRegister, idCheck } from "@/api/user.js";
  export default {
    name: 'SignUp',
    data() {
      return {
        user: {
          userId:'',
          userName: '',
          email: '',
          phone: '',
          password: '',
          agree: false
        },
        check:null,
        usable:false
      }
    },
    methods: {
      idcheck(){
        idCheck(this.user.userId,
          (response) => {
            if (this.user.userId.length<4 || this.user.userId.length>=16){
              this.check="아이디는 4자 이상 16자 미만입니다.";
              this.usable=false;
            } else{
              if (response.data==0){
                this.check="사용가능한 아이디입니다."
                this.usable=true;
              } else {
                this.check="이미 사용중인 아이디입니다."
                this.usable=false;
              }
            }
          },
          (error) => {
            console.log(error);
          })
      },
      onSubmit() {
        // this will be called only after form is valid. You can do an api call here to register users
        // console.log(this.user);
        if (this.user.agree==false){
          alert("개인정보보호정책 및 이용약관에 동의해주세요.")
        } else {
          userRegister(this.user,
            ({ data }) => {
              let msg = "수정 처리시 문제가 발생했습니다.";
              if (data == "success") {
                msg="회원가입이 되었습니다.";
              }
              alert(msg);
              // 현재 route를 /list로 변경.
              this.$router.push({ name: "SignIn" });
            },
            (error) => {
              console.log(error);
            }
          );

        }
      }
    }

  };
</script>
<style></style>
