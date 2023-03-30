import http from "./Http.js";

// 회원정보 조회
function getUserInfo(payload) {
  if (payload) {
    http({
      method: "get",
      url: "/user",
    })
      .then(({ status, data }) => {
        if (status === 200) {
          console.log("getUserInfo 성공", data);
          payload.setFunc(data);
          // data 예시
          // {
          //   characterType: 0;
          //   coin: 0;
          //   collectedPapers: 74;
          //   name: "rnjsxogud916@naver.com";
          //   titleId: 0;
          //   titleType: 0;
          // }
        }
      })
      .catch((err) => {
        console.log("getUserInfo 실패");
        console.log(err.response);
      });
  }
}

// getUserInfo 예외
// - 닉네임 중복 : Conflict
// - 칭호 타입 오류 : BadRequest
// - 칭호 오류 : BadRequest
// - 캐릭터 종류 오류 : BadRequest
// HTTP/1.1 500 INTERNAL ERROR
// {
// 		"message": 예외 메시지
// }

// 회원정보 수정
async function patchUserInfo(payload) {
  if (payload) {
    await http({
      method: "patch",
      url: "/user",
      // 바꾼 값 이외에는 null로 채워보내야함
      data: {
        name: payload.name, // 닉네임
        titleType: payload.titleType, // 칭호 종류(1:지배자, 2:칭호)
        titleId: payload.titleId, // 대표 칭호 고유번호
        characterType: payload.characterType, // 캐릭터 종류(1:남자, 2:여자)
      },
    })
      .then(({ status, data }) => {
        if (status === 200) {
          console.log("patchUserInfo 성공");
        }
      })
      .catch((err) => {
        console.log("patchUserInfo 실패");
        console.log(err.response);
      });
  }
}

// patchUserInfo 예외
// - 닉네임 중복 : Conflict
// - 칭호 타입 오류 : BadRequest
// - 칭호 오류 : BadRequest
// - 캐릭터 종류 오류 : BadRequest
// HTTP/1.1 500 INTERNAL ERROR
// {
// 		"message": 예외 메시지
// }

// 회원탈퇴
async function deleteUserInfo(payload) {
  if (payload) {
    await http({
      method: "delete",
      url: "/user",
    })
      .then(({ status, data }) => {
        if (status === 200) {
          console.log("deleteUserInfo 성공");
        }
      })
      .catch((err) => {
        console.error("deleteUserInfo 실패");
        console.log(err.response);
      });
  }
}

// 보유 칭호목록 조회
async function getTitleList(payload) {
  if (payload) {
    return await http({
      method: "get",
      url: "/user/titles",
    })
      .then(({ status, data }) => {
        if (status === 200) {
          console.log("getTitleList 성공", data);
          return data;
          // data 예시
          // {
          //   "titleType": 1,
          //   "mainTitleId": 1,
          //   "dominatorTitles": [
          //     { "id": 1, "name": "역삼역의 지배자" },
          //   ],
          //   "titles": [
          //     { "id": 1, "name": "쫄보" },
          //     { "id": 3, "name": "대충 연승 시 칭호" },
          //     { "id": 5, "name": "대충 몇 승 달성 시 칭호" },
          //   ]
          // }
        }
      })
      .catch((err) => {
        console.log("getTitleList 실패");
        console.log(err.response);
      });
  }
}

// 코인 내역 조회
async function getCoinLog(payload) {
  if (payload) {
    return await http({
      method: "get",
      url: "/user/coin-log",
      params: { size: payload.size, page: payload.page },
    })
      .then(({ status, data }) => {
        if (status === 200) {
          console.log("getCoinLog 성공", data);
          return data;
          // data 예시
          // {
          //   "balance": 1234,  -> 현재 잔액
          //   "list": [
          //     {
          //       "station": {   -> 코인 입금/출금처
          //         "id": 1,
          //         "name": "역삼역"
          //       },
          //       "type": "게임", -> 코인 변동사유 한글로 넘어옴
          //       "value": -20,
          //       "applied": true
          //       "time": 2023-03-14 14:20
          //     },
          //     ...
          //   ],
          //   "size": 10,
          //   "totalPage": 10,
          //   "currentPage": 1
          // }
        }
      })
      .catch((err) => {
        console.log("getCoinLog 실패");
        console.log(err.response);
      });
  }
}

export { getUserInfo, patchUserInfo, deleteUserInfo, getTitleList, getCoinLog };
