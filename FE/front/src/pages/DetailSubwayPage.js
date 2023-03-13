import * as React from "react"
import { Link } from "react-router-dom"
import "./DetailSubwayPage.css"
import goMap from "../asset/image/goMap.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"

function DetailSubwayPage() {
  return (
    <nav>
      <div>여긴 누른 역 detail</div>
      <Link to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link to="/map">
        <img src={goMap} alt="goMap" />
      </Link>
      <Link to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </nav>
  )
}

export default DetailSubwayPage
