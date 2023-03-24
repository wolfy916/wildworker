import React from "react"
// import { useEffect, useState } from "react"
// import axios from "axios"
// import { useState, useEffect } from "react";
// import Stomp from "stompjs";
// import SockJS from "sockjs-client";
import { Routes, Route } from "react-router-dom"
import LoginPage from "./pages/LoginPage"
import MainPage from "./pages/MainPage"
import SubwayMapPage from "./pages/SubwayMapPage"
import PvpPage from "./pages/PvpPage"
import PvpResultPage from "./pages/ResultPage"
import PvpReceipPage from "./pages/ReceiptPage"
import MySubwayPage from "./pages/MySubwayPage"
import HotSubwayPage from "./pages/HotSubwayPage"
import DetailSubwayPage from "./pages/DetailSubwayPage"
import MiniGamePage from "./pages/MiniGamePage"

import Box from "@mui/material/Box"
import Container from "@mui/material/Container"
import "./App.css"

function App() {
  // 실시간 위치 전송 코드
  // const [location, setLocation] = useState(null)

  // useEffect(() => {
  //   const intervalId = setInterval(() => {
  //     navigator.geolocation.getCurrentPosition(
  //       (position) => {
  //         setLocation(position.coords)
  //       },
  //       (error) => {
  //         console.log(error)
  //       }
  //     )
  //   }, 1000)

  //   return () => {
  //     clearInterval(intervalId)
  //   }
  // }, [])

  // useEffect(() => {
  //   if (location) {
  //     axios
  //       .post("~~~", {
  //         lat: location.latitude,
  //         lon: location.longitude,
  //       })
  //       .then((response) => {
  //         console.log(response.data)
  //       })
  //       .catch((error) => {
  //         console.log(error)
  //       })
  //   }
  // }, [location])
  // document.addEventListener('touchmove', function(event) {
  //   event.preventDefault();
  // }, { passive: false });


  // 웹에서 개발할 때, 얘 꼭 주석처리 해라



  const elem = document.documentElement;
  document.addEventListener('click', function() {
    if (elem.requestFullscreen) {
      elem.requestFullscreen();
    }
  });
  
  // const [broadcastMessage, setBroadcastMessage] = useState("");
  // const [personalMessage, setPersonalMessage] = useState("");

  // useEffect(() => {
  //   const socket = new SockJS("https://j8a304.p.ssafy.io:8443/api/v1/ws");
  //   const stompClient = Stomp.over(socket);
  //   console.log(socket)
    
  //   stompClient.connect({}, () => {
  //     console.log('연결성공')
  //     stompClient.subscribe("/sub/system", (message) => {
  //       const payload = JSON.parse(message.body);
  //       setBroadcastMessage(payload.message);
  //     });
  //     stompClient.subscribe("/user/queue/noti", (message) => {
  //       const payload = JSON.parse(message.body);
  //       setPersonalMessage(payload.message);
  //     });
  //   });
  //   return () => {
  //     stompClient.disconnect();
  //   };
  // }, []);

  // const handleBroadcastSend = () => {
  //   const message = { message: broadcastMessage };
  //   const socket = new SockJS("https://j8a304.p.ssafy.io:8443/api/v1/ws");
  //   const stompClient = Stomp.over(socket);
  //   console.log(socket)
  //   stompClient.connect({}, () => {
  //     stompClient.send("/pub/test/broadcast", {}, JSON.stringify(message));
  //     stompClient.disconnect();
  //   });
  // };

  // const handlePersonalSend = () => {
  //   const message = { message: personalMessage };
  //   const socket = new SockJS("https://j8a304.p.ssafy.io:8443/api/v1/ws");
  //   const stompClient = Stomp.over(socket);
  //   stompClient.connect({}, () => {
  //     stompClient.send("/pub/test/personal", {}, JSON.stringify(message));
  //     stompClient.disconnect();
  //   });
  // };



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
      <div>
        <h2>Personal Message</h2>
        <input
          type="text"
          value={personalMessage}
          onChange={(e) => setPersonalMessage(e.target.value)}
        />
        <button onClick={handlePersonalSend}>Send</button>
      </div>
    </div> */}
      <Container className="app-container" maxWidth="xs">
        <Box sx={{ height: "100vh" }}>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/main" element={<MainPage />} />
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
  )
}
export default App
