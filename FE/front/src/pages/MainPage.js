import * as React from "react"
import "./MainPage.css"
import character from "../asset/image/moving_man.gif"
import goMap from "../asset/image/goMap.png"
import menuBtn from "../asset/image/mainpage_menu_btn.png";
import getCoinBtn from "../asset/image/get_coin_btn.png"
import { Link } from "react-router-dom"
import SubwayBoard from "../components/mainpage/SubwayBoard";


function MainPage() {
  return (
    <div className="subway-background">
      <SubwayBoard/>
      <div className="subway">
        <img className="character" src={character} alt="character" />
        <img className="main-menu-btn" src={menuBtn} alt="menuBtn" />
        <img className="get-coin-btn" src={getCoinBtn} alt="getCoinBtn" />
        <Link className="router-map-btn" to="/map">
          <img src={goMap} alt="goMap" />
        </Link>
      </div>
    </div>
  )
}

export default MainPage
