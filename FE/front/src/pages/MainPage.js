import * as React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./MainPage.css";

import SubwayBoard from "../components/mainpage/SubwayBoard";
import GetCoinItem from "../components/mainpage/GetCoinItem";
import Modal from "../components/mainpage/Modal";

import character from "../asset/image/moving_man.gif";
import goMap from "../asset/image/goMap.png";
import menuBtn from "../asset/image/mainpage_menu_btn.png";
import getCoinImage from "../asset/image/get_coin_btn.png";
import getCoinFullImage from "../asset/image/Full_Charge_Btn.png";
import LoadingEffect from "../asset/image/pvpPageLoading.gif";

// import menuToggle from "../asset/image/menu_toggle.png";
// import menuToggle2 from "../asset/image/menu_toggle2.png";
import menuToggle3 from "../asset/image/menu_toggle3.png";

function MainPage() {
  // 수동 채굴한 갯수 데이터 받아서 coinCntData에 넣으면 됨
  let coinCntData = 0;
  const navigate = useNavigate();
  const [isReady, setIsReady] = React.useState(false); // 비동기 오류 방지
  const [isEnough, setIsEnough] = React.useState(false); // 100개 모았는지 확인
  const [coinCnt, setCoinCnt] = React.useState(coinCntData); // 수동채굴 아이템 수집량
  const [getCoinClick, setGetCoinClick] = React.useState(false); // 수집량 만족 후 클릭 여부
  const [pvpRouterClick, setPvpRouterClick] = React.useState(false); // pvp 로딩 테스트 버튼
  const [modalClick, setModalClick] = React.useState(false);
  const [menuClick, setMenuClick] = React.useState(false);
  const [selectIdx, setSelectIdx] = React.useState(0);

  // Modal창 띄우기
  function modalClickHandler() {
    setModalClick(true);
  }

  // Menu 버튼
  function menuClickHandler() {
    setMenuClick((prev) => !prev);
    const selectTags = document.getElementsByClassName('menu-select');
    for (let idx=0; idx<3; idx++) {
      console.log(selectTags);
      selectTags[idx].addEventListener('click', () => setSelectIdx(idx))
      .then(() => setModalClick(true));
    }
  }

  // 매칭 잡혔을 때의 로딩 이펙트 테스트용 함수
  function pvpRouterClickHandler() {
    setPvpRouterClick(true);
    const targetTag = document.getElementsByClassName("subway-background")[0];
    const blackBackgroundTag = document.createElement("div");
    setTimeout(() => {
      setPvpRouterClick(false);
      blackBackgroundTag.classList.add("black-background");
      targetTag.appendChild(blackBackgroundTag);
    }, 700);
    setTimeout(() => {
      navigate("/pvp");
    }, 1200);
  }

  // 하위 컴포넌트로 상속할 함수
  function setCoinCntHandler() {
    setCoinCnt((prevCnt) => {
      if (prevCnt < 4) {
        // 수집량 조건 -> 5개
        setIsEnough(false);
        return prevCnt + 1;
      } else {
        setIsEnough(true);
        if (prevCnt === 4) {
          return prevCnt + 1;
        } else {
          return prevCnt;
        }
      }
    });
  }

  // 수동 채굴 아이템 수집량에 따른 버튼 이미지 변환
  React.useEffect(() => {
    // 수동채굴 아이템 수집량 조건을 만족하고, 바뀐 버튼을 클릭했을 때
    function clickEventHandler(event) {
      if (event.target.style.animationName === "click") {
        event.target.style.animationPlayState = "paused";
        event.target.style.animationName = "none";
        setTimeout(() => {
          event.target.style.animationName = "click";
          event.target.style.animationPlayState = "running";
        }, 10);
      } else {
        event.target.style.animation = "click 1s ease-out";
      }
      event.target.removeEventListener("click", clickEventHandler);
      setTimeout(() => {
        setIsEnough(false);
        setCoinCnt(0);
        setGetCoinClick(true);
      }, 1000);
    }

    const getCoinBtnTag = document.getElementsByClassName("get-coin-btn")[0];
    if (isEnough) {
      getCoinBtnTag.style.backgroundImage = `url(${getCoinFullImage})`;
      getCoinBtnTag.addEventListener("click", clickEventHandler);
    } else {
      getCoinBtnTag.style.backgroundImage = `url(${getCoinImage})`;
    }
  }, [isEnough]);

  React.useEffect(() => {
    setIsReady(true);
  }, []);

  return (
    <div className="subway-background">
      <SubwayBoard
        getCoinClick={getCoinClick}
        setGetCoinClick={setGetCoinClick}
      />
      {menuClick && (
        <div
          className="menu-toggle-bg"
          onClick={() => {
            setMenuClick((prev) => !prev);
          }}
        ></div>
      )}
      {menuClick && (
        <div className="menu-toggle-wrap">
          <img className="menu-toggle-img" src={menuToggle3} alt="menu_toggle" />
          <div className="menu-select-list">
            <img className="menu-select" src={menuBtn} alt="menu_toggle" />
            <img className="menu-select" src={menuBtn} alt="menu_toggle" />
            <img className="menu-select" src={menuBtn} alt="menu_toggle" />
          </div>          
        </div>
      )}
      <div className="subway">
        {modalClick && (
          <Modal
            modalWidth={85}
            modalHeight={75}
            selectModalIdx={selectIdx}
            setModalClick={setModalClick}
            nickname={"우주최강원석"}
          />
        )}
        {pvpRouterClick && (
          <img
            className="test-loading-effect"
            src={LoadingEffect}
            alt="Loading Effect"
          />
        )}
        <img className="character" src={character} alt="character" />
        <img
          className="main-menu-btn"
          src={menuBtn}
          alt="menuBtn"
          onClick={menuClickHandler}
        />
        <div className="get-coin-btn">
          {!isEnough && <div className="get-coin-cnt">{coinCnt}</div>}
        </div>
        <Link className="main-router-map-btn" to="/map">
          <img src={goMap} alt="goMap" />
        </Link>
      </div>
      {isReady && (
        <GetCoinItem isEnough={isEnough} getCoinCnt={setCoinCntHandler} />
      )}
      <div className="main-router-pvp" onClick={pvpRouterClickHandler}>
        pvp
      </div>
    </div>
  );
}

export default MainPage;
