<template>
  <div>
    <b-row class="text-center w-20">
      <b-col class="sm-3">
        <transition name="fade">
          <b-form-select
            id="sido"
            v-model="sidoCode"
            :options="sidos"
            @change="gugunList"
          ></b-form-select>
        </transition>
      </b-col>
      <b-col class="sm-3">
        <transition name="fade">
          <b-form-select
            id="gugun"
            v-model="gugunCode"
            :options="guguns"
            @change="dongList"
          ></b-form-select>
        </transition>
      </b-col>
      <b-col class="sm-1">
        <transition name="fade">
          <b-form-select
            id="dong"
            v-model="dongCode"
            :options="dongs"
            @change="dongSet"
          ></b-form-select>
        </transition>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";

const houseStore = "houseStore";
export default {
  name: "MapSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      watchData: this.sidoCode
    };
  },
  created() {
    this.sidoList();
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "gugunC"])
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getDong"]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_SIGUDONG_NAME",
      "SET_SIDO",
      "SET_GUGUN",
      "SET_DONG",
      "SET_SIGUDONG_NAME"
    ]),
    sidoList() {
      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.sidoCode = null;
      this.gugunCode = null;
      this.dongCode = null;
      this.getSido();
    },
    gugunList() {
      this.SET_SIDO(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      this.dongCode = null;
      this.CLEAR_DONG_LIST();
      if (this.sidoCode) {
        this.getGugun(this.sidoCode);
      }
    },
    async dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null;

      if (this.gugunCode) {
        this.SET_GUGUN(this.gugunCode);
        await this.getDong(this.gugunCode);
        console.log(this.gugunCode);
        console.log(this.gugunC);
      }
    },
    dongSet() {
      this.CLEAR_SIGUDONG_NAME();
      this.SET_DONG(this.dongCode);
      let target = document.getElementById("sido");
      let target1 = document.getElementById("gugun");
      let target2 = document.getElementById("dong");
      const param = {
        sido: target.options[target.selectedIndex].text.trim(),
        gugun: target1.options[target1.selectedIndex].text.trim(),
        dong: target2.options[target2.selectedIndex].text.trim()
      };
      console.log(param.sido);
      console.log(param.gugun);
      console.log(param.dong);
      this.SET_SIGUDONG_NAME(param);
    }
  }
};
</script>

<style scoped>
#dong {
  position: absolute;
  width: 124px;
  height: 40px;
  top: 15px;
  left: -51%;
  z-index: 3;
}
#sido {
  position: absolute;
  width: 139px;
  height: 40px;
  top: 15px;
  left: 85%;
  z-index: 3;
}
#gugun {
  position: absolute;
  width: 139px;
  height: 40px;
  top: 15px;
  left: 17%;
  z-index: 3;
}

#clearBtn,
#jjimRegisterBtn {
  top: 20px;
  left: 55%;
  z-index: 3;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>