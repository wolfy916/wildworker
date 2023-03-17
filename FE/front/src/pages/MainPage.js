import * as React from "react";
import "./MainPage.css";
import character from "../asset/image/moving_man.gif";
import goMap from "../asset/image/goMap.png";
import menuBtn from "../asset/image/mainpage_menu_btn.png";
import getCoinImage from "../asset/image/get_coin_btn.png";
import getCoinFullImage from "../asset/image/get_coin_item.png";
import testTitleImg from "../asset/image/testTitleImg.png"
import { Link } from "react-router-dom";
import SubwayBoard from "../components/mainpage/SubwayBoard";
import GetCoinItem from "../components/mainpage/GetCoinItem";

function MainPage() {
  // 수동 채굴한 갯수 데이터 받아서 coinCntData에 넣으면 됨
  let coinCntData = 0;
  const [isReady, setIsReady] = React.useState(false); // 비동기 오류 방지
  const [isEnough, setIsEnough] = React.useState(false); // 100개 모았는지 확인
  const [coinCnt, setCoinCnt] = React.useState(coinCntData); // 수동채굴 아이템 수집량
  const [getCoinClick, setGetCoinClick] = React.useState(false); // 수집량 만족 후 클릭 여부

  function popMenuOpen() {
    document.getElementsByClassName("modal-wrap")[0].style.display ='block';
    document.getElementsByClassName("modal-bg")[0].style.display ='block';
  }

  function popMenuClose() {
    document.getElementsByClassName("modal-wrap")[0].style.display ='none';
    document.getElementsByClassName("modal-bg")[0].style.display ='none';
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
        }, 50);
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
    setTimeout(() => {
      setIsReady(true);
    }, 2000);
  }, []);

  return (
    <div className="subway-background">
      <div className="modal-bg" onClick={popMenuClose}></div>
      <div className="modal-wrap">
        <div className="title-cover">
          <img
            src={testTitleImg}
            alt="title Cover"
            className="title-img"
          />
          <p className="title-title">쫄보</p>
        </div>
      </div>
      <SubwayBoard
        getCoinClick={getCoinClick}
        setGetCoinClick={setGetCoinClick}
      />
      <div className="subway">
        <img className="character" src={character} alt="character" />
        <img className="main-menu-btn" src={menuBtn} alt="menuBtn" onClick={popMenuOpen}/>
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
    </div>
  );
}

export default MainPage;
