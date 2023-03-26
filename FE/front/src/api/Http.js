import axios from "axios";

const http = axios.create({
  baseURL: "https://j8a304.p.ssafy.io/api",
  headers: {
    "Content-type": "application/json;charset=UTF-8",
  },
});
export default http;
