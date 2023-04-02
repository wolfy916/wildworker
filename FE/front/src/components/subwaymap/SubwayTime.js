import * as React from "react";
import { useState, useEffect } from "react";
import timer from "../../asset/image/timer.png";
import "./SubwayTime.css";

function SubwayTime(props) {
  const [seconds, setSeconds] = useState(props.remainSec);

  useEffect(() => {
    const interval = setInterval(() => {
      setSeconds((prevSeconds) => prevSeconds - 1);
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  if (seconds === -1) {
    setSeconds(600);
  }

  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;

  return (
    <div className="map-timer">
      <p className="map-timer-content">수수료 정산 시간</p>
      <p className="map-timer-content">
        <img className="map-timer-img" src={timer} alt="timer" /> {minutes}:
        {remainingSeconds < 10 ? `0${remainingSeconds}` : remainingSeconds}
      </p>
    </div>
  );
}

export default SubwayTime;
