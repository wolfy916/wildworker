import * as React from "react"
import "./MainPage.css"
import character from "../asset/image/moving_man.gif"
import goMap from "../asset/image/goMap.png"
import { Link } from "react-router-dom"

function MainPage() {
  return (
    <div className="subway-background">
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
