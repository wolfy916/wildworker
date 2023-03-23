// import React, { useState, useEffect } from "react";
// import SockJS from "sockjs-client";
// import Stomp from "stompjs";

// const TestSocket = () => {
//   const [messages, setMessages] = useState([]);

//   useEffect(() => {
//     const socket = new SockJS("http://localhost:8080/ws");
//     const stompClient = Stomp.over(socket);

//     stompClient.connect({}, () => {
//       stompClient.subscribe("/topic/messages", (message) => {
//         setMessages((messages) => [...messages, message.body]);
//       });
//     });

//     return () => {
//       stompClient.disconnect();
//     };
//   }, []);

//   return (
//     <div>
//       <h2>WebSocket Example</h2>
//       <ul>
//         {messages.map((message, index) => (
//           <li key={index}>{message}</li>
//         ))}
//       </ul>
//     </div>
//   );
// };

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
