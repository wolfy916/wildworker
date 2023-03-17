import React from "react"
// import { useEffect, useState } from "react"
// import axios from "axios"
import { Routes, Route } from "react-router-dom"
import LoginPage from "./pages/LoginPage"
import MainPage from "./pages/MainPage"
import SubwayMapPage from "./pages/SubwayMapPage"
import PvpPage from "./pages/PvpPage"
import MySubwayPage from "./pages/MySubwayPage"
import HotSubwayPage from "./pages/HotSubwayPage"
import DetailSubwayPage from "./pages/DetailSubwayPage"

import Box from "@mui/material/Box"
import Container from "@mui/material/Container"
import "./App.css"

function App() {
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

  return (
    <div id="App" className="App">
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
          </Routes>
        </Box>
      </Container>
    </div>
  )
}
export default App
