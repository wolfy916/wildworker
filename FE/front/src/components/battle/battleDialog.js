import * as React from "react";
import Box from "@mui/material/Box";
import "./battleDialog.css";


import Stomp from "stompjs"
import SockJS from "sockjs-client"
// import BattleDialogTalk from "./battleDialogTalk";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
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
  const navigate = useNavigate();
  const [index, setIndex] = useState(0);
  const [audioChange, setAudioChange] = useState("");
  const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws")
  const stompClient = Stomp.over(socket)

  function handleClick() {
    setIndex(index + 1);
  }

  // 게임 진행 선택! (결투 or 도망)
  const handleSelectGame = (e) => {
    const message = JSON.stringify(e)
    stompClient.send("/stations/{station-id}/minigame/{game-id}/select", {}, message)
  }

  useEffect(() => {
    if (index === 3) {
      // 결투 누르면 밑의 함수 호출
      // handleSelectGame({isDuel:true})
      // 도망 누르면 밑의 함수 호출
      // handleSelectGame({isDuel:false})
    }
    else if (index === 5) {
      navigate("/pvp/ready");
    }
  }, [index]);
  let audio = new Audio(battleAudio);
  function audioplay() {
    audio.play().catch(e => {
      console.log(e);
    });
    console.log("dd");
  }

  return (
    <Box className="battleDialog">
      <img onClick={handleClick} src={battleDialogImg} alt="battleDialogImg" />
      {/* <BattleDialogTalk /> */}
      {/* <p>뭐 임마</p> */}
      <div className="battleDialog-talk">
        <p>{sentences[index]}</p>
      </div>

      <button onClick={audioplay} className="battleAudio">
        결투 소리 재생 임시버튼
      </button>
    </Box>
  );
}
