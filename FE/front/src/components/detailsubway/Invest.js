import * as React from "react";
import "./Invest.css";
import { invest } from "../../api/Investment.js";

function Invest(props) {
  const [userInput, setUserInput] = React.useState("");

  const userInputHandler = (e) => {
    setUserInput(e.target.value);
  };

  function changeClickHandler() {
    const payload = {
      investment: userInput,
      stationId: props.stationId,
      setFunc: {
        setUserData: props.setUserData,
        setInvestErr: props.setInvestErr,
      }
    };
    invest(payload);
    props.setModalClick((prev) => !prev);
    props.setCnt(0);
    props.setIsRetry((prev) => !prev);
  }
  function changeClickHandlerFromHot() {
    const payload = {
      investment: userInput,
      stationId: props.selectedStationId,
      setFunc: {
        setUserData: props.setUserData,
        setInvestErr: props.setInvestErr,
      }
    };
    invest(payload);
    props.setModalClick((prev) => !prev);
    props.setCnt(0);
    props.setIsRetry((prev) => !prev);
  }
  return (
    <div className="modal-component">
      <div className="modal-title">투자</div>
      <div className="modal-content">
        <div className="current-investment-info">현재 투자금액</div>
        <div className="current-investment">{props.investment}원</div>
        <div className="change-investment">
          <input
            className="change-investment-input"
            type="number"
            placeholder="투자금액 입력"
            maxLength="20"
            value={userInput}
            onChange={userInputHandler}
          />
        </div>
        <div
          className="change-investment-btn"
          onClick={
            props.detailOrHot === 0
              ? changeClickHandler
              : changeClickHandlerFromHot
          }
        >
          투자하기
        </div>
      </div>
    </div>
  );
}

export default Invest;
