import * as React from "react";
import Box from "@mui/material/Box";
import BattleCharater from "../components/battle/battlecharacter";
import battleDialogImg from "../asset/image/battleTalk.png";

import "./ResultPage.css";

function MainPage() {
  return (
    <Box className="Pvp-Result" sx={{ position: "relative" }}>
      <div className="battle-result-1">
        <p className="battle-result-p1">신도림의 지배자 권태형</p>
        <div className="battle-result-char">
          <BattleCharater />
        </div>
        <p className="battle-result-p2">승리</p>
        <p className="battle-result-p3">맞춘 개수: 12개</p>
      </div>

      <div className="battle-result-2">
        <p className="battle-result2-p1">신도림의 지배자 권태형</p>
        <div className="battle-result2-char">
          <BattleCharater />
        </div>
        <p className="battle-result2-p2">패배</p>
        <p className="battle-result2-p3">맞춘 개수: 10개</p>
      </div>

      <div className="battleResult-talk">
        <img src={battleDialogImg} alt="battleDialogImg" />
        <p className="battleResult-talk-p">결투에서 승리했다!!!!</p>
      </div>
    </Box>
  );
}

export default MainPage;
