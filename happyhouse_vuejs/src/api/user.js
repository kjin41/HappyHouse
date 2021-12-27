import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userId, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userId}`).then(success).catch(fail);
}

async function getKakaoToken(code, success, fail) {
  await api.get(`/user/kakaologin?code=${code}`).then(success).catch(fail);
}
async function getKakaoUser(token, success, fail) {
  await api.get(`/user/kakaotoken?token=${token}`).then(success).catch(fail);
}

function userRegister(user, success, fail) {
  api.post(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

function userModify(user, success, fail) {
  api.put(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

function userDelete(userId, success, fail) {
  api.delete(`/user/${userId}`).then(success).catch(fail);
}

function findPassoword(userId, success, fail) {
  api.get(`/user/${userId}`).then(success).catch(fail);
}

function idCheck(userId, success, fail) {
  api.get(`/user/idcheck/${userId}`).then(success).catch(fail);
}

// function logout(success, fail)

export { login, findById, userRegister, userModify, userDelete, getKakaoToken, getKakaoUser, findPassoword, idCheck };
