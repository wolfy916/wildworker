import React, { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import "../pages/MiniGamePage.css"
import Stomp from "stompjs"
import SockJS from "sockjs-client"

function CalculationGame() {
  const [num1, setNum1] = useState(Math.floor(Math.random() * 100))
  const [num2, setNum2] = useState(Math.floor(Math.random() * 100))
  const [score, setScore] = useState(0)
  const [value, setValue] = useState("")
  const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws")
  const stompClient = Stomp.over(socket)

  function handleSubmit(event) {
    event.preventDefault()
    const correctAnswer = num1 + num2
    if (parseInt(value) === correctAnswer) {
      setScore(score + 1)
    }
    setNum1(Math.floor(Math.random() * 100))
    setNum2(Math.floor(Math.random() * 100))
    setValue("")
  }

  const [timeLeft, setTimeLeft] = useState(15)
  const navigate = useNavigate()

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
      <h1 className="minigame-Headline">덧셈 게임</h1>
      <h1>Timer: {timeLeft} seconds</h1>
      <div className="minigame-score">
        <p>Score: {score}</p>
        <p>
          {num1} + {num2} = {value}
        </p>
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
