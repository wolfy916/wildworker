import * as React from "react";
import Box from "@mui/material/Box";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "./MiniGameReadyPage.css";

function MainPage() {
  const [timeLeft, setTimeLeft] = useState(100);
  const navigate = useNavigate();

  useEffect(() => {
    const interval = setInterval(() => {
      setTimeLeft(prevTimeLeft => prevTimeLeft - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, []);
  useEffect(() => {
    if (timeLeft === 0) {
      navigate("/pvp/minigame");
    }
  }, [timeLeft, navigate]);
  return (
    <div className="minigame-ready">
      <div className="minigame-ready-header">
        <p>회식비 정산하기!!!</p>
      </div>
      <div className="minigame-ready-main-text">
        <p>어제 과음한 탓에...</p>
        <p>내 카드로 회식비를 긁었다...</p>
        <p>정신차리고 회식비를 정산해보자!</p>
      </div>

      <div>
        <p className="minigame-ready-timer">{timeLeft}</p>
      </div>
      <div className="minigame-ready-timer-text">
        <p>초 후 시작...</p>
      </div>
      <div className="minigame-ready-gif"></div>
    </div>
  );
}

export default MainPage;
