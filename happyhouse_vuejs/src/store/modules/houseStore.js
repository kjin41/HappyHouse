import {
  sidoList,
  gugunList,
  dongList,
  houseList,
  envList,
  commList,
  houseDealList,
  jjimList,
  jjimInsert,
  jjimDelete,
  priceList,
  trainList,
  clinicList,
  busList,
  starbucksList,
  bikeList
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "시/도" }],
    guguns: [{ value: null, text: "구/군" }],
    dongs: [{ value: null, text: "읍/면/동" }],
    sidoC: null,
    gugunC: null,
    dongC: null,
    sidoName: "",
    gugunName: "",
    dongName: "",
    houses: [],
    house: null,
    envs: [],
    comms: [],
    jjims: [],
    houseDeals: [],
    housePrices: [],
    numList: [],
    avgList: [],
    trains: [],
    clinics: [],
    buses: [],
    starbuckses: [],
    bikes: []
  },
  mutations: {
    SET_SIDO_LIST(state, sidos) {
      state.sidoName = "";
      state.gugunName = "";
      state.dongName = "";
      state.sidoC = "";
      state.gugunC = "";
      state.dongC = "";
      sidos.forEach(sido => {
        state.sidos.push({
          value: sido.sidoCode,
          text: sido.sidoName
        });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      state.gugunName = "";
      state.dongName = "";
      state.gugunC = "";
      state.dongC = "";
      guguns.forEach(gugun => {
        state.guguns.push({
          value: gugun.gugunCode,
          text: gugun.gugunName
        });
      });
    },
    SET_DONG_LIST(state, dongs) {
      state.dongName = "";
      state.dongC = "";
      dongs.forEach(dong => {
        state.dongs.push({
          value: dong.dongCode,
          text: dong.dongName
        });
      });
    },
    SET_SIDO(state, sidoC) {
      state.sidoC = sidoC;
    },
    SET_GUGUN(state, gugunC) {
      state.gugunC = gugunC;
    },
    SET_DONG(state, dongC) {
      state.dongC = dongC;
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = [];
      houses.forEach(house => {
        let address =
          house.법정동 + " " + house.도로명 + " " + house.도로명건물본번호코드;
        state.houses.push({
          aptCode: house.일련번호,
          sidoName: state.sidoName,
          gugunName: state.gugunName,
          aptName: house.아파트,
          dongCode: house.법정동시군구코드,
          dongName: house.법정동,
          roadName: house.도로명,
          buildName: house.도로명건물본번호코드,
          buildYear: house.건축년도,
          jibun: house.지번,
          recentPrice: house.거래금액,
          address: address
        });
        // console.log(house);
      });
    },
    SET_ENV_LIST(state, envs) {
      state.envs = [];
      envs.forEach(env => {
        state.envs.push({
          facName: env.facName,
          jobName: env.jobName,
          checkAgName: env.checkAgName,
          checkDesc: env.checkDesc,
          roadAddress: env.roadAddress,
          mapCheckDate: env.mapCheckDate
        });
        // console.log(house);
      });
    },
    SET_COMM_LIST(state, comms) {
      state.comms = [];
      comms.forEach(comm => {
        state.comms.push({
          no: comm.no,
          name: comm.name,
          type: comm.type,
          sidoName: comm.sidoName,
          gugunName: comm.gugunName,
          dongCode: comm.dongCode,
          dongName: comm.dongName,
          lat: comm.lat,
          lng: comm.lng
        });
      });
    },
    async SET_HOUSEDEAL_LIST(state, housedeals) {
      state.houseDeals = [];
      state.numList = [];
      state.avgList = [];
      let nList = [0, 0, 0, 0, 0, 0];
      let avgList = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0];
      housedeals.forEach(housedeal => {
        let dealYMD =
          housedeal.년 + "년 " + housedeal.월 + "월 " + housedeal.일 + "일";
        let address =
          state.sidoName +
          " " +
          state.gugunName +
          " " +
          housedeal.법정동 +
          " " +
          housedeal.지번;
        nList[parseInt(housedeal.월) - 5]++;
        avgList[parseInt(housedeal.월) - 5] += parseFloat(
          housedeal.거래금액.replace(/ /g, "").replace(",", ".")
        );
        state.houseDeals.push({
          aptCode: housedeal.일련번호,
          sidoName: state.sidoName,
          gugunName: state.gugunName,
          aptName: housedeal.아파트,
          dongCode: housedeal.법정동시군구코드,
          dongName: housedeal.법정동,
          roadName: housedeal.도로명,
          buildName: housedeal.도로명건물본번호코드,
          buildYear: housedeal.건축년도,
          jibun: housedeal.지번,
          year: housedeal.년,
          month: housedeal.월,
          day: housedeal.일,
          recentPrice: housedeal.거래금액,
          area: housedeal.전용면적,
          floor: housedeal.층,
          dealYMD: dealYMD,
          address: address
        });
      });
      for (let i = 0; i < 6; i++) {
        if (nList[i] != 0) {
          avgList[i] = avgList[i] / nList[i];
        }
      }
      state.numList = nList;
      state.avgList = avgList;
    },
    SET_JJIM_LIST(state, jjims) {
      state.jjims = [];
      jjims.forEach(jjim => {
        state.jjims.push({
          dongCode: jjim.dongCode,
          dongNo: jjim.dongNo,
          sidoName: jjim.sidoName,
          gugunName: jjim.gugunName,
          dongName: jjim.dongName
        });
      });
    },
    SET_JJIM_LIST(state, jjims) {
      state.jjims = [];
      jjims.forEach(jjim => {
        state.jjims.push({
          dongCode: jjim.dongCode,
          dongNo: jjim.dongNo,
          sidoName: jjim.sidoName,
          gugunName: jjim.gugunName,
          dongName: jjim.dongName
        });
      });
    },
    SET_SIGUDONG_NAME(state, name) {
      state.sidoName = name.sido;
      state.gugunName = name.gugun;
      state.dongName = name.dong;
    },
    SET_GUGUNC(state, code) {
      state.gugunC = code;
    },
    SET_DETAIL_HOUSE(state, house) {},
    SET_PRICE_LIST(state, houses) {
      state.housePrices = [];
      houses.forEach(house => {
        let dealAmount = house.dealAmount.trim();
        state.housePrices.push({
          aptName: house.aptName,
          dongCode: house.dongCode,
          dongName: house.dongName,
          sidoName: house.sidoName,
          gugunName: house.gugunName,
          buildYear: house.buildYear,
          jibun: house.jibun,
          lat: house.lat,
          lng: house.lng,
          recentPrice: dealAmount,
          dealAmount: dealAmount,
          dealYear: house.dealYear,
          dealMonth: house.dealMonth,
          dealDay: house.dealDay,
          area: house.area,
          floor: house.floor,
          address: house.address
        });
      });
    },
    SET_TRAIN_LIST(state, trains) {
      state.trains = [];
      trains.forEach(train => {
        state.trains.push({
          name: train.name,
          distance: train.distance,
          address: train.address
        });
      });
    },
    SET_CLINIC_LIST(state, clinics) {
      state.clinics = [];
      clinics.forEach(clinic => {
        state.clinics.push({
          name: clinic.name,
          distance: clinic.distance,
          address: clinic.address
        });
      });
    },
    SET_BUS_LIST(state, buses) {
      state.buses = [];
      buses.forEach(bus => {
        state.buses.push({
          name: bus.name,
          distance: bus.distance
        });
      });
    },
    SET_STARBUCKS_LIST(state, starbuckses) {
      state.starbuckses = [];
      starbuckses.forEach(starbucks => {
        state.starbuckses.push({
          name: starbucks.name,
          distance: starbucks.distance,
          address: starbucks.address
        });
      });
    },
    SET_BIKE_LIST(state, bikes) {
      state.bikes = [];
      bikes.forEach(bike => {
        state.bikes.push({
          name: bike.name,
          distance: bike.distance,
          address: bike.address
        });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시/도" }];
      state.sidoC = null;
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구/군" }];
      state.gugunC = null;
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "읍/면/동" }];
      state.dongC = null;
    },
    CLEAR_SIGUDONG_NAME(state, name) {
      state.sidoName = "";
      state.gugunName = "";
      state.dongName = "";
    },
    CLEAR_PRICE_LIST(state) {
      state.housePrices = [];
    }
    // CLEAR_HOUSES_LIST(state) {
    //   state.houses = [];
    // }
  },
  actions: {
    getSido({ commit }) {
      sidoList(
        ({ data }) => {
          // console.log(data);
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
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    getDong({ commit }, gugunCode) {
      const params = {
        gugun: gugunCode
      };
      dongList(
        params,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    aptList({ commit }, gugun) {
      const params = {
        pageNo: "1",
        numOfRows: "10",
        LAWD_CD: gugun,
        DEAL_YMD: "202110"
      };
      houseList(
        params,
        ({ data }) => {
          commit("SET_HOUSE_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    envList({ commit }, address) {
      console.log(address);
      const params = {
        addr: address
      };
      envList(
        params,
        ({ data }) => {
          commit("SET_ENV_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    async commList({ commit }, address) {
      console.log(address);
      const params = {
        addr: address
      };
      await commList(
        params,
        ({ data }) => {
          commit("SET_COMM_LIST", data);
        },
        error => {
          console.log(error);
        }
        // () => {}
      );
    },
    houseDealList({ commit }, address) {
      const params = {
        pageNo: "1",
        numOfRows: "30",
        LAWD_CD: address.gugun,
        houseName: address.address
      };
      houseDealList(
        params,
        ({ data }) => {
          console.log(data);
          commit("SET_HOUSEDEAL_LIST", data);
        },
        error => {
          console.log(error);
        }
        // () => {}
      );
    },
    jjimList({ commit }, userId) {
      jjimList(
        userId,
        ({ data }) => {
          commit("SET_JJIM_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    async jjimInsert({ commit }, info) {
      const param = {
        userId: info.userId,
        dongCode: info.dongCode
      };
      const paramlist = {
        userId: info.userId
      };
      console.log("param", param);
      await jjimInsert(
        param,
        ({ data }) => {
          // console.log(commit, data);
          commit("SET_JJIM_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    jjimDelete({ commit }, info) {
      const param = {
        userId: info.userId,
        dongCode: info.dongCode
      };
      jjimDelete(
        param,
        ({ data }) => {
          // console.log(commit, data);
          commit("SET_JJIM_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    priceList({ commit }, coord) {
      const param = {
        lat1: coord.lat1,
        lng1: coord.lng1,
        lat2: coord.lat2,
        lng2: coord.lng2,
        stPrice: coord.stPrice,
        endPrice: coord.endPrice
      };
      priceList(
        param,
        ({ data }) => {
          commit("SET_PRICE_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    trainList({ commit }, coord) {
      const param = {
        lat: coord.lat,
        lng: coord.lng
      };
      trainList(
        param,
        ({ data }) => {
          commit("SET_TRAIN_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    clinicList({ commit }, coord) {
      const param = {
        lat: coord.lat,
        lng: coord.lng
      };
      clinicList(
        param,
        ({ data }) => {
          commit("SET_CLINIC_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    busList({ commit }, coord) {
      const param = {
        lat: coord.lat,
        lng: coord.lng
      };
      busList(
        param,
        ({ data }) => {
          commit("SET_BUS_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    starbucksList({ commit }, coord) {
      const param = {
        lat: coord.lat,
        lng: coord.lng
      };
      starbucksList(
        param,
        ({ data }) => {
          commit("SET_STARBUCKS_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    },
    bikeList({ commit }, coord) {
      const param = {
        lat: coord.lat,
        lng: coord.lng
      };
      bikeList(
        param,
        ({ data }) => {
          commit("SET_BIKE_LIST", data);
        },
        error => {
          console.log(error);
        }
      );
    }
  }
};

export default houseStore;