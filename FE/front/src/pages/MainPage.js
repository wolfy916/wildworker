import * as React from "react"
import "./MainPage.css"
import character from "../asset/image/moving_man.gif"
import goMap from "../asset/image/goMap.png"
import { Link } from "react-router-dom"
import SubwayBoard from "../components/mainpage/SubwayBoard";

function MainPage() {
  return (
    <div className="subway-background">
      <SubwayBoard/>
      <div className="subway">
        <img src={character} alt="character" />
        <Link to="/map">
          {" "}
          <img src={goMap} alt="goMap" />
        </Link>
      </div>
    </div>
  )
}

export default MainPage
