import * as React from "react"
import { Link } from "react-router-dom"
import "./DetailSubwayPage.css"

function DetailSubwayPage() {
  return (
    <nav>
    <div>여긴 누른 역 detail</div>
      <Link to="/map/mine">My역으로</Link> |<Link to="/map">노선도로</Link> |
      <Link to="/map/hot">핫한역으로</Link>
    </nav>
  )
}

export default DetailSubwayPage