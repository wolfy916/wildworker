import React, { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import "../pages/MiniGamePage.css"
import Stomp from "stompjs"
import SockJS from "sockjs-client"

function CalculationGame() {
  const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws")
  const stompClient = Stomp.over(socket)
  const [num1, setNum1] = useState(
    String(Math.floor(Math.random() * 100)) + "00"
  );
  const [num2, setNum2] = useState(
    String(Math.floor(Math.random() * 100)) + "00"
  );
  const [score, setScore] = useState(0);
  const [value, setValue] = useState("");

  function handleSubmit(event) {
    event.preventDefault();
    const correctAnswer = parseInt(num1) + parseInt(num2);
    if (parseInt(value) === correctAnswer) {
      setScore(score + 1);
      setNum1(value);
    }
    // setNum1(value);
    setNum2(String(Math.floor(Math.random() * 100) + "00"));
    setValue("");
  }

  const [timeLeft, setTimeLeft] = useState(5000);
  const navigate = useNavigate();

  // 미니 게임끝났을 때, 결과 값 백한테 주기
  const handleFinishGame = (e) => {
    const message = JSON.stringify(e)
    stompClient.send("/stations/{station-id}/minigame/{game-id}/progress", {}, message)
  }

  useEffect(() => {
    const interval = setInterval(() => {
      setTimeLeft((prevTimeLeft) => prevTimeLeft - 1)
    }, 1000)

    return () => clearInterval(interval)
  }, [])
  useEffect(() => {
    if (timeLeft === 0) {
      // handleFinishGame({result:'맞춘갯수'})
      navigate("/pvp/result")
    }
  }, [timeLeft, navigate])

  return (
    <div className="minigame">
      <h1 className="minigame-Headline">회식비 정산하기!</h1>
      <div className="minigame-cal-timer">{timeLeft}</div>
      <div className="minigame-score-board">
        <p>맞힌 갯수: {score}</p>
        <div className="minigame-cal-currentmoney">
          <div>현재 정산금:{num1}원</div>

          <div>+ 짜장면: {num2}원</div>
        </div>
        <div className="minigame-cal-money-value">= {value}원</div>
      </div>
      {/* <p>You selected: {value}</p> */}
      <div className="minigame-select">
        <div className="minigame-select-number">
          {["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"].map((a, i) => {
            return (
              <button
                className="minigame-btn"
                key={a}
                onClick={() => setValue(value + a)}
              >
                {a}
              </button>
            )
          })}

          {/* <button className="minigame-btn" onClick={() => setValue("")}>
          clear
        </button> */}
        </div>
        <form onSubmit={handleSubmit} className="minigame-submit">
          <button className="minigame-submit-btn" type="submit">
            확인
          </button>
        </form>
      </div>
      {/* <div className="minigame-pixelart-crab"></div> */}
      <div className="minigame-pixelart-metamong"></div>
    </div>
  )
}

export default CalculationGame
