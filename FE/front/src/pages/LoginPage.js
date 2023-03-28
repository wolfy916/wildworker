import * as React from "react";
import "./LoginPage.css";
import axios from 'axios';
import loginTitle from "../asset/image/intro_title.png";
import loginKakao from "../asset/image/kakao_login.png";

function LoginPage() {
  function loginClickHandler() {
    // 로그인 버튼 클릭시 이벤트
    window.location.replace("https://j8a304.p.ssafy.io/api/v1/auth/login");
    // window.location.replace("http://70.12.246.118:8080/api/v1/auth/login");
    // axios({
    //   method: 'get',
    //   url: 'https://j8a304.p.ssafy.io/api/v1/auth/login',
    //   headers: {
    //     "Content-type": "application/json;charset=UTF-8",
    //   },
    //   // withCredentials: true,
    // })
  }
  return (
    <div className="login-background">
      <img className="login-title-img" src={loginTitle} alt="loginTitle" />
      <div className="login-title-text">Episode 1. 2호선</div>
      <img
        className="login-kakao-img"
        onClick={loginClickHandler}
        src={loginKakao}
        alt="loginKakao"
      />
    </div>
  );
}
export default LoginPage;
