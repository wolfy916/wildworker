import * as React from "react";
import BattleCharLoser from "../components/battle/battleCharLoser";
import battleDialogImg from "../asset/image/battleTalk.png";
import { useNavigate, useLocation } from "react-router-dom";
import BattleCharWinner from "../components/battle/battleCharWinner";
import "./ResultPage.css";

function ResultPage(props) {
  // const gameResultData = props.gameResultData;
  const gameResultData = {
    isWinner: true,
    enemy: {
      name: "권태형",
      title: "신도림의 지배자",
      characterType: 1,
    },
    result: {
      me: 12,
      enemy: 11,
    },
    receipt: {
      cost: -20,
      runCost: 0,
      reward: 40,
      commission: -2,
      total: 18,
    },
  };
  //state에 userData 들어있음.
  const { state } = useLocation();
  const myCharType = state[0][1].characterType;
  const isWinner = gameResultData.isWinner;

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
          {state[0][1].title} <br /> {state[0][1].name}
        </p>
        <div className="battle-result-char">
          {/* 승리 패배시 컴포넌트 분리 */}
          {
            {
              true: <BattleCharWinner characterType={myCharType} />,
              false: <BattleCharLoser characterType={myCharType} />,
            }[isWinner]
          }
        </div>
        {/* 승리했을때 패배했을때 분리하기 */}
        {
          {
            true: <p className="battle-result-win-p2">승리</p>,
            false: <p className="battle-result-lose-p2">패배</p>,
          }[isWinner]
        }
        <div className="battle-result-p3">
          맞춘 개수: {gameResultData.result.me}개
        </div>
      </div>

      <div className="battle-result-2">
        {/* 적 */}
        <p className="battle-result2-p1">
          {gameResultData.enemy.title}
          <br /> {gameResultData.enemy.name}
        </p>
        <div className="battle-result2-char">
          {/* 승리 패배시 분리 */}
          {
            {
              false: (
                <BattleCharWinner
                  characterType={gameResultData.enemy.characterType}
                />
              ),
              true: (
                <BattleCharLoser
                  characterType={gameResultData.enemy.characterType}
                />
              ),
            }[isWinner]
          }
        </div>

        {/* 승리 패배시 분리 */}
        {
          {
            false: <p className="battle-result2-win-p2">승리</p>,
            true: <p className="battle-result2-lose-p2">패배</p>,
          }[isWinner]
        }
        <div className="battle-result2-p3">
          맞춘 개수: {gameResultData.result.enemy}개
        </div>
      </div>

      <div className="battleResult-talk">
        <img
          onTouchStart={handleTouchStart}
          src={battleDialogImg}
          alt="battleDialogImg"
        />
        {/* 승리했을때 패배했을때 컴포넌트로 분리하기 */}
        {
          {
            true: <p className="battleResult-talk-p">결투에서 승리했다!!!!</p>,
            false: <p className="battleResult-talk-p">결투에서 패배했다...</p>,
          }[isWinner]
        }
      </div>
    </div>
  );
}

export default ResultPage;
