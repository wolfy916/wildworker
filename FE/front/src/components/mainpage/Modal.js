import * as React from "react";
import "./Modal.css";
import NickName from "./NickName";
import Title from "./Title";
import CoinHistory from "./CoinHistory";
import Invest from "../detailsubway/Invest";
import TitleGet from "./TitleGet";
import DominatorMsg from "./DominatorMsg";

function Modal(props) {
  const modalWidth = props.modalWidth;
  const modalHeight = props.modalHeight;
  const setModalClick = props.setModalClick;
  const setTitleModalClick = props.setTitleModalClick;
  const setDominatorMsgModalClick = props.setDominatorMsgModalClick;
  const selectContent = [
    NickName,
    Title,
    CoinHistory,
    Invest,
    TitleGet,
    DominatorMsg,
  ][props.selectModalIdx];

  function modalCloseClick() {
    if (props.selectModalIdx === 4) {
      setTitleModalClick(false);
    } else if (props.selectModalIdx === 5) {
      setDominatorMsgModalClick(false);
    } else {
      setModalClick(false);
    }
  }

  React.useEffect(() => {
    function modalSet(width, height) {
      const wrapTag = document.getElementsByClassName("modal-wrap")[0];
      wrapTag.style.width = `${width}%`;
      wrapTag.style.height = `${height}%`;
    }
    modalSet(modalWidth, modalHeight);
  }, [modalWidth, modalHeight]);

  return (
    <div className="modal-container">
      <div className="modal-bg" onClick={modalCloseClick}></div>
      <div className="modal-wrap">{selectContent(props)}</div>
    </div>
  );
}

export default Modal;
