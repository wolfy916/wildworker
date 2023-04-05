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
  const [isLogin, setIsLogin] = useState(false);
  const [isConnected, setIsConnected] = useState(false);
  const [isMatched, setIsMatched] = useState(false);
  const [isObtainTitle, setIsObtainTitle] = useState(false);
  const [isGetError, setIsGetError] = useState(false);
  const [subwayContentIdx, setSubwayContentIdx] = React.useState(0);
  const [nicknameErr, setNicknameErr] = useState(false);

  // 유저 데이터
  const [userData, setUserData] = useState({
    characterType: 0,
    coin: 0,
    collectedPapers: 0,
    name: "이름바꿔",
    title: { id: 1, name: "x" },
    titleType: 0,
  });

  // 보유 칭호목록 조회 데이터
  const [myTitles, setMyTitles] = useState({
    titleType: 1,
    mainTitleId: 0,
    dominatorTitles: [{ id: 1, name: "역삼역의 지배자" }],
    titles: [{ id: 1, name: "없음" }],
  });

  // 코인 내역 조회 데이터
  const [myCoinLogs, setMyCoinLogs] = useState({
    balance: 1234,
    list: [
      {
        station: {
          id: 1,
          name: "역삼역",
        },
        type: "게임",
        value: -20,
        applied: true,
        time: "2023-03-14 14:20",
      },
    ],
    size: 10,
    totalPage: 1,
    currentPage: 0,
  });

  // 실시간 역 랭킹 데이터
  const [stationRank, setStationRank] = useState({
    ranking: [
      {
        rank: 1,
        station: {
          id: 1,
          name: "역삼역",
          totalInvestment: 12345,
          prevCommission: 1234,
          currentCommission: 123,
        },
      },
    ],
    orderBy: "investment",
  });

  // 해당 역에 대한 지분 데이터
  const [stationStake, setStationStake] = useState({
    stationName: null,
    dominator: null,
    totalInvestment: 0,
    prevCommission: 0,
    currentCommission: 0,
    ranking: [
      {
        rank: null,
        name: null,
        investment: 0,
        percent: null,
      },
    ],
    mine: {
      rank: null,
      investment: 0,
      percent: null,
    },
  });

  // 내가 투자한 역 목록
  const [myInvestList, setMyInvestList] = useState({
    investments: [
      {
        station: {
          id: 1,
          name: "역삼역",
        },
        investment: 1234,
        percent: 10,
      },
    ],
    remainSec: 90,
    orderBy: "investment",
    ascend: "ASC",
  });

  // 소켓 메세지로 넘어오는 데이터
  const [store, setStore] = useState({
    locationData: {
      prev: null,
      current: null,
      next: null,
    },
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
    getTitle: "쫄보",
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
      setStompClient(
        connectSocket(
          Stomp.over(socket),
          setStore,
          setUserData,
          store,
          setIsMatched,
          setIsObtainTitle,
          setIsGetError
        )
      );
      setIsConnected(true);
    }
  }, [isLogin]);

  // 지하철역 구독해제하고 새로운 지하철로 재연결
  useEffect(() => {
    if (store.locationData) {
      setStompClient(unsubscribeStation(stompClient, store.locationData.prev));
      setStompClient(
        subscribeStation(
          stompClient,
          setStore,
          store.locationData.current,
          setSubwayContentIdx
        )
      );
    }
  }, [store.locationData]);

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
      }, 2000);
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

  const [isFlashing, setIsFlashing] = React.useState(false);

  React.useEffect(() => {
    if (store.dominatorAppear) {
      setIsFlashing(true);
      setTimeout(() => {
        setIsFlashing(false);
      }, 3000);
    }
  }, [store.dominatorAppear]);

  React.useEffect(() => {
    if (isFlashing) {
      document.getElementsByClassName(
        "main-board-modal-wrap"
      )[0].style.display = "block";
    } else {
      document.getElementsByClassName(
        "main-board-modal-wrap"
      )[0].style.display = "none";
    }
  }, [isFlashing]);

  return (
    <div id="App" className="App">
      <div className="main-board-modal-wrap">지배자 강림</div>
      <Container className="app-container" maxWidth="xs">
        <Box sx={{ height: "100vh" }}>
          {isObtainTitle && (
            <Modal
              modalWidth={70}
              modalHeight={40}
              selectModalIdx={6}
              setModalClick={setIsObtainTitle}
              store={store}
            />
          )}
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
                  setIsLogin={setIsLogin}
                  myTitles={myTitles}
                  setMyTitles={setMyTitles}
                  myCoinLogs={myCoinLogs}
                  setMyCoinLogs={setMyCoinLogs}
                  isMatched={isMatched}
                  setIsMatched={setIsMatched}
                  isGetError={isGetError}
                  setIsGetError={setIsGetError}
                  subwayContentIdx={subwayContentIdx}
                  setSubwayContentIdx={setSubwayContentIdx}
                  nicknameErr={nicknameErr}
                  setNicknameErr={setNicknameErr}
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
            <Route
              path="/map"
              element={
                <SubwayMapPage
                  myInvestList={myInvestList}
                  setMyInvestList={setMyInvestList}
                  store={store}
                />
              }
            />
            <Route
              path="/map/mine"
              element={
                <MySubwayPage
                  myInvestList={myInvestList}
                  setMyInvestList={setMyInvestList}
                />
              }
            />
            <Route
              path="/map/hot"
              element={
                <HotSubwayPage
                  stationRank={stationRank}
                  setStationRank={setStationRank}
                  stationStake={stationStake}
                  setStationStake={setStationStake}
                  setUserData={setUserData}
                />
              }
            />
            <Route
              path="/map/detail"
              element={
                <DetailSubwayPage
                  stationStake={stationStake}
                  setStationStake={setStationStake}
                  setUserData={setUserData}
                />
              }
            />
            <Route
              path="/pvp"
              element={
                <PvpPage
                  matchingData={store.matching}
                  gameRunData={store.gameCancel}
                  gameStartData={store.gameStart}
                  currentLocationData={store.locationData.current}
                  stompClient={stompClient}
                  userData={userData}
                />
              }
            />
            <Route
              path="/pvp/ready"
              element={<MiniGameReadyPage gameStartData={store.gameStart} />}
            />
            <Route
              path="/pvp/minigame"
              element={<MiniGamePage stompClient={stompClient} />}
            />
            <Route
              path="/pvp/result"
              element={
                <PvpResultPage
                  gameResultData={store.gameResult}
                  userData={userData}
                />
              }
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
