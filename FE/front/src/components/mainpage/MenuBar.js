import * as React from "react";
import "./MenuBar.css";

function MenuBar(props) {
  const [menuClick, setMenuClick] = React.useState(false);

  const handleLocations = [{ left: "20%" }, { left: "40%" }, { left: "60%" }];
  const handleLabel = ["닉네임", "수식어", "정산서"];
  const subwayHandle = handleLocations.map((value, idx) => {
    return (
      <div
        className="handle-rope"
        key={`${value.left}`}
        style={handleLocations[idx]}
        onClick={async () => {
          await props.setSelectIdx(idx);
          props.setModalClick(true);
        }}
      >
        <div className="handle-rope-label">
          <div className="label-char">{handleLabel[idx][0]}</div>
          <div className="label-char">{handleLabel[idx][1]}</div>
          <div className="label-char">{handleLabel[idx][2]}</div>
        </div>
        <div className="handle-grip"></div>
      </div>
    );
  });

  return (
    <div className="menu-bar-container">
      {menuClick && (
        <div
          className="menu-bar-bg"
          onClick={async () => {
            const menuBarTag = document.getElementsByClassName("menu-bar")[0];
            menuBarTag.style.animationPlayState = "paused";
            menuBarTag.style.animationName = "menuBarDisappear";
            const ropeTags = document.querySelectorAll(".handle-rope");
            const gripTags = document.querySelectorAll(".handle-grip");
            ropeTags.forEach((ropeTag) => {
              ropeTag.style.animationPlayState = "paused";
              ropeTag.style.animation = "ropeMotionReverse 2s linear";
            });
            gripTags.forEach((gripTag) => {
              gripTag.style.animationPlayState = "paused";
              gripTag.style.animation = "gripMotionReverse 2.2s linear";
            });
            setTimeout(() => {
              menuBarTag.style.animationPlayState = "running";
              ropeTags.forEach((ropeTag) => {
                ropeTag.style.animationPlayState = "running";
              });
              gripTags.forEach((gripTag) => {
                gripTag.style.animationPlayState = "running";
              });
              setMenuClick(false);
            }, 10);
          }}
        ></div>
      )}
      <div
        className="menu-bar"
        onClick={async (e) => {
          if (
            e.target.style.animationName != "menuBarClickAppear" &&
            e.target.classList[0] == "menu-bar"
          ) {
            e.target.style.animationPlayState = "paused";
            e.target.style.animationName = "none";
            const ropeTags = await document.querySelectorAll(".handle-rope");
            const gripTags = await document.querySelectorAll(".handle-grip");
            ropeTags.forEach((ropeTag) => {
              ropeTag.style.animationPlayState = "paused";
              ropeTag.style.animation = "ropeMotion 2s linear forwards";
            });
            gripTags.forEach((gripTag) => {
              gripTag.style.animationPlayState = "paused";
              gripTag.style.animation = "gripMotion 2.2s linear forwards";
            });

            setTimeout(async () => {
              e.target.style.animationName = "menuBarClickAppear";
              e.target.style.animationFillMode = "forwards";
              e.target.style.animationPlayState = "running";
              ropeTags.forEach((ropeTag) => {
                ropeTag.style.animationPlayState = "running";
              });
              gripTags.forEach((gripTag) => {
                gripTag.style.animationPlayState = "running";
              });
              setMenuClick(true);
            }, 10);
          }
        }}
      >
        {subwayHandle}
      </div>
    </div>
  );
}

export default MenuBar;
