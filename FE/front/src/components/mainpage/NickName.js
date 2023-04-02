import * as React from "react";
import "./NickName.css";
import character_man from "../../asset/image/stop_man.png";
import character_woman from "../../asset/image/stop_woman.png";
import { patchUserInfo } from "../../api/User.js";

function NickName(props) {
  function changeClickHandler() {
    props.setModalClick((prev) => !prev);
    const inputTag = document.getElementsByClassName(
      "change-nickname-input"
    )[0];
    props.setUserData((prev) => {
      return {
        ...prev,
        name: inputTag.value,
        CharacterType: mySelectGender,
      };
    });
    const payload = {
      name: inputTag.value,
      titleType: props.userData.titleType,
      titleId: props.userData.titleId,
      CharacterType: mySelectGender,
    };
    patchUserInfo(payload);
  }

  let mySelectGender = props.gender;

  const genderList = [character_man, character_woman];
  const genderItemTags = genderList.map((value, idx) => {
    let isSelected = false;
    if (mySelectGender === idx + 1) {
      isSelected = true;
    }
    return (
      <input
        className="current-gender-input"
        type="radio"
        name="gender"
        value={value}
        defaultChecked={isSelected}
        key={`${idx}`}
        onClick={(event) => {
          mySelectGender = idx + 1;
        }}
        style={{ backgroundImage: `url(${value})` }}
      />
    );
  });
  return (
    <div className="modal-component">
      <div className="modal-title">닉네임</div>
      <div className="modal-content">
        <div className="current-nickname-info">닉네임 ?</div>
        <div className="current-nickname">{props.userData.name}</div>
        <div className="change-nickname">
          <input
            className="change-nickname-input"
            type="text"
            placeholder="닉네임 변경"
            maxLength="8"
            defaultValue={props.userData.name}
          />
        </div>
        <div className="current-gender-info">성별 ?</div>
        <div className="current-gender-wrapper">{genderItemTags}</div>
        <div className="change-nickname-btn" onClick={changeClickHandler}>
          변경하기
        </div>
      </div>
    </div>
  );
}

export default NickName;
