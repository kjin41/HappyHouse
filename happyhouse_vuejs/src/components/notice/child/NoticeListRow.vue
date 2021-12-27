<template>
  <b-tr>
    <b-td>{{ no }}</b-td>
    <b-th class="text-left">
      <router-link
        :to="{ name: 'NoticeView', params: { no: no } }"
        >{{ title }}</router-link
      >
    </b-th>
    <b-td>{{ hit }}</b-td>
    <b-td>{{ userId }}</b-td>
    <b-td>{{ date }}</b-td>
  </b-tr>
</template>

<script>
// import moment from "moment";

export default {
  name: "NoticeListRow",
  props: {
    no: Number,
    userId: String,
    title: String,
    hit: Number,
    date: String,
  },
  computed: {
    // changeDateFormat() {
    //   return moment(new Date(this.date)).format("YY.MM.DD hh:mm:ss");
    // },
  },
};
</script>

<style></style>
