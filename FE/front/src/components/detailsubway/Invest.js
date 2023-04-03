import * as React from "react";
import "./Invest.css";
import { invest } from "../../api/Investment.js";

function Invest(props) {
  const [newInvestment, setNewInvestment] = React.useState("");

  const handleInputChange = (e) => {
    setNewInvestment(e.target.value);
  };

  function changeClickHandler() {
    const payload = {
      investment: newInvestment,
      stationId: props.stationId,
    };
    invest(payload);
    props.setModalClick((prev) => !prev);
    props.setCnt(0)
    props.setIsRetry((prev) => !prev)
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
            value={newInvestment}
            onChange={handleInputChange}
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
