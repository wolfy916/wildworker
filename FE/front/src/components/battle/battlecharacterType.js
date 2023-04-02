import * as React from "react";
import "./battlecharacterType.css";

import characterMan from "../../asset/image/stop_man.png";
import characterWoman from "../../asset/image/stop_woman.png";

export default function battleCharacterType(props) {
  // const characterType = props.CharacterType;
  //더미데이터
  const characterType = props.characterType;
  const TypeList = [characterMan, characterWoman];
  return (
    <div className="battleCharacter">
      <img src={TypeList[characterType]} alt="character" />
    </div>
  );
}
