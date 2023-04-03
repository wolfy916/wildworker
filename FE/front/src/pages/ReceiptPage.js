import * as React from "react";
import { useNavigate } from "react-router-dom";
import "./ReceiptPage.css";

function ReceiptPage(props) {
  // const gameResultData = props.gameResultData;
  const gameResultData = {
    isWinner: true,
    enemy: {
      name: "권태형",
      title: "신도림의 지배자",
      characterType: 1,
    },
    result: {
      me: 12,
      enemy: 11,
    },
    receipt: {
      cost: -20,
      runCost: 0,
      reward: 40,
      commission: -2,
      total: 18,
    },
  };
  function cut1000(data) {
    //1000의 자리에서 자르면  이거 쓰자.
    //ㄴ나중에...
  }
  const navigate = useNavigate();
  function handleTouchStart(event) {
    // 다음 페이지로 이동하는 로직을 작성합니다.
    navigate("/main");
    console.log("go to mainPage");
  }
  return (
    <div className="receipt">
      <div className="receipt-backimg">
        <div className="receipt-whiteblock"></div>
        <div className="receipt-ticket"></div>
        <div className="receipt-text" onTouchStart={handleTouchStart}>
          <p className="receipt-text-header"></p>
          <div className="receipt-text-main">
            <p className="receipt-text-main-left">게임비</p>
            <p className="receipt-text-main-right">
              {gameResultData.receipt.cost}원
            </p>
            <p className="receipt-text-main-left">도망비</p>
            <p className="receipt-text-main-right">
              {gameResultData.receipt.runCost}원
            </p>
            <p className="receipt-text-main-left">환급비</p>
            <p className="receipt-text-main-right">
              {gameResultData.receipt.reward}원
            </p>
            <p className="receipt-text-main-left">수수료</p>
            <p className="receipt-text-main-right">
              {gameResultData.receipt.commission}원
            </p>
            <p className="receipt-text-main-total1">TOTAL</p>
            <p className="receipt-text-main-right">
              {gameResultData.receipt.total}원
            </p>
          </div>
          <p className="receipt-text-footer">야생의 직장인</p>
        </div>
      </div>
    </div>
  );
}

export default ReceiptPage;
