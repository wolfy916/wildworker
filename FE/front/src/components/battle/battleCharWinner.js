import * as React from "react";
import "./battleCharWinner.css";

import WinnerMan from "../../asset/image/battleCharWinnerMan.png";
import WinnerWoman from "../../asset/image/battleCharWinnerWoman.png";

export default function BattleTalk(props) {
  // const characterType = props.CharacterType;
  //더미데이터
  const characterType = 0;
  const TypeList = [WinnerMan, WinnerWoman];
  return (
    <div className="battle-char-winner">
      <img src={TypeList[characterType]} alt="character" />
    </div>
  );
}
