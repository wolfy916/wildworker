import * as React from "react";
import "./NickName.css";
import character_man from "../../asset/image/stop_man.png";
import character_woman from "../../asset/image/stop_woman.png";
import { patchUserInfo } from "../../api/User.js";

function NickName(props) {
  const [errSentenceIdx, setErrSentenceIdx] = React.useState(0);
  const errorStateList = ["", "특수문자는 사용할 수 없어요."];

  function changeClickHandler() {

    const inputTag = document.getElementsByClassName(
      "change-nickname-input"
    )[0];
    const regex = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]+$/;
    const validate = regex.test(inputTag.value);

    const payload = {
      name: inputTag.value === props.userData.name ? null : inputTag.value,
      titleType: null,
      mainTitleId: null,
      characterType:
        mySelectGender === props.userData.characterType ? null : mySelectGender,
      setFunc: props.setUserData,
      setErrFunc: props.setNicknameErr,
    };
    if (validate) {
      props.setModalClick((prev) => !prev);
      patchUserInfo(payload);
      setErrSentenceIdx(0);
    } else {
      setErrSentenceIdx(1);
    }
  }

  let mySelectGender = props.userData.characterType;

  const genderList = [character_man, character_woman];
  const genderItemTags = genderList.map((value, idx) => {
    let isSelected = false;
    if (mySelectGender === idx) {
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
        onClick={() => {
          mySelectGender = idx;
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
            style={{borderBottomColor: (errSentenceIdx ? "red" : "black")}}
            defaultValue={props.userData.name}
          />
          <div className="nickname-err-sentence">{errorStateList[errSentenceIdx]}</div>
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
