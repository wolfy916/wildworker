import axios from "axios";

const http = axios.create({
  baseURL: "https://j8a304.p.ssafy.io/api/v1",
  headers: {
    "Content-type": "application/json;charset=UTF-8",
  },
  withCredentials: true,
});
export default http;
