import * as React from "react";
import Box from "@mui/material/Box";
import "./battleDialog.css";
// import BattleDialogTalk from "./battleDialogTalk";
import { useState } from "react";
import battleDialogImg from "../../asset/image/battleTalk.png";

export default function BattleDialog() {
  const sentences = [
    "신도림의 지배자 권태형이 나타났다!!!",
    "강한 기운이 느껴진다...",
    "결투하기: -20원   도망치기: -5원",
    "...",
    "결투가 곧 시작될 것 같다!!!",
  ];
  const [index, setIndex] = useState(0);

  function handleClick() {
    setIndex(index + 1);
  }
  return (
    <Box className="battleDialog">
      <img src={battleDialogImg} alt="battleDialogImg" />
      {/* <BattleDialogTalk /> */}
      {/* <p>뭐 임마</p> */}
      <div onClick={handleClick} className="battleDialog-talk">
        <p>{sentences[index]}</p>
      </div>
    </Box>
  );
}
