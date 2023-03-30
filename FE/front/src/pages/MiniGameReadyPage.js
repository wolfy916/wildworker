import * as React from "react";
import CalGameReady from "../components/minigame/CalGameReady";
import ClickerGameReady from "../components/minigame/ClickerGameReady";
import "../pages/MiniGameReadyPage.css";

function MiniGameReadyPage() {
  return (
    <div className="minigame-ready">
      {/* <CalGameReady /> */}
      <ClickerGameReady />
    </div>
  );
}

export default MiniGameReadyPage;
