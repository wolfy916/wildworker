import React from "react"
import ReactDOM from "react-dom/client"
import { BrowserRouter } from "react-router-dom"
import "./index.css"
import reportWebVitals from "./reportWebVitals"
import App from "./App"

document.documentElement.addEventListener('touchstart', function (event) {
  if (event.touches.length > 1) {
       event.preventDefault(); 
     } 
 }, false);

var lastTouchEnd = 0; 

document.documentElement.addEventListener('touchend', function (event) {
  var now = (new Date()).getTime();
  if (now - lastTouchEnd <= 300) {
       event.preventDefault(); 
     } lastTouchEnd = now; 
 }, false);

const root = ReactDOM.createRoot(document.getElementById("root"))
root.render(
  // React.StrictMode는 개발 전용 모드로 해당 태그 때문에
  // 모든 하위 컴포넌트의 함수가 2번씩 실행되어서 주석처리
  // <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  // </React.StrictMode>
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
