import * as React from "react";
import Box from "@mui/material/Box";
import "./battleCharWinner.css";

import character from "../../asset/image/fightingMan.png";

export default function BattleTalk() {
  return (
    <div className="battle-char-winner">
      <img src={character} alt="character" />
    </div>
  );
}
