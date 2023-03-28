import React, { useState, useEffect } from "react";
import blank from "../../asset/image/black.png";
import beam from "../../asset/image/tomato.png";

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

  function handleMoleClick(index) {
    if (moles[index]) {
      setScore(score + 1);
      setMoles(moles.map((mole, i) => (i === index ? false : mole)));
    }
  }

  function startNewGame() {
    setScore(0);
    setTimeLeft(100000);
    setMoles(MOLE_INITIAL_STATE);
  }

  useEffect(() => {
    const timerId = setInterval(() => {
      const randomIndex = Math.floor(Math.random() * 9);
      setMoles(moles.map((mole, i) => (i === randomIndex ? true : mole)));
    }, 100);
    return () => clearInterval(timerId);
  }, [moles]);

  return (
    <div className="container">
      <h1>Mole Game</h1>
      <p>Score: {score}</p>
      <p>Time left: {timeLeft}</p>
      <div className="board">
        {moles.map((mole, index) => (
          <div
            key={index}
            className="hole"
            onClick={() => handleMoleClick(index)}
          >
            {mole ? (
              <img src={blank} alt="blank" />
            ) : (
              <img src={beam} alt="blank" />
            )}
          </div>
        ))}
      </div>
      {/* {timeLeft === 0 && (
        <div>
          <p>Game over!</p>
          <button onClick={startNewGame}>Start new game</button>
        </div>
      )} */}
    </div>
  );
}
export default ClickerGame;
