import * as React from "react";
import "./ErrorMessage.css";

function ErrorMessage(props) {
  const errFirstSentenceList = ["지배자가 아닌 사람은"];
  const errSecondSentenceList = ["사용할 수 없어요."];
  return (
    <div
      className="error-message-container"
      onClick={() => {
        props.setModalClick(false);
      }}
    >
      <div className="error-message-content">
        <div>{errFirstSentenceList[props.selectErrorIdx]}</div>
        <div>{errSecondSentenceList[props.selectErrorIdx]}</div>
      </div>
      <div className="error-message-info">아무 곳이나 터치해주세요.</div>
    </div>
  );
}
export default ErrorMessage;
