import { sidoList, gugunList, getNews, getTemp, getCorona } from "@/api/news";

const newsStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "시/도" }],
    guguns: [{ value: null, text: "구/군" }],
    sidoC: null,
    gugunC: null,
    sidoName: "",
    gugunName: "",
    news: [],
    temps: {},
    coronas: []
  },
  mutations: {
    SET_SIDO_LIST(state, sidos) {
      state.sidoName = "";
      state.gugunName = "";
      state.sidoC = "";
      state.gugunC = "";
      sidos.forEach(sido => {
        state.sidos.push({
          value: sido.sidoCode,
          text: sido.sidoName
        });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      state.gugunName = "";
      state.gugunC = "";
      guguns.forEach(gugun => {
        state.guguns.push({
          value: gugun.gugunCode,
          text: gugun.gugunName
        });
      });
    },
    SET_SIGU_NAME(state, name) {
      state.sidoName = name.sido;
      state.gugunName = name.gugun;
    },
    CLEAR_SIGU_NAME(state, name) {
      state.sidoName = "";
      state.gugunName = "";
    },
    SET_NEWS_LIST(state, news) {
      state.news = [];
      news.forEach(article => {
        state.news.push({
          url: article.url,
          title: article.title,
          content: article.content,
          writing: article.writing,
          date: article.date,
          imgUrl: article.imgUrl
        });
      });
    },
    SET_TEMP_LIST(state, temp) {
      state.temps = {};
      const result = {
        low: temp.low,
        high: temp.high
      };
      state.temps = result;
    },
    SET_CORONA_LIST(state, coronas) {
      state.coronas = [];
      coronas.forEach(corona => {
        state.coronas.push({
          region: corona.region,
          tdTotal: corona.tdTotal,
          tdIn: corona.tdIn,
          tdOut: corona.tdOut,
          total: corona.total,
          cure: corona.cure,
          dead: corona.dead,
          rate: corona.rate
        });
      });
    },
    SET_SIDO(state, sidoC) {
      state.sidoC = sidoC;
    },
    SET_GUGUN(state, gugunC) {
      state.gugunC = gugunC;
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시/도" }];
      state.sidoC = null;
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구/군" }];
      state.gugunC = null;
    }
  },
  actions: {
    getSido({ commit }) {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    getGugun({ commit }, sidoCode) {
      const params = {
        sido: sidoCode
      };
      gugunList(
        params,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    newsList({ commit }, data) {
      const params = {
        cityNo: data.sido,
        dvsnNo: data.gugun
      };
      getNews(
        params,
        ({ data }) => {
          commit("SET_NEWS_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    tempList({ commit }) {
      getTemp(
        ({ data }) => {
          commit("SET_TEMP_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    coronaList({ commit }) {
      getCorona(
        ({ data }) => {
          commit("SET_CORONA_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    }
  }
};
export default newsStore;