import * as React from "react";
import "./CoinHistory.css";
import CoinHistoryItem from "./CoinHistoryItem";
import { getCoinLog } from "../../api/User";

function CoinHistory(props) {
  const coinLogTags = props.myCoinLogs.list.map((item, idx) => {
    return <CoinHistoryItem item={item} key={idx} idx={idx} />;
  });

  React.useEffect(() => {
    getCoinLog({
      size: 8,
      page: 0,
      setFunc: {
        setMyCoinLogs: props.setMyCoinLogs,
        setUserData: props.setUserData,
      },
    });
  }, []);

  function leftClickHandler() {
    getCoinLog({
      size: 8,
      page: props.myCoinLogs.currentPage - 1,
      setFunc: props.setMyCoinLogs,
    });
  }

  function rightClickHandler() {
    getCoinLog({
      size: 8,
      page: props.myCoinLogs.currentPage + 1,
      setFunc: props.setMyCoinLogs,
    });
  }

  return (
    <div className="modal-component">
      <div className="modal-title">정산 내역</div>
      <div className="modal-content">
        <div className="coin-history-head">
          <div className="coin-history-balance">
            <div>현재 잔액 :</div>
            <div>{props.userData.coin.toLocaleString("ko-KR")} 원</div>
          </div>
        </div>
        <div className="coin-history-body">
          {coinLogTags}
          {!(
            (props.myCoinLogs.currentPage === 0) ===
            props.myCoinLogs.totalPage - 1
          ) && (
            <div className="coin-history-page-move">
              {props.myCoinLogs.currentPage !== 0 && (
                <div
                  className="coin-history-page-left"
                  onClick={leftClickHandler}
                >
                  {"<- 이전"}
                </div>
              )}
              {props.myCoinLogs.currentPage !== props.myCoinLogs.totalPage - 1 && (
                <div
                  className="coin-history-page-right"
                  onClick={rightClickHandler}
                >
                  {"다음 ->"}
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default CoinHistory;
