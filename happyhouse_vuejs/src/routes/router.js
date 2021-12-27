import Vue from 'vue';
import VueRouter from 'vue-router';
// import routes from './routes';

Vue.use(VueRouter);


import DashboardLayout from "@/views/Layout/DashboardLayout.vue";
import AuthLayout from "@/views/Pages/AuthLayout.vue";

import NotFound from "@/views/NotFoundPage.vue";

// import Maps from "@/views/Maps.vue";
// import KakaoMap from "@/components/Map/KakaoMap.vue";
// import User from "@/views/User.vue";
import UserLogin from "@/views/Pages/Login.vue";
import KakaoLogin from "@/views/Pages/KakaoLogin.vue";
import UserJoin from "@/views/Pages/Register.vue";
import UserProfile from "@/views/Pages/UserProfile.vue";
import UserModify from "@/views/Pages/UserModify.vue";
import UserDelete from "@/views/Pages/UserProfile/UserDelete.vue";

import store from "@/store/index.js";

import Notice from "@/views/Notice.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeWrite from "@/components/notice/NoticeWrite.vue";
import NoticeView from "@/components/notice/NoticeView.vue";
import NoticeUpdate from "@/components/notice/NoticeUpdate.vue";
import NoticeDelete from "@/components/notice/NoticeDelete.vue";

import Qna from "@/views/Qna.vue";
import QnaRegister from "@/components/qna/QnaRegister.vue";
import QnaDelete from "@/components/qna/QnaDelete.vue";
import QnaList from "@/components/qna/QnaList.vue";
import QnaModify from "@/components/qna/QnaModify.vue";
import QnaView from "@/components/qna/QnaView.vue";
// import QnaWriteForm from "@/components/qna/child/QnaWriteForm.vue";
// import QnaComment from "@/components/qna/child/comment/QnaComment.vue";
// import QnaCommentWrite from "@/components/qna/child/comment/QnaCommentWrite.vue";

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  // console.log(checkUserInfo);
  const getUserInfo = store._actions["userStore/getUserInfo"];
  // console.log(getUserInfo);
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "SignIn" });
    router.push({ name: "SignIn" });
  } else {
    console.log("로그인 했다.");
    next();
  }
};

const onlyAdmin = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  // console.log(checkUserInfo);
  const getUserInfo = store._actions["userStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo == null || checkUserInfo.userId != 'admin') {
    alert("관리자 외 접근할 수 없습니다.");
    // next({ name: "SignIn" });
    router.push({ name: "SignIn" });
  } else {
    console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    redirect: "dashboard",
    component: DashboardLayout,
    children: [
      {
        path: "/dashboard",
        name: "dashboard",
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Dashboard.vue")
      },
      {
        path: "/icons",
        name: "icons",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Icons.vue")
      },

      // BaseSlider.vue
      {
        path: "/slider",
        name: "Slider",
        component: () =>
          import(/* webpackChunkName: "demo" */ "@/components/BaseSlider.vue")
      },

      // map 추가
      {
        path: "/maps",
        name: "maps",
        // component: Maps,
        // children:[
        //   {
        //     path: "/kakaomap",
        //     name: "Kakaomap",
        //     component: KakaoMap,

        //   }
        // ]
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Maps.vue")
      },
      {
        path: "/tables",
        name: "tables",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/RegularTables.vue")
      },

      // user 추가
      {
        path: "login",
        name: "SignIn",
        component: UserLogin,
      },
      {
        path: "kakaologin",
        name: "KakaoLogin",
        component: KakaoLogin,
      },
      {
        path: "join",
        name: "SignUp",
        component: UserJoin,
      },
      {
        path: "/profile",
        name: "profile",
        component: UserProfile,
        beforeEnter: onlyAuthUser,
      },
      {
        path: "/profile/modify/",
        name: "UserModify",
        component: UserModify,
        beforeEnter: onlyAuthUser,
      },
      {
        path: "/profile/delete/:userId",
        name: "UserDelete",
        component: UserDelete,
        beforeEnter: onlyAuthUser,
      },
        

      // notice 추가
      {
        path: "/notice",
        name: "Notice",
        component: Notice,
        redirect: "/notice/list",
        children: [
          {
            path: "list",
            name: "NoticeList",
            component: NoticeList,
          },
          {
            path: "write",
            name: "NoticeWrite",
            beforeEnter: onlyAdmin,
            component: NoticeWrite,
          },
          {
            path: "detail/:no",
            name: "NoticeView",
            component: NoticeView,
          },
          {
            path: "update/:no",
            name: "NoticeUpdate",
            beforeEnter: onlyAdmin,
            component: NoticeUpdate,
          },
          {
            path: "delete/:no",
            name: "NoticeDelete",
            beforeEnter: onlyAdmin,
            component: NoticeDelete,
          },
        ],
      },

      // qna 추가
      {
        path: "/qna",
        name: "Qna",
        component: Qna,
        redirect:"/qna/list",
        children:[
          {
            path:"/qna/list",
            name:"QnaList",
            beforeEnter: onlyAuthUser,
            component:QnaList
          },
          {
            path:"/qna/create",
            name:"QnaRegister",
            beforeEnter: onlyAuthUser,
            component:QnaRegister
          },
          {
            path:"/qna/delete/:no",
            name:"QnaDelete",
            beforeEnter: onlyAuthUser,
            component:QnaDelete
          },
          {
            path:"/qna/modify/:no",
            name:"QnaModify",
            beforeEnter: onlyAuthUser,
            component:QnaModify
          },
          {
            path:"/qna/view/:no",
            name:"QnaView",
            beforeEnter: onlyAuthUser,
            component:QnaView,
          },
        ]
        
      },
    ]
  },
  {
    path: "/",
    redirect: "login",
    component: AuthLayout,
    children: [
      {
        path: "/login",
        name: "login",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Login.vue")
      },
      {
        path: "/register",
        name: "register",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Register.vue")
      },
      { path: "*", component: NotFound }
    ]
  }
];


// configure router
const router = new VueRouter({
  mode: "history",
  routes, // short for routes: routes
  linkActiveClass: 'active',
  scrollBehavior: (to, from ,savedPosition) => {
    if (savedPosition) {
      return savedPosition;
    }
    if (to.hash) {
      return { selector: to.hash };
    }
    return { x: 0, y: 0 };
  }
});

export default router;
