import * as React from "react";
import "./SubwayBoard.css";
import SubwayBoardFirstPart from "./SubwayBoardFirstPart";

function SubwayBoard(props) {
  const [isFlashing, setIsFlashing] = React.useState(false);

  let getCoinClick = props.getCoinClick;
  const setGetCoinClick = props.setGetCoinClick;
  const setUserData = props.setUserData;

  React.useEffect(() => {
    if (getCoinClick === true) {
      setGetCoinClick(false);
      setIsFlashing(true);
      setTimeout(() => {
        setIsFlashing(false);
      }, 2000);
    }
  }, [getCoinClick, setGetCoinClick, setUserData]);

  React.useEffect(() => {
    if (isFlashing) {
      document.getElementsByClassName("board-modal-wrap")[0].style.display =
        "block";
    } else {
      document.getElementsByClassName("board-modal-wrap")[0].style.display =
        "none";
    }
  }, [isFlashing]);

  return (
    <div>
      <div className="board-modal-wrap">코인획득</div>
      <div className="subway-board-container">
        <div className="subway-board-ceiling"></div>
        <div className="subway-board-bridges">
          <div className="subway-board-bridge"></div>
          <div className="subway-board-bridge"></div>
        </div>
        <div className="subway-board-wrapper">
          <div className="subway-board-screen">
            <SubwayBoardFirstPart
              store={props.store}
              userData={props.userData}
              subwayContentIdx={props.subwayContentIdx}
              setSubwayContentIdx={props.setSubwayContentIdx}
              isFlashing={isFlashing}
            />
            {props.store.locationData.current != null ? (
              <div className="board-second-part">
                <span className="current-station">
                  {" "}
                  {props.store.locationData.current.name}의 지배자{" "}
                </span>
                는
                {props.store.locationData.current.dominator != null ? (
                  <span className="current-station">
                    {" "}
                    {props.store.locationData.current.dominator}{" "}
                  </span>
                ) : (
                  ""
                )}
                {props.store.locationData.current.dominator != null
                  ? "입니다."
                  : " 아직 결정되지 않았습니다."}
              </div>
            ) : (
              <div className="board-second-part">
                <span className="current-station">
                  이곳은 지하철 역이 아닙니다.
                </span>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default SubwayBoard;
