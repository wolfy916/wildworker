import React, { useState, useEffect } from "react";
import "./ClickerGame.css";
import { useNavigate } from "react-router-dom";
import beam from "../../asset/image/stop_man.png";

const MOLE_INITIAL_STATE = Array(9).fill(false);

function ClickerGame(props) {
  const stompClient = props.stompClient;
  //navData에 userData담아서 /result로 보내기
  const propState = props.state;
  const stationId = propState[0][0].stationId;
  const gameId = propState[0][2].matchingDataId;
  const [moles, setMoles] = useState(MOLE_INITIAL_STATE);
  const [score, setScore] = useState(0);
  const [timeLeft, setTimeLeft] = useState(200);
  const navigate = useNavigate();

  useEffect(() => {
    if (timeLeft > 0) {
      const timerId = setTimeout(() => {
        setTimeLeft(timeLeft - 1);
      }, 100);
      return () => clearTimeout(timerId);
    }
    if (timeLeft === 0) {
      // stomClient Send보내기
      // const result = {
      //   result: score,
      // };
      // const message = JSON.stringify(result);
      // stompClient.send(
      //   // `/stations/${stationId}/minigame/${gameId}/progress`,
      //   {},
      //   message
      // );
      navigate("/pvp/result", { state: propState });
    }
  }, [timeLeft, score, stompClient]);

  function handleMoleClick(index, event) {
    console.log(event);
    console.log(event.pageX);
    console.log(event.pageY);

    if (moles[index]) {
      setScore(score + 1);
      getCoinEffect(event.pageX, event.pageY);
    }
  }

  useEffect(() => {
    const randomInterval = Math.floor(Math.random() * (900 - 200)) + 200;
    const createItem = setInterval(() => {
      const randomIndex = Math.floor(Math.random() * 9);
      setMoles(
        moles.map((mole, i) => {
          if (i === randomIndex && !mole) {
            return true;
          }
        })
      );
    }, randomInterval);

    return () => {
      clearInterval(createItem);
    };
  }, [moles]);

  function getCoinEffect(x, y) {
    const target = document.querySelector(".minigame-clicker-body");
    console.log(x, y);

    // + 1 이펙트를 넣을 div Tag 생성 및 속성 설정
    const getClickerScoreEffectObject = document.createElement("div");
    getClickerScoreEffectObject.classList.add("minigame-clicker-effect");
    getClickerScoreEffectObject.innerHTML = "+ 1";
    getClickerScoreEffectObject.style.top = y + "px";
    getClickerScoreEffectObject.style.left = x + "px";

    console.log(getClickerScoreEffectObject);
    // .subject-background 태그의 자식 태그로 추가
    target.appendChild(getClickerScoreEffectObject);

    // 애니메이션(1초)가 끝난 후, 해당 태그 제거
    setTimeout(() => target.removeChild(getClickerScoreEffectObject), 500);
  }

  return (
    <div className="minigame-clicker">
      <div className="minigame-clicker-header">
        <h1>지하철 빈자리 앉기!!</h1>
        <div className="minigame-clicker-timeleft">
          Time left: {timeLeft / 10}
        </div>
        <div className="minigame-clicker-score">Score: {score}</div>
      </div>
      <div className="minigame-clicker-body">
        {moles.map((mole, index) => (
          <div
            key={index}
            className="minigame-clicker-chair"
            onClick={(event) => handleMoleClick(index, event)}
          >
            {mole ? (
              <div />
            ) : (
              <img
                className="minigame-clicker-seatman"
                src={beam}
                alt="seatman"
              />
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
export default ClickerGame;
