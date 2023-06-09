import * as React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import SubwayTime from "../components/subwaymap/SubwayTime";
import subwaymap from "../asset/image/subwaymap.png";
import goMain from "../asset/image/goMain.png";
import myMap from "../asset/image/myMap.png";
import hotMap from "../asset/image/hotMap.png";
import current_point from "../asset/image/current_point.gif";
import money from "../asset/image/money.gif";
import campusSeoul from "../asset/image/campusSeoul.png";
import campusDaejeon from "../asset/image/campusDaejeon.png";
import campusGwangju from "../asset/image/campusGwangju.png";
import campusGumi from "../asset/image/campusGumi.png";
import campusBuulgyeong from "../asset/image/campusBuulgyeong.png";

import "./SubwayMapPage.css";

import { getMyInvestList } from "../api/Investment";

function SubwayMapPage(props) {
  const stompClient = props.stompClient;
  const [cnt, setCnt] = useState(0);
  const [position, setPosition] = useState({ x: 0, y: 0 });
  const [myStationList, setMyStationList] = useState([]);
  const CURRENT_STATION = props.store.locationData.current
    ? props.store.locationData.current.id
    : "null";
  const [remainSec, setRemainSec] = useState(10000);
  const [zoomLevel, setZoomLevel] = useState(1);
  const [previousZoomLevel, setPreviousZoomLevel] = useState(1);
  const [previousDistance, setPreviousDistance] = useState(0);

  const [MIN_ZOOM_LEVEL, MAX_ZOOM_LEVEL] = [0.6, 1.4];

  function handleDoubleTouchMove(e) {
    if (e.touches.length === 2) {
      const [touch1, touch2] = e.touches;
      const distance = Math.sqrt(
        (touch1.clientX - touch2.clientX) ** 2 +
          (touch1.clientY - touch2.clientY) ** 2
      );
      if (previousDistance === 0) {
        setPreviousDistance(distance);
        setPreviousZoomLevel(zoomLevel);
      } else {
        const delta = distance - previousDistance;
        let newZoomLevel = previousZoomLevel + delta / 500;
        newZoomLevel = Math.max(
          MIN_ZOOM_LEVEL,
          Math.min(MAX_ZOOM_LEVEL, newZoomLevel)
        ); // 범위 제한
        setZoomLevel(newZoomLevel);
        // 확대/축소 이외의 경우에만 setPosition 호출
        const mapElement = document.querySelector(".test");
        const rect = mapElement.getBoundingClientRect();
        const centerX = (touch1.clientX + touch2.clientX) / 2;
        const centerY = (touch1.clientY + touch2.clientY) / 2;

        // 현재 중심점까지의 거리를 구합니다.
        const distanceToCenterX = centerX - rect.width / 2;
        const distanceToCenterY = centerY - rect.height / 2;
        const distanceToCenter = Math.sqrt(
          distanceToCenterX ** 2 + distanceToCenterY ** 2
        );

        // 현재 줌 레벨에서 화면 중심을 중심으로 확대/축소 했을 때,
        // 중심점이 이동해야 할 거리를 계산합니다.
        const newDistanceToCenter =
          (distanceToCenter / zoomLevel) * newZoomLevel;
        const ratio = newDistanceToCenter / distanceToCenter;
        const deltaX = (centerX - rect.width / 2) * (ratio - 1);
        const deltaY = (centerY - rect.height / 2) * (ratio - 1);
        const x = position.x - deltaX;
        const y = position.y - deltaY;
        if (x < 60 && x > -350 && y < 100 && y > -610) {
          setPosition({ x, y });
        }
      }
    }
  }

  function handleTouchEnd() {
    setPreviousDistance(0);
    setPreviousZoomLevel(zoomLevel);
  }

  const handleTouchStart = (e) => {
    const touch = e.touches[0];
    const startX = touch.pageX - position.x;
    const startY = touch.pageY - position.y;

    const handleTouchMove = (e) => {
      const touch = e.touches[0];
      const x = touch.pageX - startX;
      const y = touch.pageY - startY;
      if (x < 150 && x > -500 && y < 180 && y > -610) {
        setPosition({ x, y });
      }
    };

    document.addEventListener("touchmove", handleTouchMove);
    document.addEventListener("touchend", () => {
      document.removeEventListener("touchmove", handleTouchMove);
    });
  };

  const navigate = useNavigate();

  function navigateToPage(stationId) {
    navigate(`/map/detail`, { state: stationId });
  }

  const PAGE_COUNT = 56;

  const currentPointStyle = {
    position: "relative",
    width: "100%",
    height: "100%",
  };

  const blinkAnimation = `
    @keyframes blink {
      50% {
        opacity: 0;
      }
    }
  `;

  useEffect(() => {
    if (cnt < 2) {
      const fetchData = async () => {
        await getMyInvestList({
          order: "investment",
          ascend: "DESC",
          setFunc: props.setMyInvestList,
        });
        const myIdList = props.myInvestList.investments.map(
          (investment) => investment.station.id
        );
        setMyStationList(myIdList);
        setCnt((prev) => (prev += 1));
        setRemainSec(props.myInvestList.remainSec);
      };
      fetchData();
    }
  }, [props.myInvestList]);

  const seoul = 52;
  const daejeon = 53;
  const gwangju = 54;
  const gumi = 55;
  const buulgyeong = 56;

  useEffect(() => {
    if (cnt === 2) {
      const photoMapTag = document.getElementsByName("photo-map")[0];
      for (let i = 1; i <= PAGE_COUNT; i++) {
        const areaTag = document.createElement("area");
        areaTag.setAttribute("alt", "area");
        if (myStationList.includes(i)) {
          const currentPoint = document.createElement("div");
          const imgTag = document.createElement("img");

          imgTag.setAttribute("src", `${money}`);
          imgTag.style.width = "120%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "10%";
          imgTag.style.left = "-20%";
          imgTag.style.zIndex = 1500;
          currentPoint.appendChild(imgTag);

          areaTag.appendChild(currentPoint);
        }
        if (CURRENT_STATION === i) {
          const currentPoint = document.createElement("div");
          currentPoint.style = currentPointStyle;

          const imgTag = document.createElement("img");
          imgTag.setAttribute("src", `${current_point}`);
          imgTag.style.width = "150%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "15%";
          imgTag.style.left = "-30%";
          imgTag.style.zIndex = 2000;
          currentPoint.appendChild(imgTag);

          const blinkAnimationStyle = document.createElement("style");
          blinkAnimationStyle.innerHTML = blinkAnimation;
          currentPoint.appendChild(blinkAnimationStyle);

          // currentPoint.style.animation = "blink 1s linear infinite";
          areaTag.appendChild(currentPoint);
        }

        if (seoul === i) {
          const currentPoint = document.createElement("div");
          const imgTag = document.createElement("img");

          imgTag.setAttribute("src", `${campusSeoul}`);
          imgTag.style.width = "100%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "10%";
          imgTag.style.left = "-20%";
          currentPoint.appendChild(imgTag);

          areaTag.appendChild(currentPoint);
        }
        if (daejeon === i) {
          const currentPoint = document.createElement("div");
          const imgTag = document.createElement("img");

          imgTag.setAttribute("src", `${campusDaejeon}`);
          imgTag.style.width = "100%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "10%";
          imgTag.style.left = "-20%";
          currentPoint.appendChild(imgTag);

          areaTag.appendChild(currentPoint);
        }
        if (gwangju === i) {
          const currentPoint = document.createElement("div");
          const imgTag = document.createElement("img");

          imgTag.setAttribute("src", `${campusGwangju}`);
          imgTag.style.width = "100%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "10%";
          imgTag.style.left = "-20%";
          currentPoint.appendChild(imgTag);

          areaTag.appendChild(currentPoint);
        }
        if (gumi === i) {
          const currentPoint = document.createElement("div");
          const imgTag = document.createElement("img");

          imgTag.setAttribute("src", `${campusGumi}`);
          imgTag.style.width = "100%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "10%";
          imgTag.style.left = "-20%";
          currentPoint.appendChild(imgTag);

          areaTag.appendChild(currentPoint);
        }
        if (buulgyeong === i) {
          const currentPoint = document.createElement("div");
          const imgTag = document.createElement("img");

          imgTag.setAttribute("src", `${campusBuulgyeong}`);
          imgTag.style.width = "100%";
          imgTag.style.position = "absolute";
          imgTag.style.bottom = "10%";
          imgTag.style.left = "-20%";
          currentPoint.appendChild(imgTag);

          areaTag.appendChild(currentPoint);
        }

        areaTag.addEventListener("click", () => {
          return navigateToPage(i);
        });

        photoMapTag.appendChild(areaTag);
      }
      setCnt((prev) => (prev += 1));
    }
  });

  // 메인 페이지 여부 보내주기
  const [isSubwayMap, setIsSubwayMap] = React.useState(true);

  React.useEffect(() => {
    if (isSubwayMap) {
      const message = JSON.stringify({ mainPage: false });
      stompClient.send("/pub/minigame/mainpage", {}, message);
      setIsSubwayMap(false);
    }
  }, [stompClient]);

  return (
    <div>
      <SubwayTime remainSec={remainSec} setRemainSec={setRemainSec} />
      <div className="map-station-color">
        <p className="map-station-color-content">
          <img className="map-station-color-img" src={money} alt="color" /> 나의
          투자역
        </p>
        <p className="map-station-color-content">
          <img
            className="map-station-color-img2"
            src={current_point}
            alt="color"
          />{" "}
          현재 위치역
        </p>
      </div>
      <div
        style={{
          position: "relative",
          width: "100%",
          height: "100vh",
          overflow: "hidden",
        }}
      >
        <div
          className="test"
          style={{
            position: "absolute",
            top: `${position.y}px`,
            left: `${position.x}px`,
            width: "692px",
            height: "824px",
            backgroundImage: `url(${subwaymap})`,
            backgroundPosition: "top left",
            transition: "transform 2s ease-out",
            // transform: "translate(0%,0%) scale(1.4)",
            // transformOrigin: "0 0",
            zoom: zoomLevel,
          }}
          useMap="#photo-map"
          onTouchStart={handleTouchStart}
          onTouchMove={handleDoubleTouchMove}
          onTouchEnd={handleTouchEnd}
        >
          <map name="photo-map"></map>
        </div>
      </div>
      <Link className="map-router-my-btn" to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link className="map-router-main-btn" to="/main">
        <img src={goMain} alt="goMain" />
      </Link>
      <Link className="map-router-hot-btn" to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </div>
  );
}

export default SubwayMapPage;
