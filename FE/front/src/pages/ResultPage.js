import * as React from "react"
import Box from "@mui/material/Box"
import BattleCharater from "../components/battle/battlecharacter"
import BattleCharLoser from "../components/battle/battleCharLoser"
import battleDialogImg from "../asset/image/battleTalk.png"
import { useNavigate } from "react-router-dom"
import BattleCharWinner from "../components/battle/battleCharWinner"
import "./ResultPage.css"

function MainPage(props) {
  const gameResultData = props.gameResultData

  const navigate = useNavigate()

  function handleTouchStart(event) {
    // 다음 페이지로 이동하는 로직을 작성합니다.
    navigate("/pvp/receipt")
    console.log("go to receiptPage")
  }
  return (
    <Box className="Pvp-Result" sx={{ position: "relative" }}>
      <div className="battle-result-1">
        <p className="battle-result-p1">
          신도림의 지배자 <br /> 권태형
        </p>
        <div className="battle-result-char">
          <BattleCharWinner />
        </div>
        <p className="battle-result-p2">승리</p>
        <p className="battle-result-p3">맞춘 개수: 12개</p>
      </div>

      <div className="battle-result-2">
        <p className="battle-result2-p1">
          신도림의 지배자
          <br /> 권태형
        </p>
        <div className="battle-result2-char">
          <BattleCharLoser />
        </div>
        <p className="battle-result2-p2">패배</p>
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
    </Box>
  )
}

export default MainPage
