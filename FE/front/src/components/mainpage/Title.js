import * as React from "react";
import "./Title.css";
import { patchUserInfo } from "../../api/User";

function Title(props) {
  function toggleClickHandler() {
    const selectBadgeTags = document.getElementsByClassName("select-badge");
    for (let idx = 0; idx < 2; idx++) {
      selectBadgeTags[idx].classList.toggle("badge-appear");
      selectBadgeTags[idx].classList.toggle("badge-disappear");
    }
    props.setIsToggled((prev) => !prev);
    patchUserInfo({
      name: null,
      titleType: (props.userData.titleType + 1) % 2,
      mainTitleId: null,
      characterType: null,
      setFunc: props.setUserData,
    });
  }

  React.useState(async () => {
    const selectBadgeTags = await document.getElementsByClassName(
      "select-badge"
    );
    if (!props.isToggled) {
      selectBadgeTags[0].classList.add("badge-appear");
      selectBadgeTags[1].classList.add("badge-disappear");
    } else {
      selectBadgeTags[0].classList.add("badge-disappear");
      selectBadgeTags[1].classList.add("badge-appear");
    }
  }, []);

  const titleItemTags = props.myTitles.titles.map((title, idx) => {
    let isSelected = false;
    if (props.userData.title.id === title.id) {
      isSelected = true;
    }
    return (
      <div className="title-item" key={`${idx}`}>
        <div className="title-item-name">{title.name}</div>
        <input
          className="title-item-input"
          type="radio"
          name="title"
          defaultChecked={isSelected}
          onClick={() => {
            patchUserInfo({
              name: null,
              titleType: null,
              mainTitleId: (isSelected ? null: title.id),
              characterType: null,
              setFunc: props.setUserData,
            });
          }}
        />
      </div>
    );
  });

  return (
    <div className="modal-component">
      <div className="modal-title">나의 수식어</div>
      <div className="modal-content">
        <div>어떤 것을 보여줄까요?</div>
        <div className="title-toggle-container" onClick={toggleClickHandler}>
          <div className="select-badge">지배자</div>
          <div className="select-badge">칭호</div>
        </div>
        <div className="title-container">{titleItemTags}</div>
        {!props.isToggled && <div className="title-container-blur"></div>}
      </div>
    </div>
  );
}

export default Title;
