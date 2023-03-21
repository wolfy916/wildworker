import * as React from "react"
import { useLocation } from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "axios"
import { Link } from "react-router-dom"
import "./DetailSubwayPage.css"
import goMap from "../asset/image/goMap.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"

function DetailSubwayPage() {
  const location = useLocation()
  const [data, setData] = useState([]);
  const holderTag = document.getElementsByClassName("detail-holder")[0]
  const testData = {
    stationName: "역삼역",
    dominator: "S2태형S2",
    totalInvestment: 10000000,
    prevCommission: 12345,
    currentCommission: 1234,
    ranking: [
      { 
        rank: 1,
        name: "S2태형S2",
        investment: 123,
        percent: 10 
      }
    ],
    mine: 	
      { 
        rank: 1,
        investment: 123,
        percent: 10 
      }
  }

  useEffect(() => {
    axios.get(`/api/${location.state}`)
      .then(response => {
        setData(response.data);
        for (let i = 1; i <= data.investments.length; i++) {
          const divContentTag = document.createElement("div")
          divContentTag.classList.add("detail-content")
          const div1Tag = document.createElement("div")
          const div2Tag = document.createElement("div")
          const p1_1Tag = document.createElement("p")
          p1_1Tag.classList.add("detail-subject")
          const p1_2Tag = document.createElement("p")
          p1_2Tag.classList.add("detail-subject")
          const p2Tag = document.createElement("p")
          p2Tag.classList.add("detail-subject-2")

          p1_1Tag.innerHTML = data.ranking.name
          div1Tag.appendChild(p1_1Tag)
          p1_2Tag.innerHTML = data.ranking.investment.toLocaleString("ko-KR") + "원"
          p2Tag.innerHTML =  "(" + data.ranking.percent +"%)"
          div2Tag.appendChild(p1_2Tag)
          div2Tag.appendChild(p2Tag)
          divContentTag.appendChild(div1Tag)
          divContentTag.appendChild(div2Tag)
          holderTag.appendChild(divContentTag)
        
        }}
      )
      .catch(error => {
        console.log(error);
      });
  }, []);

  

  return (
    <div className="detail-background">
      <div className="detail-holder">
        <div className="detail-title">
          <p className="detail-subject">{testData.dominator}의 {testData.stationName}</p>
        </div>

        <div className="detail-content-box">
          <div className="detail-content">
            <div>
              <p className="detail-subject">총 투자금액</p>
            </div>
            <div>
              <p className="detail-subject">{testData.totalInvestment.toLocaleString("ko-KR")}</p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">수수료 총액</p>
            </div>
            <div>
              <p className="detail-subject">{testData.currentCommission.toLocaleString("ko-KR")}</p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">10분간 누적 수수료</p>
            </div>
            <div>
              <p className="detail-subject">{testData.prevCommission.toLocaleString("ko-KR")}</p>
            </div>
          </div>
        </div>
        <div className="detail-subtitle">
          <div>
            <p className="detail-subject">닉네임</p>
            <p className="detail-subject-2">(순위순)</p>
          </div>
          <div>
            <p className="detail-subject">투자금액</p>
            <p className="detail-subject-2">(지분율)</p>
          </div>
        </div>
        <div className="detail-content">
          <div>
            <p className="detail-subject">zl존원석</p>
          </div>
          <div>
            <p className="detail-subject">5,000,000원</p>
            <p className="detail-subject-2">(24%)</p>
          </div>
        </div>
        <div className="detail-content">
          <div>
            <p className="detail-subject">S2권태형S2</p>
          </div>
          <div>
            <p className="detail-subject">4,000,000원</p>
            <p className="detail-subject-2">(20%)</p>
          </div>
        </div>
        <div className="detail-content">
          <div>
            <p className="detail-subject">행인</p>
          </div>
          <div>
            <p className="detail-subject">3,000,000원</p>
            <p className="detail-subject-2">(16%)</p>
          </div>
        </div>
        <div className="detail-content">
          <div>
            <p className="detail-subject">아무나</p>
          </div>
          <div>
            <p className="detail-subject">2,000,000원</p>
            <p className="detail-subject-2">(24%)</p>
          </div>
        </div>
      </div>

      <div className="detail-mine">
        <div>
          <p className="detail-subject-1">나의 랭킹 및 정보</p>
          <p className="detail-subject-1">{testData.mine.rank}등 {testData.mine.investment.toLocaleString("ko-KR")}({testData.mine.percent}%)</p>
        </div>
        <div>
          <button className="detail-subject">투자하기</button>
        </div>
      </div>

      <Link className="detail-router-my-btn" to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link className="detail-router-map-btn" to="/map">
        <img src={goMap} alt="goMap" />
      </Link>
      <Link className="detail-router-hot-btn" to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </div>
  )
}

export default DetailSubwayPage
