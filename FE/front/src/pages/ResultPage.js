import * as React from "react";
import Box from "@mui/material/Box";
// import BattleCharater from "../components/battle/battlecharacterType";
import BattleCharLoser from "../components/battle/battleCharLoser";
import battleDialogImg from "../asset/image/battleTalk.png";
import { useNavigate } from "react-router-dom";
import BattleCharWinner from "../components/battle/battleCharWinner";
import "./ResultPage.css";

function ResultPage(props) {
  const gameResultData = props.gameResultData;
  const userData = props.userData;

  const navigate = useNavigate();

  function handleTouchStart(event) {
    // 다음 페이지로 이동하는 로직을 작성합니다.
    navigate("/pvp/receipt");
    console.log("go to receiptPage");
  }
  return (
    <div className="Pvp-Result">
      <div className="battle-result-1">
        {/* 본인 */}
        <p className="battle-result-p1">
          {userData.titleId} <br /> {userData.name}
        </p>
        <div className="battle-result-char">
          {/* 삼항연산자로 true=winner false=loser */}
          <BattleCharWinner />
        </div>
        {/* 승리했을때 패배했을때 컴포넌트로 분리하기 */}
        <p className="battle-result-p2">승리</p>
        <p className="battle-result-p3">맞춘 개수: 12개</p>
        {/* <p className="battle-result-p3">맞춘 개수: {gameResultData.result.me}개</p> */}
      </div>

      <div className="battle-result-2">
        {/* 적 */}
        {/* <p className="battle-result2-p1">
          {gameResultData.enemy.title}
          <br /> {gameResultData.enemy.name}
        </p> */}
        <p className="battle-result2-p1">
          신도림의 지배자
          <br /> 권태형
        </p>
        <div className="battle-result2-char">
          <BattleCharLoser />
        </div>
        <p className="battle-result2-p2">패배</p>
        {/* <p className="battle-result-p3">
          맞춘 개수: {gameResultData.result.enemy}개
        </p> */}
        <p className="battle-result2-p3">맞춘 개수: 10개</p>
      </div>

      <div className="battleResult-talk">
        <img
          onTouchStart={handleTouchStart}
          src={battleDialogImg}
          alt="battleDialogImg"
        />
        <p className="battleResult-talk-p">결투에서 승리했다!!!!</p>
      </div>
    </div>
  );
}

export default ResultPage;
