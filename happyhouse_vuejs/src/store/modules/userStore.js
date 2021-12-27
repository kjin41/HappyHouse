import jwt_decode from "jwt-decode";
import { login, findById, getKakaoToken, getKakaoUser } from "@/api/user.js";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isKakaoLogin: false,
    isLoginError: false,
    userInfo: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      console.log(isLogin);
      state.isLogin = isLogin;
    },
    SET_IS_KAKAO_LOGIN: (state, isLogin) => {
      console.log(isLogin);
      state.isKakaoLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          console.log("userConfirm : response");
          // console.log(response);
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },

    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      console.log(decode_token);
      findById(
        decode_token.userId,
        (response) => {
          // console.log(response);
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async kakaoUserConfirm({ commit }, code) {
      await getKakaoToken(code,
        ({data}) => {
          // console.log(data);
          console.log(data.access_token);
          console.log(commit);
          let token = data.access_token;
          commit("SET_IS_LOGIN", true);
          commit("SET_IS_KAKAO_LOGIN", true);
          commit("SET_IS_LOGIN_ERROR", false);
          sessionStorage.setItem("access-token", token);
        },
        (error) => {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_KAKAO_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
          console.log(error);
        }
      );
    },
    getKakaoUserInfo({ commit }, token) {
      getKakaoUser(
        token,
        (response) => {
          console.log(response.data);
          let name=response.data.properties.nickname
          console.log(commit);
          commit("SET_USER_INFO", {userId: name, userName:name});
        },
        (error) => {
          console.log(error);
          //   console.log("유저 정보 없음!!");
        }
      );
    },
  },
};

export default userStore;
