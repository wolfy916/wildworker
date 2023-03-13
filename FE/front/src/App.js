import React from "react";
import { Routes, Route, Link } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import SubwayMapPage from "./pages/SubwayMapPage";
import PvpPage from "./pages/PvpPage";

import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Container className="app-container" maxWidth="xs">
        <Box sx={{ height: "100vh" }}>
          <nav>
            <Link to="/">Login</Link> | 
            <Link to="/main">Main</Link> | 
            <Link to="/map">Map</Link> | 
            <Link to="/pvp">PvP</Link>
          </nav>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/main" element={<MainPage />} />
            <Route path="/map" element={<SubwayMapPage />} />
            <Route path="/pvp" element={<PvpPage />} />
          </Routes>
        </Box>
      </Container>
    </div>
  );
}
export default App;
