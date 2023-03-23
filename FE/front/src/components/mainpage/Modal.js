import * as React from "react";
import "./Modal.css";
import NickName from "./NickName";
import Title from "./Title";
import CoinHistory from "./CoinHistory";
import Invest from "../detailsubway/Invest";

function Modal(props) {
  const modalWidth = props.modalWidth;
  const modalHeight = props.modalHeight;
  const setModalClick = props.setModalClick;
  const selectContent = [NickName, Title, CoinHistory, Invest][props.selectModalIdx];

  function modalCloseClick() {
    setModalClick(false);
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
