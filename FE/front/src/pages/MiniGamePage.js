import React, { useState } from "react";
import "../pages/MiniGamePage.css";

function CalculationGame() {
  const [num1, setNum1] = useState(Math.floor(Math.random() * 100));
  const [num2, setNum2] = useState(Math.floor(Math.random() * 100));
  const [score, setScore] = useState(0);
  const [value, setValue] = useState("");

  const handleSubmit = event => {
    event.preventDefault();
    const correctAnswer = num1 + num2;
    if (parseInt(value) === correctAnswer) {
      setScore(score + 1);
    }
    setNum1(Math.floor(Math.random() * 100));
    setNum2(Math.floor(Math.random() * 100));
    setValue("");
  };

  return (
    <div className="miniGame">
      <h1 className="miniGame-Headline">덧셈 게임</h1>
      <div className="miniGame-score">
        <p>Score: {score}</p>
        <p>
          {num1} + {num2} = {value}
        </p>
      </div>
      {/* <p>You selected: {value}</p> */}
      <div className="miniGame-select">
        <div className="miniGame-select-number">
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "1")}
          >
            1
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "2")}
          >
            2
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "3")}
          >
            3
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "4")}
          >
            4
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "5")}
          >
            5
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "6")}
          >
            6
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "7")}
          >
            7
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "8")}
          >
            8
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "9")}
          >
            9
          </button>
          <button
            className="miniGame-btn"
            onClick={() => setValue(value + "0")}
          >
            0
          </button>
          {/* <button className="miniGame-btn" onClick={() => setValue("")}>
          clear
        </button> */}
        </div>
        <form onSubmit={handleSubmit} className="miniGame-submit">
          <button className="miniGame-submit-btn" type="submit">
            확인
          </button>
        </form>
      </div>
      {/* <div className="minigame-pixelart-crab"></div> */}
      <div className="minigame-pixelart-metamong"></div>
    </div>
  );
}

export default CalculationGame;
