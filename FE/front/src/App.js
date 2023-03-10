import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import MainPage from "./pages/MainPage";
import SubwayMapPage from "./pages/SubwayMapPage";

function App() {
  return (
    <div className="App">
      <nav>
        <Link to="/">로그인하면메인으로</Link> |
        <Link to="/map"> 노선도기기</Link>
      </nav>
      <Routes>
        <Route path="/" element={<MainPage/>}/>
        <Route path="/map" element={<SubwayMapPage/>}/>
      </Routes>
    </div>
  )
}
export default App
