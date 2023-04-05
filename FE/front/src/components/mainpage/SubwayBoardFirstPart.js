import * as React from "react";
import "./SubwayBoardFirstPart.css";

function SubwayBoardFirstPart(props) {
  const stationContent = (
    <div className="board-content">
      {/* <div className="subway-board-grid"></div> */}
      <span className="current-station-info">이번 역</span>
      <span className="current-station-info2">
        {props.store.locationData.current != null
          ? props.store.locationData.current.name
          : ""}
      </span>
    </div>
  );
  const coinContent = (
    <div className="board-content" style={{ justifyContent: "space-around" }}>
      <span className="coin-text">●</span>
      <span className={`board-coin ${props.isFlashing ? "flash" : ""}`}>
        {props.userData.coin
          ? props.userData.coin.toLocaleString("ko-KR")
          : "0"}{" "}
        원
      </span>
    </div>
  );

  const dominatorContent = (
    <div className="board-content" style={{ flexDirection: "column" }}>
      <div className="board-dominator-chat">
        {props.store.locationData.current ? (
          props.store.dominatorMsg ? (
            <span style={{ color: "#fec189" }}>
              {props.store.locationData.current.dominator}
            </span>
          ) : (
            ""
          )
        ) : (
          ""
        )}
        {props.store.locationData.current
          ? props.store.dominatorMsg
            ? ` : ${props.store.dominatorMsg}`
            : "지배자의 한마디 없음"
          : "역이 아님"}
      </div>
    </div>
  );

  const contentList = [stationContent, coinContent, dominatorContent];

  function pageMoveClickHandler() {
    props.setSubwayContentIdx((prev) => (prev + 1) % 3);
  }

  return (
    <div className="board-first-part" onClick={pageMoveClickHandler}>
      {props.store.locationData.current != null
        ? contentList[props.subwayContentIdx]
        : coinContent}
    </div>
  );
}
export default SubwayBoardFirstPart;
