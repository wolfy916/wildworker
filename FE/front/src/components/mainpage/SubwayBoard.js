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
      console.log("수동채굴 제출");
      setGetCoinClick(false);
      setIsFlashing(true);
      setTimeout(() => {
        setIsFlashing(false);
      }, 1000);
    }
  }, [getCoinClick, setGetCoinClick, setUserData]);

  return (
    <div>
      <div className="board-modal-wrap">코인획득!!</div>
      <div className="subway-board-container">
        <div className="subway-board-ceiling"></div>
        <div className="subway-board-bridges">
          <div className="subway-board-bridge"></div>
          <div className="subway-board-bridge"></div>
        </div>
        <div className="subway-board-wrapper">
          <div className="subway-board-screen">
            <SubwayBoardFirstPart
              userData={props.userData}
              store={props.store}
              isFlashing={isFlashing}
            />
            <div className="board-second-part">
              <span className="current-station">
                {" "}
                {props.store.location}의 지배자{" "}
              </span>
              는<span className="current-station"> {props.store.dominatorAppear} </span>
              입니다.
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SubwayBoard;
