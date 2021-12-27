import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import houseStore from "@/store/modules/houseStore.js";
import qnaStore from "@/store/modules/qnaStore.js";
import userStore from "@/store/modules/userStore.js";
import newsStore from "@/store/modules/newsStore.js";

const store = new Vuex.Store({
  modules: {
    houseStore,
    qnaStore,
    userStore,
    newsStore
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage
    })
  ]
});

export default store;
