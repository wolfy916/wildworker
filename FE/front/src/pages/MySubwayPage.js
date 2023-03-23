import * as React from "react"
import { useState, useEffect } from "react"
import axios from "axios"
import { Link } from "react-router-dom"
import "./MySubwayPage.css"
import goMap from "../asset/image/goMap.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"

function MySubwayPage() {
  // const [data, setData] = useState([]);
  // const holderTag = document.getElementsByClassName("my-holder")[0]

  // useEffect(() => {
  //   axios.get('/api/data')
  //     .then(response => {
  //       setData(response.data);
  //       for (let i=1; i <= data.investments.length; i++) {
  //         const divContentTag = document.createElement("div")
  //         divContentTag.classList.add("my-content")
  //         const div1Tag = document.createElement("div")
  //         const div2Tag = document.createElement("div")
  //         const p1_1Tag = document.createElement("p")
  //         p1_1Tag.classList.add("my-subject")
  //         const p1_2Tag = document.createElement("p")
  //         p1_2Tag.classList.add("my-subject")
  //         const p2Tag = document.createElement("p")
  //         p2Tag.classList.add("my-subject-2")

  //         p1_1Tag.innerHTML = data.investments[i-1].station.name
  //         div1Tag.appendChild(p1_1Tag)
  //         p1_2Tag.innerHTML = data.investments[i-1].investment.toLocaleString("ko-KR") + "원"
  //         p2Tag.innerHTML =  "(" +data.investments[i-1].percent +"%)"
  //         div2Tag.appendChild(p1_2Tag)
  //         div2Tag.appendChild(p2Tag)
  //         divContentTag.appendChild(div1Tag)
  //         divContentTag.appendChild(div2Tag)
  //         holderTag.appendChild(divContentTag)
  //       }
  //     })
  //     .catch(error => {
  //       console.log(error);
  //     });
  // }, []);

  return (
    <div className="my-background">
      <div className="my-holder">
        <div className="my-title">
          <p className="my-subject">나의 투자 내역</p>
        </div>
        <div className="my-subtitle">
          <div>
            <p className="my-subject">역 이름</p>
          </div>
          <div>
            <p className="my-subject">투자 금액</p>
            <p className="my-subject-2">(지분율)</p>
          </div>
        </div>
        <div className="my-content">
          <div>
            <p className="my-subject">역삼역</p>
          </div>
          <div>
            <p className="my-subject">200,000원</p>
            <p className="my-subject-2">(24%)</p>
          </div>
        </div>
        <div className="my-content">
          <div>
            <p className="my-subject">역곡역</p>
          </div>
          <div>
            <p className="my-subject">3,000,000원</p>
            <p className="my-subject-2">(45%)</p>
          </div>
        </div>
        <div className="my-content">
          <div>
            <p className="my-subject">청계산입구역</p>
          </div>
          <div>
            <p className="my-subject">120,000원</p>
            <p className="my-subject-2">(5%)</p>
          </div>
        </div>
      </div>
      <Link className="my-router-my-btn" to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link className="my-router-map-btn" to="/map">
        <img src={goMap} alt="goMap" />
      </Link>
      <Link className="my-router-hot-btn" to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </div>
  )
}

export default MySubwayPage
