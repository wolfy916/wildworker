import * as React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./HotSubwayPage.css";
import goMap from "../asset/image/goMap.png";
import myMap from "../asset/image/myMap.png";
import hotMap from "../asset/image/hotMap.png";
import two_img from "../asset/image/two_img.png";
import subway_info from "../asset/image/subway_info.png";
import Modal from "../components/mainpage/Modal";
import { getStationRanking, getStationStake } from "../api/Investment.js";

function HotSubwayPage(props) {
  const [modalClick, setModalClick] = useState(false);
  const [cnt, setCnt] = useState(0);
  const [isRetry, setIsRetry] = useState(true);
  const [selectedStationId, setSelectedStationId] = useState(1);
  const [sortingOrder, setSortingOrder] = useState("investment");

  const sortingOptions = [
    { value: "investment", label: "총 투자 금액순" },
    { value: "commission", label: "10분간 수수료순" },
  ];

  function handleSortingChange(event) {
    setSortingOrder(event.target.value);
    setCnt(0);
    setIsRetry((prev) => !prev);
  }

  useEffect(() => {
    if (cnt < 10) {
      function go() {
        getStationRanking({
          size: 5,
          order: sortingOrder,
          setFunc: props.setStationRank,
        });
      }
      setCnt((prev) => prev + 1);
      go();
    }
  }, [props.stationRank, sortingOrder, isRetry]);

  const hotSubwayData = props.stationRank.ranking.map((item, idx) => (
    <div
      className="hot-content"
      key={idx}
      onClick={() => {
        setModalClick(true);
        setSelectedStationId(item.station.id);
        getStationStake({
          stationId: item.station.id,
          setFunc: props.setStationStake,
        });
      }}
    >
      <div className="hot-div-size">
        <p className="hot-subject">
          <img className="hot-twoimg" src={two_img} alt="two_img" />{" "}
          {item.station.name}
        </p>
      </div>
      <div>
        <p className="hot-subject">
          {item.station.totalInvestment.toLocaleString("ko-KR")}원
        </p>
        <p className="hot-subject">
          {item.station.currentCommission.toLocaleString("ko-KR")}원
        </p>
        <p className="hot-subject-2">
          ({item.station.prevCommission.toLocaleString("ko-KR")}원)
        </p>
      </div>
    </div>
  ));

  return (
    <div className="hot-background">
      <div className="hot-holder">
        <div className="hot-title">
          <img
            className="hot-subwayinfo-img"
            src={subway_info}
            alt="subway_info"
          />
          <p className="hot-title-subject">실시간 역 순위</p>
          <select
            className="hot-select"
            value={sortingOrder}
            onChange={handleSortingChange}
          >
            {sortingOptions.map((option) => (
              <option
                className="hot-option"
                key={option.value}
                value={option.value}
              >
                {option.label}
              </option>
            ))}
          </select>
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
        {hotSubwayData}
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
      {modalClick && (
        <Modal
          modalWidth={85}
          modalHeight={55}
          selectModalIdx={3}
          detailOrHot={1}
          setIsRetry={setIsRetry}
          setCnt={setCnt}
          selectedStationId={selectedStationId}
          investment={
            props.stationStake.mine
              ? props.stationStake.mine.investment.toLocaleString("ko-KR")
              : 0
          }
          setModalClick={setModalClick}
          setUserData={props.setUserData}
          setInvestErr={props.setInvestErr}
        />
      )}
    </div>
  );
}

export default HotSubwayPage;
