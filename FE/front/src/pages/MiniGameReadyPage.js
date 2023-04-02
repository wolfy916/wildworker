import * as React from "react";
import CalGameReady from "../components/minigame/CalGameReady";
import ClickerGameReady from "../components/minigame/ClickerGameReady";
import { useLocation } from "react-router-dom";
import "../pages/MiniGameReadyPage.css";

function MiniGameReadyPage(props) {
  const { state } = useLocation();
  // const gameType = props.gameStartData.gameType;
  //일단 더미로 gameType = 0
  const gameType = 1;
  //state 잘들어옴.
  console.log(state);

  return (
    <div className="minigame-ready">
      {
        {
          0: <CalGameReady state={state} />,
          1: <ClickerGameReady state={state} />,
        }[gameType]
      }
    </div>
  );
}

export default MiniGameReadyPage;
