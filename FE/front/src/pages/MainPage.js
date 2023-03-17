import * as React from "react"
// import { useState } from "react"
import "./MainPage.css"
import character from "../asset/image/moving_man.gif"
import goMap from "../asset/image/goMap.png"
import menuBtn from "../asset/image/mainpage_menu_btn.png";
import getCoinBtn from "../asset/image/get_coin_btn.png"
import testTitleImg from "../asset/image/testTitleImg.png"
import { Link } from "react-router-dom"
import SubwayBoard from "../components/mainpage/SubwayBoard";

function MainPage() {

  function popMenuOpen() {
      document.getElementsByClassName("modal-wrap")[0].style.display ='block';
      document.getElementsByClassName("modal-bg")[0].style.display ='block';
  }

  function popMenuClose() {
      document.getElementsByClassName("modal-wrap")[0].style.display ='none';
      document.getElementsByClassName("modal-bg")[0].style.display ='none';
  }

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
      <SubwayBoard />
      <div className="subway">
        <img className="character" src={character} alt="character" />
        <img className="main-menu-btn" src={menuBtn} alt="menuBtn" onClick={popMenuOpen}/>
        <img className="get-coin-btn" src={getCoinBtn} alt="getCoinBtn" />
        <Link className="main-router-map-btn" to="/map">
          <img src={goMap} alt="goMap" />
        </Link>
      </div>
    </div>
  );
}

export default MainPage;
