import * as React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./MainPage.css";

import Stomp from "stompjs"
import SockJS from "sockjs-client"

import SubwayBoard from "../components/mainpage/SubwayBoard";
import GetCoinItem from "../components/mainpage/GetCoinItem";
import Modal from "../components/mainpage/Modal";
import MenuBar from "../components/mainpage/MenuBar";

import character_man from "../asset/image/moving_man.gif";
import character_woman from "../asset/image/moving_woman1.gif";
import goMap from "../asset/image/goMap.png";
import getCoinImage from "../asset/image/get_coin_btn.png";
import getCoinFullImage from "../asset/image/Full_Charge_Btn.png";
import LoadingEffect from "../asset/image/pvpPageLoading.gif";
import morningBackgroundImg from "../asset/image/test_morning.png";

function MainPage() {
  // 수동 채굴한 갯수 데이터 받아서 coinCntData에 넣으면 됨
  let coinCntData = 0;
  const navigate = useNavigate();

  const [isReady, setIsReady] = React.useState(false); // 비동기 오류 방지
  const [isEnough, setIsEnough] = React.useState(false); // 수동채굴 100개 모았는지 확인
  const [coinCnt, setCoinCnt] = React.useState(coinCntData); // 수동채굴 아이템 수집량
  const [getCoinClick, setGetCoinClick] = React.useState(false); // 수집량 만족 후 클릭 여부
  const [pvpRouterClick, setPvpRouterClick] = React.useState(false); // pvp 로딩 테스트 버튼
  const [modalClick, setModalClick] = React.useState(false);
  const [selectIdx, setSelectIdx] = React.useState(0);
  const [isToggled, setIsToggled] = React.useState(false);

  // 유저 관련 정보
  const [badge, setBadge] = React.useState("쫄보");
  const [nickname, setNickname] = React.useState("우주최강원석");
  const [coin, setCoin] = React.useState(1500);
  const [gender, setGender] = React.useState(1);
  const genderList = [character_man, character_woman];

  // 현재역 관련 정보
  const [station, setStation] = React.useState("역삼역");
  const [dominator, setDominator] = React.useState("매의호크민성");
  const socket = new SockJS("https://j8a304.p.ssafy.io/api/v1/ws")
  const stompClient = Stomp.over(socket)

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
  
  // 서류 눌렀을 때마다 보내는거
  const handleGetCnt = () => {
    const message = "서류 - back요청대로 보내야함"
    stompClient.send("/pub/system/mining/collect", {}, message)
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
    handleGetCnt();
  }

  // 수동채굴 다 채우고 가방눌러서 코인 받을 때
  const handleGetCoin = () => {
    const message = "가방 - back요청대로 보내야함"
    stompClient.send("/pub/system/mining/sell", {}, message)
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
        handleGetCoin();
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

    // 06 ~ 16시는 아침 이미지
    // 17 ~ 05시는 밤 이미지
    let today = new Date();   
    let hours = today.getHours();
    console.log(hours);
    if (5 < hours && hours < 17) {
      const backgroundTag = document.querySelector(".subway-background");
      backgroundTag.style.backgroundImage=`url(${morningBackgroundImg})`;
    }
  }, []);
  return (
    <div className="subway-background">
      <SubwayBoard
        getCoinClick={getCoinClick}
        setGetCoinClick={setGetCoinClick}
        badge={badge}
        setBadge={setBadge}
        nickname={nickname}
        coin={coin}
        setCoin={setCoin}
        station={station}
        setstation={setStation}
        dominator={dominator}
        setDominator={setDominator}
      />
      <div className="subway">
        {modalClick && (
          <Modal
            modalWidth={85}
            modalHeight={75}
            selectModalIdx={selectIdx}
            setModalClick={setModalClick}
            nickname={nickname}
            setNickname={setNickname}
            badge={badge}
            setBadge={setBadge}
            isToggled={isToggled}
            setIsToggled={setIsToggled}
            gender={gender}
            setGender={setGender}
          />
        )}
        {pvpRouterClick && (
          <img
            className="test-loading-effect"
            src={LoadingEffect}
            alt="Loading Effect"
          />
        )}
        <div className="character-nickname-title">
          <div className="character-nickname">{nickname}</div>
          <img className="character" src={genderList[gender-1]} alt="character" onClick={()=>{
            const titleTag = document.querySelector(".character-title");
            if (titleTag.style.visibility === "visible") {
              titleTag.style.visibility = "hidden";
            } else {
              titleTag.style.visibility = "visible";
            }
          }}/>
          <div className="character-title">{badge}</div>
        </div>
        {!pvpRouterClick && <MenuBar setModalClick={setModalClick} setSelectIdx={setSelectIdx} />}
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
