import React from "react";
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
import Modal from "./components/mainpage/Modal";

import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import "./App.css";
import {
  connectSocket,
  subscribeStation,
  unsubscribeStation,
} from "../src/api/socketFunc";

function App() {
  // 웹에서 개발할 때, 얘 꼭 주석처리 해라

  // const elem = document.documentElement;
  // document.addEventListener('click', function() {
  //   if (elem.requestFullscreen) {
  //     elem.requestFullscreen();
  //   }
  // });

  const [isLogin, setIsLogin] = useState(false);
  const [isConnected, setIsConnected] = useState(false);
  const [isChangeId, setIsChangeId] = useState(false);
  const [userData, setUserData] = useState({
    characterType: 0,
    coin: 0,
    collectedPapers: 0,
    name: "",
    titleId: 0,
    titleType: 0,
  });
  const [store, setStore] = useState({
    locationData: {},
    manualMining: 1,
    dominatorAppear: "",
    dominatorMsg: "",
    coinChange: {
      AUTO_MINING: {},
      MANUAL_MINING: {},
      MINI_GAME_COST: {},
      MINI_GAME_RUN_COST: {},
      MINI_GAME_REWARD: {},
      INVESTMENT: {},
      INVESTMENT_REWARD: {},
    },
    getTitle: "",
    changeTitle: {},
    matching: {},
    gameStart: {},
    gameCancel: {},
    gameResult: {},
  });
  // 소켓 인스턴스 생성하고, 상태관리에 넣음
  const [stompClient, setStompClient] = useState({});

  // 연결하고, 필요한거 다 구독하고 상태관리에 넣어 유지함
  useEffect(() => {
    if (isLogin) {
      const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws");
      setStompClient(connectSocket(Stomp.over(socket), setStore, setUserData));
      setIsConnected(true);
    }
  }, [isLogin]);

  // // 5초 뒤에 isChangeId = true로 지하철 Id가 변경되는 타이밍이라고 가정
  // const [isChangeId, setIsChangeId] = useState(false);
  // setTimeout(() => {
  //   setIsChangeId(true);
  // }, 5000);

  // isChangeId값의 변화로 지하철역 구독해제하고 새로운 지하철로 재연결

  useEffect(() => {
    if (store.locationData.prev) {
      setStompClient(unsubscribeStation(stompClient, store.locationData.prev));
      setStompClient(
        subscribeStation(stompClient, setStore, store.locationData.current)
      );
    }
  }, [store.locationData.prev]);

  // 실시간 위치 전송 코드
  useEffect(() => {
    if (isConnected) {
      const intervalId = setInterval(() => {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            if (position.coords) {
              handleSendLocation({
                lat: position.coords.latitude,
                lon: position.coords.longitude,
              });
            }
          },
          (error) => {
            console.log(error);
          }
        );
      }, 5000);
      return () => {
        clearInterval(intervalId);
      };
    }
  }, [isConnected]);

  // 위치 전송 백에게 전달하는 함수
  const handleSendLocation = (e) => {
    const message = JSON.stringify(e);
    stompClient.send("/pub/system/location", {}, message);
  };

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
                  store={store}
                  setStore={setStore}
                  userData={userData}
                  setUserData={setUserData}
                  stompClient={stompClient}
                />
              }
            />
            <Route
              path="/redirect/login"
              element={
                <RedirectLogin
                  setIsLogin={setIsLogin}
                  setUserData={setUserData}
                />
              }
            />
            <Route path="/map" element={<SubwayMapPage />} />
            <Route path="/map/mine" element={<MySubwayPage />} />
            <Route path="/map/hot" element={<HotSubwayPage />} />
            <Route path="/map/detail" element={<DetailSubwayPage />} />
            <Route
              path="/pvp"
              element={
                <PvpPage
                  matchingData={store.matching}
                  gameRunData={store.gameCancel}
                  gameStartData={store.gameStart}
                />
              }
            />
            <Route path="/pvp/ready" element={<MiniGameReadyPage />} />
            <Route path="/pvp/minigame" element={<MiniGamePage />} />
            <Route
              path="/pvp/result"
              element={<PvpResultPage gameResultData={store.gameResult} />}
            />
            <Route
              path="/pvp/receipt"
              element={<PvpReceipPage gameResultData={store.gameResult} />}
            />
          </Routes>
        </Box>
      </Container>
    </div>
  );
}
export default App;
