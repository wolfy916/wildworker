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
  const userData = props.userData;
  //userdata 받기
  // const userData = props.

  return (
    <div className="PvpPageBg">
      <div className="battleCharacter1">
        {/* 상대편 */}
        <img src={battleDirection} alt="battleDirection" />
        {/* <BattleCharater characterType={matchingData.enemy.characterType} /> */}
        <BattleCharater type={1} />
        {/* <p>
          {matchingData.enemy.title} {matchingData.enemy.data}{" "}
        </p> */}
        <p>신도림의 지배자 권태형</p>
      </div>
      <div className="battleCharacter2">
        {/* user 본인 */}
        <img src={battleDirection} alt="battleDirection" />
        {/* <BattleCharater type={userData.CharcterType} /> */}
        <BattleCharater type={0} />
        {/* title type, id 있는데 어떻게 받아야 할지 프론트 논의필요 */}
        {/* <p>
          {userData.titleId} {userData.name}
        </p> */}
        <p>신도림의 지배자 권태형</p>
      </div>

      <BattleDialog
        stompClient={stompClient}
        matchingData={matchingData}
        gameCancelData={gameCancelData}
        gameStartData={gameStartData}
        //result아직 app.js없음
        // gameResultData={gameResultData}
        //본인정보 받기
        userData={userData}
        //현재 역id만 받기
        // stationId = {stationId}
      />
    </div>
  );
}

export default PvpPage;
