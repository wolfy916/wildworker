import * as React from "react";
import "./RedirectLoginPage.css";
import { useState, useEffect } from "react";
import { getUserInfo } from "../api/User";
import { useNavigate } from "react-router";
import LoadingEffect from "../asset/image/pvpPageLoading.gif";
import axios from "axios";

function LoginPage() {
  const [timeLeft, setTimeLeft] = useState(3.5);
  const [imageSrc, setImageSrc] = useState("");
  const [userData, setUserData] = useState({ hi: 1 });
  const navigate = useNavigate();
  const targetTag = document.getElementsByClassName(
    "redirect-login-background"
  )[0];
  const blackBackgroundTag = document.createElement("div");
  useEffect(() => {
    const href = window.location.href;
    let params = new URL(href).searchParams;
    let session = params.get("SESSION");
    document.cookie = `SESSION=${session}; path=/; expires=Tue, 19 Jan 2038 03:14:07 GMT`;
    axios({
      method: "get",
      url: "/user",
      baseURL: "https://j8a304.p.ssafy.io/api/v1",
      // baseURL: "http://70.12.246.118:8080/api/v1",
      headers: {
        "Content-type": "application/json;charset=UTF-8",
      },
      widthCredentials: true,
    })
      .then(({ status, data }) => {
        if (status === 200) {
          console.log("getUserInfo 성공", data);
          return data;
          // data 예시
          // {
          //   "name": "S2태형S2",
          //   "titleType": 1,
          //   "titleId": 1,
          //   "characterType": 1
          // }
        }
      })
      .catch((err) => {
        console.log("getUserInfo 실패");
        console.log(err.response);
      });
    const interval = setInterval(() => {
      setTimeLeft((prevTimeLeft) => prevTimeLeft - 0.25);
    }, 250);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    if (timeLeft === 0.75) {
      setImageSrc(LoadingEffect);
    }
    if (timeLeft === 0.25) {
      blackBackgroundTag.classList.add("redirect-black-background");
      targetTag.appendChild(blackBackgroundTag);
    }
    if (timeLeft === 0) {
      // navigate("/main", { state: userData });
    }
  }, [timeLeft, navigate]);
  return (
    <div className="redirect-login-background">
      <div className="redirect-login-effect">
        {imageSrc && (
          <img className="test-loading-effect" src={imageSrc} alt="imageSrc" />
        )}
      </div>
    </div>
  );
}
export default LoginPage;
