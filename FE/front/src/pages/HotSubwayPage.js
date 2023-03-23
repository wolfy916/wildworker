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
  const holderTag = document.getElementsByClassName("hot-holder")[0]

  useEffect(() => {
    axios
      .get("/api/data")
      .then((response) => {
        setData(response.data)
        for (let i = 1; i <= data.investments.length; i++) {
          const divContentTag = document.createElement("div")
          divContentTag.classList.add("hot-content")
          const div1Tag = document.createElement("div")
          const div2Tag = document.createElement("div")
          const p1_1Tag = document.createElement("p")
          p1_1Tag.classList.add("hot-subject")
          const p1_2Tag = document.createElement("p")
          p1_2Tag.classList.add("hot-subject")
          const p1_3Tag = document.createElement("p")
          p1_3Tag.classList.add("hot-subject")
          const p2Tag = document.createElement("p")
          p2Tag.classList.add("hot-subject-2")

          p1_1Tag.innerHTML = data.ranking[i - 1].station.name
          div1Tag.appendChild(p1_1Tag)
          p1_2Tag.innerHTML =
            data.ranking[i - 1].station.totalInvestment.toLocaleString(
              "ko-KR"
            ) + "원"
          p1_3Tag.innerHTML =
            data.ranking[i - 1].station.currentCommission.toLocaleString(
              "ko-KR"
            ) + "원"
          p2Tag.innerHTML =
            "(" +
            data.ranking[i - 1].station.prevCommission.toLocaleString("ko-KR") +
            "원)"
          div2Tag.appendChild(p1_2Tag)
          div2Tag.appendChild(p1_3Tag)
          div2Tag.appendChild(p2Tag)
          divContentTag.appendChild(div1Tag)
          divContentTag.appendChild(div2Tag)
          holderTag.appendChild(divContentTag)
        }
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
