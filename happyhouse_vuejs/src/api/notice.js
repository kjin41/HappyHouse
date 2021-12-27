import { apiInstance } from "./index.js";

const api = apiInstance();

function listArticle(success, fail) {
  api.get(`/notice`).then(success).catch(fail);
}
// article, JSON.stringify(article), 
function writeArticle(formData, success, fail) {
  api.post(`/notice`, formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(success).catch(fail);
}

function getArticle(no, success, fail) {
  api.get(`/notice/${no}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/notice`, JSON.stringify(article)).then(success).catch(fail);
}
//, {headers: { responseType: 'arraybuffer' }}
function downloadFile(file, success, fail) {
  api.post(`/notice/download`, file, {'responseType':'blob'}).then(success).catch(fail);
}

function deleteArticle(no, success, fail) {
  api.delete(`/notice/${no}`).then(success).catch(fail);
}

export { listArticle, writeArticle, getArticle, modifyArticle, deleteArticle, downloadFile };
