import DashboardLayout from "@/views/Layout/DashboardLayout.vue";
import AuthLayout from "@/views/Pages/AuthLayout.vue";

import NotFound from "@/views/NotFoundPage.vue";

// import User from "@/views/User.vue";
import UserLogin from "@/views/Pages/Login.vue";
import UserJoin from "@/views/Pages/Register.vue";
import UserMyPage from "@/views/Pages/UserProfile.vue";

import store from "@/store/index.js";

import Notice from "@/views/Notice.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeWrite from "@/components/notice/NoticeWrite.vue";
import NoticeView from "@/components/notice/NoticeView.vue";
import NoticeUpdate from "@/components/notice/NoticeUpdate.vue";

import Qna from "@/views/Qna.vue";
import QnaRegister from "@/components/qna/QnaRegister.vue";
import QnaDelete from "@/components/qna/QnaDelete.vue";
import QnaList from "@/components/qna/QnaList.vue";
import QnaModify from "@/components/qna/QnaModify.vue";
import QnaView from "@/components/qna/QnaView.vue";

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const getUserInfo = store._actions["userStore/getUserInfo"];
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
      {
        path: "/profile",
        name: "profile",
        component: () =>
          import(
            /* webpackChunkName: "demo" */ "../views/Pages/UserProfile.vue"
          )
      },

      // map 추가
      {
        path: "/maps",
        name: "maps",
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
        path: "join",
        name: "SignUp",
        component: UserJoin,
      },
      {
        path: "mypage",
        name: "MyPage",
        beforeEnter: onlyAuthUser,
        component: UserMyPage,
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
            beforeEnter: onlyAuthUser,
            component: NoticeWrite,
          },
          {
            path: "detail/:no",
            name: "NoticeView",
            // beforeEnter: onlyAuthUser,
            component: NoticeView,
          },
          {
            path: "update/:no",
            name: "NoticeUpdate",
            // beforeEnter: onlyAuthUser,
            component: NoticeUpdate,
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
            component:QnaList
          },
          {
            path:"/qna/create",
            name:"QnaRegister",
            component:QnaRegister
          },
          {
            path:"/qna/delete/:no",
            name:"QnaDelete",
            component:QnaDelete
          },
          {
            path:"/qna/modify/:no",
            name:"QnaModify",
            component:QnaModify
          },
          {
            path:"/qna/view/:no",
            name:"QnaView",
            component:QnaView
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

export default routes;
