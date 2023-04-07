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
import two_img from "../asset/image/two_img.png";
import king from "../asset/image/king.png";

import { getStationStake } from "../api/Investment.js";

function DetailSubwayPage(props) {
  const location = useLocation();
  const [cnt, setCnt] = useState(0);
  const [isRetry, setIsRetry] = useState(false);

  const [rankingData, setRankingData] = useState([]);
  const [modalClick, setModalClick] = useState(false);

  useEffect(() => {
    // cnt 5번은 해야 실시간으로 다 바뀜
    if (cnt < 10) {
      const fetchData = async () => {
        console.log(location.state);
        console.log(props.stationStake);
        await getStationStake({
          stationId: location.state,
          setFunc: props.setStationStake,
        });
        const rankingDataSave = props.stationStake.ranking.map((item, idx) => (
          <div className="detail-content" key={idx}>
            {idx === 0 ? (
              <div className="detail-div-size">
                <p className="detail-subject-king">
                  <img className="detail-kingimg" src={king} alt="king" />{" "}
                  {item.name}
                </p>
              </div>
            ) : (
              <div>
                <p className="detail-subject">{item.name}</p>
              </div>
            )}
            <div>
              <p className="detail-subject">
                {item.investment.toLocaleString("ko-KR")}원
              </p>
              <p className="detail-subject-2">({item.percent}%)</p>
            </div>
          </div>
        ));
        setRankingData(rankingDataSave);
        setCnt((prev) => (prev += 1));
      };
      fetchData();
    }
  }, [props.stationStake, isRetry]);

  return (
    <div className="detail-background">
      <div className="detail-holder">
        <div className="detail-title">
          {props.stationStake.dominator ? (
            <p className="detail-subject">
              {props.stationStake.dominator}의{" "}
              <img className="detail-twoimg" src={two_img} alt="two_img" />
              {props.stationStake.stationName}
            </p>
          ) : (
            <p className="detail-subject">
              <img className="detail-twoimg" src={two_img} alt="two_img" />{" "}
              {props.stationStake.stationName}
            </p>
          )}
        </div>

        <div className="detail-content-box">
          <div className="detail-content">
            <div>
              <p className="detail-subject">총 투자금액</p>
            </div>
            <div>
              <p className="detail-subject">
                {props.stationStake.totalInvestment.toLocaleString("ko-KR")}
              </p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">수수료 총액</p>
            </div>
            <div>
              <p className="detail-subject">
                {props.stationStake.currentCommission.toLocaleString("ko-KR")}
              </p>
            </div>
          </div>
          <div className="detail-content">
            <div>
              <p className="detail-subject">10분간 누적 수수료</p>
            </div>
            <div>
              <p className="detail-subject">
                {props.stationStake.prevCommission.toLocaleString("ko-KR")}
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
        <div className="detail-scroll-div">{rankingData}</div>
      </div>

      <div className="detail-mine">
        <div>
          <p className="detail-subject-1">나의 랭킹 및 정보</p>
          {props.stationStake.mine ? (
            <p className="detail-subject-1">
              {props.stationStake.mine.rank === 0
                ? 1
                : props.stationStake.mine.rank}
              등 {props.stationStake.mine.investment.toLocaleString("ko-KR")}원
              ({props.stationStake.mine.percent}%)
            </p>
          ) : (
            <p className="detail-subject-1">투자한 기록이 없어요.</p>
          )}
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
          modalHeight={55}
          selectModalIdx={3}
          detailOrHot={0}
          stationId={location.state}
          investment={
            props.stationStake.mine
              ? props.stationStake.mine.investment.toLocaleString("ko-KR")
              : 0
          }
          setModalClick={setModalClick}
          setUserData={props.setUserData}
          setCnt={setCnt}
          setIsRetry={setIsRetry}
          setInvestErr={props.setInvestErr}
        />
      )}
    </div>
  );
}

export default DetailSubwayPage;
