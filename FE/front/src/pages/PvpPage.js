import * as React from "react";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import BattleDialog from "../components/battle/battleDialog";
import BattleCharater from "../components/battle/battlecharacter";
import battleDirection from "../asset/image/battleDirection.png";
import "./PvpPage.css";

function MainPage() {
  return (
    <Container className="container" maxWidth="xs">
      <Box className="PvpPageBg" sx={{ position: "relative" }}>
        <div className="battleCharacter1">
          <img src={battleDirection} alt="battleDirection" />
          <BattleCharater />
          <p>신도림의 지배자 권태형</p>
        </div>
        <div className="battleCharacter2">
          <img src={battleDirection} alt="battleDirection" />
          <BattleCharater className="charcterImg2" />
          <p>신도림의 지배자 권태형</p>
        </div>

        <BattleDialog />
      </Box>
    </Container>
  );
}

export default MainPage;
