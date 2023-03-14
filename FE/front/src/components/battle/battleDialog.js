import * as React from "react";
import Box from "@mui/material/Box";
import "./battleDialog.css";
// import BattleDialogTalk from "./battleDialogTalk";
import { useState, useEffect } from "react";
import battleDialogImg from "../../asset/image/battleTalk.png";
import battleAudio from "../../asset/audio/battleAudioBack.mp3";

export default function BattleDialog() {
  const sentences = [
    "신도림의 지배자 권태형이 나타났다!!!",
    "강한 기운이 느껴진다...",
    "결투하기: -20원   도망치기: -5원",
    "...",
    "결투가 곧 시작될 것 같다!!!",
  ];
  const [index, setIndex] = useState(0);
  const [audioChange, setAudioChange] = useState("");

  function handleClick() {
    setIndex(index + 1);
  }
  let audio = new Audio(battleAudio);
  function audioplay() {
    audio.play().catch(e => {
      console.log(e);
    });
    console.log("dd");
  }

  useEffect(() => {
    console.log("dd");
  }, []);

  return (
    <Box className="battleDialog">
      <img src={battleDialogImg} alt="battleDialogImg" />
      {/* <BattleDialogTalk /> */}
      {/* <p>뭐 임마</p> */}
      <div onClick={handleClick} className="battleDialog-talk">
        <p>{sentences[index]}</p>
      </div>

      <button onClick={audioplay} className="battleAudio">
        결투 소리 재생 임시버튼
      </button>
    </Box>
  );
}
