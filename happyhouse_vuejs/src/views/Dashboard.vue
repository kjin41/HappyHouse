<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
        <b-col xl="3" md="6">
          <b-form-select
            id="sido"
            v-model="sidoCode"
            :options="sidos"
            @change="gugunList"
          ></b-form-select>
        </b-col>
        <b-col xl="3" md="6">
          <b-form-select
            id="gugun"
            v-model="gugunCode"
            :options="guguns"
            @change="submitGugun"
          ></b-form-select>
        </b-col>
        <b-col xl="3" md="6" v-if="newsShow">
          <stats-card
            title="최저 기온 (전국)"
            type="gradient-green"
            :sub-title="temps.low"
            icon="ni ni-notification-70"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">최저 기온 (전국)</span>
              <span class="text-nowrap">기상청</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6" v-if="news">
          <stats-card
            title="최고 기온 (전국)"
            type="gradient-info"
            :sub-title="temps.high"
            icon="ni ni-satisfied"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-danger mr-2">최고 기온 (전국)</span>
              <span class="text-nowrap">기상청</span>
            </template>
          </stats-card>
        </b-col>
      </b-row>
    </base-header>

    <!--Charts-->
    <b-container fluid class="mt--7" v-if="newsShow">
      <h1>NEWS</h1>
      <b-row id="newsrow">
        <b-col xl="4" class="mb-5 mb-xl-0">
          <b-card
            :title="carousels[0].title"
            :img-src="carousels[0].imgUrl"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 30rem;"
            class="mb-2"
          >
            <b-card-text>
              {{ carousels[0].content }}
            </b-card-text>

            <b-button :href="carousels[0].url" variant="primary"
              >원문 보러가기</b-button
            >
          </b-card>
        </b-col>
        <b-col xl="4" class="mb-5 mb-xl-0">
          <b-card
            :title="carousels[1].title"
            :img-src="carousels[1].imgUrl"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 30rem;"
            class="mb-2"
          >
            <b-card-text>
              {{ carousels[1].content }}
            </b-card-text>

            <b-button :href="carousels[1].url" variant="primary"
              >원문 보러가기</b-button
            >
          </b-card>
        </b-col>

        <b-col xl="4" class="mb-5 mb-xl-0">
          <card header-classes="bg-transparent">
            <b-row align-v="center" slot="header">
              <b-col>
                <h4
                  class="text-uppercase text-muted ls-1 mb-1"
                  v-if="sidoName == '시/도'"
                >
                  전국
                </h4>
                <h4
                  class="text-uppercase text-muted ls-1 mb-1"
                  v-if="sidoName != '시/도'"
                >
                  {{ sidoName }}
                </h4>
                <h5 class="h3 mb-0">코로나 누적 확진자 현황</h5>
              </b-col>
            </b-row>
            <div>
              <canvas id="myChart"></canvas>
            </div>
            <h5 class="ml-5 mt-5">오늘 확진자 수 : {{ corona.tdTotal }}명</h5>
            <h5 class="ml-5 pt-2">오늘 국내 확진자 수 : {{ corona.tdIn }}명</h5>
            <h5 class="ml-5 pt-2">
              오늘 해외 확진자 수 : {{ corona.tdOut }}명
            </h5>
            <h5 class="ml-5 pt-2">누적 확진자 수 : {{ corona.total }}명</h5>
            <h5 class="ml-5 pt-2">
              누적 격리해제 환자 수 : {{ corona.cure }}명
            </h5>
            <h5 class="ml-5 pt-2">누적 사망자 수 : {{ corona.dead }}명</h5>
            <h5 class="ml-5 pt-2">사망률 (10만명 당) : {{ corona.rate }}명</h5>
          </card>
        </b-col>
      </b-row>
      <b-row id="inforow mt-5">
        <b-col xl="4" class="mb-5 mb-xl-0">
          <b-card
            :title="carousels[2].title"
            :img-src="carousels[2].imgUrl"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 25rem;"
            class="mb-2"
          >
            <b-card-text>
              {{ carousels[2].content }}
            </b-card-text>

            <b-button :href="carousels[2].url" variant="primary"
              >원문 보러가기</b-button
            >
          </b-card>
        </b-col>
        <b-col xl="4" class="mb-5 mb-xl-0">
          <b-card
            :title="carousels[3].title"
            :img-src="carousels[3].imgUrl"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 25rem;"
            class="mb-2"
          >
            <b-card-text>
              {{ carousels[3].content }}
            </b-card-text>

            <b-button :href="carousels[3].url" variant="primary"
              >원문 보러가기</b-button
            >
          </b-card>
        </b-col>

        <b-col xl="4" class="mb-5 mb-xl-0">
          <b-card
            :title="carousels[4].title"
            :img-src="carousels[4].imgUrl"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 25rem;"
            class="mb-2"
          >
            <b-card-text>
              {{ carousels[4].content }}
            </b-card-text>

            <b-button :href="carousels[4].url" variant="primary"
              >원문 보러가기</b-button
            >
          </b-card>
        </b-col>
      </b-row>
      <!-- End charts-->

      <!--Tables-->
      <!-- <b-row class="mt-5">
        <b-col xl="8" class="mb-5 mb-xl-0">
          <page-visits-table></page-visits-table>
        </b-col>
        <b-col xl="4" class="mb-5 mb-xl-0">
          <social-traffic-table></social-traffic-table>
        </b-col>
      </b-row> -->
      <!--End tables-->
    </b-container>
  </div>
