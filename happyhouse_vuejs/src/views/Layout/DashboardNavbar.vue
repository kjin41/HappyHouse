<template>
  <base-nav
    container-classes="container-fluid"
    class="navbar-top navbar-expand"
    :class="{'navbar-dark': type === 'default'}"
  >
    <span aria-current="page" class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block active router-link-active" v-if="$route.name=='dashboard'">NEWS</span>
    <span aria-current="page" class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block active router-link-active" v-else> {{$route.name}} </span>
    <!-- Navbar links -->
    <b-navbar-nav class="align-items-center ml-md-auto">
      <!-- This item dont have <b-nav-item> because item have data-action/data-target on tag <a>, wich we cant add -->
      <!-- <li class="nav-item d-sm-none">
        <a class="nav-link" href="#" data-action="search-show" data-target="#navbar-search-main">
          <i class="ni ni-zoom-split-in"></i>
        </a>
      </li> -->
    </b-navbar-nav>
    <b-navbar-nav class="align-items-center ml-auto ml-md-0">
        <!-- <b-form class="navbar-search form-inline mr-sm-3"
            :class="{'navbar-search-dark': type === 'default', 'navbar-search-light': type === 'light'}"
            id="navbar-search-main"> -->
        <!-- <b-form-group class="mb-0">
          <b-input-group class="input-group-alternative input-group-merge">
            <b-form-input placeholder="Search" type="text"> </b-form-input>

            <div class="input-group-append">
              <span class="input-group-text"><i class="fas fa-search"></i></span>
            </div>
          </b-input-group>
        </b-form-group> -->
      <!-- </b-form> -->
      <base-dropdown menu-on-right
                     class="nav-item"
                     tag="li"
                     title-tag="a"
                     title-classes="nav-link pr-0">
        <b-button class="nav-link pr-0" @click.prevent slot="title-container">
          <b-media no-body class="align-items-center">
                  <span class="avatar avatar-sm rounded-circle">
                    <i class="ni ni-favourite-28"></i>
                    <!-- <img alt="Image placeholder" src="img/theme/team-4.jpg"> -->
                  </span>
            <b-media-body class="ml-2 d-none d-lg-block">
              <span class="m-3 text-sm font-weight-bold text-dark" v-if="userInfo">{{userInfo.userName}}님 환영합니다!</span>
            </b-media-body>
          </b-media>
        </b-button>

        <template>

          <!-- <b-dropdown-header class="noti-title">
            <h6 class="text-overflow m-0">환영합니다!</h6>
          </b-dropdown-header> -->
          <b-dropdown-item @click.prevent="mvProfile" v-if="userInfo">
            <i class="ni ni-single-02"></i>
            <span>내 계정</span>
          </b-dropdown-item>
          <b-dropdown-item @click.prevent="mvSignUp" v-if="!userInfo">
            <i class="ni ni-single-02"></i>
            <span>회원가입</span>
          </b-dropdown-item>
          <div class="dropdown-divider"></div>
          <b-dropdown-item @click.prevent="mvSignIn" v-if="!userInfo">
            <i class="ni ni-single-02"></i>
            <span>로그인</span>
          </b-dropdown-item>
          <b-dropdown-item @click.prevent="onClickLogout" v-if="userInfo">
            <i class="ni ni-user-run"></i>
            <span>로그아웃</span>
          </b-dropdown-item>

        </template>
      </base-dropdown>
    </b-navbar-nav>
  </base-nav>
</template>
<script>
import { CollapseTransition } from 'vue2-transitions';
import { BaseNav, Modal } from '@/components';


import { mapState, mapMutations } from "vuex";

const userStore = "userStore";

export default {
  components: {
    CollapseTransition,
    BaseNav,
    Modal
  },
  props: {
    type: {
      type: String,
      default: 'default', // default|light
      description: 'Look of the dashboard navbar. Default (Green) or light (gray)'
    }
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),


    routeName() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    }
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: ''
    };
  },
  methods: {
    ...mapMutations(userStore, ["SET_IS_LOGIN", "SET_USER_INFO", "SET_IS_KAKAO_LOGIN"]),
    onClickLogout() {
      console.log("logout");
      this.SET_IS_LOGIN(false);
      this.SET_IS_KAKAO_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/dashboard") this.$router.push({ name: "dashboard" });
    },
    mvProfile(){
      this.$router.push({ name: "profile" });
    },
    mvSignUp(){
      this.$router.push({ name: "SignUp" });
    },
    mvSignIn(){
      this.$router.push({ name: "SignIn" });
    },
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    }
  }
};
</script>
