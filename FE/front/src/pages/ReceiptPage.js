import * as React from "react"
import Box from "@mui/material/Box"
import { useNavigate } from "react-router-dom"
import "./ReceiptPage.css"

function MainPage(props) {
  const gameResultData = props.gameResultData

  const navigate = useNavigate()
  function handleTouchStart(event) {
    // 다음 페이지로 이동하는 로직을 작성합니다.
    navigate("/main")
    console.log("go to mainPage")
  }
  return (
    <Box className="receiptPage" sx={{ position: "relative" }}>
      <div className="receipt">
        <div className="receipt-text">
          <p className="receipt-text-header">영 수 증</p>
          <div className="receipt-text-main">
            <p className="receipt-text-main-left">게임비</p>
            <p className="receipt-text-main-right">1,000원</p>
            <p className="receipt-text-main-left">도망비</p>
            <p className="receipt-text-main-right">1111111111원</p>
            <p className="receipt-text-main-left">환급비</p>
            <p className="receipt-text-main-right">111,111,111,111원</p>
            <p className="receipt-text-main-left">수수료</p>
            <p className="receipt-text-main-right">2,342,314,234원</p>
            <p className="receipt-text-main-total1">TOTAL</p>
            <p className="receipt-text-main-total2">10,000원</p>
          </div>
          <p className="receipt-text-footer">야생의 직장인</p>
          <div className="receipt-button-home">
            <p onTouchStart={handleTouchStart}>확인</p>
          </div>
        </div>
      </div>
    </Box>
  )
}

export default MainPage
