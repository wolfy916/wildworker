import * as React from "react";
import "./SubwayBoardFirstPart.css";

function SubwayBoardFirstPart(props) {
  const [contentIdx, setContentIdx] = React.useState(0);

  const stationContent = (
    <div className="board-content">
      {/* <div className="subway-board-grid"></div> */}
      <span className="current-station-info">이번 역</span>
      <span className="current-station-info2">{props.store.locatinData}</span>
    </div>
  );
  const coinContent = (
    <div className="board-content" style={{ justifyContent: "space-around" }}>
      <span className="coin-text">●</span>
      <span className={`board-coin ${props.isFlashing ? "flash" : ""}`}>
        {props.userData.coin ? props.userData.coin.toLocaleString("ko-KR") : ""} 원
      </span>
    </div>
  );
  const dominatorContent = (
    <div className="board-content" style={{ flexDirection: "column" }}>
      <div className="board-dominator-chat">
        <span style={{ color: "#fec189" }}>{`${props.store.dominatorMsg}`}</span> : 하하하, 이거라도 주워오실래요?
      </div>
    </div>
  );

  const contentList = [stationContent, coinContent, dominatorContent];

  function pageMoveClickHandler() {
    setContentIdx((prev) => (prev + 1) % 3);
  }

  return (
    <div className="board-first-part" onClick={pageMoveClickHandler}>
      {contentList[contentIdx]}
    </div>
  );
}
export default SubwayBoardFirstPart;
