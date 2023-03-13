import * as React from "react";
import Box from "@mui/material/Box";
import "./battlecharacter.css";

import character from "../../asset/image/stop_man.png";

export default function BattleTalk() {
  return (
    <Box className="battleCharacter">
      <img src={character} alt="character" />
    </Box>
  );
}
