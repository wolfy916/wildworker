// 연결
function connectSocket(
  client,
  setstore,
  setUserData,
  store,
  setIsMatched,
  setIsObtainTitle,
  setIsGetError
) {
  client.connect(
    {},
    () => {
      subscribeUser(
        client,
        setstore,
        setUserData,
        setIsMatched,
        setIsObtainTitle,
        setIsGetError
      );
    },
    () => {
      connectSocket(
        client,
        setstore,
        setUserData,
        store,
        setIsMatched,
        setIsObtainTitle,
        setIsGetError
      );
    }
  );
  return client;
}
// 지하철 구독
function subscribeStation(client, setStore, curStation, setSubwayContentIdx) {
  if (curStation !== null) {
    client.subscribe(
      `/sub/stations/${curStation ? curStation.id : 1}`,
      (message) => {
        const payload = JSON.parse(message.body);
        // 지배자 기능 모음
        if (payload.type === "STATION") {
          // 지배자 강림
          if (payload.subType === "SHOW_UP_DOMINATOR") {
            setStore((prev) => {
              return {
                ...prev,
                dominatorAppear: payload.data,
              };
            });
          }
          // 현재 역의 지배자 변동
          else if (payload.subType === "CHANGE_DOMINATOR") {
            setStore((prev) => {
              return {
                ...prev,
                locationData: {
                  ...prev.locationData,
                  current: {
                    ...prev.locationData.current,
                    dominator: payload.data,
                  },
                },
              };
            });
          }
          // 지배자 확성기
          else if (payload.subType === "MESSAGE") {
            setStore((prev) => {
              return {
                ...prev,
                dominatorMsg: payload.data.message,
              };
            });
            setSubwayContentIdx(2);
          }
        }
      }
    );
  }
  return client;
}

function subscribeUser(
  client,
  setStore,
  setUserData,
  setIsMatched,
  setIsObtainTitle,
  setIsGetError
) {
  client.subscribe("/user/queue", (message) => {
    const payload = JSON.parse(message.body);

    //현재 역 변동 & 역 정보
    if (payload.type === "STATION") {
      if (payload.subType === "STATUS") {
        setStore((prev) => {
          return {
            ...prev,
            locationData: {
              prev: prev.locationData.current,
              current: payload.data.current,
            },
          };
        });
      }
    }
    // 수동 채굴 모음
    else if (payload.type === "MINING") {
      // 서류 종이 카운트
      if (payload.subType === "PAPER_COUNT") {
        setUserData((prev) => {
          return {
            ...prev,
            collectedPapers: payload.data,
          };
        });
      }
    }

    // 코인변동 모음
    else if (payload.type === "COIN") {
      // 코인 변동
      setStore((prev) => {
        return {
          ...prev,
          coinChange: {
            ...prev.coinchange,
            [payload.subType]: payload.data,
          },
        };
      });
      setUserData((prev) => {
        return {
          ...prev,
          coin: payload.data.balance,
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
        setUserData((prev) => {
          return {
            ...prev,
            title: { id: payload.data.id, name: payload.data.name },
          };
        });
      }
    }

    // 미니게임 모음
    else if (payload.type === "MINIGAME") {
      // 게임 매칭
      if (payload.subType === "MATCHING") {
        setStore((prev) => {
          let titleName;
          if (payload.data.titleType === 0 && payload.data.title.id !== -1) {
            titleName = `${payload.title.name}의 지배자`;
          } else {
            titleName = payload.title.name;
          }
          return {
            ...prev,
            matching: {
              ...payload.data,
              enemy: {
                ...payload.data.enemy,
                title: {
                  id: payload.data.title.id,
                  name: titleName,
                }
              }
            },
          };
        });
        setIsMatched(true);
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
    } else if (payload.type === "EXCEPTION") {
      // 확성기 실패 -> 지배중인 역이 없습니다.
      if (payload.subType === "NOT_DOMINATOR") {
        setIsGetError(true);
      }
    }
  });
  return client;
}

function unsubscribeStation(client, prevStation) {
  if (prevStation != null) {
    client.unsubscribe(`/sub/stations/${prevStation.id}`);
  }
  return client;
}

export { connectSocket, subscribeStation, subscribeUser, unsubscribeStation };
