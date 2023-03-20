import * as React from "react"
import { useState, useEffect } from "react"
import io from "socket.io-client"
import "./SubwayBoard.css"

const SOCKET_SERVER_URL = 'http://localhost:5000';

function SubwayBoard(props) {
  const BADGE = "사당역의 지배자"
  const NICKNAME = "우주최강원석"
  const COIN = 1500
  const [coin, setCoin] = React.useState(COIN);
  const CURRENT_STATION = "역삼역"
  const CURRENT_STATION_DOMINATOR = "매의호크민성"

  const [isFlashing, setIsFlashing] = useState(false)

  function popOpen() {
    document.getElementsByClassName("modal-wrap")[0].style.display = "block"
  }

  function popClose() {
    document.getElementsByClassName("modal-wrap")[0].style.display = "none"
  }

  // <<<<<<<<<<<<<<<<<<<<    1

  let getCoinClick = props.getCoinClick;
  const setGetCoinClick = props.setGetCoinClick;

  React.useEffect(() => {
    if (getCoinClick === true) {
      setCoin((prev) => prev + 100);
      setGetCoinClick(false);
    }
  }, [getCoinClick]);

  return (
    <div>
      <div className="board-modal-wrap">코인획득!!</div>
      <div className="subway-board-container">
        <div className="subway-board-ceiling"></div>
        <div className="subway-board-bridges">
          <div className="subway-board-bridge"></div>
          <div className="subway-board-bridge"></div>
        </div>
        <div className="subway-board-wrapper">
          <div className="subway-board-screen">
            <div className="board-part first-part">
              <span className="board-badge">{BADGE} </span>
              <span className="board-nickname">{NICKNAME}</span>
            </div>
            <div className="board-part second-part">
              <span className={`board-coin ${isFlashing ? "flash" : ""}`}>
                남은 잔액 : {coin.toLocaleString("ko-KR")} 원
              </span>
            </div>
            <div className="board-part third-part">
              이번 역은
              <span className="current-station"> {CURRENT_STATION} </span>
              입니다.
              <span className="current-station">
                {" "}
                {CURRENT_STATION}의 지배자{" "}
              </span>
              는
              <span className="current-station">
                {" "}
                {CURRENT_STATION_DOMINATOR}{" "}
              </span>
              입니다.
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SubwayBoard
