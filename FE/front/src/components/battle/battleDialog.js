import * as React from "react";
import "./battleDialog.css";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import battleDialogImg from "../../asset/image/battleTalk.png";

export default function BattleDialog(props) {
  const stompClient = props.stompClient;
  const matchingData = props.matchingData;
  // const gameCancelData = props.gameCancelData;
  // const gameStartData = props.gameStartData;
  const sentences = [
    matchingDialog,
    selectStartOrCancel,
    loadBattle,
    loadCancel,
    startGame,
    failCancel,
    successCancel,
    enemyRun,
  ];
  const navigate = useNavigate();
  const [sentenceIndex, setSentenceIndex] = useState(0);
  const [msg, setmsg] = useState("");
  const [msgCancel, setmsgCancel] = useState("");
  const [loadingDot, setloadingDot] = useState("");
  const dotList = [".", "...", ".."];
  const [gameStartClock, setGameStartClock] = useState(true);
  const [dialogNext, setDialogNext] = useState(true);

  //첫번째dialog를 클릭하면 sentenceIndex를 다음 값으로
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
  const [timeLeft, setTimeLeft] = useState(25);

  //처음에 시간 받아서 설정
  useEffect(() => {
    // setTimeLeft(matchingData.timeLimit);
    const interval = setInterval(() => {
      setTimeLeft((prevTimeLeft) => prevTimeLeft - 1);
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    //두번째,세번째 대화창에서 ... 설정
    if (sentenceIndex === 2 || sentenceIndex === 3) {
      setloadingDot(dotList[timeLeft % 3]);
      setmsg(loadingDot);
    }
    if (timeLeft === 0) {
      setGameStartClock(false);
    }
    //여기는 아님
    // if (timeLeft === 0 && sentenceIndex === 2) {
    //   setSentenceIndex(4);
    //   setGameStartClock(false);
    // }
    // if (timeLeft === 0 && (sentenceIndex === 0 || sentenceIndex === 1)) {
    //   //timeLeft는 결투 도망 분기를 위한 시간.
    //   //0이 되면 무조건 결투를 선택하게 한다.
    //   const isDual = {
    //     isDual: true,
    //   };
    //   const message = JSON.stringify(isDual);
    //   stompClient.send(`/minigame/select`, {}, message);
    //   console.log("첫번째 dialog에서 선택을 안해서 바로 결투");
    //   setSentenceIndex(4);
    //   setGameStartClock(false);
    // }

    //처음 주어진 시간이 0이 되고 dialog가0,1(선택안한 경우), 3(도망을 선택한 경우)에 도망에 대한 결과를 받는다.
    if (
      timeLeft === 0 &&
      (sentenceIndex === 0 || sentenceIndex === 1 || sentenceIndex === 3)
    ) {
      //맨 처음에 cancelData를 저장하면 나중에 바뀌는 값을 못가져올것같음
      //그래서 첫번째 분기 시간이 0일때 props를 가져오면 될까.. test필요함.
      const gameCancelData = props.gameCancelData;
      const gameStartData = props.gameStartData;
      console.log("도망에 대한 결과 받기");
      //더미데이터: 도망가자마자 6번 index로 가게
      // setSentenceIndex(6);
      //도망여부 받기
      // console.log(gameCancelData);
      if (gameCancelData.runner) {
        setSentenceIndex(6);
      }
      if (gameStartData) {
        setSentenceIndex(5);
      }
    }
    //결투를 위한 dialog일때
    if (timeLeft === 0 && sentenceIndex === 2) {
      const gameCancelData = props.gameCancelData;
      const gameStartData = props.gameStartData;
      if (gameCancelData.runner === false) {
        setSentenceIndex(7);
      }
      if (gameStartData) {
        setSentenceIndex(4);
      }
    }

    //pvp시작부터 시작도망선택
  }, [timeLeft, navigate]);

  function matchingDialog() {
    //첫 dialog
    // 적의 이름 띄워주기
    setmsg(
      matchingData.enemy.title === "x"
        ? ""
        : matchingData.enemy.title +
            " " +
            matchingData.enemy.name +
            "이 나타났다!!!!"
    );
  }
  function selectStartOrCancel() {
    setmsg("결투하기: " + matchingData.cost + "원");
    setmsgCancel("도망가기: " + matchingData.runCost + "원");
    runDialogNext();
  }
  //결투와 도망에만 쓰이는 클릭버튼
  function msgClick() {
    if (sentenceIndex === 1) {
      //결투하기 연결
      //역주소랑 게임아이디 받아야 함.
      setSentenceIndex(2);
      console.log("결투하기 연결");
      const duel = {
        duel: true,
      };
      const message = JSON.stringify(duel);
      stompClient.send("/pub/minigame/select", {}, message);
    }
  }
  function cancelClick() {
    if (sentenceIndex === 1) {
      //도망치기 연결
      console.log("도망치기 연결");
      setSentenceIndex(3);
      const duel = {
        duel: false,
      };

      const message = JSON.stringify(duel);
      stompClient.send("/pub/minigame/select", {}, message);
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
      setGameTimeLeft((prevTimeLeft) => prevTimeLeft - 1);
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
        console.log("도망 실패");
        navigate("ready");
      }
      if (sentenceIndex === 6) {
        //도망성공
        // main stompClient Error로 일단 주석 처리
        navigate("/main");
        console.log("메인으로 이동!");
      }
      if (sentenceIndex === 7) {
        //상대방이 도망성공
        // main stompClient Error로 일단 주석 처리
        navigate("/main");
        console.log("상대방이 도망성공");
      }
    }
    //여기서 소켓통신해서
  }, [gametimeLeft]);

  function startGame() {
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

  function enemyRun() {
    setmsg("적이 도망쳤다!!");
    leftGameTime();
  }
  function runDialogNext() {
    setDialogNext(false);
  }

  return (
    <div className="battleDialog">
      <img src={battleDialogImg} alt="battleDialogImg" />
      {dialogNext ? (
        <p className="battle-dialog-next">클릭해서 넘어가기</p>
      ) : (
        <p className="battle-dialog-next"></p>
      )}

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
      </div>
    </div>
  );
}
