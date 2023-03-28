import * as React from "react";
import subwaymap_logo from "../../asset/image/subwaymap_logo.png";
import "./TitleGet.css";

function TitleGet(props) {
  function changeClickHandler() {
    props.setModalClick("");
  }
  const getTitleData = props.getTitleData
  return (
    <div className="modal-component">
      <div className="modal-title">!칭호!</div>
      <div className="modal-content">
        <div className="title-get">'승부사' 칭호 획득</div>
        <div>
          <img className="title-get-img" src={subwaymap_logo} />
        </div>
        <div className="title-check-btn" onClick={changeClickHandler}>
          확인
        </div>
      </div>
    </div>
  );
}

export default TitleGet;
