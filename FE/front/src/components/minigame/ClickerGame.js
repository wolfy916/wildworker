import React, { useState, useEffect } from "react";
import "./ClickerGame.css";
// import blank from "../../asset/image/battleCharWinnerMan.png";
import beam from "../../asset/image/stop_man.png";

const MOLE_INITIAL_STATE = Array(9).fill(false);

function ClickerGame() {
  const [moles, setMoles] = useState(MOLE_INITIAL_STATE);
  const [score, setScore] = useState(0);
  const [timeLeft, setTimeLeft] = useState(100000);

  useEffect(() => {
    if (timeLeft > 0) {
      const timerId = setTimeout(() => {
        setTimeLeft(timeLeft - 1);
      }, 100);
      return () => clearTimeout(timerId);
    }
  }, [timeLeft]);

  function handleMoleClick(index, event) {
    console.log(event);
    console.log(event.pageX);
    console.log(event.pageY);
    // let elementTop = window.pageYOffset + event.getBoundingClientRect().top;
    // let elementLeft = window.pageXOffset + event.getBoundingClientRect().left;
    // console.log(elementTop, elementLeft);

    if (moles[index]) {
      setScore(score + 1);
      getCoinEffect(event.pageX, event.pageY);
    }
  }

  useEffect(() => {
    const randomInterval = Math.floor(Math.random() * (900 - 200)) + 200;
    const createItem = setInterval(() => {
      const randomIndex = Math.floor(Math.random() * 9);
      // const randomIndex1 = Math.floor(Math.random() * 9);
      setMoles(
        moles.map((mole, i) => {
          if (i === randomIndex && !mole) {
            // console.log("hi");
            return true;
          }
          // if (i === randomIndex1 && !mole) {
          //   // console.log("hi");
          //   return true;
          // }
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
    // 수집 아이템 이동 중 클릭하였을 때의 위치 좌표
    // let elementTop = window.pageYOffset + element.getBoundingClientRect().top;
    // let elementLeft = window.pageXOffset + element.getBoundingClientRect().left;

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
            onClick={event => handleMoleClick(index, event)}
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
