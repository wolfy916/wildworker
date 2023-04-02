import http from "./Http.js";

// 실시간 역 랭킹 조회
function getStationRanking(payload) {
  if (payload) {
    http({
      method: "get",
      url: "/investment",
      params: {
        size: payload.size,
        order: payload.investment  // -> payload.investment or payload.commission
      }
    }).then(({status, data}) => {
      if (status == 200) {
        console.log("getStationRanking 성공");
        payload.setFunc(data);
        // data 예시
        // {
        //   "ranking": [
        //     { 
        //       "rank": 1,                   -> 순위
        //       "station": [
        //         "id": 1,                   -> 역 고유 번호
        //         "name": "역삼역",
        //         "totalInvestment": 12345,  -> 총 투자금액
        //         "prevCommission": 1234,    -> 이전 10분 누적 수수료 총액
        //         "currentCommission": 123   -> 현재 쌓이고 있는 누적 수수료 총액
        //       ]
        //     } 
        //     ... // size 크기만큼의 데이터 (지면 상 생략) 
        //   ],
        //   "orderBy": "investment"   -> 정렬 기준
        // }
      }
    }).catch(err => {
      console.log("getStationRanking 실패");
      console.log(err.response);
    });
  }
}

// 해당 역에 대한 지분 조회
function getStationStake(payload) {
  if (payload) {
    http({
      method: "get",
      url: `/investment/${payload.stationId}`,
    }).then(({status, data}) => {
      if (status == 200) {
        console.log("getStationStake 성공");
        payload.setFunc(data);
        // data 예시
        // {
        //   "stationName": "역삼역",
        //   "dominator": "S2태형S2", -> 해당 역의 지배자
        //   "totalInvestment": 10000000, -> 총 투자금액
        //   "prevCommission": 12345,     -> 이전 10분 누적 수수료 총액
        //   "currentCommission": 1234,   -> 현재 쌓이고 있는 누적 수수료 총액
        //   "ranking": [ -> 지분 순위 정보
        //     { 
        //       "rank": 1,
        //       "namae": "S2태형S2",
        //       "investment": 123,  -> 투자금액
        //       "percent": 10       -> 지분율
        //     }
        //     ... // 5개 (지면 상 생략) 
        //   ],
        //   "mine": 	-> 내 지분 정보
        //     { 
        //       "rank": 1,
        //       "investment": 123,
        //       "percent": 10 
        //     }
        // }
      }
    }).catch(err => {
      console.log("getStationStake 실패");
      console.log(err.response);
    });
  }
}

// 해당 역에 투자
function invest(payload) {
  if (payload) {
    http({
      method: "post",
      url: `/investment/${payload.stationId}`,
      data: {
        investment: payload.investment,
      }
    }).then(({status, data}) => {
      if (status == 200) {
        console.log("invest 성공");
      }
    }).catch(err => {
      console.log("invest 실패");
      console.log(err.response);
    });
  }
}

// 내가 투자한 역 목록 조회
// 노선도 클릭 시 and 내가 투자한 역 클릭 시 호출
function getMyInvestList(payload) {
  if (payload) {
    http({
      method: "get",
      url: "/investment/mine",
      params: {
        order: payload.name, // payload.name || payload.investment || payload.percent
        ascend: payload.ascend, // ASC = 오름차순 정렬, DESC = 내림차순 정렬
      },
    }).then(({status, data}) => {
      if (status == 200) {
        console.log("getMyInvestList 성공");
        payload.setFunc(data);
        // data 예시
        // {
        //   "investments": [
        //     {
        //       "station": {
        //         "id": 1,
        //         "name": "역삼역"
        //       },
        //       "investment": 1234,
        //       "percent": 10
        //     }, ...
        //   ],
        //   "remainSec": 90
        //   "orderBy": "investment",
        //   "ascend": "ASC"
        // }
      }
    }).catch(err => {
      console.log("getMyInvestList 실패");
      console.log(err.response);
    });
  }
}

export { getStationRanking, getStationStake, invest, getMyInvestList };