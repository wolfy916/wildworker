import axios from "axios";

const http = axios.create({
  baseURL: "https://j8a304.p.ssafy.io/api/v1",
  // baseURL: "http://70.12.246.118:8080/api/v1",
  headers: {
    "Content-type": "application/json;charset=UTF-8",
  },
});
export default http;
