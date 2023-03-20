  // SubwayBoard.js 1번

  // const [sock, setSock] = useState(null);
  
  // useEffect(() => {
  //   const socket = new SockJS(SOCKET_SERVER_URL);
  //   setSock(socket);

  //   return () => {
  //     socket.close();
  //   };
  // }, []);

  // useEffect(() => {
  //   if (!sock) return;

  //   sock.onmessage = (e) => {
  //     if (e.type == 'COIN' && e.subType == 'AUTO_MINING') {
  //       setCoin(e.data.balance);
  //       popOpen()
  //       setIsFlashing(true);

  //       setTimeout(() => {
  //         popClose()
  //         setIsFlashing(false);
  //       }, 1000);
  //     }
  //   };

  //   return () => {
  //     sock.close();
  //   };
  // }, [sock]);

  // return (
  //   <div>
  //     {data && <p>{data}</p>}
  //   </div>
  // );

  // 백에서 위치 받는 코드

  // const [sock, setSock] = useState(null);
  // const [data, setData] = useState(null);

  // useEffect(() => {
  //   const socket = new SockJS('http://localhost:3000/sockjs');
  //   setSock(socket);

  //   return () => {
  //     socket.close();
  //   };
  // }, []);

  // useEffect(() => {
  //   if (!sock) return;

  //   sock.onmessage = (e) => {
  //     if (e.type == 'STATION' && e.subType == 'STATUS'){
  //     setData(e.data);}
  //   };

  //   return () => {
  //     sock.close();
  //   };
  // }, [sock]);

  // return (
  //   <div>
  //     {data && <p>{data}</p>}
  //   </div>
  // );


