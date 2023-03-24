import * as React from "react";
import "./CoinHistoryItem.css";

function CoinHistoryItem(props) {
  const [isClicked, setIsClicked] = React.useState(false);

  function openClickHandler() {
    if (!isClicked) {
      const targetTag =
        document.querySelectorAll(".coin-history-item")[props.idx];
      targetTag.style.animationState = "paused";
      setTimeout(() => {
        targetTag.style.animation =
          "coinHistoryItemAppear 0.2s linear 0s 1 forwards";
        targetTag.style.animationState = "running";
        setIsClicked(true);
      }, 10);
    }
  }

  function closeClickHandler() {
    const targetTag =
      document.querySelectorAll(".coin-history-item")[props.idx];
    targetTag.style.animationState = "paused";
    setTimeout(() => {
      targetTag.style.animation = "coinHistoryItemDisappear 0.2s linear 0s 1";
      targetTag.style.animationState = "running";
      setIsClicked(false);
    }, 10);
  }

  return (
    <div className="coin-history-item">
      <div className="coin-history-item-wrapper" onClick={openClickHandler}>
        <div className="coin-history-item-title">
          <div>{props.item.station.name}</div>
          <div>
            {(props.item.value > 0 ? "+" : "") +
              props.item.value.toLocaleString("ko-KR")}{" "}
            원
          </div>
        </div>
        {isClicked && (
          <div className="coin-history-item-close" onClick={closeClickHandler}>
            X
          </div>
        )}
      </div>
      <div className="coin-history-item-body">
        <div className="coin-history-item-wrapper">
          <div>사건 :</div>
          <div>{props.item.type}</div>
        </div>
        <div className="coin-history-item-wrapper">
          <div>날짜 :</div>
          <div>{props.item.time}</div>
        </div>
        <div className="coin-history-item-wrapper">
          <div>반영 여부 :</div>
          <div>{props.item.applied ? "Yes!!" : "No.."}</div>
        </div>
      </div>
    </div>
  );
}

export default CoinHistoryItem;
