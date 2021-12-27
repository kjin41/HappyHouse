import { apiInstance, houseInstance } from "./index.js";

const api = apiInstance();
const house = houseInstance();

function sidoList(success, fail) {
  api
    .get(`/map/sido`)
    .then(success)
    .catch(fail);
}

function gugunList(params, success, fail) {
  api
    .get(`/map/gugun`, { params: params })
    .then(success)
    .catch(fail);
}

function dongList(params, success, fail) {
  api
    .get(`/map/dong`, { params: params })
    .then(success)
    .catch(fail);
}

function houseList(params, success, fail) {
  api
    .get(`/map/housePd`, { params: params })
    .then(success)
    .catch(fail);
}

function houseDealList(params, success, fail) {
  api
    .post(`/map/housePd`, JSON.stringify(params))
    .then(success)
    .catch(fail);
}

function envList(params, success, fail) {
  api
    .get(`/env/list`, { params: params })
    .then(success)
    .catch(fail);
}

function commList(params, success, fail) {
  api
    .get(`/store/list`, { params: params })
    .then(success)
    .catch(fail);
}

function jjimList(userId, success, fail) {
  api
    .get(`/map/jjim/${userId}`)
    .then(success)
    .catch(fail);
}
function jjimInsert(params, success, fail) {
  api
    .get(`/map/jjim`, { params: params })
    .then(success)
    .catch(fail);
}
function jjimDelete(payload, success, fail) {
  api
    .delete(`/map/jjim`, { data: payload })
    .then(success)
    .catch(fail);
}
function priceList(params, success, fail) {
  api
    .get(`/map/price`, { params: params })
    .then(success)
    .catch(fail);
}
function trainList(params, success, fail) {
  api
    .get(`/map/train`, { params: params })
    .then(success)
    .catch(fail);
}
function clinicList(params, success, fail) {
  api
    .get(`/map/clinic`, { params: params })
    .then(success)
    .catch(fail);
}
function busList(params, success, fail) {
  api
    .get(`/map/bus`, { params: params })
    .then(success)
    .catch(fail);
}
function starbucksList(params, success, fail) {
  api
    .get(`/map/starbucks`, { params: params })
    .then(success)
    .catch(fail);
}
function bikeList(params, success, fail) {
  api
    .get(`/map/bike`, { params: params })
    .then(success)
    .catch(fail);
}

export {
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
};