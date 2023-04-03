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
  const [cnt, setCnt] = useState(0);
  const [isRetry, setIsRetry] = useState(true);
  const [mySubway, setMySubway] = useState([]);
  const [sortingOrder, setSortingOrder] = useState("investment");

  const sortingOptions = [
    { value: "investment", label: "나의 투자 금액" },
    { value: "percent", label: "나의 지분율" },
    { value: "name", label: "이름순" },
  ];

  function handleSortingChange(event) {
    setCnt(0);
    setSortingOrder(event.target.value);
  }

  useEffect(() => {
    if (cnt < 2) {
      const fetchData = async () => {
        await getMyInvestList({
          order: sortingOrder,
          ascend: sortingOrder === "name" ? "ASC" : "DESC",
          setFunc: props.setMyInvestList,
        });
        const mySubwayData = props.myInvestList.investments.map((item, idx) => (
          <div className="my-content" key={idx}>
            <div>
              <p className="my-subject">{item.station.name}</p>
            </div>
            <div>
              <p className="my-subject">{item.investment}원</p>
              <p className="my-subject-2">({item.percent}%)</p>
            </div>
          </div>
        ));
        setMySubway(mySubwayData);
        setCnt((prev) => (prev += 1));
      };
      fetchData();
    }
  }, [props.myInvestList, sortingOrder]);

  return (
    <div className="my-background">
      <div className="my-holder">
        <div className="my-title">
          <p className="my-subject">나의 투자 내역</p>
          <select value={sortingOrder} onChange={handleSortingChange}>
            {sortingOptions.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
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
        <div className="my-scroll-div">{mySubway}</div>
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
