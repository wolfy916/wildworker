import * as React from "react";
import "./DominatorMsg.css";

function DominatorMsg(props) {
  const [newMsg, setNewMsg] = React.useState("");
  const dominatorMsg = props.store.dominatorMsg;
  const stompClient = props.stompClient;
  const currentStation = props.store.locationData.current;

  const handleInputChange = (e) => {
    setNewMsg(e.target.value);
  };

  const changeClickHandler = (e) => {
    setNewMsg(e.target.value);
    if (currentStation) {
      handleDominatorMsgClick();
    }
    props.setModalClick(false);
  };

  // 지배자 한마디 소켓 전송
  const handleDominatorMsgClick = (e) => {
    const message = JSON.stringify({ message: newMsg });
    stompClient.send(`/sub/station/${currentStation.id}/message`, {}, message);
  };

  return (
    <div className="modal-component">
      <div className="modal-title">지배자 확성기</div>
      <div className="modal-content">
        <div className="current-msg-info">기존 확성 내용</div>
        <div className="current-msg">{dominatorMsg}</div>
        <div className="change-msg">
          <input
            className="change-msg-input"
            type="text"
            placeholder="확성내용 입력"
            maxLength="20"
            value={newMsg}
            onChange={handleInputChange} // input 값 변경 시 호출되는 함수
          />
        </div>
        <div className="change-msg-btn" onClick={changeClickHandler}>
          게시하기
        </div>
      </div>
    </div>
  );
}

export default DominatorMsg;
