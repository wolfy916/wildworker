// 연결
function connectSocket(client, setstore) {
  client.connect({}, () => {
    subscribeStation(client, setstore);
    subscribeUser(client, setstore);
  });
  return client;
}
// 지하철 구독
function subscribeStation(client, setStore, curStation) {
  if (curStation != null) {
    client.subscribe(`/sub/systems/${curStation.id}`, (message) => {
      const payload = JSON.parse(message.body);
      // 지배자 기능 모음
      if (payload.type === "STATION") {
        // 지배자 강림
        if (payload.subType === "DOMINATOR") {
          setStore((prev) => {
            return {
              ...prev,
              dominatoreAppear: payload.data,
            };
          });
        }
        // 지배자 확성기
        else if (payload.subType === "MESSAGE") {
          setStore((prev) => {
            return {
              ...prev,
              dominatoreMsg: payload.data,
            };
          });
        }
      }
    });
  }
  return client;
}

function subscribeUser(client, setStore) {
  client.subscribe("/user/queue", (message) => {
    const payload = JSON.parse(message.body);

    //현재 역 변동 & 역 정보
    if (payload.type === "STATION" && payload.subType === "STATUS") {
      setStore((prev) => {
        return {
          ...prev,
          locationData: payload.data,
        };
      });
    }

    // 수동 채굴 모음
    else if (payload.type === "MINING") {
      // 서류 종이 카운트
      if (payload.subType === "PAPER_COUNT") {
        setStore((prev) => {
          return {
            ...prev,
            manualMining: payload.data,
          };
        });
      }
    }

    // 코인변동 모음
    else if (payload.type === "COIN") {
      // 자동 코인 변동
      setStore((prev) => {
        return {
          ...prev,
          coinChange: {
            ...prev.coinchange,
            [payload.subType]: payload.data,
          },
        };
      });
    }

    // 칭호관련 모음
    else if (payload.type === "TITLE") {
      // 칭호 획득
      if (payload.subType === "GET") {
        setStore((prev) => {
          return {
            ...prev,
            getTitle: payload.data,
          };
        });
      }
      // 내 대표 칭호 변동
      else if (payload.subType === "MAIN_TITLE_UPDATE") {
        setStore((prev) => {
          return {
            ...prev,
            changeTitle: payload.data,
          };
        });
      }
    }

    // 미니게임 모음
    else if (payload.type === "MINIGAME") {
      // 게임 매칭
      if (payload.subType === "MATCHING") {
        setStore((prev) => {
          return {
            ...prev,
            matching: payload.data,
          };
        });
      }
      // 게임 취소 (도망 성공)
      else if (payload.subType === "CANCEL") {
        setStore((prev) => {
          return {
            ...prev,
            gameCancel: payload.data,
          };
        });
      }
      // 게임 시작
      else if (payload.subType === "START") {
        setStore((prev) => {
          return {
            ...prev,
            gameStart: payload.data,
          };
        });
      }
      // 게임 결과
      else if (payload.subType === "RESULT") {
        setStore((prev) => {
          return {
            ...prev,
            gameResult: payload.data,
          };
        });
      }
    }
  });
  return client;
}

function unsubscribeStation(client, curStation) {
  if (curStation != null) {
    client.unsubscribe(`/sub/systems/${curStation.id}`);
  }
  return client;
}

export { connectSocket, subscribeStation, subscribeUser, unsubscribeStation };
