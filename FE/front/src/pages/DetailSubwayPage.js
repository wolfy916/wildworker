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
    <div className="detail-background">
      <div className="detail-holder">
        <div className="detail-title">
          <p className="detail-subject">id {location.state}의 역</p>
        </div>
        <div className="detail-content-box">
          <div className="detail-content">
            <div>
              <p className="detail-subject">총 투자금액</p>
            </div>
            <div>
              <p className="detail-subject">120,000,000원</p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">수수료 총액</p>
            </div>
            <div>
              <p className="detail-subject">15,200,000원</p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">10분간 누적 수수료</p>
            </div>
            <div>
              <p className="detail-subject">142,200원</p>
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

      <button className="detail-invest">투자하기</button>

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
