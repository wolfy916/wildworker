import React from "react";
import { useEffect, useState } from "react"
// import axios from "axios"
import Stomp from "stompjs"
import SockJS from "sockjs-client"
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

function App() {
  // 웹에서 개발할 때, 얘 꼭 주석처리 해라

  // const elem = document.documentElement;
  // document.addEventListener('click', function() {
  //   if (elem.requestFullscreen) {
  //     elem.requestFullscreen();
  //   }
  // });


  const [location, setLocation] = useState(null)
  const [broadcastMessage, setBroadcastMessage] = useState("")
  const [personalMessage, setPersonalMessage] = useState("")
  const [broadcastData, setBroadcastData] = useState("")
  const [personalData, setPersonalData] = useState("")
  const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws")
  const stompClient = Stomp.over(socket)
  // 실시간 위치 전송 코드
  useEffect(() => {
    console.log("현철")
    const intervalId = setInterval(() => {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          if (position.coords) {
            setLocation(position.coords)
            handleSendLocation({
              lat: position.coords.latitude,
              lon: position.coords.longitude,
            })
          }
        },
        (error) => {
          console.log(error)
        }
      )
    }, 2000)

    return () => {
      clearInterval(intervalId)
    }
  }, [])

  useEffect(() => {
    stompClient.connect({}, () => {
      console.log("연결성공")
      stompClient.subscribe("/sub/system", (message) => {
        const payload = JSON.parse(message.body)
        setBroadcastData(payload.data)
      })
      stompClient.subscribe("/user/queue", (message) => {
        const payload = JSON.parse(message.body)

        //현재 역 변동 & 역 정보
        if (payload.type === "STATION" && payload.subType === "STATUS") {
          if (payload.data.current) {
            setPersonalData(payload.data.current.name)
          } else {
            setPersonalData("역아임다")
          }
        }
        // 수동 채굴 - 종이 줍기
        else if (payload.type === "MINING" && payload.subType === "PAPER_COUNT") {
          if (payload.data.paperCount) {
            setPersonalData(payload.data.paperCount)
          }
        } 
        // 수동 채굴 - 가방 누르면
        // else if (payload.type === "MINING" && payload.subType === "??") {
        //   if (payload.data) {
        //     setPersonalData(payload.data)
        //   }
        // } 

        // 코인 변동
        else if (payload.type === "COIN") {
          if (payload.subType === "AUTO_MINING") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          } else if (payload.subType === "MANUAL_MINING") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          } else if (payload.subType === "MINI_GAME_COST") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          } else if (payload.subType === "MINI_GAME_RUN_COST") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          } else if (payload.subType === "MINI_GAME_REWARD") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          } else if (payload.subType === "INVESTMENT") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          } else if (payload.subType === "INVESTMENT_REWARD") {
            if (payload.data) {
              setPersonalData(payload.data)
            }
          }
        }
        // 칭호 획득
        else if (payload.type === "TITLE" && payload.subType === "GET") {
          if (payload.data) {
            setPersonalData(payload.data)
          }
        }
        // 내 대표 칭호 변동
        else if (payload.type === "TITLE" && payload.subType === "MAIN_TITLE_UPDATE") {
          if (payload.data) {
            setPersonalData(payload.data)
          }
        }
        // 게임 매칭
        else if (payload.type === "MINIGAME" && payload.subType === "MATCHING") {
          if (payload.data) {
            setPersonalData(payload.data)
          }
        }
        // 게임 취소 (도망 성공)
        else if (payload.type === "MINIGAME" && payload.subType === "CANCEL") {
          if (payload.data) {
            setPersonalData(payload.data)
          }
        }
        // 게임 시작
        else if (payload.type === "MINIGAME" && payload.subType === "START") {
          if (payload.data) {
            setPersonalData(payload.data)
          }
        }
        // 게임 결과
        else if (payload.type === "MINIGAME" && payload.subType === "RESULT") {
          if (payload.data) {
            setPersonalData(payload.data)
          }
        }
      })
    })
  }, [])

  // const handleBroadcastSend = () => {
  //   const message = broadcastMessage
  //   stompClient.send("/pub/test/broadcast", {}, message)
  // }
  
  // 위치 전송
  const handleSendLocation = (e) => {
    const message = JSON.stringify(e)
    stompClient.send("/pub/system/location", {}, message)
  }

  return (
    <div id="App" className="App">
      {/* <div>
        <div>
          <h2>Broadcast Message</h2>
          <input
            type="text"
            value={broadcastMessage}
            onChange={(e) => setBroadcastMessage(e.target.value)}
          />
          <button onClick={handleBroadcastSend}>Send</button>
        </div>

        <div>{broadcastData}</div>
      </div> */}
        {/* <div>{personalData}</div> */}

      <Container className="app-container" maxWidth="xs">
        <Box sx={{ height: "100vh" }}>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/main" element={<MainPage />} />
            <Route path="/redirect/login" element={<RedirectLogin />} />
            <Route path="/map" element={<SubwayMapPage />} />
            <Route path="/map/mine" element={<MySubwayPage />} />
            <Route path="/map/hot" element={<HotSubwayPage />} />
            <Route path="/map/detail" element={<DetailSubwayPage />} />
            <Route path="/pvp" element={<PvpPage />} />
            <Route path="/pvp/result" element={<PvpResultPage />} />
            <Route path="/pvp/ready" element={<MiniGameReadyPage />} />
            <Route path="/pvp/receipt" element={<PvpReceipPage />} />
            <Route path="/pvp/minigame" element={<MiniGamePage />} />
          </Routes>
        </Box>
      </Container>
    </div>
  );
}
export default App;
