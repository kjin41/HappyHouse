<template>
  <section class="page-section happyhouse" id="happyhouse">
    <div>
      <base-header class="pb-1 bg-gradient-success">
        <h1
          class="pt-3"
          style="text-align: center; font-size: 40px; color: #333333; font-family: Impact, Charcoal,sans-serif; font-style:italic; text-decoration:underline;"
        >
          MAP SEARCH
        </h1>
      </base-header>
      <section id="index_section">
        <div class="card col-sm-12 mt-1" style="min-height: 850px">
          <div class="card-body">
            <div id="container">
              <div id="rvWrapper">
                <div id="roadview" style="width:100%;height:100%;"></div>
                <!-- 로드뷰를 표시할 div 입니다 -->
                <div id="close" title="로드뷰닫기" @click="closeRoadview()">
                  <span class="img"></span>
                </div>
              </div>
              <div id="mapWrapper">
                <div id="map" style="width: 100%; height: 800px">
                  <div id="roadviewControl" @click="setRoadviewRoad()"></div>
                  <transition name="fade">
                    <img
                      src="img/happyhouse/aptsearch_logo.png"
                      id="aptcontrol"
                      class="aptSearchBtn c-fade"
                      title="아파트검색"
                      @click="aptSearch()"
                      v-show="mshow"
                    />
                  </transition>
                  <transition name="fade">
                    <img
                      src="img/happyhouse/jjim.png"
                      id="jjimcontrol"
                      class="v-fade"
                      title="즐겨찾기"
                      @click="jjimSearch()"
                      v-show="mshow"
                    />
                  </transition>
                  <transition name="fade">
                    <img
                      src="img/happyhouse/comm.png"
                      id="commcontrol"
                      class="commSearchBtn v-fade"
                      title="동네상권"
                      @click="commSearch()"
                      v-show="mshow"
                    />
                  </transition>
                  <transition name="fade">
                    <img
                      src="img/happyhouse/environment.png"
                      id="envcontrol"
                      class="envSearchBtn v-fade"
                      title="동네환경점검"
                      @click="envSearch()"
                      v-show="mshow"
                    />
                  </transition>
                  <map-search-bar
                    v-show="mshow"
                    class="v-fade"
                  ></map-search-bar>
                  <transition name="fade">
                    <button
                      type="button"
                      class="btn btn-primary btn-sm ml-2 v-fade "
                      id="jjimRegisterBtn"
                      v-if="checkUserInfo != null"
                      @click="jjimRegister()"
                      v-show="mshow"
                    >
                      관심지역 등록
                    </button>
                  </transition>
                  <transition name="fade">
                    <button
                      type="button"
                      class="btn btn-warning btn-sm v-fade"
                      id="clearBtn"
                      @click="allClear()"
                      v-show="mshow"
                    >
                      초기화
                    </button>
                  </transition>
                  <div>
                    <b-modal
                      id="modal-center"
                      ref="infoModal"
                      size="xl"
                      centered
                      scrollable
                      title="아파트 상세정보"
                      v-if="modal"
                    >
                      <b-container fluid>
                        <b-row
                          class="mb-5 font-weight-bold"
                          style="font-size: 30px;"
                        >
                          <b-col>{{ selectedHouse.aptName }}</b-col>
                        </b-row>
                        <b-row class="mb-5 text-center">
                          <b-col cols="3"
                            ><img
                              src="img/happyhouse/apt.png"
                              style="width:380px; height:280px; border-radius: 30px;"
                              alt=""
                          /></b-col>
                          <b-col cols="5" id="infotable">
                            <div
                              class="container mt-3"
                              style="margin-left:0px;padding-left:0px;"
                            >
                              <h1>아파트 정보</h1>
                              <table
                                class="table table-borderless"
                                style="font-size:30px"
                              >
                                <tbody>
                                  <tr>
                                    <th style="font-size:25px">주소</th>
                                    <td style="font-size:20px">
                                      {{ selectedHouse.address }}
                                    </td>
                                  </tr>
                                  <tr>
                                    <th style="font-size:25px">건축년도</th>
                                    <td style="font-size:20px">
                                      {{ selectedHouse.buildYear }}년
                                    </td>
                                  </tr>
                                  <tr>
                                    <th style="font-size:25px">최근거래금액</th>
                                    <td style="font-size:20px">
                                      {{ selectedHouse.recentPrice }}(만 원)
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                            </div>
                          </b-col>
                        </b-row>

                        <b-row class="mb-5">
                          <b-col xl="8">
                            <card
                              type="default"
                              header-classes="bg-transparent"
                            >
                              <b-row align-v="center" slot="header">
                                <b-col>
                                  <h6
                                    class="text-light text-uppercase ls-1 mb-1"
                                  >
                                    최근 6개월 이내 (단위 : 1000만 원)
                                  </h6>
                                  <h5 class="h3 text-white mb-0">
                                    아파트 월 평균 거래 금액
                                  </h5>
                                </b-col>
                                <b-col>
                                  <b-nav class="nav-pills justify-content-end">
                                    <b-nav-item
                                      class="mr-2 mr-md-0"
                                      :active="bigLineChart.activeIndex === 0"
                                      link-classes="py-2 px-3"
                                      @click.prevent="initBigChart(0)"
                                    >
                                      <span class="d-md-none">M</span>
                                    </b-nav-item>
                                  </b-nav>
                                </b-col>
                              </b-row>
                              <line-chart
                                :height="300"
                                ref="bigChart"
                                class="bigChartDiv"
                                :chart-data="bigLineChart.chartData"
                                :extra-options="bigLineChart.extraOptions"
                              >
                              </line-chart>
                            </card>
                          </b-col>
                          <b-col xl="4">
                            <card header-classes="bg-transparent">
                              <b-row align-v="center" slot="header">
                                <b-col>
                                  <h6
                                    class="text-uppercase text-muted ls-1 mb-1"
                                  >
                                    최근 6개월 이내
                                  </h6>
                                  <h5 class="h3 mb-0">월별 거래량</h5>
                                </b-col>
                              </b-row>

                              <bar-chart
                                :height="350"
                                ref="barChart"
                                class="barChartDiv"
                                :chart-data="redBarChart.chartData"
                              >
                              </bar-chart>
                            </card>
                          </b-col>
                        </b-row>

                        <b-row class="mb-3">
                          <b-col
                            cols="4"
                            class="trainNear"
                            style="text-align:center;"
                          >
                            <h3 class="ml-3">근처 지하철역 (800m 이내)</h3>
                            <ul style="list-style-type: decimal;">
                              <li
                                v-for="(info, index) in trainInfo"
                                :key="index"
                              >
                                <h4>
                                  역 이름 : {{ info.name }} ({{
                                    info.distance
                                  }}m)
                                </h4>
                              </li>
                            </ul>
                            <h4 class="ml-5" style="color:red;">
                              동네 지하철역 수 {{ trains.length }}
                              <b-button
                                size="sm"
                                class="mr-2 float-right"
                                v-b-modal.trainModal
                              >
                                자세히
                              </b-button>
                            </h4>
                            <h6 class="ml-5 float-right" style="color:red;">
                              *같은 이름의 역은 호선이 다른 역입니다.
                            </h6>
                          </b-col>
                          <b-col
                            cols="4"
                            class="clinicNear"
                            style="text-align:center;"
                          >
                            <h3 class="ml-3">근처 버스정류장 (300m 이내)</h3>
                            <ul style="list-style-type: decimal;">
                              <li v-for="(bus, index) in busInfo" :key="index">
                                <h4>
                                  정류장 이름 : {{ bus.name }} ({{
                                    bus.distance
                                  }}m)
                                </h4>
                              </li>
                            </ul>
                            <h4 class="ml-5" style="color:red;">
                              동네 버스정류장 수 {{ buses.length }}
                              <b-button
                                size="sm"
                                class="mt-2 mr-2 float-right"
                                v-b-modal.busModal
                              >
                                자세히
                              </b-button>
                            </h4>
                          </b-col>
                          <b-col xl="4" class="mb-5 mb-xl-0">
                            <card header-classes="bg-transparent">
                              <b-row align-v="center" slot="header">
                                <b-col>
                                  <h5
                                    class="text-uppercase text-muted ls-1 mb-1"
                                  >
                                    교통, 보건, 카페
                                  </h5>
                                  <h6 class="h3 mb-0">동네 편의시설 그래프</h6>
                                </b-col>
                              </b-row>
                              <div>
                                <canvas id="myChart"></canvas>
                              </div>
                            </card>
                          </b-col>
                        </b-row>
                        <b-row class="mb-1">
                          <b-col
                            cols="4"
                            class="clinicNear"
                            style="text-align:center;"
                          >
                            <h3 class="ml-3">근처 선별진료소 (2km 이내)</h3>
                            <ul style="list-style-type: decimal;">
                              <li
                                v-for="(clinic, index) in clinicInfo"
                                :key="index"
                              >
                                <h4>
                                  진료소 이름 : {{ clinic.name }} ({{
                                    clinic.distance
                                  }}m)
                                </h4>
                              </li>
                            </ul>
                            <h4 class="ml-5" style="color:red;">
                              동네 선별진료소 수 {{ clinics.length }}
                              <b-button
                                size="sm"
                                class="mt-2 mr-2 float-right"
                                v-b-modal.clinicModal
                              >
                                자세히
                              </b-button>
                            </h4>
                          </b-col>
                          <b-col
                            cols="4"
                            class="clinicNear"
                            style="text-align:center;"
                          >
                            <h3 class="ml-3">근처 starbucks (800m 이내)</h3>
                            <ul style="list-style-type: decimal;">
                              <li
                                v-for="(starbucks, index) in starbucksInfo"
                                :key="index"
                              >
                                <h4>
                                  지점 이름 : {{ starbucks.name }}점 ({{
                                    starbucks.distance
                                  }}m)
                                </h4>
                              </li>
                            </ul>
                            <h4 class="ml-5" style="color:red;">
                              동네 스타벅스 지점 수 {{ starbuckses.length }}
                              <b-button
                                size="sm"
                                class="mt-2 mr-2 float-right"
                                v-b-modal.starbucksModal
                              >
                                자세히
                              </b-button>
                            </h4>
                          </b-col>
                          <b-col
                            cols="4"
                            class="clinicNear"
                            style="text-align:center;"
                          >
                            <h3 class="ml-3">근처 따릉이 (400m 이내)</h3>
                            <ul style="list-style-type: decimal;">
                              <li
                                v-for="(bike, index) in bikeInfo"
                                :key="index"
                              >
                                <h4>
                                  따릉이 위치 : {{ bike.name }} ({{
                                    bike.distance
                                  }}m)
                                </h4>
                              </li>
                            </ul>
                            <h4 class="ml-5" style="color:red;">
                              동네 따릉이 수 {{ bikes.length }}
                              <b-button
                                size="sm"
                                class="mt-2 mr-2 float-right"
                                v-b-modal.bikeModal
                              >
                                자세히
                              </b-button>
                            </h4>
                          </b-col>
                        </b-row>
                      </b-container>
                    </b-modal>
                  </div>
                  <div>
                    <b-modal
                      id="trainModal"
                      size="lg"
                      centered
                      scrollable
                      title="지하철 상세정보"
                      v-if="modal"
                    >
                      <b-container fluid>
                        <div>
                          <b-table striped hover :items="trainDetail"></b-table>
                          <h6 class="ml-5 float-right" style="color:red;">
                            *같은 이름의 역은 호선이 다른 역입니다.
                          </h6>
                        </div>
                      </b-container>
                    </b-modal>
                  </div>
                  <div>
                    <b-modal
                      id="busModal"
                      size="lg"
                      centered
                      scrollable
                      title="정류장 상세정보"
                      v-if="modal"
                    >
                      <b-container fluid>
                        <div>
                          <b-table striped hover :items="busDetail"></b-table>
                        </div>
                      </b-container>
                    </b-modal>
                  </div>
                  <div>
                    <b-modal
                      id="clinicModal"
                      size="lg"
                      centered
                      scrollable
                      title="선별진료소 상세정보"
                      v-if="modal"
                    >
                      <b-container fluid>
                        <div>
                          <b-table
                            striped
                            hover
                            :items="clinicDetail"
                          ></b-table>
                        </div>
                      </b-container>
                    </b-modal>
                  </div>
                  <div>
                    <b-modal
                      id="starbucksModal"
                      size="lg"
                      centered
                      scrollable
                      title="스타벅스 상세정보"
                      v-if="modal"
                    >
                      <b-container fluid>
                        <div>
                          <b-table
                            striped
                            hover
                            :items="starbucksDetail"
                          ></b-table>
                        </div>
                      </b-container>
                    </b-modal>
                  </div>
                  <div>
                    <b-modal
                      id="bikeModal"
                      size="lg"
                      centered
                      scrollable
                      title="따릉이 상세정보"
                      v-if="modal"
                    >
                      <b-container fluid>
                        <div>
                          <b-table striped hover :items="bikeDetail"></b-table>
                        </div>
                      </b-container>
                    </b-modal>
                  </div>
                  <div class="MapControlView">
                    <div data-id="tools" class="tools">
                      <a data-id="distance" class="distance" href="#">
                        <span class="coach_distance">거리재기</span>
                      </a>
                      <a data-id="area" class="area" href="#"
                        ><span class="coach_area">면적재기</span></a
                      >
                      <a data-id="clear" class="clear" href="#"
                        ><span class="coach_clear">지우기</span></a
                      >
                    </div>
                  </div>
                  <transition name="fade">
                    <div class="pricesli" v-show="!mshow">
                      <h1
                        class="pt-3"
                        style="text-align: center; font-size: 40px; color: #15c115; font-family: Impact, Charcoal,sans-serif; font-style:italic; text-decoration:underline;"
                      >
                        SEARCH BY PRICE
                      </h1>
                      <transition name="fade">
                        <div id="priceSlider" v-show="!mshow"></div>
                      </transition>
                    </div>
                  </transition>
                  <div class="SearchView">
                    <div class="searchTool">
                      <a class="priceSearch" href="#">
                        <span
                          class="search_price"
                          :class="{ on: pon }"
                          id="priceSearch"
                          @click="getHousesPrice()"
                          ><img
                            src="img/happyhouse/priceSearch.jpg"
                            style="width:50px; height:50px; border-radius:7px; box-shadow: 0 0 3px;"
                            title="가격으로 검색"
                        /></span>
                      </a>
                    </div>
                  </div>
                  <!-- Icon Divider-->
                  <div id="menu_wrap" class="bg_white" :class="{ on: mon }">
                    <h2 class="mt-2" style="text-align:center;">
                      <i
                        class="ni ni-bold-left float-right mt-1"
                        @click="aptSearch()"
                      ></i>
                      아파트 실거래가 검색
                    </h2>
                    <hr />
                    <ul id="placesList"></ul>
                    <div id="pagination"></div>
                  </div>
                  <div id="jjim_wrap" class="bg_white" :class="{ on: jjimon }">
                    <h2 class="mt-2" style="text-align:center;">
                      <i
                        class="ni ni-bold-left float-right mt-1"
                        @click="jjimSearch()"
                      ></i>
                      관심지역 정보
                    </h2>
                    <hr />
                    <ul id="jjim_placesList"></ul>
                    <div id="jjim_pagination"></div>
                  </div>
                  <div id="store_wrap" class="bg_white" :class="{ on: son }">
                    <h2 class="mt-2" style="text-align:center;">
                      <i
                        class="ni ni-bold-left float-right mt-1"
                        @click="commSearch()"
                      ></i>
                      동네 상권 정보
                    </h2>
                    <hr />
                    <ul id="store_placesList"></ul>
                    <div id="store_pagination"></div>
                  </div>
                  <div id="apt_wrap" class="bg_white" :class="{ on: aon }">
                    <h2 class="mt-2" style="text-align:center;">
                      <i
                        class="ni ni-bold-right float-left mt-1"
                        @click="aon = !aon"
                      ></i
                      >아파트 실거래가 내역 (최근 6개월)
                    </h2>
                    <hr />
                    <ul
                      id="apt_placesList"
                      style="padding-left:4px; list-style:none;"
                    >
                      <li
                        v-for="(houseDeal, index) in houseDeals"
                        :key="index"
                        class="aptdeal"
                      >
                        <table class="table table-dark mb-2">
                          <thead></thead>
                          <tbody>
                            <tr>
                              <th style="width:25%">아파트이름</th>
                              <td colspan="2">
                                {{ houseDeal.aptName }}
                              </td>
                            </tr>
                            <tr>
                              <th>전용면적</th>
                              <td colspan="2">
                                {{ houseDeal.area }}
                              </td>
                            </tr>
                            <tr>
                              <th>층</th>
                              <td colspan="2">
                                {{ houseDeal.floor }}
                              </td>
                            </tr>
                            <tr>
                              <th>거래날짜</th>
                              <td colspan="2">
                                {{ houseDeal.dealYMD }}
                              </td>
                            </tr>
                            <tr>
                              <th>거래금액</th>
                              <td colspan="2">
                                {{ houseDeal.recentPrice }}
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </li>
                    </ul>
                    <div id="apt_pagination"></div>
                  </div>
                  <div id="env_wrap" class="bg_white" :class="{ on: eon }">
                    <h2 class="mt-2" style="text-align:center;">
                      <i
                        class="ni ni-bold-left float-right mt-1"
                        @click="envSearch()"
                      ></i>
                      동네 환경 점검 정보
                    </h2>
                    <hr />
                    <ul
                      id="env_placesList"
                      style="padding-left:4px; list-style:none;"
                    ></ul>
                    <div id="env_pagination"></div>
                  </div>
                </div>
              </div>
              <nav></nav>
            </div>
          </div>
        </div>
      </section>
    </div>
  </section>
