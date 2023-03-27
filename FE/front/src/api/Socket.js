// const [isConnected, setIsConnected] = useState(false);

// // 연결
// useEffect(()=>{
//   stompClient.connect({}, () => {
//     setIsConnected(true);
//   });
// },[]);

// // 지하철 구독
// useEffect(()=>{
//   if (isConnected) {
//     const stationSubscription = stompClient.subscribe(`/sub/systems/${stationId}`, (message) => {
//       const payload = JSON.parse(message.body);
//       // 지배자 기능 모음
//       if (payload.type === "STATION") {
//         // 지배자 강림
//         if (payload.subType === "DOMINATOR") {
//           setDominatorComeData(payload.data);
//         }
//         // 지배자 확성기
//         else if (payload.subType === "MESSAGE") {
//           setDominatorMessageData(payload.data);
//         }
//       }
//     });
//   }

//   return () => {
//     stationSubscription.unsubscribe();
//   };
  
// },[isConnected, stationId]);

// // 나머지 구독
// useEffect(() => {
//   if (isConnected) {
//     stompClient.subscribe("/user/queue", (message) => {
//       const payload = JSON.parse(message.body);
  
//       //현재 역 변동 & 역 정보
//       if (payload.type === "STATION" && payload.subType === "STATUS") {
//         setLocationData(payload.data);
//       }
  
//       // 수동 채굴 모음
//       else if (payload.type === "MINING") {
//         // 서류 종이 카운트
//         if (payload.subType === "PAPER_COUNT") {
//           setManualMiningData(payload.data);
//         }
//       }
  
//       // 코인변동 모음
//       else if (payload.type === "COIN") {
//         // 자동 코인 변동
//         if (payload.subType === "AUTO_MINING") {
//           setAutoCoinData(payload.data);
//         }
//         //수동 코인변동
//         else if (payload.subType === "MANUAL_MINING") {
//           setManualCoinData(payload.data);
//         }
//         //게임비
//         else if (payload.subType === "MINI_GAME_COST") {
//           setGameCostData(payload.data);
//         }
//         //도망비
//         else if (payload.subType === "MINI_GAME_RUN_COST") {
//           setRunCostData(payload.data);
//         }
//         //게임보상금
//         else if (payload.subType === "MINI_GAME_REWARD") {
//           setGameRewardData(payload.data);
//         }
//         //투자액
//         else if (payload.subType === "INVESTMENT") {
//           setInvestCostData(payload.data);
//         }
//         //투자보상금
//         else if (payload.subType === "INVESTMENT_REWARD") {
//           setInvestRewardData(payload.data);
//         }
//       }
  
//       // 칭호관련 모음
//       else if (payload.type === "TITLE") {
//         // 칭호 획득
//         if (payload.subType === "GET") {
//           setGetTitleData(payload.data);
//         }
//         // 내 대표 칭호 변동
//         else if (payload.subType === "MAIN_TITLE_UPDATE") {
//           setChangeTitleData(payload.data);
//         }
//       }
  
//       // 미니게임 모음
//       else if (payload.type === "MINIGAME") {
//         // 게임 매칭
//         if (payload.subType === "MATCHING") {
//           setMatchingData(payload.data);
//         }
//         // 게임 취소 (도망 성공)
//         else if (payload.subType === "CANCEL") {
//           setGameRunData(payload.data);
//         }
//         // 게임 시작
//         else if (payload.subType === "START") {
//           setGameStartData(payload.data);
//         }
//         // 게임 결과
//         else if (payload.subType === "RESULT") {
//           setGameResultData(payload.data);
//         }
//       }
//     });
//   }
// }, [isConnected]);

// export { runSocket };
