import * as React from "react";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "../minigame/ClickerGameReady.css";

function ClickerGameReadyPage(props) {
  const [timeLeft, setTimeLeft] = useState(3);
  const navigate = useNavigate();
  //gameid, userData,stationId을 useLocation으로 minigame에 보내줘야함.
  //navData에 user정보가 담겨야 함.
  const state = props.state;
  // console.log(state);
  const navData = [state, { gameType: 1 }];

  useEffect(() => {
    const interval = setInterval(() => {
      setTimeLeft((prevTimeLeft) => prevTimeLeft - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, []);
  useEffect(() => {
    if (timeLeft === 0) {
      navigate("/pvp/minigame", { state: navData });
    }
  }, [timeLeft, navigate]);
  return (
    <div className="minigame-clicker-ready">
      <div className="minigame-clicker-ready-header">
        <p>
          지하철 빈자리
          <br />
          차지하기!!!
        </p>
      </div>
      <div className="minigame-clicker-ready-main-text">
        <p>지하철 타는 시간 1시간...</p>
        <p>격하게 의자에 앉고 싶다...</p>
        <p>최대한 빨리 빈자리를 차지해보자!</p>
      </div>

      <div>
        <p className="minigame-clicker-ready-timer">{timeLeft}</p>
      </div>
      <div className="minigame-clicker-ready-timer-text">
        <p>초 후 시작...</p>
      </div>
      <div className="minigame-clicker-ready-gif"></div>
    </div>
  );
}

export default ClickerGameReadyPage;
