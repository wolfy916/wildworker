import * as React from "react";
import BattleDialog from "../components/battle/battleDialog";
import BattleCharater from "../components/battle/battlecharacterType";
import battleDirection from "../asset/image/battleDirection.png";
import "./PvpPage.css";

function PvpPage(props) {
  const matchingData = props.matchingData;
  const gameCancelData = props.gameCancelData;
  const gameStartData = props.gameStartData;
  const stompClient = props.stompClient;

  //getUserInfo를 useEffect로 받아서 내 캐릭터에 props로 내려주기.
  return (
    <div className="PvpPageBg">
      <div className="battleCharacter1">
        {/* 상대편 */}
        <img src={battleDirection} alt="battleDirection" />
        {/* <BattleCharater characterType={matchingData.enemy.characterType} /> */}
        <BattleCharater type={1} />
        <p>신도림의 지배자 권태형</p>
        {/* <p>
          {matchingData.enemy.title} {matchingData.enemy.data}{" "}
        </p> */}
      </div>
      <div className="battleCharacter2">
        {/* user 본인 */}
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater type={0} />
        <p>신도림의 지배자 권태형</p>
      </div>

      <BattleDialog
        stompClient={stompClient}
        matchingData={matchingData}
        gameCancelData={gameCancelData}
        gameStartData={gameStartData}
      />
    </div>
  );
}

export default PvpPage;
