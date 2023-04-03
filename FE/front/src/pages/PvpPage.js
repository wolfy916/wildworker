import * as React from "react";
import BattleDialog from "../components/battle/battleDialog";
import BattleCharater from "../components/battle/battlecharacterType";
import battleDirection from "../asset/image/battleDirection.png";
import "./PvpPage.css";

function PvpPage(props) {
  // const matchingData = props.matchingData;
  const matchingData = {
    id: 1,
    cost: 20,
    runCost: 5,
    enemy: {
      name: "권태형",
      title: "신도림의 지배자",
      characterType: 1,
      relativeStrength: 0,
    },
    timeLimit: 5,
  };
  const gameCancelData = props.gameCancelData;
  const gameStartData = props.gameStartData;
  const stompClient = props.stompClient;
  // const userData = props.userData;
  //userdata 받기
  // const currentStation = props.currentLocationData;
  const currentStation = {
    id: 2,
    name: "역삼역",
    dominator: "현재 지배자 이름",
  };
  const userData = {
    name: "지원석",
    characterType: 0,
    titleType: 1,
    titleId: 1,
    title: "사당역의 지배자",
    coin: 123,
    collectedPapers: 1,
  };

  return (
    <div className="PvpPageBg">
      <div className="battleCharacter1">
        {/* 상대편 */}
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater characterType={matchingData.enemy.characterType} />
        <p>
          {matchingData.enemy.title} {matchingData.enemy.name}{" "}
        </p>
      </div>
      <div className="battleCharacter2">
        {/* user 본인 */}
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater characterType={userData.characterType} />
        {/* title type, id 있는데 어떻게 받아야 할지 프론트 논의필요 */}
        <p>
          {userData.title} {userData.name}
        </p>
      </div>

      <BattleDialog
        stompClient={stompClient}
        matchingData={matchingData}
        gameCancelData={gameCancelData}
        gameStartData={gameStartData}
        // gameResultData={gameResultData}
        //본인정보 받기
        userData={userData}
        //현재 역id만 받기
        //역 id는 계속 끌고가야함.
        stationId={currentStation.id}
      />
    </div>
  );
}

export default PvpPage;
