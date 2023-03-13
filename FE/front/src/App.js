import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import MainPage from "./pages/MainPage";
import SubwayMapPage from "./pages/SubwayMapPage";
import PvpPage from "./pages/PvpPage";

function App() {
  return (
    <div className="App">
      <nav>
        <Link to="/">로그인하면메인으로</Link> |
        <Link to="/map"> 노선도기기</Link> |
        <Link to="/pvp">임시 결투화면 이동</Link>
      </nav>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/map" element={<SubwayMapPage />} />
        <Route path="/pvp" element={<PvpPage />} />
      </Routes>
    </div>
  );
}
export default App;
