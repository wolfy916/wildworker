import * as React from "react";
import "./obtainTitle.css";

function obtainTitle(props) {
  const myObtainTitle = props.store.getTitle;
  return (
    <div
      className="obtain-title-modal-container"
      onClick={() => {
        props.setModalClick(false);
      }}
    >
      <div className="obtain-title-modal-content">
        <div>특정 조건을 달성해서</div>
        <div>" {myObtainTitle} "</div>
        <div> 칭호를 얻었어요 !</div>
      </div>
      <div className="obtain-title-modal-info">아무 곳이나 터치해주세요.</div>
    </div>
  );
}
export default obtainTitle;