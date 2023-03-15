import * as React from "react";
import Box from "@mui/material/Box";
import "./ReceiptPage.css";

function MainPage() {
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
            <p className="receipt-text-main-right">1111111111111111111원</p>
            <p className="receipt-text-main-left">수수료</p>
            <p className="receipt-text-main-right">12342314234원</p>
            <p className="receipt-text-main-total1">TOTAL</p>
            <p className="receipt-text-main-total2">10,000원</p>
          </div>
          <p className="receipt-text-footer">야생의 직장인</p>
        </div>
      </div>
    </Box>
  );
}

export default MainPage;
