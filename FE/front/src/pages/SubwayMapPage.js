import * as React from "react"
import { Link } from "react-router-dom"
import subwaymap from "../asset/image/subwaymap.png";
import goMain from "../asset/image/goMain.png";
import myMap from "../asset/image/myMap.png";
import hotMap from "../asset/image/hotMap.png";
import "./SubwayMapPage.css"

function SubwayMapPage() {
  return (
    <nav>
      <img src={subwaymap}/>
      <Link to="/map/detail">역 누르면 detail화면으로</Link>
      <hr/>
      <Link to="/map/mine"><img src={myMap} alt="myMap" /></Link>
      <Link to="/"><img src={goMain} alt="goMain" /></Link>
      <Link to="/map/hot"><img src={hotMap} alt="hotMap" /></Link>
    </nav>
  )
}

export default SubwayMapPage
