import * as React from "react";
import "./TitleGet.css";

function TitleGet(props) {
  function changeClickHandler() {
    props.setTitleModalClick(false);
  }
  const getTitle = props.getTitle
  return (
    <div className="modal-component">
      <div className="modal-title">칭호 획득</div>
      <div className="modal-content">
        <div className="title-get">'승부사'</div>
        {/* <div className="title-get">{getTitle.name} 칭호 획득</div> */}
        <div className="title-check-btn" onClick={changeClickHandler}>
          확인
        </div>
      </div>
    </div>
  );
}

export default TitleGet;
