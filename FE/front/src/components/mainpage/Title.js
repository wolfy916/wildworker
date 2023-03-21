import * as React from "react";
import "./Title.css";

function Title(props) {
  function toggleClickHandler() {
    props.setIsToggled((prev) => !prev);
    const selectBadgeTags = document.getElementsByClassName("select-badge");
    for (let idx = 0; idx < 2; idx++) {
      selectBadgeTags[idx].classList.toggle("badge-appear");
      selectBadgeTags[idx].classList.toggle("badge-disappear");
    }
  }

  React.useState(async () => {
    const selectBadgeTags = await document.getElementsByClassName(
      "select-badge"
    );
    if (props.isToggled) {
      selectBadgeTags[0].classList.add("badge-appear");
      selectBadgeTags[1].classList.add("badge-disappear");
    } else {
      selectBadgeTags[0].classList.add("badge-disappear");
      selectBadgeTags[1].classList.add("badge-appear");
    }
  }, []);

  React.useState(() => {}, [props.isToggled]);

  return (
    <div className="modal-component">
      <div className="modal-title">나의 수식어</div>
      <div className="modal-content">
        <div>어떤 것을 보여줄까요?</div>
        <div className="title-toggle-container" onClick={toggleClickHandler}>
          <div className="select-badge">지배자</div>
          <div className="select-badge">칭호</div>
        </div>
        <div className="title-container">여기는 칭호리스트</div>
        {props.isToggled && <div className="title-container-blur"></div>}
      </div>
    </div>
  );
}

export default Title;