</template>

<script>
import MapSearchBar from "./MapSearchBar.vue";
import { mapActions, mapGetters, mapMutations, mapState } from "vuex";
import * as chartConfigs from "@/components/Charts/config";
import LineChart from "@/components/Charts/LineChart";
import BarChart from "@/components/Charts/BarChart";

// Components
import BaseProgress from "@/components/BaseProgress";
import StatsCard from "@/components/Cards/StatsCard";

// Tables
import SocialTrafficTable from "@/views/Dashboard/SocialTrafficTable";
import PageVisitsTable from "@/views/Dashboard/PageVisitsTable";
import noUiSlider from "nouislider";

const houseStore = "houseStore";
const userStore = "userStore";
export default {
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      map: null,
      marker: null,
      markers: [],
      infowindow: null,
      infowindows: [],
      mshow: true,
      mon: false,
      eon: false,
      son: false,
      jjimon: false,
      aon: false,
      dcon: false,
      pon: false,
      overlayOn: false,
      container: null,
      mapWrapper: null,
      mapContainer: null,
      mapOption: null,
      rvContainer: null,
      mapCenter: null,
      ps: null,
      customOverlay: null,
      geocoder: null,
      clickedOverlay: null,
      bounds: null,
      rv: null,
      rvClient: null,
      mapTypeControl: null,
      zoomControl: null,
      jjimShow: false,
      mapDistance: [],
      modal: true,
      slider: null,
      priceFlag: 0,
      trainDetail: [],
      busDetail: [],
      clinicDetail: [],
      starbucksDetail: [],
      bikeDetail: [],
      trainInfo: [
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        }
      ],
      clinicInfo: [
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        }
      ],
      busInfo: [
        {
          name: "",
          distance: 0
        },
        {
          name: "",
          distance: 0
        },
        {
          name: "",
          distance: 0
        }
      ],
      starbucksInfo: [
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        }
      ],
      bikeInfo: [
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        },
        {
          name: "",
          distance: 0,
          address: ""
        }
      ],
      selectedHouse: {
        aptCode: "",
        sidoName: "",
        gugunName: "",
        aptName: "",
        dongCode: "",
        dongName: "",
        roadName: "",
        buildName: "",
        buildYear: "",
        jibun: "",
        year: "",
        month: "",
        day: "",
        recentPrice: "",
        area: "",
        floor: "",
        dealYMD: "",
        address: ""
      },
      // slider: {
      //   start: [100000, 150000],
      //   step: 6,
      //   connect: true,
      //   tooltips: [true, true],
      //   range: {
      //     min: 0,
      //     max: 300000
      //   },
      //   pips: {
      //     mode: "steps",
      //     stepped: true,
      //     density: 4
      //   }
      // },
      // ttSlider: document.getElementById("priceSlider"),
      bigLineChart: {
        allData: [[0, 20, 10, 30, 15, 40]],
        activeIndex: 0,
        chartData: {
          datasets: [
            {
              label: "Performance",
              data: [0, 20, 10, 30, 15, 40]
            }
          ],
          labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"]
        },
        extraOptions: chartConfigs.blueChartOptions
      },
      redBarChart: {
        chartData: {
          labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"],
          datasets: [
            {
              label: "Sales",
              data: [25, 20, 30, 22, 17, 29]
            }
          ]
        },
        extraOptions: chartConfigs.blueChartOptions
      },
      myRadarChart: {
        labels: ["지하철", "버스", "따릉이", "진료소", "스타벅스"],
        datasets: [
          {
            label: "아파트 편의그래프",
            data: [0, 0, 0, 0, 0],
            fill: true,
            backgroundColor: "rgba(255, 99, 132, 0.2)",
            borderColor: "rgb(255, 99, 132)",
            pointBackgroundColor: "rgb(54, 162, 235)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgb(54, 162, 235)"
          }
        ]
      }
    };
  },

  components: {
    MapSearchBar,
    LineChart,
    BarChart,
    BaseProgress,
    StatsCard,
    PageVisitsTable,
    SocialTrafficTable
  },
  computed: {
    ...mapState(houseStore, [
      "sidoC",
      "gugunC",
      "dongC",
      "sidoName",
      "gugunName",
      "dongName",
      "houses",
      "sidos",
      "envs",
      "comms",
      "houseDeals",
      "jjims",
      "housePrices",
      "numList",
      "avgList",
      "trains",
      "clinics",
      "buses",
      "starbuckses",
      "bikes"
    ]),
    ...mapGetters(userStore, ["checkUserInfo"])
  },
  created() {
    this.mapDistance = [
      0.02,
      0.03,
      0.05,
      0.1,
      0.25,
      0.5,
      1,
      2,
      4,
      8,
      16,
      32,
      64,
      128
    ];
  },
  mounted() {
    this.initBigChart(0);
    this.createSlider();
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=5efc8ee1f89ac5be24b83653bd092d76&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    }
  },
  watch: {
    "$store.state.houseStore.envs": function() {
      this.displayEnvMarkers(this.envs);
    },
    "$store.state.houseStore.houses": function() {
      this.displayMarkers(this.houses);
    },
    "$store.state.houseStore.comms": function() {
      this.displayStoreMarkers(this.comms);
    },
    "$store.state.houseStore.jjims": function() {
      this.makeList(this.jjims);
    },
    "$store.state.userStore.userInfo": function() {
      this.getJjim();
    },
    "$store.state.houseStore.housePrices": function() {
      this.displayPricemarker(this.housePrices);
    },
    "$store.state.houseStore.avgList": function() {
      this.updateChart(this.avgList);
    },
    "$store.state.houseStore.numList": function() {
      this.updateBarChart(this.numList);
      setTimeout(() => {
        this.setRadarChart();
      }, 300);
    },
    "$store.state.houseStore.trains": function() {
      this.updateTrainInfo(this.trains);
    },
    "$store.state.houseStore.clinics": function() {
      this.updateClinicInfo(this.clinics);
    },
    "$store.state.houseStore.buses": function() {
      this.updateBusInfo(this.buses);
    },
    "$store.state.houseStore.starbuckses": function() {
      this.updateStarbucksInfo(this.starbuckses);
    },
    "$store.state.houseStore.bikes": function() {
      this.updateBikeInfo(this.bikes);
    }
  },
  methods: {
    ...mapActions(houseStore, [
      "aptList",
      "envList",
      "commList",
      "houseDealList",
      "jjimList",
      "jjimInsert",
      "jjimDelete",
      "priceList",
      "trainList",
      "clinicList",
      "busList",
      "starbucksList",
      "bikeList"
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_HOUSES_LIST",
      "SET_SIGUDONG_NAME",
      "SET_GUGUNC",
      "CLEAR_PRICE_LIST"
    ]),
    qqq() {
      var slider = document.getElementById("priceSlider");
      alert(slider.noUiSlider.get()[0]);
    },
    setRadarChart() {
      const myChart = document.getElementById("myChart");
      console.log(myChart);
      setTimeout(() => {
        const chart = new Chart(myChart, {
          label: "거주 편의 그래프",
          type: "radar",
          data: {
            labels: ["지하철", "버스", "따릉이", "진료소", "스타벅스"],
            datasets: [
              {
                label: "편의 지수",
                data: [
                  this.trains.length,
                  this.buses.length,
                  this.bikes.length,
                  this.clinics.length,
                  this.starbuckses.length
                ],
                fill: true,
                backgroundColor: "rgba(255, 99, 132, 0.2)",
                borderColor: "rgb(255, 99, 132)",
                pointBackgroundColor: "rgb(54, 162, 235)",
                pointBorderColor: "#fff",
                pointHoverBackgroundColor: "#fff",
                pointHoverBorderColor: "rgb(54, 162, 235)"
              }
            ],
            hoverOffset: 4
          }
        });
      }, 700);
    },
    initBigChart(index) {
      let chartData = {
        datasets: [
          {
            label: "Performance",
            data: this.bigLineChart.allData[index]
          }
        ],
        labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"]
      };
      this.bigLineChart.chartData = chartData;
      this.bigLineChart.activeIndex = index;
    },
    openModal() {
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    createSlider() {
      // noUiSlider.create(this.ttSlider, {
      //   start: this.slider.start,
      //   step: this.slider.step,
      //   connect: this.slider.connect,
      //   tooltips: this.slider.tooltips,
      //   range: this.slider.range,
      //   pips: this.slider.pips
      // });
      // console.log(noUiSlider);
      var tooltipSlider = document.getElementById("priceSlider");
      noUiSlider.create(tooltipSlider, {
        start: [100000, 600000],
        step: 6,
        connect: true,
        tooltips: [true, true],
        range: {
          min: 0,
          max: 800000
        },
        pips: {
          mode: "range",
          density: 4
        }
      });

      tooltipSlider.noUiSlider.on("update", () => {
        var tooltipSlider = document.getElementById("priceSlider");
        const check = tooltipSlider.noUiSlider.get();
        if (
          check[0] == "100002.00" &&
          check[1] == "600000.00" &&
          this.priceFlag < 2
        ) {
          this.priceFlag++;
          return;
        } else {
          this.clearInfoWindows(this.infowindows);
          setTimeout(() => {
            this.searchLive(check);
          }, 1000);
        }
      });
    },
    getHousesPrice() {
      this.allClear();
      if (!this.mshow) {
        this.deleteWindow(this.housePrices);
        this.mshow = !this.mshow;
        this.searchOn(false, this.map);
        this.CLEAR_PRICE_LIST();
        this.infowindow = [];
        return;
      } else {
        this.mshow = !this.mshow;
        this.searchOn(true, this.map);

        // this.displayPriceMarkers();
      }
    },
    searchLive(params) {
      var bounds = this.map.getBounds();
      var lng1 = bounds.ha;
      var lat1 = bounds.qa;
      var lng2 = bounds.oa;
      var lat2 = bounds.pa;
      var price1 = params[0];
      var price2 = params[1];
      const param = {
        lat1: lat1,
        lng1: lng1,
        lat2: lat2,
        lng2: lng2,
        stPrice: price1,
        endPrice: price2
      };
      this.priceList(param);
    },
    dragHandler: function() {
      var bounds = this.map.getBounds();
      var lng1 = bounds.ha;
      var lat1 = bounds.qa;
      var lng2 = bounds.oa;
      var lat2 = bounds.pa;
      var slider = document.getElementById("priceSlider");
      var price1 = slider.noUiSlider.get()[0];
      var price2 = slider.noUiSlider.get()[1];
      const param = {
        lat1: lat1,
        lng1: lng1,
        lat2: lat2,
        lng2: lng2,
        stPrice: price1,
        endPrice: price2
      };
      this.priceList(param);
      this.clearInfoWindows(this.infowindows);
    },
    searchOn(param, map) {
      if (param) {
        kakao.maps.event.addListener(this.map, "dragend", this.dragHandler);
        kakao.maps.event.addListener(
          this.map,
          "zoom_changed",
          this.dragHandler
        );
      } else {
        kakao.maps.event.removeListener(this.map, "dragend", this.dragHandler);
        kakao.maps.event.removeListener(
          this.map,
          "zoom_changed",
          this.dragHandler
        );
      }
    },
    addWindow(placePosition, place) {
      var iwContent = document.createElement("div");
      iwContent.id = "priceDiv";
      iwContent.style =
        "padding:5px; border-radius: 30px; font-weight:bold; background-color : lightpink; z-index:4;";
      iwContent.innerHTML = `${place.dealAmount}
      <input id="aptName" type="hidden" value="${place.aptName}"></input>
      <input id="dongCode" type="hidden" value="${place.dongCode}"></input>
      <input id="dongName" type="hidden" value="${place.dongName}"></input>
      <input id="sidoName" type="hidden" value="${place.sidoName}"></input>
      <input id="gugunName" type="hidden" value="${place.gugunName}"></input>
      <input id="buildYear" type="hidden" value="${place.buildYear}"></input>
      <input id="jibun" type="hidden" value="${place.jibun}"></input>
      <input id="lat" type="hidden" value="${place.lat}"></input>
      <input id="lng" type="hidden" value="${place.lng}"></input>
      <input id="recentPrice" type="hidden" value="${place.recentPrice}"></input>
      <input id="dealAmount" type="hidden" value="${place.dealAmount}"></input>
      <input id="dealYear" type="hidden" value="${place.dealYear}"></input>
      <input id="dealMonth" type="hidden" value="${place.dealMonth}"></input>
      <input id="dealDay" type="hidden" value="${place.dealDay}"></input>
      <input id="area" type="hidden" value="${place.area}"></input>
      <input id="floor" type="hidden" value="${place.floor}"></input>
      <input id="address" type="hidden" value="${place.address}"></input>
      </div>`; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      var iwPosition = placePosition; //인포윈도우 표시 위치입니다
      // 인포윈도우를 생성하고 지도에 표시합니다
      var infowindow = new kakao.maps.CustomOverlay({
        // map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent
      });
      iwContent.onclick = () => {
        console.log(this.gugunC);
        const param = {
          aptCode: "",
          sidoName: place.sidoName,
          gugunName: place.gugunName,
          aptName: place.aptName,
          dongCode: place.dongCode,
          dongName: place.dongName,
          roadName: "",
          buildName: "",
          buildYear: place.buildYear,
          jibun: place.jibun,
          year: place.dealYear,
          month: place.dealMonth,
          day: place.dealDay,
          recentPrice: place.recentPrice,
          area: place.area,
          floor: place.floor,
          dealYMD:
            place.dealYear +
            "년 " +
            place.dealMonth +
            "월 " +
            place.dealDay +
            "일",
          address: place.address
        };
        let code = place.dongCode.substring(0, 5);
        this.selectedHouse = param;
        this.getTrainList(placePosition);
        this.getClinicList(placePosition);
        this.getBusList(placePosition);
        this.getStarbucksList(placePosition);
        this.getBikeList(placePosition);
        this.getHouseDeals(place.aptName, code);
        setTimeout(() => {
          this.$refs.infoModal.show();
          // this.setRadarChart();
        }, 1000);
      };
      setTimeout(() => {
        infowindow.setMap(this.map);
        this.infowindows.push(infowindow);
      }, 10);
      return infowindow;
    },
    deleteWindow() {
      this.infowindows.forEach(window => {
        window.setMap(null);
      });
    },
    async displayPricemarker(places) {
      this.infowindows = [];
      for (let place of places) {
        // this.geocoder.addressSearch("", () => {
        var placePosition = new kakao.maps.LatLng(place.lat, place.lng);
        var infowindow = await this.addWindow(placePosition, place);
        // });
      }
      // setTimeout(() => {
      //   var els = document.querySelectorAll("#priceDiv");

      //   for (let el of els) {
      //     el.addEventListener("click", () => {
      //       el.querySelector("#aptName").value;
      // const param = {
      //   aptCode: "",
      //   sidoName: el.querySelector("#sidoName").value,
      //   gugunName: el.querySelector("#gugunName").value,
      //   aptName: el.querySelector("#aptName").value,
      //   dongCode: el.querySelector("#dongCode").value,
      //   dongName: el.querySelector("#dongName").value,
      //   roadName: "",
      //   buildName: "",
      //   buildYear: el.querySelector("#buildYear").value,
      //   jibun: el.querySelector("#jibun").value,
      //   year: el.querySelector("#dealYear").value,
      //   month: el.querySelector("#dealMonth").value,
      //   day: el.querySelector("#dealDay").value,
      //   recentPrice: el.querySelector("#recentPrice").value,
      //   area: el.querySelector("#area").value,
      //   floor: el.querySelector("floor").value,
      //   dealYMD:
      //     el.querySelector("#dealYear").value +
      //     "년 " +
      //     el.querySelector("#dealMonth").value +
      //     "월 " +
      //     el.querySelector("#dealDay").value +
      //     "일",
      //   address: el.querySelector("address").value
      // };
      //       this.selectedHouse = param;

      //     });
      //   }
      // }, 1000);
      // setTimeout(() => {
      //   this.setInfoWindows(this.infowindows);
      // }, 1000);
    },
    clearInfoWindows(infowindows) {
      for (let infowindow of infowindows) {
        infowindow.setMap(null);
      }
    },
    setInfoWindows(infowindows) {
      setTimeout(() => {
        for (let infowindow of infowindows) {
          console.log(infowindow);
          infowindow.setMap(this.map);
        }
      }, 1000);
    },
    allClear() {
      if (this.markers) this.removeMarker();
      if (this.customOverlay) this.customOverlay.setMap(null);
      this.mon = false;
      this.eon = false;
      this.son = false;
      this.jjimon = false;
      this.aon = false;
    },
    async getJjim() {
      const checkUserInfo = this.checkUserInfo;
      if (checkUserInfo == null) {
        if (this.jjimon) this.jjimon = !this.jjimon;
        this.jjimShow = false;
      } else {
        await this.jjimList(userId);
        this.jjimShow = true;
        this.makeList(this.jjims);
      }
    },
    jjimSearch() {
      const checkUserInfo = this.checkUserInfo;
      if (checkUserInfo == null) {
        alert("로그인 후 이용가능합니다.");
        return;
      } else {
        if (this.jjimon) {
          this.jjimon = !this.jjimon;
          return;
        }
        if (this.markers) this.removeMarker();
        if (this.customOverlay) this.customOverlay.setMap(null);
        this.mon = false;
        this.eon = false;
        this.son = false;
        this.aon = false;
        let userId = checkUserInfo.userId.trim();
        this.jjimList(userId);
        this.jjimon = !this.jjimon;
      }
    },
    async jjimRegister() {
      const aaa = await this.valueCheck();
      if (!aaa) {
        return;
      } else {
        let userId = this.checkUserInfo.userId.trim();
        let dongCode = this.dongC;
        const param = {
          userId: userId,
          dongCode: dongCode
        };
        console.log(param.userId, param.dongCode);
        await setTimeout(() => {
          this.jjimInsert(param);
        }, 100);
        // this.makeList(this.jjims);
        alert("등록 성공!");
      }
    },
    async deleteJjim(dongCode) {
      let userId = this.checkUserInfo.userId.trim();
      const param = {
        userId: userId,
        dongCode: dongCode
      };
      this.jjimDelete(param);
      alert("삭제 완료!!");
    },
    initMap() {
      var root = "";

      // 마커를 담을 배열입니다
      this.markers = [];

      //var mapContainer = document.getElementById("map"); // 지도를 표시할 div
      //var mapOption = {
      //       center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      //       level: 3, // 지도의 확대 레벨
      //   };

      this.overlayOn = false; // 지도 위에 로드뷰 오버레이가 추가된 상태를 가지고 있을 변수
      this.container = document.getElementById("container"); // 지도와 로드뷰를 감싸고 있는 div 입니다
      this.mapWrapper = document.getElementById("mapWrapper"); // 지도를 감싸고 있는 div 입니다
      this.mapContainer = document.getElementById("map"); // 지도를 표시할 div 입니다
      this.rvContainer = document.getElementById("roadview"); //로드뷰를 표시할 div 입니다
      (this.mapCenter = new kakao.maps.LatLng(37.566826, 126.9786567)), // 지도의 중심좌표
        (this.mapOption = {
          center: this.mapCenter, // 지도의 중심좌표
          level: 3 // 지도의 확대 레벨
        });
      // 지도를 생성합니다
      this.map = new kakao.maps.Map(this.mapContainer, this.mapOption);

      // 장소 검색 객체를 생성합니다
      this.ps = new kakao.maps.services.Places();

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      // 커스텀 오버레이를 생성합니다
      //주소-좌표 변환 객체를 생성합니다
      this.geocoder = new kakao.maps.services.Geocoder();
      this.clickedOverlay = null;
      this.bounds = new kakao.maps.LatLngBounds();

      this.rv = new kakao.maps.Roadview(this.rvContainer);

      //좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다
      this.rvClient = new kakao.maps.RoadviewClient();

      this.mapTypeControl = new kakao.maps.MapTypeControl();

      //지도에 컨트롤을 추가해야 지도위에 표시됩니다
      //kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      this.map.addControl(
        this.mapTypeControl,
        kakao.maps.ControlPosition.TOPRIGHT
      );

      //지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      this.zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(this.zoomControl, kakao.maps.ControlPosition.RIGHT);

      // 로드뷰!!!

      kakao.maps.event.addListener(this.rv, "position_changed", () => {
        // 현재 로드뷰의 위치 좌표를 얻어옵니다
        var rvPosition = this.rv.getPosition();

        // 지도의 중심을 현재 로드뷰의 위치로 설정합니다
        this.map.setCenter(rvPosition);

        // 지도 위에 로드뷰 도로 오버레이가 추가된 상태이면
        if (this.overlayOn) {
          // 마커의 위치를 현재 로드뷰의 위치로 설정합니다
          this.marker.setPosition(rvPosition);
        }
      });

      // 마커 이미지를 생성합니다
      var markImage = new kakao.maps.MarkerImage(
        "https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png",
        new kakao.maps.Size(26, 46),
        {
          // 스프라이트 이미지를 사용합니다.
          // 스프라이트 이미지 전체의 크기를 지정하고
          spriteSize: new kakao.maps.Size(1666, 168),
          // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
          // background-position으로 지정하는 값이며 부호는 반대입니다.
          spriteOrigin: new kakao.maps.Point(705, 114),
          offset: new kakao.maps.Point(13, 46)
        }
      );

      // 드래그가 가능한 마커를 생성합니다
      this.marker = new kakao.maps.Marker({
        image: markImage,
        position: this.mapCenter,
        draggable: true
      });

      // 마커에 dragend 이벤트를 등록합니다
      kakao.maps.event.addListener(this.marker, "dragend", () => {
        // 현재 마커가 놓인 자리의 좌표입니다
        var position = this.marker.getPosition();

        // 마커가 놓인 위치를 기준으로 로드뷰를 설정합니다
        this.toggleRoadview(position);
      });

      // 지도에 클릭 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "click", mouseEvent => {
        // 지도 위에 로드뷰 도로 오버레이가 추가된 상태가 아니면 클릭이벤트를 무시합니다
        if (!this.overlayOn) {
          return;
        }

        // 클릭한 위치의 좌표입니다
        var position = mouseEvent.latLng;

        // 마커를 클릭한 위치로 옮깁니다
        this.marker.setPosition(position);

        // 클락한 위치를 기준으로 로드뷰를 설정합니다
        this.toggleRoadview(position);
      });
    },
    updateTrainInfo(trains) {
      this.trainInfo = [];
      this.trainDetail = [];
      for (let train of this.trains) {
        let distance = Math.round(parseFloat(train.distance) * 1000);
        let dist = distance + "m";
        const param = {
          "역 이름": train.name,
          거리: dist,
          "역사 주소": train.address
        };
        this.trainDetail.push(param);
      }
      if (this.trains.length > 0) {
        let distance = Math.round(parseFloat(this.trains[0].distance) * 1000);
        console.log(distance);
        this.trainInfo.push({
          name: this.trains[0].name,
          distance: distance,
          address: this.trains[0].address
        });
      }
      if (this.trains.length > 1) {
        let distance = Math.round(parseFloat(this.trains[1].distance) * 1000);
        this.trainInfo.push({
          name: this.trains[1].name,
          distance: distance,
          address: this.trains[1].address
        });
      }
      if (this.trains.length > 2) {
        let distance = Math.round(parseFloat(this.trains[2].distance) * 1000);
        this.trainInfo.push({
          name: this.trains[2].name,
          distance: distance,
          address: this.trains[2].address
        });
      }
    },
    getTrainList(param) {
      const params = {
        lat: param.Ma,
        lng: param.La
      };
      this.trainList(params);
    },
    updateClinicInfo(clinics) {
      this.clinicInfo = [];
      this.clinicDetail = [];
      for (let clinic of this.clinics) {
        let distance = Math.round(parseFloat(clinic.distance) * 1000);
        let dist = distance + "m";
        const param = {
          "진료소 이름": clinic.name,
          거리: dist,
          "진료소 주소": clinic.address
        };
        this.clinicDetail.push(param);
      }
      if (this.clinics.length > 0) {
        let distance = Math.round(parseFloat(this.clinics[0].distance) * 1000);
        this.clinicInfo.push({
          name: this.clinics[0].name,
          distance: distance,
          address: this.clinics[0].address
        });
      }
      if (this.clinics.length > 1) {
        let distance = Math.round(parseFloat(this.clinics[1].distance) * 1000);
        this.clinicInfo.push({
          name: this.clinics[1].name,
          distance: distance,
          address: this.clinics[1].address
        });
      }
      if (this.clinics.length > 2) {
        let distance = Math.round(parseFloat(this.clinics[2].distance) * 1000);
        this.clinicInfo.push({
          name: this.clinics[2].name,
          distance: distance,
          address: this.clinics[2].address
        });
      }
    },
    getClinicList(param) {
      const params = {
        lat: param.Ma,
        lng: param.La
      };
      this.clinicList(params);
    },
    updateBusInfo(bus) {
      this.busInfo = [];
      this.busDetail = [];
      for (let bus of this.buses) {
        let distance = Math.round(parseFloat(bus.distance) * 1000);
        let dist = distance + "m";
        const param = {
          "버스정류장 이름": bus.name,
          거리: dist
        };
        this.busDetail.push(param);
      }
      if (this.buses.length > 0) {
        let distance = Math.round(parseFloat(this.buses[0].distance) * 1000);
        this.busInfo.push({
          name: this.buses[0].name,
          distance: distance
        });
      }
      if (this.buses.length > 1) {
        let distance = Math.round(parseFloat(this.buses[1].distance) * 1000);
        this.busInfo.push({
          name: this.buses[1].name,
          distance: distance
        });
      }
      if (this.buses.length > 2) {
        let distance = Math.round(parseFloat(this.buses[2].distance) * 1000);
        this.busInfo.push({
          name: this.buses[2].name,
          distance: distance
        });
      }
    },
    getBusList(param) {
      const params = {
        lat: param.Ma,
        lng: param.La
      };
      this.busList(params);
    },
    updateStarbucksInfo(starbuckses) {
      this.starbucksInfo = [];
      this.starbucksDetail = [];
      for (let starbucks of this.starbuckses) {
        let distance = Math.round(parseFloat(starbucks.distance) * 1000);
        let dist = distance + "m";
        const param = {
          "지점 이름": starbucks.name,
          거리: dist,
          "지점 주소": starbucks.address
        };
        this.starbucksDetail.push(param);
      }
      if (this.starbuckses.length > 0) {
        let distance = Math.round(
          parseFloat(this.starbuckses[0].distance) * 1000
        );
        this.starbucksInfo.push({
          name: this.starbuckses[0].name,
          distance: distance,
          address: this.starbuckses[0].address
        });
      }
      if (this.starbuckses.length > 1) {
        let distance = Math.round(
          parseFloat(this.starbuckses[1].distance) * 1000
        );
        this.starbucksInfo.push({
          name: this.starbuckses[1].name,
          distance: distance,
          address: this.starbuckses[1].address
        });
      }
      if (this.starbuckses.length > 2) {
        let distance = Math.round(
          parseFloat(this.starbuckses[2].distance) * 1000
        );
        this.starbucksInfo.push({
          name: this.starbuckses[2].name,
          distance: distance,
          address: this.starbuckses[2].address
        });
      }
    },
    getStarbucksList(param) {
      const params = {
        lat: param.Ma,
        lng: param.La
      };
      this.starbucksList(params);
    },
    updateBikeInfo(bikes) {
      this.bikeInfo = [];
      this.bikeDetail = [];
      for (let bike of this.bikes) {
        let distance = Math.round(parseFloat(bike.distance) * 1000);
        let dist = distance + "m";
        const param = {
          "따릉이 대여 장소": bike.name,
          거리: dist,
          "따릉이 대여 주소": bike.address
        };
        this.bikeDetail.push(param);
      }
      if (this.bikes.length > 0) {
        let distance = Math.round(parseFloat(this.bikes[0].distance) * 1000);
        this.bikeInfo.push({
          name: this.bikes[0].name,
          distance: distance,
          address: this.bikes[0].address
        });
      }
      if (this.bikes.length > 1) {
        let distance = Math.round(parseFloat(this.bikes[1].distance) * 1000);
        this.bikeInfo.push({
          name: this.bikes[1].name,
          distance: distance,
          address: this.bikes[1].address
        });
      }
      if (this.bikes.length > 2) {
        let distance = Math.round(parseFloat(this.bikes[2].distance) * 1000);
        this.bikeInfo.push({
          name: this.bikes[2].name,
          distance: distance,
          address: this.bikes[2].address
        });
      }
    },
    getBikeList(param) {
      const params = {
        lat: param.Ma,
        lng: param.La
      };
      this.bikeList(params);
    },
    async getHouseDeals(address, code) {
      if (this.aon) {
        this.aon = !this.aon;
      }
      let tmp = "";
      if (this.gugunC == "") {
        tmp = code;
      } else {
        tmp = this.gugunC;
      }
      const param = {
        address: address,
        gugun: tmp
      };
      await this.houseDealList(param);
      setTimeout(() => {
        this.aon = !this.aon;
        console.log(this.avgList);
        console.log(this.numList);
      }, 400);
    },

    updateChart(ary) {
      this.bigLineChart.allData[0] = ary;
      let chartData = {
        datasets: [
          {
            label: "평균 거래가",
            data: this.bigLineChart.allData[0]
          }
        ],
        labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"]
      };
      this.bigLineChart.chartData = chartData;
      this.bigLineChart.activeIndex = 0;
    },
    updateBarChart(ary) {
      let chartData = {
        labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"],
        datasets: [
          {
            label: "월 거래량",
            data: ary
          }
        ]
      };
      this.redBarChart.chartData = chartData;
    },
    distanceControl() {
      this.dcon = !this.dcon;
    },
    valueCheck() {
      let err = true;
      let msg = "";
      !this.sidoC && ((msg = "시/도 선택해주세요!"), (err = false));
      err && !this.gugunC && ((msg = "구/군 선택해주세요!"), (err = false));
      err && !this.dongC && ((msg = "읍/면/동 선택해주세요!"), (err = false));

      if (!err) {
        alert(msg);
      }
      return err;
    },
    jjimAptSearch(code) {
      if (this.mon) {
        this.mon = !this.mon;
        if (this.markers) this.removeMarker();

        if (this.customOverlay) this.customOverlay.setMap(null);
      }
      this.eon = false;
      this.son = false;
      this.jjimon = false;
      this.aon = false;
      this.mon = true;
      this.aptList(code);
    },
    aptSearch() {
      if (this.mon) {
        this.mon = !this.mon;
        if (this.markers) this.removeMarker();

        if (this.customOverlay) this.customOverlay.setMap(null);
        return;
      }
      this.eon = false;
      this.son = false;
      this.jjimon = false;
      this.aon = false;
      this.modal = true;
      // this.CLEAR_HOUSES_LIST();
      let vmsg = this.valueCheck();
      if (!vmsg) return;
      this.mon = !this.mon;
      this.getApt();
    },

    getApt() {
      this.aptList(this.gugunC);
      // console.log(this.houses);
      return this.houses;
    },
    // 환경
    envSearch() {
      if (this.eon) {
        this.eon = !this.eon;
        if (this.markers) this.removeMarker();

        if (this.customOverlay) this.customOverlay.setMap(null);
        return;
      }
      let aaa = null;
      this.mon = false;
      this.son = false;
      this.jjimon = false;
      this.aon = false;
      // this.CLEAR_HOUSES_LIST();
      let vmsg = this.valueCheck();
      if (!vmsg) return;
      this.eon = !this.eon;
      aaa = this.getEnv();
    },

    getEnv() {
      this.envList(this.sidoName + " " + this.gugunName);
      // console.log(this.houses);
      return this.envs;
    },

    // 상업
    commSearch() {
      if (this.son) {
        this.son = !this.son;
        if (this.markers) this.removeMarker();

        if (this.customOverlay) this.customOverlay.setMap(null);
        return;
      }
      this.mon = false;
      this.eon = false;
      this.jjimon = false;
      this.aon = false;
      // this.CLEAR_HOUSES_LIST();
      let vmsg = this.valueCheck();
      if (!vmsg) return;
      this.son = !this.son;
      this.getComm();
    },

    getComm() {
      this.commList(this.sidoName + this.gugunName + this.dongName);
      // console.log(this.houses);
      return this.comms;
    },
    //마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, idx) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage
        });
      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다
      return marker;
    },

    //검색 결과 목록과 마커를 표출하는 함수입니다
    async displayMarkers(places) {
      var listEl = document.getElementById("placesList"),
        menuEl = document.getElementById("menu_wrap"),
        fragment = document.createDocumentFragment();
      this.bounds = new kakao.maps.LatLngBounds();
      var listStr = "";
      this.removeAllChildNods(listEl);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();
      // places.forEach(function(place, index) {
      console.log(places);
      var index = 0;
      for (let place of places) {
        this.geocoder.addressSearch(place.address, (result, status) => {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            var placePosition = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = this.addMarker(placePosition, index);
            var itemEl = this.getListItem(index, place); // 검색 결과 항목 Element를 생성합니다
            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다

            this.bounds.extend(placePosition);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다

            kakao.maps.event.addListener(marker, "click", () => {
              this.displayInfowindow(marker, place.aptName, place);
              if (this.clickedOverlay) {
                this.clickedOverlay.setMap(null);
              }
              this.customOverlay.setMap(this.map);
              this.clickedOverlay = this.customOverlay;
            });

            // kakao.maps.event.addListener(map, "click", mouseEvent => {
            //   this.customOverlay.setMap(null);
            // });
            //                 kakao.maps.event.addListener(marker, "click", function () {
            //                    console.log(customOverlay);
            //
            //                    customOverlay.setMap(null);
            //                 });
            //
            //                 kakao.maps.event.addListener(marker, "click", function () {
            //                    displayInfowindow(marker, title, place);
            //                    console.log(title + " " + code);
            //                 });
            //
            //                 kakao.maps.event.addListener(map, "click", function () {
            //                    console.log(customOverlay);
            //                    customOverlay.setMap(null);
            //                 });
            //
            itemEl.onmouseenter = () => {
              if (this.customOverlay) this.customOverlay.setMap(null);
              this.displayInfowindow(marker, place.aptName, place);
            };

            itemEl.onmouseleave = () => {
              if (this.customOverlay) this.customOverlay.setMap(null);
            };
            itemEl.onclick = () => {
              this.selectedHouse = place;
              this.getTrainList(placePosition);
              this.getClinicList(placePosition);
              this.getBusList(placePosition);
              this.getStarbucksList(placePosition);
              this.getBikeList(placePosition);
              this.getHouseDeals(place.aptName);
              setTimeout(() => {
                this.$refs.infoModal.show();
                // this.setRadarChart();
              }, 1000);
            };
            // (function(marker, title, code, place) {
            //   kakao.maps.event.addListener(marker, "click", function() {
            //     this.displayInfowindow(marker, title, place);
            //     if (this.clickedOverlay) {
            //       this.clickedOverlay.setMap(null);
            //     }
            //     this.customOverlay.setMap(map);
            //     this.clickedOverlay = this.customOverlay;
            //     console.log(title + " " + code);
            //   });
            //   //                 kakao.maps.event.addListener(marker, "click", function () {
            //   //                    console.log(customOverlay);
            //   //
            //   //                    customOverlay.setMap(null);
            //   //                 });
            //   //
            //   //                 kakao.maps.event.addListener(marker, "click", function () {
            //   //                    displayInfowindow(marker, title, place);
            //   //                    console.log(title + " " + code);
            //   //                 });
            //   //
            //   //                 kakao.maps.event.addListener(map, "click", function () {
            //   //                    console.log(customOverlay);
            //   //                    customOverlay.setMap(null);
            //   //                 });
            //   //
            //   itemEl.onmouseenter = function() {
            //     this.displayInfowindow(marker, title, place);
            //   };

            //   itemEl.onmouseleave = function() {
            //     this.customOverlay.setMap(null);
            //   };
            // })(this.marker, place.aptName, place.aptCode, place);

            index++;
            setTimeout(() => {
              fragment.appendChild(itemEl);
            }, 50);
          }
        });
      }
      setTimeout(() => {
        listEl.appendChild(fragment);
      }, 500);
      menuEl.scrollTop = 0;
      // 마커를 생성하고 지도에 표시합니다
      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      //    panTo();
      // setTimeout(() => {
      //   console.log(this.bounds);
      //   this.map.setBounds(this.bounds);
      // }, 3000);
      this.setBounds(this.bounds);
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },

    //검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, place) {
      var el = document.createElement("li");
      var itemStr =
        '<span class="markerbg marker_' +
        (index + 1) +
        '"></span>' +
        '<div class="info">' +
        '<input type="hidden" value="' +
        place.dongCode +
        '">' +
        "    <h5>" +
        place.aptName +
        "</h5>" +
        "<span>" +
        place.sidoName +
        " " +
        place.gugunName +
        " " +
        place.dongName +
        " " +
        place.jibun +
        "</span>" +
        "</div>";
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    //검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
    //인포윈도우에 장소명을 표시합니다
    displayInfowindow(marker, title, place) {
      var content =
        `
   <div class="wrap">
      <div class="info">'
      <div class="overlaybox">
         <div class="boxtitle">` +
        title +
        `<img src="img/happyhouse/close.png" id="closeimg" class="close" title="닫기"></img>
         </div>
         <div class="first"><img src="img/happyhouse/apt.png" style="width:247px; height:136px;" alt=""></div>
         <ul style="padding-left:0px;">
            <li class="up">
               <span class="title">건축년도</span>
               <span class="count">` +
        place.buildYear +
        `</span>
            </li>
            <li>
               <span class="title">주소</span>
               <span class="count">` +
        place.sidoName +
        ` ` +
        place.gugunName +
        ` ` +
        place.dongName +
        ` ` +
        place.jibun +
        `</span>
            </li>
            <li>
               <span class="title">최신거래금액</span>
               <span class="count">` +
        place.recentPrice +
        `</span>
            </li>
         </ul>
      </div>
      </div>
   </div>
   `;
      var position = new kakao.maps.LatLng(
        marker.getPosition().getLat() + 0.00033,
        marker.getPosition().getLng() - 0.00003
      );
      this.customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content,
        xAnchor: 0.4,
        yAnchor: 1.0
      });

      this.customOverlay.setMap(this.map);
      const clsbtn = document.getElementById("closeimg");
      clsbtn.addEventListener("click", event => {
        this.closeOverlay();
      });
    },

    closeOverlay() {
      this.customOverlay.setMap(null);
    },

    panTo() {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new kakao.maps.LatLng(33.45058, 126.574942);

      // 지도 중심을 부드럽게 이동시킵니다
      // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      this.map.panTo(moveLatLon);
    },

    setBounds(result) {
      // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
      // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
      setTimeout(() => {
        this.map.setBounds(result);
      }, 1000);
    },

    //검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },

    relayout() {
      // 지도를 표시하는 div 크기를 변경한 이후 지도가 정상적으로 표출되지 않을 수도 있습니다
      // 크기를 변경한 이후에는 반드시  map.relayout 함수를 호출해야 합니다
      // window의 resize 이벤트에 의한 크기변경은 map.relayout 함수가 자동으로 호출됩니다
      this.map.relayout();
    },

    // 전달받은 좌표(position)에 가까운 로드뷰의 파노라마 ID를 추출하여
    // 로드뷰를 설정하는 함수입니다
    toggleRoadview(position) {
      this.rvClient.getNearestPanoId(position, 50, panoId => {
        // 파노라마 ID가 null 이면 로드뷰를 숨깁니다
        if (panoId === null) {
          this.toggleMapWrapper(true, position);
        } else {
          this.toggleMapWrapper(false, position);

          // panoId로 로드뷰를 설정합니다
          this.rv.setPanoId(panoId, position);
        }
      });
    },

    // 지도를 감싸고 있는 div의 크기를 조정하는 함수입니다
    toggleMapWrapper(active, position) {
      if (active) {
        // 지도를 감싸고 있는 div의 너비가 100%가 되도록 class를 변경합니다
        this.container.className = "";

        // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
        this.map.relayout();

        // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
        this.map.setCenter(position);
      } else {
        // 지도만 보여지고 있는 상태이면 지도의 너비가 50%가 되도록 class를 변경하여
        // 로드뷰가 함께 표시되게 합니다
        if (this.container.className.indexOf("view_roadview") === -1) {
          this.container.className = "view_roadview";

          // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
          this.map.relayout();

          // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
          this.map.setCenter(position);
        }
      }
    },

    // 지도 위의 로드뷰 도로 오버레이를 추가,제거하는 함수입니다
    toggleOverlay(active) {
      if (active) {
        this.overlayOn = true;

        // 지도 위에 로드뷰 도로 오버레이를 추가합니다
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위에 마커를 표시합니다
        this.marker.setMap(this.map);

        // 마커의 위치를 지도 중심으로 설정합니다
        this.marker.setPosition(this.map.getCenter());

        // 로드뷰의 위치를 지도 중심으로 설정합니다
        this.toggleRoadview(this.map.getCenter());
      } else {
        this.overlayOn = false;

        // 지도 위의 로드뷰 도로 오버레이를 제거합니다
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위의 마커를 제거합니다
        this.marker.setMap(null);
      }
    },

    // 지도 위의 로드뷰 버튼을 눌렀을 때 호출되는 함수입니다
    setRoadviewRoad() {
      var control = document.getElementById("roadviewControl");

      // 버튼이 눌린 상태가 아니면
      if (control.className.indexOf("active") === -1) {
        control.className = "active";

        // 로드뷰 도로 오버레이가 보이게 합니다
        this.toggleOverlay(true);
      } else {
        control.className = "";

        // 로드뷰 도로 오버레이를 제거합니다
        this.toggleOverlay(false);
      }
    },

    // 로드뷰에서 X버튼을 눌렀을 때 로드뷰를 지도 뒤로 숨기는 함수입니다
    closeRoadview() {
      var position = this.marker.getPosition();
      this.toggleMapWrapper(true, position);
    },

    // 아파트 검색 메뉴 부분!!!

    //검색결과 목록 하단에 페이지번호를 표시하는 함수입니다
    displayPagination(pagination) {
      var paginationEl = document.getElementById("store_pagination"),
        fragment = document.createDocumentFragment(),
        i;

      // 기존에 추가된 페이지번호를 삭제합니다
      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
      }

      for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.onclick = (function(i) {
            return function() {
              pagination.gotoPage(i);
            };
          })(i);
        }

        fragment.appendChild(el);
      }
      paginationEl.appendChild(fragment);
    },

    // 상권 부분!!!

    //검색 결과 목록과 마커를 표출하는 함수입니다
    displayStoreMarkers(places) {
      var listEl = document.getElementById("store_placesList"),
        menuEl = document.getElementById("store_wrap"),
        fragment = document.createDocumentFragment();
      this.bounds = new kakao.maps.LatLngBounds();
      var listStr = "";
      this.removeAllChildNods(listEl);
      this.removeMarker();

      // 지도에 표시되고 있는 마커를 제거합니다
      var index = 0;
      for (let place of places) {
        this.geocoder.addressSearch(place.sidoName, (result, status) => {
          var placePosition = new kakao.maps.LatLng(place.lat, place.lng);
          var marker = this.addStoreMarker(placePosition, index);
          kakao.maps.event.addListener(marker, "click", () => {
            this.displayStoreInfowindow(marker, place.name, place);
            if (this.clickedOverlay) {
              this.clickedOverlay.setMap(null);
            }
            this.customOverlay.setMap(this.map);
            this.clickedOverlay = this.customOverlay;
          });
          // 마커와 검색결과 항목에 mouseover 했을때
          var itemEl = this.getListStoreItem(index, place); // 검색 결과 항목 Element를 생성합니다
          fragment.appendChild(itemEl);
          // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
          // LatLngBounds 객체에 좌표를 추가합니다

          this.bounds.extend(placePosition);

          // 해당 장소에 인포윈도우에 장소명을 표시합니다
          // mouseout 했을 때는 인포윈도우를 닫습니다
          //                 kakao.maps.event.addListener(marker, "click", function () {
          //                    console.log(customOverlay);
          //
          //                    customOverlay.setMap(null);
          //                 });
          //
          //                 kakao.maps.event.addListener(marker, "click", function () {
          //                    displayInfowindow(marker, title, place);
          //                    console.log(title + " " + code);
          //                 });
          //
          //                 kakao.maps.event.addListener(map, "click", function () {
          //                    console.log(customOverlay);
          //                    customOverlay.setMap(null);
          //                 });
          //
          itemEl.onmouseenter = () => {
            if (this.customOverlay) this.customOverlay.setMap(null);
            this.displayStoreInfowindow(marker, place.name, place);
          };

          itemEl.onmouseleave = () => {
            if (this.customOverlay) this.customOverlay.setMap(null);
          };
          index++;

          setTimeout(function() {
            fragment.appendChild(itemEl);
          }, 50);
          // setTimeout(function() {
          // }, 50);
        });
      }
      setTimeout(() => {
        listEl.appendChild(fragment);
      }, 500);
      menuEl.scrollTop = 0;
      // 마커를 생성하고 지도에 표시합니다
      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      //    panTo();
      this.setBounds(this.bounds);

      // displayPagination(pagination);
    },

    getListStoreItem(index, place) {
      var el = document.createElement("li");
      var itemStr =
        '<div class="info">' +
        "    <h5>" +
        '<img src="img/happyhouse/store.png">' +
        "  " +
        place.name +
        "</h5>" +
        "<span>" +
        "<h4> 업종 :  " +
        place.type +
        "</h4>" +
        place.sidoName +
        " " +
        place.gugunName +
        " " +
        place.dongName +
        "</span>" +
        "</div>";
      el.innerHTML = itemStr;
      el.className = "store";
      return el;
    },

    displayStoreInfowindow(marker, title, place) {
      var content = `
   <div class="wrap">
      <div class="info">'
      <div class="overlaybox">
         <div class="boxtitle">${title}
         <img src="img/happyhouse/close.png" id="closeimg" class="close" title="닫기"></img>
         </div>
         <div class="first"><img src="img/happyhouse/storepic.png" style="width:247px; height:136px;" alt=""></div>
         <ul style="padding-left:0px;">
            <li class="up">
               <span class="title">업종</span>
               <span class="count">${place.type}</span>
            </li>
            <li>
               <span class="title">주소</span>
               <span class="count">${place.sidoName} ${place.gugunName} ${place.dongName} </span>
            </li>
         </ul>
      </div>
      </div>
   </div>
   `;
      var position = new kakao.maps.LatLng(
        marker.getPosition().getLat() + 0.00033,
        marker.getPosition().getLng() - 0.00003
      );
      this.customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content,
        xAnchor: 0.4,
        yAnchor: 0.85
      });
      this.customOverlay.setMap(this.map);
      const clsbtn = document.getElementById("closeimg");
      clsbtn.addEventListener("click", event => {
        this.closeOverlay();
      });
    },

    //마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addStoreMarker(position, idx) {
      var imageSrc =
          "http://t1.daumcdn.net/localimg/localimages/07/2018/pc/img/marker_spot.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 37), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position // 마커의 위치
        });
      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 환경 점검 !!!
    displayEnvMarkers(places) {
      var listEl = document.getElementById("env_placesList"),
        menuEl = document.getElementById("env_wrap"),
        fragment = document.createDocumentFragment();
      this.bounds = new kakao.maps.LatLngBounds();
      var listStr = "";

      this.removeAllChildNods(listEl);
      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();
      var index = 0;
      for (let place of places) {
        this.geocoder.addressSearch(place.roadAddress, (result, status) => {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            var placePosition = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = this.addMarker(placePosition, index);
            var itemEl = this.getListEnvItem(index, place); // 검색 결과 항목 Element를 생성합니다
            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다

            this.bounds.extend(placePosition);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다

            kakao.maps.event.addListener(marker, "click", () => {
              this.displayEnvInfowindow(marker, place.facName, place);
              if (this.clickedOverlay) {
                this.clickedOverlay.setMap(null);
              }
              this.customOverlay.setMap(this.map);
              this.clickedOverlay = this.customOverlay;
            });
            //                 kakao.maps.event.addListener(marker, "click", function () {
            //                    console.log(customOverlay);
            //
            //                    customOverlay.setMap(null);
            //                 });
            //
            //                 kakao.maps.event.addListener(marker, "click", function () {
            //                    displayInfowindow(marker, title, place);
            //                    console.log(title + " " + code);
            //                 });
            //
            //                 kakao.maps.event.addListener(map, "click", function () {
            //                    console.log(customOverlay);
            //                    customOverlay.setMap(null);
            //                 });
            //
            itemEl.onmouseenter = () => {
              if (this.customOverlay) this.customOverlay.setMap(null);
              this.displayEnvInfowindow(marker, place.facName, place);
            };

            itemEl.onmouseleave = () => {
              if (this.customOverlay) this.customOverlay.setMap(null);
            };

            index++;
            setTimeout(() => {
              fragment.appendChild(itemEl);
            }, 50);
          }
        });
      }

      setTimeout(() => {
        listEl.appendChild(fragment);
        if (this.customOverlay) this.customOverlay.setMap(null);
      }, 500);
      menuEl.scrollTop = 0;
      // 마커를 생성하고 지도에 표시합니다
      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      //    panTo();
      this.setBounds(this.bounds);
    },

    //검색결과 항목을 Element로 반환하는 함수입니다
    getListEnvItem(index, place) {
      var el = document.createElement("li");
      var itemStr =
        '<span class="markerbg marker_' +
        (index + 1) +
        '"></span>' +
        '<div class="info">' +
        "    <h5>" +
        place.facName +
        "</h5>" +
        "<span>" +
        place.roadAddress +
        "</span>" +
        "</div>";
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },

    //검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
    //인포윈도우에 장소명을 표시합니다
    displayEnvInfowindow(marker, title, place) {
      var content = `
   <div class="wrap">
      <div class="info">'
      <div class="overlaybox">
         <div class="boxtitle">${title}
         <img src="img/happyhouse/close.png" id="closeimg" class="close" title="닫기"></img>
         </div>
         <div class="first"><img src="img/happyhouse/factory.png" style="width:247px; height:136px;" alt=""></div>
         <ul style="padding-left:0px;">
            <li class="up">
               <span class="title">관리 대상</span>
               <span class="count">${place.jobName}</span>
            </li>
            <li>
               <span class="title">주소</span>
               <span class="count">${place.roadAddress}</span>
            </li>
            <li>
               <span class="title">점검사항</span>
               <span class="count">${place.checkDesc}</span>
            </li>
            <li>
               <span class="title">점검날짜</span>
               <span class="count">${place.mapCheckDate}</span>
            </li>
         </ul>
      </div>
      </div>
   </div>
   `;
      // });
      var position = new kakao.maps.LatLng(
        marker.getPosition().getLat() + 0.00033,
        marker.getPosition().getLng() - 0.00003
      );
      this.customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content,
        xAnchor: 0.4,
        yAnchor: 1.0
      });
      this.customOverlay.setMap(this.map);
      const clsbtn = document.getElementById("closeimg");
      clsbtn.addEventListener("click", event => {
        this.closeOverlay();
      });
    },
    async makeList(jjims) {
      var listEl = document.getElementById("jjim_placesList");
      listEl.innerHTML = "";
      // $("#jjim_placesList").empty();
      for (let jjim of jjims) {
        let str = `
	        <button type="button" id="jjimDeleteBtn" class="delBtn btn btn-outline-danger btn-sm mt-2 float-right mt-3 mr-5" value="${jjim.dongCode}">삭제</button>
          <li class="item">
            <span class="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"></span>
            <div class="info">
            	<input type="hidden" id="jjimdongC" value="${jjim.dongCode}">
        	    <h4 style="text-align:center;" value="${jjim.dongNo}">동 번호 : ${jjim.dongNo}</h4>
              <span style="font-size:17px; text-align:center; font-weight:bold;" id="jjimaddr">${jjim.sidoName} ${jjim.gugunName} ${jjim.dongName} </span>
	          </div>
          </li>
          `;
        listEl.innerHTML += str;
      }
      const delbtn = document.querySelectorAll("#jjimDeleteBtn");
      delbtn.forEach(del => {
        del.addEventListener("click", event => {
          this.deleteJjim(del.value);
        });
      });

      const jjimAptBtn = document.querySelectorAll(".item");

      jjimAptBtn.forEach(apt => {
        apt.addEventListener("click", event => {
          let addr = apt.querySelector("#jjimaddr").textContent;
          const split = addr.split(" ");
          let val = apt.querySelector("#jjimdongC").value.substring(0, 5);
          const param = {
            sido: split[0].trim(),
            gugun: split[1].trim(),
            dong: split[2].trim()
          };
          this.SET_SIGUDONG_NAME(param);
          this.SET_GUGUNC(val);
          this.jjimAptSearch(val);
        });
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "~@/assets/css/apt.css";
</style>
<style type="text/css" scoped>
#container {
  overflow: hidden;
  height: 800px;
  position: relative;
}
#mapWrapper {
  width: 100%;
  height: 800px;
  z-index: 1;
}
#rvWrapper {
  width: 50%;
  height: 800px;
  top: 0;
  right: 0;
  position: absolute;
  z-index: 0;
}
#container.view_roadview #mapWrapper {
  width: 50%;
}
#roadviewControl {
  position: absolute;
  top: 5px;
  left: 5px;
  width: 42px;
  height: 42px;
  z-index: 2;
  cursor: pointer;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2018/pc/common/img_search.png)
    0 -450px no-repeat;
}
#aptcontrol {
  position: absolute;
  top: 7px;
  left: 50px;
  width: 36px;
  height: 36px;
  z-index: 2;
  cursor: pointer;
  border-radius: 7px;
  box-shadow: 0 0 3px;
}
#jjimcontrol {
  position: absolute;
  top: 7px;
  left: 91px;
  width: 36px;
  height: 36px;
  z-index: 2;
  cursor: pointer;
  border-radius: 7px;
  box-shadow: 0 0 3px;
}
#commcontrol {
  position: absolute;
  top: 7px;
  left: 132px;
  width: 36px;
  height: 36px;
  z-index: 2;
  cursor: pointer;
  border-radius: 7px;
  box-shadow: 0 0 3px;
}
#envcontrol {
  position: absolute;
  top: 7px;
  left: 173px;
  width: 36px;
  height: 36px;
  z-index: 2;
  cursor: pointer;
  border-radius: 7px;
  box-shadow: 0 0 3px;
}
#dong {
  position: absolute;
  top: 20px;
  left: 49%;
  z-index: 3;
}
#sido {
  position: absolute;
  top: 20px;
  left: 39%;
  z-index: 3;
}
#gugun {
  position: absolute;
  top: 20px;
  left: 44%;
  z-index: 3;
}

#clearBtn,
#jjimRegisterBtn {
  top: 20px;
  left: 60%;
  z-index: 3;
}
#roadviewControl.active {
  background-position: 0 -350px;
}
#close {
  position: absolute;
  padding: 4px;
  top: 5px;
  left: 5px;
  cursor: pointer;
  background: #fff;
  border-radius: 4px;
  border: 1px solid #c8c8c8;
  box-shadow: 0px 1px #888;
}
#close .img {
  display: block;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/rv_close.png)
    no-repeat;
  width: 14px;
  height: 14px;
}
</style>
