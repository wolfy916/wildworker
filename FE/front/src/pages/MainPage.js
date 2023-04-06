import * as React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./MainPage.css";

import SubwayBoard from "../components/mainpage/SubwayBoard";
import GetCoinItem from "../components/mainpage/GetCoinItem";
import Modal from "../components/mainpage/Modal";
import MenuBar from "../components/mainpage/MenuBar";
import StationEvent from "../components/mainpage/StationEvent";

import character_man from "../asset/image/moving_man.gif";
import character_woman from "../asset/image/moving_woman1.gif";
import goMap from "../asset/image/goMap.png";
import getCoinImage from "../asset/image/get_coin_btn.png";
import getCoinFullImage from "../asset/image/Full_Charge_Btn.png";
import LoadingEffect from "../asset/image/pvpPageLoading.gif";
import morningBackgroundImg from "../asset/image/test_morning.png";
import dominator_speaker from "../asset/image/dominator_speaker.png";

import { getUserInfo } from "../api/User";

function MainPage(props) {
  const navigate = useNavigate();
  const stompClient = props.stompClient;
  const coinCnt = props.userData.collectedPapers;

  const [isReady, setIsReady] = React.useState(false); // 비동기 오류 방지

  const [isEnough, setIsEnough] = React.useState(false); // 수동채굴 수집량 달성 여부 확인
  const [getCoinClick, setGetCoinClick] = React.useState(false); // 수집량 만족 후 클릭 여부
  const [isClickDoc, setIsClickDoc] = React.useState(false); // 서류 click시

  const [modalClick, setModalClick] = React.useState(false); // 메인페이지의 메뉴바 클릭 여부

  const [titleModalClick, setTitleModalClick] = React.useState(false); // 메인페이지의 칭호 메뉴 획득하면 띄움
  const [dominatorMsgModalClick, setDominatorMsgModalClick] =
    React.useState(false); // 지배자 한마디 모달
  const [selectIdx, setSelectIdx] = React.useState(0); // 모달창에 띄울 컨텐츠 인덱스
  const [isToggled, setIsToggled] = React.useState(
    Boolean(props.userData.titleType)
  ); // 유저의 칭호타입

  const characterList = [character_man, character_woman];

  const [currentStation, setCurrentStation] = React.useState("");
  const [startStationEvent, setStartStationEvent] = React.useState(false);
  const eventStationList = [
    "역삼역",
    "신도림역",
    "잠실역",
    "홍대입구역",
    "사당역",
    "멀티캠퍼스",
  ];

  // 지하철 역에 맞는 이벤트 실행
  React.useEffect(() => {
    const eventStationCheck = async () => {
      if (props.store.locationData.current) {
        if (props.store.locationData.current.name !== currentStation) {
          setCurrentStation(props.store.locationData.current.name);
          await setStartStationEvent(false);
          if (
            eventStationList.includes(props.store.locationData.current.name)
          ) {
            setStartStationEvent(true);
          }
        }
      } else {
        setStartStationEvent(false);
      }
    };
    eventStationCheck();
  }, [props.store.locationData.current]);

  React.useEffect(() => {
    setIsReady(true);
    props.setIsLogin(true);
    getUserInfo({ setFunc: props.setUserData });

    if (coinCnt > 30) {
      setIsEnough(true);
    }

    // 06 ~ 16시는 아침 이미지
    // 17 ~ 05시는 밤 이미지
    let today = new Date();
    let hours = today.getHours();
    if (5 < hours && hours < 17) {
      const backgroundTag = document.querySelector(".subway-background");
      backgroundTag.style.backgroundImage = `url(${morningBackgroundImg})`;
    }
  }, []);

  // socket : 서류 수집량 조건 만족 -> 제출
  const handleGetCoin = () => {
    const message = "서류를 다 모아서 가방을 클릭했어요";
    stompClient.send("/pub/system/mining/sell", {}, message);
  };

  // socket : 서류클릭 신호 송신
  const handleGetCnt = () => {
    const message = "서류를 클릭했어요";
    stompClient.send("/pub/system/mining/collect", {}, message);
  };

  React.useEffect(() => {
    if (isReady) {
      if (coinCnt < 29) {
        setIsEnough(false);
        handleGetCnt();
      } else if (coinCnt === 29) {
        setIsEnough(true);
        handleGetCnt();
      } else if (coinCnt > 29) {
        setIsEnough(true);
      }
    }
  }, [isClickDoc]);

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
        setIsEnough(false); // 수집 버튼 되돌리기
        props.setUserData((prev) => {
          return {
            ...prev,
            collectedPapers: 0,
          };
        });
        setGetCoinClick(true); // 전광판에 돈 갱신 이펙트 발생시키는 트리거
        handleGetCoin(); // 소켓으로 수집량 달성 신호 송신
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

  // 매칭 잡혔을 때 로딩 이펙트 시작 + navigate
  React.useEffect(() => {
    if (props.isMatched) {
      const targetTag = document.getElementsByClassName("subway-background")[0];
      const blackBackgroundTag = document.createElement("div");
      setTimeout(() => {
        props.setIsMatched(false);
        blackBackgroundTag.classList.add("black-background");
        targetTag.appendChild(blackBackgroundTag);
      }, 700);
      setTimeout(() => {
        navigate("/pvp");
      }, 1200);
    }
  }, [props.isMatched]);

  // 칭호 획득 시 ( 처음에는 getTitle은 빈문자열 )
  const getTitle = "";
  React.useEffect(() => {
    if (getTitle) {
      setTitleModalClick(true);
    }
  }, [getTitle]);

  function dominatorMsgModalClickHandler() {
    setDominatorMsgModalClick(true);
  }

  // 메인 페이지 여부 보내주기
  const [isMain, setIsMain] = React.useState(true);

  React.useEffect(() => {
    if (isMain) {
      const message = JSON.stringify({ isMainPage: true });
      stompClient.send("/pub/minigame/mainpage", {}, message);
      setIsMain(false);
    }
  }, [stompClient]);

  return (
    <div className="subway-background">
      <SubwayBoard
        getCoinClick={getCoinClick}
        setGetCoinClick={setGetCoinClick}
        userData={props.userData}
        setUserData={props.setUserData}
        store={props.store}
        setStore={props.setStore}
        subwayContentIdx={props.subwayContentIdx}
        setSubwayContentIdx={props.setSubwayContentIdx}
      />
      <div className="subway">
        {modalClick && (
          <Modal
            modalWidth={85}
            modalHeight={75}
            selectModalIdx={selectIdx}
            setModalClick={setModalClick}
            store={props.store}
            setStore={props.setStore}
            userData={props.userData}
            setUserData={props.setUserData}
            myTitles={props.myTitles}
            setMyTitles={props.setMyTitles}
            isToggled={isToggled}
            setIsToggled={setIsToggled}
            myCoinLogs={props.myCoinLogs}
            setMyCoinLogs={props.setMyCoinLogs}
            setNicknameErr={props.setNicknameErr}
          />
        )}
        {props.isMatched && (
          <img
            className="pvp-loading-effect"
            src={LoadingEffect}
            alt="Loading Effect"
          />
        )}
        <div className="character-nickname-title">
          <div className="character-title">
            {props.userData.title.name === "x"
              ? " "
              : props.userData.title.name}
          </div>
          <div className="character-nickname">{props.userData.name}</div>
          {props.userData.characterType + 1 && (
            <img
              className="character"
              src={characterList[props.userData.characterType]}
              alt="character"
              onClick={() => {
                const titleTag = document.querySelector(".character-title");
                if (titleTag.style.visibility === "visible") {
                  titleTag.style.visibility = "hidden";
                } else {
                  titleTag.style.visibility = "visible";
                }
              }}
            />
          )}
        </div>
        {!props.isMatched && (
          <MenuBar
            setModalClick={setModalClick}
            setSelectIdx={setSelectIdx}
            setMyTitles={props.setMyTitles}
          />
        )}
        <div className="get-coin-btn">
          {!isEnough && <div className="get-coin-cnt">{coinCnt}</div>}
        </div>
        <Link className="main-router-map-btn" to="/map">
          <img src={goMap} alt="goMap" />
        </Link>
      </div>
      {isReady && (
        <GetCoinItem
          isEnough={isEnough}
          userData={props.userData}
          setUserData={props.setUserData}
          setIsClickDoc={setIsClickDoc}
        />
      )}
      {startStationEvent && (
        <StationEvent
          startStationEvent={startStationEvent}
          stationName={
            props.store.locationData.current
              ? props.store.locationData.current.name
              : "없음"
          }
        />
      )}
      <img
        onClick={dominatorMsgModalClickHandler}
        className="main-dominator-msg-btn"
        src={dominator_speaker}
        alt="dominator_speaker"
      />
      {titleModalClick && (
        <Modal
          modalWidth={85}
          modalHeight={35}
          selectModalIdx={4}
          getTitle={props.store.getTitle}
          setTitleModalClick={setTitleModalClick}
        />
      )}
      {dominatorMsgModalClick && (
        <Modal
          modalWidth={85}
          modalHeight={35}
          selectModalIdx={5}
          stompClient={props.stompClient}
          setModalClick={setDominatorMsgModalClick}
          store={props.store}
        />
      )}
      {props.isGetError && (
        <Modal
          modalWidth={70}
          modalHeight={30}
          selectModalIdx={7}
          selectErrorIdx={0}
          setModalClick={props.setIsGetError}
          store={props.store}
        />
      )}
      {props.nicknameErr && (
        <Modal
          modalWidth={70}
          modalHeight={30}
          selectModalIdx={7}
          selectErrorIdx={1}
          setModalClick={props.setNicknameErr}
          store={props.store}
        />
      )}
    </div>
  );
}

export default MainPage;
