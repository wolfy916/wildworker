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
    totalPage: 10,
    currentPage: 1,
  };

  const [data, setData] = React.useState(DATA);

  const coinLogTags = data.list.map((item, idx) => {
    return (
      <CoinHistoryItem item={item} key={idx} idx={idx} />
    );
  });

  function addBtnClickHandler() {
    // DATA를 axios로 갱신하고 ~
    console.log({...DATA});
    console.log(Object.assign({list: [{
      station: {
        id: 1,
        name: "지옥",
      },
      type: "investment",
      value: -150,
      applied: true,
      time: "2023-03-14 14:20",  
    }]}, {list: DATA.list}));
    // setData(prev => );
  };

  return (
    <div className="modal-component">
      <div className="modal-title">코인 내역</div>
      <div className="modal-content">
        <div className="coin-history-head">
          <div className="coin-history-balance">
            <div>실제 잔액 :</div>
            <div>{DATA.balance.toLocaleString("ko-KR")} 원</div>
          </div>
          <div className="coin-history-untreated">
            <div>미정산 금액 : </div>
            <div>
              {(DATA.untreated > 0 ? "+ " : "") +
                DATA.untreated.toLocaleString("ko-KR")}{" "}
              원
            </div>
          </div>
        </div>
        <div className="coin-history-body">
          {coinLogTags}
          <div className="coin-history-add" onClick={addBtnClickHandler}>더 보기</div>
        </div>
      </div>
    </div>
  );
}

export default CoinHistory;
