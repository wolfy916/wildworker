import * as React from "react";
import Box from "@mui/material/Box";
import "./battleDialog.css";
import BattleDialogTalk from "./battleDialogTalk";
import battleDialogImg from "../../asset/image/battleTalk.png";

export default function battleDialog() {
  return (
    <Box className="battleDialog">
      <img src={battleDialogImg} alt="battleDialogImg" />
      <BattleDialogTalk />
    </Box>
  );
}
