import * as React from "react";
import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./DetailSubwayPage.css";
import goMap from "../asset/image/goMap.png";
import myMap from "../asset/image/myMap.png";
import hotMap from "../asset/image/hotMap.png";
import Modal from "../components/mainpage/Modal";
import { getStationStake } from "../api/Investment.js";

function DetailSubwayPage() {
  const location = useLocation();
  const [data, setData] = useState([])
  const detailSubwayTotalData = getStationStake(location.state);
  const [ranking, setRanking] = useState([]);
  const [modalClick, setModalClick] = useState(false);
  const testData = {
    stationName: "역삼역",
    dominator: "S2태형S2",
    totalInvestment: 10000000,
    prevCommission: 12345,
    currentCommission: 1234,
    ranking: [
      {
        rank: 1,
        name: "zl존원석",
        investment: 12341,
        percent: 20,
      },
      {
        rank: 2,
        name: "S2태형S2",
        investment: 123,
        percent: 10,
      },
    ],
    mine: {
      rank: 1,
      investment: 123,
      percent: 10,
    },
  };
  
  // useEffect(() => {
  //   axios
  //     .get(`/api/${location.state}`)
  //     .then((response) => {
  //       setData(response.data)
  //       const rankingData = data.ranking.map((item) => (
  //         <div className="detail-content">
  //           <div>
  //             <p className="detail-subject">{item.name}</p>
  //           </div>
  //           <div>
  //             <p className="detail-subject">
  //               {item.investment.toLocaleString("ko-KR")}
  //             </p>
  //             <p className="detail-subject-2">({item.percent}%)</p>
  //           </div>
  //         </div>
  //       ))
  //       setRanking(rankingData)
  //     })
  //     .catch((error) => {
  //       console.log(error)
  //     })
  // }, [])


  useEffect(() => {
    const rankingData = detailSubwayTotalData.ranking.map((item) => (
      <div className="detail-content">
        <div>
          <p className="detail-subject">{item.name}</p>
        </div>
        <div>
          <p className="detail-subject">
            {item.investment.toLocaleString("ko-KR")}
          </p>
          <p className="detail-subject-2">({item.percent}%)</p>
        </div>
      </div>
    ));
    setRanking(rankingData);
  }, []);

  return (
    <div className="detail-background">
      <div className="detail-holder">
        <div className="detail-title">
          <p className="detail-subject">
            {testData.dominator}의 {testData.stationName}
          </p>
        </div>

        <div className="detail-content-box">
          <div className="detail-content">
            <div>
              <p className="detail-subject">총 투자금액</p>
            </div>
            <div>
              <p className="detail-subject">
                {testData.totalInvestment.toLocaleString("ko-KR")}
              </p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">수수료 총액</p>
            </div>
            <div>
              <p className="detail-subject">
                {testData.currentCommission.toLocaleString("ko-KR")}
              </p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">10분간 누적 수수료</p>
            </div>
            <div>
              <p className="detail-subject">
                {testData.prevCommission.toLocaleString("ko-KR")}
              </p>
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
        {ranking}
      </div>

      <div className="detail-mine">
        <div>
          <p className="detail-subject-1">나의 랭킹 및 정보</p>
          <p className="detail-subject-1">
            {testData.mine.rank}등{" "}
            {testData.mine.investment.toLocaleString("ko-KR")}(
            {testData.mine.percent}%)
          </p>
        </div>
        <div>
          <button
            className="detail-invest"
            onClick={() => {
              setModalClick(true);
            }}
          >
            투자하기
          </button>
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
      {modalClick && (
        <Modal
          modalWidth={85}
          modalHeight={75}
          selectModalIdx={3}
          stationId={location.state}
          investment={testData.mine.investment.toLocaleString("ko-KR")}
          setModalClick={setModalClick}
        />
      )}
    </div>
  );
}

export default DetailSubwayPage;
