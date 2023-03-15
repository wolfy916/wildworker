import React, { useState } from "react";
import "../pages/MiniGamePage.css";

function CalculationGame() {
  const [num1, setNum1] = useState(Math.floor(Math.random() * 100));
  const [num2, setNum2] = useState(Math.floor(Math.random() * 100));
  const [answer, setAnswer] = useState("");
  const [score, setScore] = useState(0);

  const handleAnswerChange = event => {
    setAnswer(event.target.value);
  };

  const handleSubmit = event => {
    event.preventDefault();
    const correctAnswer = num1 + num2;
    if (parseInt(answer) === correctAnswer) {
      setScore(score + 1);
    }
    setNum1(Math.floor(Math.random() * 100));
    setNum2(Math.floor(Math.random() * 100));
    setAnswer("");
  };

  return (
    <div className="MiniGame">
      <h1 className="MiniGame-Headline">덧셈 게임</h1>
      <p>Score: {score}</p>
      <form onSubmit={handleSubmit}>
        <p>
          What is {num1} + {num2}?
        </p>
        <div className="MiniGame-Numberselect"></div>
        <input type="number" value={answer} onChange={handleAnswerChange} />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default CalculationGame;
