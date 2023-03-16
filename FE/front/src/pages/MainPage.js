import * as React from "react";
import "./MainPage.css";
import character from "../asset/image/moving_man.gif";
import goMap from "../asset/image/goMap.png";
import menuBtn from "../asset/image/mainpage_menu_btn.png";
import getCoinImage from "../asset/image/get_coin_btn.png";
import getCoinFullImage from "../asset/image/get_coin_item.png";
import { Link } from "react-router-dom";
import SubwayBoard from "../components/mainpage/SubwayBoard";
import GetCoinItem from "../components/mainpage/GetCoinItem";

function MainPage() {
  // 수동 채굴한 갯수 데이터 받아서 coinCntData에 넣으면 됨
  let coinCntData = 0;
  const [isReady, setIsReady] = React.useState(false);  // 비동기 오류 방지
  const [isEnough, setIsEnough] = React.useState(false);  // 100개 모았는지 확인
  const [coinCnt, setCoinCnt] = React.useState(coinCntData);

  // 하위 컴포넌트로 상속할 함수
  function setCoinCntHandler() {
    setCoinCnt((prevCnt) => {
      if (prevCnt < 4){
        setIsEnough(false);
        return prevCnt + 1
      }
      else {
        setIsEnough(true);
        if (prevCnt === 4) {
          return prevCnt + 1;
        }
        else{
          return prevCnt
        }
      }
    });
  }

  function clickEventHandler(event) {
    console.log(" 서류 100개 제출 !!!");
    event.target.style.animation = "click 1s ease-out";
    event.target.removeEventListener("click", clickEventHandler);
    setTimeout(()=>{
      setIsEnough(false);
      setCoinCnt(0);
    }, 1000)
  }

  // 수동 채굴 아이템 수집량에 따른 버튼 이미지 변환
  React.useEffect(() => {
    const getCoinBtnTag = document.getElementsByClassName("get-coin-btn")[0];
    if (isEnough) {
      getCoinBtnTag.style.backgroundImage = `url(${getCoinFullImage})`;
      getCoinBtnTag.addEventListener("click", clickEventHandler);
    } else {
      getCoinBtnTag.style.backgroundImage = `url(${getCoinImage})`;
    }
  },[isEnough]);

  React.useEffect(() => {
    setTimeout(() => {
      setIsReady(true);
    }, 2000);
  }, []);

  return (
    <div className="subway-background">
      <SubwayBoard />
      <div className="subway">
        <img className="character" src={character} alt="character" />
        <img className="main-menu-btn" src={menuBtn} alt="menuBtn" />
          <div className="get-coin-btn">
            {!isEnough && (<div className="get-coin-cnt">{coinCnt}</div>)}
          </div>
        <Link className="router-map-btn" to="/map">
          <img src={goMap} alt="goMap" />
        </Link>
      </div>
      {isReady && (
        <GetCoinItem
          isEnough={isEnough}
          getCoinCnt={setCoinCntHandler}
        />
      )}
    </div>
  );
}

export default MainPage;
