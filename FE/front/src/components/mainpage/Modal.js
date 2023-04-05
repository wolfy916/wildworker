import * as React from "react";
import "./Modal.css";
import NickName from "./NickName";
import Title from "./Title";
import CoinHistory from "./CoinHistory";
import Invest from "../detailsubway/Invest";
import TitleGet from "./TitleGet";
import DominatorMsg from "./DominatorMsg";
import obtainTitle from "./obtainTitle";
import ErrorMessage from "./ErrorMessage";

function Modal(props) {
  const modalWidth = props.modalWidth;
  const modalHeight = props.modalHeight;
  const setModalClick = props.setModalClick;

  const selectContent = [
    NickName,
    Title,
    CoinHistory,
    Invest,
    TitleGet,
    DominatorMsg,
    obtainTitle,
    ErrorMessage,
  ][props.selectModalIdx];

  function modalCloseClick() {
    if (props.selectModalIdx === 4) {
      props.setTitleModalClick(false);
    } else {
      setModalClick(false);
    }
  }

  React.useEffect(() => {
    function modalSet(width, height) {
      const wrapTag = document.getElementsByClassName("modal-wrap")[0];
      wrapTag.style.width = `${width}%`;
      wrapTag.style.height = `${height}%`;
      wrapTag.style.left = `${50 - width / 2}%`;
      wrapTag.style.top = `${50 - height / 2}%`;
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
