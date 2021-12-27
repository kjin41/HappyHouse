import axios from "axios";
import { API_BASE_URL, APT_PUBLIC_URL } from "@/config";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json"
    }
  });
  return instance;
}

function houseInstance() {
  const instance = axios.create({
    baseURL: APT_PUBLIC_URL,
    headers: {
      "Content-type": "application/json"
    }
  });
  return instance;
}

export { apiInstance, houseInstance };
