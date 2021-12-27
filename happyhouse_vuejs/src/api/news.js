import { apiInstance } from "./index.js";

const api = apiInstance();

function getNews(params, success, fail) {
  api
    .get(`/news/list`, { params: params })
    .then(success)
    .catch(fail);
}
function getTemp(success, fail) {
  api
    .get(`/news/temp`)
    .then(success)
    .catch(fail);
}
function getCorona(success, fail) {
  api
    .get(`/news/corona`)
    .then(success)
    .catch(fail);
}
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
export { sidoList, gugunList, getNews, getTemp, getCorona };