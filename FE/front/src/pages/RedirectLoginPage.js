import * as React from "react";
import "./RedirectLoginPage.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import LoadingEffect from "../asset/image/pvpPageLoading.gif";
import { getUserInfo } from "../api/User";

function LoginPage(props) {
  const [timeLeft, setTimeLeft] = useState(3.5);
  const [imageSrc, setImageSrc] = useState("");
  const navigate = useNavigate();
  const targetTag = document.getElementsByClassName(
    "redirect-login-background"
  )[0];
  const blackBackgroundTag = document.createElement("div");
  useEffect(() => {
    props.setIsLogin(true);
    getUserInfo({ setFunc: props.setUserData });

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
      navigate("/main");
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
