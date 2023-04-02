import * as React from "react";
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
  const sentences = [
    matchingDialog,
    selectStartOrCancel,
    loadBattle,
    loadCancel,
    startGame,
    failCancel,
    successCancel,
  ];
  // const sentences = [index1, index2, index3, index4];
  const navigate = useNavigate();
  const [sentenceIndex, setSentenceIndex] = useState(0);
  const [msg, setmsg] = useState("");
  const [msgCancel, setmsgCancel] = useState("");
  const [loadingDot, setloadingDot] = useState("");
  const dotList = [".", "...", ".."];
  const [nextClock, setNextClock] = useState(true);
  const [gameStartClock, setGameStartClock] = useState(true);

  function matchingDialog() {
    console.log("나타났다.");
    setmsg("신도림의" + "지배자 " + "권태형이 나타났다.");
    //데이터 연결 잘되는지 확인용
    // setmsg(`${userData.name}이 나타났다.!!`);
    // setmsg(
    //   matchingData.enemy.title + matchingData.enemy.name + " 이 나타났다."
    // );
  }
  function selectStartOrCancel() {
    console.log("나타났다.1");
    // setmsg("결투하기: -" + matchingData.cost +"원");
    // setmsgSecond("도망가기: -" + matchingData.runCost +"원");
    setmsg("결투하기: -20원");
    setmsgCancel("도망가기: -5원");
    //여기에서 도망 및 결투 하면 될듯 todo
  }
  //결투와 도망에만 쓰이는 클릭버튼
  function msgClick() {
    if (sentenceIndex === 1) {
      //결투하기 연결
      //역주소랑 게임아이디 받아야 함.
      setSentenceIndex(2);
      console.log("결투하기 연결");
      // const isDual = {
      //   isDual: true,
      // };
      // const message = JSON.stringify(isDual);
      // stompClient.send(
      //   `/stations/${stationId}/minigame/${matchingData.id}/select`,
      //   {},
      //   message
      // );
    }
  }
  function cancelClick() {
    if (sentenceIndex === 1) {
      //도망치기 연결
      console.log("도망치기 연결");
      setSentenceIndex(3);
      console.log(sentenceIndex);
      // const isDual = {
      //   isDual: false,
      // };
      // const message = JSON.stringify(isDual);
      // stompClient.send(
      //   `/stations/${stationId}/minigame/${matchingData.id}/select`,
      //   {},
      //   message
      // );
    }
  }

  function loadBattle() {
    console.log("결투를 위한 로딩");
    setmsg("");
    setmsgCancel("");
  }

  function loadCancel() {
    console.log("도망을 위한 로딩");
    setmsg("");
    setmsgCancel("");
  }
  //로딩이후 결투, 도망, 도망실패에 대한 분기를 3초간 띄워주고 다음으로 넘어감
  const [gametimeLeft, setGameTimeLeft] = useState(3);
  function leftGameTime() {
    const interval = setInterval(() => {
      setGameTimeLeft(prevTimeLeft => prevTimeLeft - 1);
    }, 1000);
    return () => clearInterval(interval);
  }
  useEffect(() => {
    if (gametimeLeft === 0) {
      if (sentenceIndex === 4) {
        //결투시작
        console.log("결투시작");
        navigate("ready");
      }
      //
      if (sentenceIndex === 5) {
        //도망실패
      }
      if (sentenceIndex === 6) {
        //도망성공
        // main stompClient Error로 일단 주석 처리
        navigate("/main");
        console.log("메인으로 이동!");
      }
    }
    //여기서 소켓통신해서
  }, [gametimeLeft]);

  function startGame() {
    console.log("나타났다.2");
    setmsg("결투가 곧 시작될 것 같다!!");
    setmsgCancel("");
    leftGameTime();
  }

  function failCancel() {
    setmsg("도망에 실패했다.. 게임비도 지불하고 결투에 참가해야 한다..");
    leftGameTime();
  }

  function successCancel() {
    setmsg("도망에 성공했다!!");
    leftGameTime();
  }

  //dialog를 클릭하면 sentenceIndex를 다음 값으로
  function handleClick() {
    if (sentenceIndex < 1) {
      setSentenceIndex(sentenceIndex + 1);
    }
  }

  useEffect(() => {
    console.log(sentenceIndex);
    sentences[sentenceIndex]();
  }, [sentenceIndex]);

  //게임 타이머
  //시간 5~7초 정도 주고 연결 여부판단 및 게임 아이디 받아서 진행 -todo
  //받는 동안에는 ...으로 표시 -todo
  const [timeLeft, setTimeLeft] = useState(8);

  //처음에 시간
  useEffect(() => {
    const interval = setInterval(() => {
      setTimeLeft(prevTimeLeft => prevTimeLeft - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    if (sentenceIndex === 2 || sentenceIndex === 3) {
      setloadingDot(dotList[timeLeft % 3]);
      setmsg(loadingDot);
    }
    if (timeLeft === 0 && sentenceIndex === 2) {
      setSentenceIndex(4);
      setGameStartClock(false);
    }
    if (timeLeft === 0 && (sentenceIndex === 0 || sentenceIndex === 1)) {
      //timeLeft는 결투 도망 분기를 위한 시간.
      //0이 되면 무조건 결투를 선택하게 한다.
      // const isDual = {
      //   isDual: true,
      // };
      // const message = JSON.stringify(isDual);
      // stompClient.send(
      //   `/stations/${stationId}/minigame/${matchingData.id}/select`,
      //   {},
      //   message
      // );
      console.log("첫번째 dialog에서 선택을 안해서 바로 결투");
      setSentenceIndex(4);
      setGameStartClock(false);
    }
    if (timeLeft === 0 && sentenceIndex === 3) {
      console.log("도망에 대한 결과 받기");
      setSentenceIndex(6);
      //도망여부 받기
      // console.log(gameCancelData);
      // if (gameCancelData.isRunner) {
      //   setSentenceIndex(6);
      // }
      // if (gameCancelData.isRunner === false) {
      //   setSentenceIndex(5);
      // }
      setGameStartClock(false);
    }

    //여기서 소켓통신해서
  }, [timeLeft, navigate]);

  return (
    <div className="battleDialog">
      <img src={battleDialogImg} alt="battleDialogImg" />
      <div className="battleDialog-talk" onClick={handleClick}>
        {/* <div>{sentences[index]}</div> */}
        <p className="battle-dialog-start" onClick={msgClick}>
          {msg}
        </p>
        <p className="battle-dialog-run" onClick={cancelClick}>
          {msgCancel}
        </p>
      </div>
      <div className="battle-dialog-timer">
        <div className="battle-dialog-timer-img"></div>
        <p className="battle-dialog-timer-count">
          {gameStartClock ? timeLeft : gametimeLeft}
        </p>
        {/* <p className="battle-dialog-timer-count">{RuntimeLeft}</p> */}
      </div>
    </div>
  );
}
