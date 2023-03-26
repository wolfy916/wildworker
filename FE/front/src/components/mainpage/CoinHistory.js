import * as React from "react";
import "./CoinHistory.css";
import CoinHistoryItem from "./CoinHistoryItem";

function CoinHistory(props) {
  const DATA = {
    balance: 1500,
    untreated: +15,
    list: [
      {
        station: {
          id: 1,
          name: "역삼역",
        },
        type: "game",
        value: +20,
        applied: false,
        time: "2023-03-14 14:20",
      },
      {
        station: {
          id: 1,
          name: "사당역",
        },
        type: "investment",
        value: -1000,
        applied: true,
        time: "2023-03-14 14:20",
      },
      {
        station: {
          id: 1,
          name: "서울대입구역",
        },
        type: "commition",
        value: +30,
        applied: true,
        time: "2023-03-14 14:20",
      },
      {
        station: {
          id: 1,
          name: "사당역",
        },
        type: "investment",
        value: -20,
        applied: true,
        time: "2023-03-14 14:20",
      },
      {
        station: {
          id: 1,
          name: "신도림역",
        },
        type: "game",
        value: -20,
        applied: true,
        time: "2023-03-14 14:20",
      },
      {
        station: {
          id: 1,
          name: "잠실역",
        },
        type: "investment",
        value: -150,
        applied: true,
        time: "2023-03-14 14:20",
      },
    ],
    size: 10,
    totalPage: 3,
    currentPage: 1,
  };

  const [data, setData] = React.useState(DATA);
  const [currentPage, setCurrentPage] = React.useState(1);

  const coinLogTags = data.list.map((item, idx) => {
    return <CoinHistoryItem item={item} key={idx} idx={idx} />;
  });

  function requestPageAxios(curPage) {
    // 이 함수는 useEffect로 렌더링시에 한 번 호출되어야하고,
    // 이전, 다음 페이지 버튼 클릭시 호출되어야함
    // currentPage 값을 인자로 Axios를 호출하고
    // setData로 데이터 상태관리를 진행
  }

  async function leftClickHandler() {
    await setCurrentPage((prev) => prev - 1);
    requestPageAxios(currentPage);
  }

  async function rightClickHandler() {
    await setCurrentPage((prev) => prev + 1);
    requestPageAxios(currentPage);
  }

  return (
    <div className="modal-component">
      <div className="modal-title">정산 내역</div>
      <div className="modal-content">
        <div className="coin-history-head">
          <div className="coin-history-balance">
            <div>현재 잔액 :</div>
            <div>{DATA.balance.toLocaleString("ko-KR")} 원</div>
          </div>
        </div>
        <div className="coin-history-body">
          {coinLogTags}
          {!((currentPage === 1) === data.totalPage) && (
            <div className="coin-history-page-move">
              {currentPage !== 1 && (
                <div
                  className="coin-history-page-left"
                  onClick={leftClickHandler}
                >
                  {"<- 이전"}
                </div>
              )}
              {currentPage !== data.totalPage && (
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
