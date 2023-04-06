import * as React from "react";
import BattleDialog from "../components/battle/battleDialog";
import BattleCharater from "../components/battle/battlecharacterType";
import battleDirection from "../asset/image/battleDirection.png";
import "./PvpPage.css";

function PvpPage(props) {
  const matchingData = props.matchingData;
  // console.log(matchingData);
  // const matchingData = {
  //   id: 1,
  //   cost: 20,
  //   runCost: 5,
  //   enemy: {
  //     name: "권태형",
  //     title: "신도림의 지배자",
  //     characterType: 1,
  //   },
  //   timeLimit: 5,
  // };
  const gameRunData = props.gameRunData;
  const gameStartData = props.gameStartData;
  const stompClient = props.stompClient;
  //userdata 받기
  const userData = props.userData;
  // console.log(userData);
  // const userData = {
  //   name: "지원석",
  //   characterType: 0,
  //   titleType: 1,
  //   titleId: 1,
  //   title: "사당역의 지배자",
  //   coin: 123,
  //   collectedPapers: 1,
  // };

  return (
    <div className="PvpPageBg">
      <div className="battleCharacter1">
        {/* 상대편 */}
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater characterType={matchingData.enemy.characterType} />
        <p>
          {matchingData.enemy.title === "x" ? "" : matchingData.enemy.title}{" "}
          <br />
          {matchingData.enemy.name}{" "}
        </p>
      </div>
      <div className="battleCharacter2">
        {/* user 본인 */}
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater characterType={userData.characterType} />
        {/* title type, id 있는데 어떻게 받아야 할지 프론트 논의필요 */}
        <p>
          {userData.title.name}
          <br /> {userData.name}
        </p>
      </div>

      <BattleDialog
        stompClient={stompClient}
        matchingData={matchingData}
        gameCancelData={gameRunData}
        gameStartData={gameStartData}
        //본인정보 받기
        userData={userData}
      />
    </div>
  );
}

export default PvpPage;
