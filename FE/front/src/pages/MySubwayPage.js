import * as React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./MySubwayPage.css";
import goMap from "../asset/image/goMap.png";
import myMap from "../asset/image/myMap.png";
import hotMap from "../asset/image/hotMap.png";

import { getMyInvestList } from "../api/Investment";

function MySubwayPage(props) {
  const payload = {
    order: "investment",
    ascend: "DESC",
    setFunc:props.setMyInvestList
  };
  const myInvestListData = getMyInvestList(payload)
  const [investData, setInvestData] = useState([])
  const [data, setData] = useState([]);
  const [mySubway, setMySubway] = useState([]);



  useEffect(() => {
    setInvestData(myInvestListData.investments)
        const mySubwayData = investData.investments.map((item) => (
          <div className="my-content">
            <div>
              <p className="my-subject">{item.station.name}</p>
            </div>
            <div>
              <p className="my-subject">{item.investment}</p>
              <p className="my-subject-2">({item.percent}%)</p>
            </div>
          </div>
        ))
        setMySubway(mySubwayData)
  }, [])

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
        {mySubway}
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
  );
}

export default MySubwayPage;
