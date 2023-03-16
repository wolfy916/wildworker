import React, { useState } from "react";
import "../pages/MiniGamePage.css";

function CalculationGame() {
  const [num1, setNum1] = useState(Math.floor(Math.random() * 100));
  const [num2, setNum2] = useState(Math.floor(Math.random() * 100));
  const [score, setScore] = useState(0);
  const [value, setValue] = useState("");

  function handleSubmit(event) {
    event.preventDefault();
    const correctAnswer = num1 + num2;
    if (parseInt(value) === correctAnswer) {
      setScore(score + 1);
    }
    setNum1(Math.floor(Math.random() * 100));
    setNum2(Math.floor(Math.random() * 100));
    setValue("");
  }

  return (
    <div className="minigame">
      <h1 className="minigame-Headline">덧셈 게임</h1>
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
            );
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
  );
}

export default CalculationGame;
