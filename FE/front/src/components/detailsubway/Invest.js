import * as React from "react";
import "./Invest.css";

function Invest(props) {
  function changeClickHandler() {
    props.setModalClick((prev) => !prev);
    // axios 보낼 함수 짜야함
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
            type="text"
            placeholder="투자금액 입력"
            maxLength="20"
          />
        </div>
        <div className="change-investment-btn" onClick={changeClickHandler}>
          변경하기
        </div>
      </div>
    </div>
  );
}

export default Invest;