</template>
<script>
// Charts
import * as chartConfigs from "@/components/Charts/config";
import LineChart from "@/components/Charts/LineChart";
import BarChart from "@/components/Charts/BarChart";
import { mapActions, mapMutations, mapState } from "vuex";

// Components
import BaseProgress from "@/components/BaseProgress";
import StatsCard from "@/components/Cards/StatsCard";

// Tables
import SocialTrafficTable from "./Dashboard/SocialTrafficTable";
import PageVisitsTable from "./Dashboard/PageVisitsTable";
import { Pie } from "vue-chartjs";

const houseStore = "houseStore";
const newsStore = "newsStore";
export default {
  components: {
    LineChart,
    BarChart,
    BaseProgress,
    StatsCard,
    PageVisitsTable,
    SocialTrafficTable
  },
  computed: {
    ...mapState(newsStore, [
      "sidos",
      "guguns",
      "sidoC",
      "gugunC",
      "sidoName",
      "gugunName",
      "news",
      "temps",
      "coronas"
    ])
  },
  data() {
    return {
      slide: 0,
      sliding: null,
      sidoCode: null,
      gugunCode: null,
      newsShow: false,
      imgSource: [],
      carousels: [],
      corona: {},
      pieChart: {
        labels: ["Red", "Blue", "Yellow"],
        datasets: [
          {
            label: "first",
            data: [300, 50, 100],
            backgroundColor: [
              "rgb(255, 99, 132)",
              "rgb(54, 162, 235)",
              "rgb(255, 205, 86)"
            ],
            hoverOffset: 4
          }
        ]
      },
      pieConfig: {
        type: "pie",
        data: this.pieChart
      },
      redBarChart: {
        chartData: {
          labels: ["Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
          datasets: [
            {
              label: "Sales",
              data: [25, 20, 30, 22, 17, 29]
            }
          ]
        },
        extraOptions: chartConfigs.blueChartOptions
      }
    };
  },
  watch: {
    "$store.state.newsStore.gugunC": function() {
      if (this.gugunC != null) {
        let sido = this.sidoC + "00000000";
        let gugun = this.gugunC + "00000";
        const params = {
          sido: sido,
          gugun: gugun
        };
        let target = document.getElementById("sido");
        let target1 = document.getElementById("gugun");
        const param = {
          sido: target.options[target.selectedIndex].text.trim(),
          gugun: target1.options[target1.selectedIndex].text.trim()
        };
        this.SET_SIGU_NAME(param);
        this.newsList(params);
        // setTimeout(() => {
        //   this.setPieChart(target.options[target.selectedIndex].text.trim());
        // }, 300);
      }
    },
    "$store.state.newsStore.news": function() {
      this.carousels = this.news;
      this.newsShow = true;
    },
    "$store.state.newsStore.sidoName": function() {
      if (!this.sidoName) return;
      let target = document.getElementById("sido");
      setTimeout(() => {
        this.setPieChart(target.options[target.selectedIndex].text.trim());
      }, 300);
    },
    "$store.state.newsStore.sidos": function() {
      let target = document.getElementById("sido");
      setTimeout(() => {
        this.setPieChart(target.options[target.selectedIndex].text.trim());
      }, 300);
    }
  },
  methods: {
    ...mapActions(newsStore, [
      "getSido",
      "getGugun",
      "newsList",
      "tempList",
      "coronaList"
    ]),
    ...mapMutations(newsStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "SET_SIDO",
      "SET_GUGUN",
      "SET_SIGU_NAME",
      "CLEAR_SIGU_NAME"
    ]),
    sidoList() {
      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.sidoCode = null;
      this.gugunCode = null;
      this.getSido();
    },
    gugunList() {
      this.CLEAR_SIGU_NAME();
      this.SET_SIDO(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) {
        this.getGugun(this.sidoCode);
      }
    },
    submitGugun() {
      this.SET_GUGUN(this.gugunCode);
      console.log(this.sidoC, this.gugunC);
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    async setPieChart(reg) {
      console.log("이전", reg);
      if (reg == "충청북도") {
        reg = "충북";
      } else if (reg == "충청남도") {
        reg = "충남";
      } else if (reg == "전라북도") {
        reg = "전북";
      } else if (reg == "전라남도") {
        reg = "전남";
      } else if (reg == "경상북도") {
        reg = "경북";
      } else if (reg == "경상남도") {
        reg = "경남";
      } else if (reg == "시/도") {
        reg = "합계";
      } else {
        reg = reg.substring(0, 2);
      }
      console.log("이후", reg);

      for (let i = 0; i < 19; i++) {
        if (this.coronas[i].region == reg) {
          this.corona = this.coronas[i];
          console.log(this.corona.region);
          break;
        }
      }
      console.log(this.corona.dead);

      const myChart = document.getElementById("myChart");
      setTimeout(() => {
        const chart = new Chart(myChart, {
          label: "누적 코로나 확진자 현황",
          type: "pie",
          data: {
            labels: ["누적 확진자", "격리해제", "사망자"],
            datasets: [
              {
                label: "코로나 확진자",
                data: [this.corona.total, this.corona.cure, this.corona.dead],
                backgroundColor: [
                  "rgb(255, 99, 132)",
                  "rgb(54, 162, 235)",
                  "rgb(255, 205, 86)"
                ]
              }
            ],
            hoverOffset: 4
          }
        });
      }, 500);
      //   data: { [this.corona.total, this.corona.cure, this.corona.dead],
      //   backgroundColor: [
      //     "rgb(255, 99, 132)",
      //     "rgb(54, 162, 235)",
      //     "rgb(255, 205, 86)"
      //   ],
      //   hoverOffset: 4
      //   }
      // };
      // let label = ["누적 확진자", "격리해제", "사망자"];
      // this.pieChart.datasets[0] = param;
      // this.pieChart.labels = label;
      // this.pieConfig.data = this.pieChart;
      // console.log(this.pieChart);
      // console.log(this.pieConfig);
      // setTimeout(() => {
      //   console.log(this.pieConfig);
      //   pieChart = new Chart(myChart, this.pieConfig);
      // }, 2000);
    }
  },
  created() {
    this.tempList();
    this.coronaList();
    this.sidoList();
    this.newsList("", "");
  }
};
</script>
<style scoped>
.el-table .cell {
  padding-left: 0px;
  padding-right: 0px;
}
.card-body {
  height: 480px;
}
.carousel-inner > .carousel-item > img {
  width: 1024px;
  height: 240px;
}
</style>