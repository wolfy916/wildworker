import React, { useCallback } from "react";
import { useEffect, useState } from "react";
import Stomp from "stompjs";
import SockJS from "sockjs-client";
import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RedirectLogin from "./pages/RedirectLoginPage";
import MainPage from "./pages/MainPage";
import SubwayMapPage from "./pages/SubwayMapPage";
import PvpPage from "./pages/PvpPage";
import PvpResultPage from "./pages/ResultPage";
import PvpReceipPage from "./pages/ReceiptPage";
import MySubwayPage from "./pages/MySubwayPage";
import HotSubwayPage from "./pages/HotSubwayPage";
import DetailSubwayPage from "./pages/DetailSubwayPage";
import MiniGamePage from "./pages/MiniGamePage";
import MiniGameReadyPage from "./pages/MiniGameReadyPage";

import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import "./App.css";
// import { runSocket } from "../src/api/Socket";

function App() {
  // 웹에서 개발할 때, 얘 꼭 주석처리 해라

  // const elem = document.documentElement;
  // document.addEventListener('click', function() {
  //   if (elem.requestFullscreen) {
  //     elem.requestFullscreen();
  //   }
  // });
  // const [store, setStore] = useState({
  //   location : "",
  //   userMining : "",
  //   dominatorAppear : "",
  //   dominatorMsg : "",
  //   coinChange: {
  //     autoMining : "",
  //     userMining : "",
  //   },
  // });
  const [dominatorComeData, setDominatorComeData] = useState("");
  const [dominatorMessageData, setDominatorMessageData] = useState("");
  const [manualMiningData, setManualMiningData] = useState(1);
  const [locationData, setLocationData] = useState("");

  const [autoCoinData, setAutoCoinData] = useState("");
  const [manualCoinData, setManualCoinData] = useState("");
  const [investCostData, setInvestCostData] = useState("");
  const [investRewardData, setInvestRewardData] = useState("");
  const [gameCostData, setGameCostData] = useState("");
  const [runCostData, setRunCostData] = useState("");
  const [gameRewardData, setGameRewardData] = useState("");
  const [getTitleData, setGetTitleData] = useState("");
  const [changeTitleData, setChangeTitleData] = useState("");
  const [matchingData, setMatchingData] = useState("");
  const [gameRunData, setGameRunData] = useState("");
  const [gameResultData, setGameResultData] = useState("");
  const [gameStartData, setGameStartData] = useState("");

  // 소켓 인스턴스 생성하고, 상태관리에 넣음
  const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws");
  const [stompClient, setStompClient] = useState(Stomp.over(socket));

  // 연결하고, 필요한거 다 구독하고 상태관리에 넣어 유지함
  useEffect(() => {
    stompClient.connect({}, () => {
      stompClient.subscribe("/sub/systems/", (message) => {
        // 지하철역 구독에 필요한 모든 코드
      });
      stompClient.subscribe("/user/queue", (message) => {
        // 유저 구독에 필요한 모든 코드
      });
    })
    setStompClient(stompClient);
  }, []);

  // 5초 뒤에 isChangeId = true로 지하철 Id가 변경되는 타이밍이라고 가정
  const [isChangeId, setIsChangeId] = useState(false);
  setTimeout(() => {
    setIsChangeId(true);
  }, 5000);

  // isChangeId값의 변화로 지하철역 구독해제하고 새로운 지하철로 재연결
  useEffect(()=>{
    if (isChangeId) {
      stompClient.unsubscribe("/sub/systems/");  // 지하철역 구독 해제
      stompClient.subscribe("/sub/재연결", (message) => {
        // 지하철역 구독에 필요한 모든 코드
      });
    }
  }, [isChangeId]);
  
  // // 실시간 위치 전송 코드
  // useEffect(() => {
  //   const intervalId = setInterval(() => {
  //     navigator.geolocation.getCurrentPosition(
  //       (position) => {
  //         if (position.coords) {
  //           handleSendLocation({
  //             lat: position.coords.latitude,
  //             lon: position.coords.longitude,
  //           });
  //         }
  //       },
  //       (error) => {
  //         console.log(error);
  //       }
  //     );
  //   }, 5000);

  //   return () => {
  //     clearInterval(intervalId);
  //   };
  // }, []);

  // // 위치 전송 백에게 전달하는 함수
  // const handleSendLocation = (e) => {
  //   const message = JSON.stringify(e);
  //   stompClient.send("/pub/system/location", {}, message);
  // };

  //연결, 구독하기, 구독끊기, 데이터 받는 곳
  // useEffect(() => {
  //   socket()
  // // 연결
  // stompClient.connect({}, () => {
  //   // 같은 역 사람들 구독
  //   stompClient.subscribe("/sub/systems/{station-id}", (message) => {
  //     const payload = JSON.parse(message.body);

  //     // 지배자 기능 모음
  //     if (payload.type === "STATION") {
  //       // 지배자 강림
  //       if (payload.subType === "DOMINATOR") {
  //         setDominatorComeData(payload.data);
  //       }
  //       // 지배자 확성기
  //       else if (payload.subType === "MESSAGE") {
  //         setDominatorMessageData(payload.data);
  //       }
  //     }
  //   });
  //   stompClient.subscribe("/user/queue", (message) => {
  //     const payload = JSON.parse(message.body);

  //     //현재 역 변동 & 역 정보
  //     if (payload.type === "STATION" && payload.subType === "STATUS") {
  //       setLocationData(payload.data);
  //     }

  //     // 수동 채굴 모음
  //     else if (payload.type === "MINING") {
  //       // 서류 종이 카운트
  //       if (payload.subType === "PAPER_COUNT") {
  //         setManualMiningData(payload.data);
  //       }
  //     }

  //     // 코인변동 모음
  //     else if (payload.type === "COIN") {
  //       // 자동 코인 변동
  //       if (payload.subType === "AUTO_MINING") {
  //         setAutoCoinData(payload.data);
  //       }
  //       //수동 코인변동
  //       else if (payload.subType === "MANUAL_MINING") {
  //         setManualCoinData(payload.data);
  //       }
  //       //게임비
  //       else if (payload.subType === "MINI_GAME_COST") {
  //         setGameCostData(payload.data);
  //       }
  //       //도망비
  //       else if (payload.subType === "MINI_GAME_RUN_COST") {
  //         setRunCostData(payload.data);
  //       }
  //       //게임보상금
  //       else if (payload.subType === "MINI_GAME_REWARD") {
  //         setGameRewardData(payload.data);
  //       }
  //       //투자액
  //       else if (payload.subType === "INVESTMENT") {
  //         setInvestCostData(payload.data);
  //       }
  //       //투자보상금
  //       else if (payload.subType === "INVESTMENT_REWARD") {
  //         setInvestRewardData(payload.data);
  //       }
  //     }

  //     // 칭호관련 모음
  //     else if (payload.type === "TITLE") {
  //       // 칭호 획득
  //       if (payload.subType === "GET") {
  //         setGetTitleData(payload.data);
  //       }
  //       // 내 대표 칭호 변동
  //       else if (payload.subType === "MAIN_TITLE_UPDATE") {
  //         setChangeTitleData(payload.data);
  //       }
  //     }

  //     // 미니게임 모음
  //     else if (payload.type === "MINIGAME") {
  //       // 게임 매칭
  //       if (payload.subType === "MATCHING") {
  //         setMatchingData(payload.data);
  //       }
  //       // 게임 취소 (도망 성공)
  //       else if (payload.subType === "CANCEL") {
  //         setGameRunData(payload.data);
  //       }
  //       // 게임 시작
  //       else if (payload.subType === "START") {
  //         setGameStartData(payload.data);
  //       }
  //       // 게임 결과
  //       else if (payload.subType === "RESULT") {
  //         setGameResultData(payload.data);
  //       }
  //     }
  //   });
  // });
  //   runSocket(stompClient);
  // }, []);

  return (
    <div id="App" className="App">
      <Container className="app-container" maxWidth="xs">
        <Box sx={{ height: "100vh" }}>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route
              path="/main"
              element={
                <MainPage
                  dominatorComeData={dominatorComeData}
                  dominatorMessageData={dominatorMessageData}
                  locationData={locationData}
                  manualMiningData={manualMiningData}
                  autoCoinData={autoCoinData}
                  manualCoinData={manualCoinData}
                  gameCostData={gameCostData}
                  runCostData={runCostData}
                  gameRewardData={gameRewardData}
                  investCostData={investCostData}
                  investRewardData={investRewardData}
                  changeTitleData={changeTitleData}
                  stompClient={stompClient}
                />
              }
            />
            <Route path="/redirect/login" element={<RedirectLogin />} />
            <Route path="/map" element={<SubwayMapPage />} />
            <Route path="/map/mine" element={<MySubwayPage />} />
            <Route path="/map/hot" element={<HotSubwayPage />} />
            <Route path="/map/detail" element={<DetailSubwayPage />} />
            <Route
              path="/pvp"
              element={
                <PvpPage
                  matchingData={matchingData}
                  gameRunData={gameRunData}
                  gameStartData={gameStartData}
                />
              }
            />
            <Route path="/pvp/ready" element={<MiniGameReadyPage />} />
            <Route path="/pvp/minigame" element={<MiniGamePage />} />
            <Route
              path="/pvp/result"
              element={<PvpResultPage gameResultData={gameResultData} />}
            />
            <Route
              path="/pvp/receipt"
              element={<PvpReceipPage gameResultData={gameResultData} />}
            />
          </Routes>
        </Box>
      </Container>
    </div>
  );
}
export default App;
