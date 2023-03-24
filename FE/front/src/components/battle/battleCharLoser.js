import * as React from "react";
import "./battleCharLoser.css";

import character from "../../asset/image/battleCharLoser.png";

export default function BattleTalk() {
  return (
    <div className="battle-char-loser">
      <img src={character} alt="character" />
    </div>
  );
}
