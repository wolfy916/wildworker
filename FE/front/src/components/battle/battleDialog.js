import * as React from "react";
import Box from "@mui/material/Box";
import "./battleDialog.css";

// import BattleDialogTalk from "./battleDialogTalk";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import battleDialogImg from "../../asset/image/battleTalk.png";

export default function BattleDialog(props) {
  const stompClient = props.stompClient;
  const matchingData = props.matchingData;
  const gameCancelData = props.gameCancelData;
  const gameStartData = props.gameStartData;
  const gameResultData = props.gameResultData;
  const userData = props.userData;
  const stationId = props.stationId;
  // const sentences = [
  //   "신도림의 지배자 권태형이 나타났다!!!",
  //   "강한 기운이 느껴진다...",
  //   "결투하기: -20원   도망치기: -5원",
  //   "...",
  //   "결투가 곧 시작될 것 같다!!!",
  // ];
  const sentences = [index1, index2, index3, index4, index5, index6, index7];
  const navigate = useNavigate();
  const [index, setIndex] = useState(0);
  const [msg, setmsg] = useState("");
  const [msgSecond, setmsgSecond] = useState("");
  const [loadingDot, setloadingDot] = useState("");
  const dotList = ["...", "..", "."];

  function index1() {
    console.log("나타났다.");
    setmsg("신도림의" + "지배자 " + "권태형ddddddddddd이 나타났다.");
    // setmsg(
    //   matchingData.enemy.title + matchingData.enemy.name + " 이 나타났다."
    // );
  }
  function index2() {
    console.log("나타났다.1");
    // setmsg("결투하기: -" + matchingData.cost +"원");
    // setmsgSecond("도망가기: -" + matchingData.runCost +"원");
    setmsg("결투하기: -20원");
    setmsgSecond("도망가기: -5원");
    //여기에서 도망 및 결투 하면 될듯 todo
  }
  //결투와 도망에만 쓰이는 클릭버튼
  function msgClick() {
    if (index === 1) {
      console.log("ㅋㅋㅋㅋㅋㅋ결투햅보던가");
      //결투하기 연결
      //역주소랑 게임아이디 받아야 함.
      setIndex(index + 1);
      const isDual = {
        isDual: true,
      };
      const message = JSON.stringify(isDual);
      stompClient.send(
        `/stations/${stationId}/minigame/${matchingData.id}/select`,
        {},
        message
      );
    }
  }
  function msgSecondClick() {
    if (index === 1) {
      console.log("ㅋㅋㅋㅋㅋㅋ도망쳐보던가");
      //도망치기 연결
      const isDual = {
        isDual: false,
      };
      const message = JSON.stringify(isDual);
      stompClient.send(
        `/stations/${stationId}/minigame/${matchingData.id}/select`,
        {},
        message
      );
      setIndex(index + 1);
    }
  }

  function index3() {
    console.log("나타났다.2");
    setmsg("");
    setmsgSecond("");
    //임시로 다음페이지로 mvp는 자동으로 넘어가게...
    // setIndex(index + 1);
  }

  function index4() {
    console.log("나타났다.2");
    setmsg("결투가 곧 시작될 것 같다!!");
    setmsgSecond("");
    //임시로 다음페이지로
    // setIndex(index + 1);
  }
  function index5() {
    setmsg("도망에 실패했다.. 게임비도 지불하고 결투에 참가해야 한다..");
  }
  function index6() {
    setmsg("도망에 성공했다!!");
    //main으로 navigate
  }
  function index7() {
    navigate("/pvp/ready");
  }

  function handleClick() {
    if (index !== 1) {
      setIndex(index + 1);
    }
  }

  useEffect(() => {
    console.log(index);
    sentences[index]();
    if (index === 3) {
      // 결투 누르면 밑의 함수 호출
      // handleSelectGame({isDuel:true})
      // 도망 누르면 밑의 함수 호출
      // handleSelectGame({isDuel:false})
    } else if (index === 10) {
      navigate("/pvp/ready", { matchingData: matchingData });
    }
  }, [index]);

  //게임 타이머
  //시간 5~7초 정도 주고 연결 여부판단 및 게임 아이디 받아서 진행 -todo
  //받는 동안에는 ...으로 표시 -todo
  const [timeLeft, setTimeLeft] = useState(1000);

  useEffect(() => {
    const interval = setInterval(() => {
      setTimeLeft(prevTimeLeft => prevTimeLeft - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, []);
  useEffect(() => {
    if (timeLeft === 0) {
      navigate("/pvp/minigame");
      //게임 진행 선택 - 싸우기 socket연결
    }
    if (index === 2) {
      setloadingDot(dotList[timeLeft % 3]);
      setmsg(loadingDot);
    }
    //여기서 소켓통신해서
  }, [timeLeft, navigate]);

  return (
    <div className="battleDialog">
      <img src={battleDialogImg} alt="battleDialogImg" />
      <div className="battleDialog-talk" onClick={handleClick}>
        {/* <div>{sentences[index]}</div> */}
        <p onClick={msgClick}>{msg}</p>
        <p onClick={msgSecondClick}>{msgSecond}</p>
      </div>
      <div className="battle-dialog-timer">
        <div className="battle-dialog-timer-img"></div>
        <p className="battle-dialog-timer-count">{timeLeft}</p>
      </div>
    </div>
  );
}
