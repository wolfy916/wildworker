import * as React from "react";
import Box from "@mui/material/Box";
import BattleDialog from "../components/battle/battleDialog";
import BattleCharater from "../components/battle/battlecharacter";

import "./PvpPage.css";

function MainPage() {
  return (
    <Box className="PvpPageBg" sx={{ position: "relative" }}>
      <div className="battleCharacter1">
        <BattleCharater />
        <p>신도림의 지배자 권태형</p>
      </div>
      <div className="battleCharacter2">
        <BattleCharater />
        <p>신도림의 지배자 권태형</p>
      </div>

      <BattleDialog />
    </Box>
  );
}

export default MainPage;
