import * as React from "react";
import "./SubwayBoard.css";

function SubwayBoard(props) {
  const [isFlashing, setIsFlashing] = React.useState(false);

  let getCoinClick = props.getCoinClick;
  const setGetCoinClick = props.setGetCoinClick;
  const setCoin = props.setCoin;
  
  React.useEffect(() => {
    if (getCoinClick === true) {
      setCoin((prev) => prev + 100);
      setGetCoinClick(false);
      setIsFlashing(true);
      setTimeout(() => {
        setIsFlashing(false);
      }, 1000);
    }
  }, [getCoinClick, setGetCoinClick, setCoin]);

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
            {/* <div className="subway-board-grid"></div> */}
            <div className="board-part first-part">
              <span className="board-badge">{props.badge} </span>
              <span className="board-nickname">{props.nickname}</span>
            </div>
            <div className="board-part second-part">
              <span className={`board-coin ${isFlashing ? "flash" : ""}`}>
                남은 잔액 : {props.coin.toLocaleString("ko-KR")} 원
              </span>
            </div>
            <div className="board-part third-part">
              이번 역은
              <span className="current-station"> {props.station} </span>
              입니다.
              <span className="current-station">
                {" "}
                {props.station}의 지배자{" "}
              </span>
              는<span className="current-station"> {props.dominator} </span>
              입니다.
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SubwayBoard;
