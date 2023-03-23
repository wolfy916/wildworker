import * as React from "react"
import { useState, useEffect } from "react"
import axios from "axios"
import { Link } from "react-router-dom"
import "./HotSubwayPage.css"
import goMap from "../asset/image/goMap.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"

function HotSubwayPage() {
  const [data, setData] = useState([])
  const [hotSubway, setHotSubway] = useState([])

  useEffect(() => {
    axios
      .get("/api/data")
      .then((response) => {
        setData(response.data)
        const hotSubwayData = data.ranking.map((item) => (
          <div className="hot-content">
            <div>
              <p className="hot-subject">{item.station.name}</p>
            </div>
            <div>
              <p className="hot-subject">{item.station.totalInvestment.toLocaleString("ko-KR")}원</p>
              <p className="hot-subject">{item.station.currentCommission.toLocaleString("ko-KR")}원</p>
              <p className="hot-subject-2">({item.station.prevCommission.toLocaleString("ko-KR")}원)</p>
            </div>
          </div>
        ))
        setHotSubway(hotSubwayData)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  return (
    <div className="hot-background">
      <div className="hot-holder">
        <div className="hot-title">
          <p className="hot-subject">실시간 역 순위</p>
        </div>
        <div className="hot-subtitle">
          <div>
            <p className="hot-subject">역 이름</p>
          </div>
          <div>
            <p className="hot-subject">총 투자 금액</p>
            <p className="hot-subject">수수료 총액</p>
            <p className="hot-subject-2">(10분간 누적 수수료 총액)</p>
          </div>
        </div>
        <div className="hot-content">
          <div>
            <p className="hot-subject">강남역</p>
          </div>
          <div>
            <p className="hot-subject">215,200,000원</p>
            <p className="hot-subject">11,600,000원</p>
            <p className="hot-subject-2">(214,200원)</p>
          </div>
        </div>
        <div className="hot-content">
          <div>
            <p className="hot-subject">왕십리역</p>
          </div>
          <div>
            <p className="hot-subject">175,200,000원</p>
            <p className="hot-subject">10,420,000원</p>
            <p className="hot-subject-2">(142,200원)</p>
          </div>
        </div>
        <div className="hot-content">
          <div>
            <p className="hot-subject">신림역</p>
          </div>
          <div>
            <p className="hot-subject">160,320,000원</p>
            <p className="hot-subject">8,240,000원</p>
            <p className="hot-subject-2">(102,200원)</p>
          </div>
        </div>
        {hotSubway}
      </div>

      <Link className="hot-router-my-btn" to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link className="hot-router-map-btn" to="/map">
        <img src={goMap} alt="goMap" />
      </Link>
      <Link className="hot-router-hot-btn" to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </div>
  )
}

export default HotSubwayPage
