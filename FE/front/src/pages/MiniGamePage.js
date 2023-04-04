import "../pages/MiniGamePage.css";
import { useLocation } from "react-router-dom";
import ClickerGame from "../components/minigame/ClickerGame";
import CalGame from "../components/minigame/CalGame";

function MiniGame(props) {
  const stompClient = props.stompClient;
  //이곳에 역 id 게임 id
  const { state } = useLocation();
  // console.log(state);
  const gameType = state.gameType;

  return (
    <div className="minigame">
      {
        {
          0: <CalGame stompClient={stompClient} />,
          1: <ClickerGame stompClient={stompClient} />,
        }[gameType]
      }
    </div>
  );
}

export default MiniGame;
