/*!

=========================================================
* BootstrapVue Argon Dashboard - v1.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/bootstrap-vue-argon-dashboard
* Copyright 2020 Creative Tim (https://www.creative-tim.com)

* Coded by www.creative-tim.com

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import Vue from "vue";
import DashboardPlugin from "./plugins/dashboard-plugin";
import App from "./App.vue";

// router setup
import router from "./routes/router";
import store from "./store";
// import userStore from "./store/modules/userStore";

// plugin setup
Vue.use(DashboardPlugin);
Vue.use(store);

// table setup
import VueGoodTablePlugin from 'vue-good-table';

// import the styles
import 'vue-good-table/dist/vue-good-table.css'

Vue.use(VueGoodTablePlugin);


// import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
// // Import Bootstrap an BootstrapVue CSS files (order is important)
// import "bootstrap/dist/css/bootstrap.css";
// import "bootstrap-vue/dist/bootstrap-vue.css";
// // Make BootstrapVue available throughout your project
// Vue.use(BootstrapVue);
// // Optionally install the BootstrapVue icon components plugin
// Vue.use(IconsPlugin);

Vue.config.productionTip = false;
/* eslint-disable no-new */
new Vue({
  // el: "#app",
  router,
  store,
  // async beforeCreate() {
  //   let token = sessionStorage.getItem("access-token");
  //   console.log(token);
  //   if (userStore.state.userInfo == null && token) {
  //     await userStore.dispatch("getUserInfo", token);
  //   }
  // },
  render: h => h(App),
}).$mount("#app");
