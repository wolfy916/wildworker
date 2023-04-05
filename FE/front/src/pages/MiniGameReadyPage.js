import * as React from "react";
import CalGameReady from "../components/minigame/CalGameReady";
import ClickerGameReady from "../components/minigame/ClickerGameReady";
import "../pages/MiniGameReadyPage.css";

function MiniGameReadyPage(props) {
  const gameType = props.gameStartData.gameType;
  console.log(gameType);
  //일단 더미로 gameType = 0
  // const gameType = 1;
  return (
    <div className="minigame-ready">
      {
        {
          0: <CalGameReady />,
          1: <ClickerGameReady />,
        }[gameType]
      }
    </div>
  );
}

export default MiniGameReadyPage;
