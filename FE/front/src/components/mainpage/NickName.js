import * as React from "react";
import "./NickName.css";

function NickName(props) {
  function changeClickHandler() {
    props.setModalClick((prev) => !prev);
    const inputTag = document.getElementsByClassName(
      "change-nickname-input"
    )[0];
    props.setNickname(inputTag.value);
  }
  return (
    <div className="modal-component">
      <div className="modal-title">닉네임</div>
      <div className="modal-content">
        <div className="current-nickname-info">현재 닉네임</div>
        <div className="current-nickname">{props.nickname}</div>
        <div className="change-nickname">
          <input
            className="change-nickname-input"
            type="text"
            placeholder="닉네임 변경"
            maxLength="8"
          />
        </div>
        <div className="change-nickname-btn" onClick={changeClickHandler}>
          변경하기
        </div>
      </div>
    </div>
  );
}

export default NickName;
