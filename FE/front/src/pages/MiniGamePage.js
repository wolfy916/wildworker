import "../pages/MiniGamePage.css";
import ClickerGame from "../components/minigame/ClickerGame";
import CalGame from "../components/minigame/CalGame";

function CalculationGame() {
  return (
    <div className="minigame">
      <ClickerGame />
      {/* <CalGame /> */}
    </div>
  );
}

export default CalculationGame;
