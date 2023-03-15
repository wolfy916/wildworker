import * as React from "react"
import { useLocation } from "react-router-dom"
import { Link } from "react-router-dom"
import "./DetailSubwayPage.css"
import goMap from "../asset/image/goMap.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"

function DetailSubwayPage() {
  // const {id} = props.match.params;
  const location = useLocation()

  return (
    <nav>
      <h1> 역아이디 {location.state}의 Detail 페이지 </h1>
      <Link className="detail-router-my-btn" to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link className="detail-router-map-btn" to="/map">
        <img src={goMap} alt="goMap" />
      </Link>
      <Link className="detail-router-hot-btn" to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </nav>
  )
}

export default DetailSubwayPage
