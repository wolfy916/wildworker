import * as React from "react"
import Box from "@mui/material/Box"
import BattleDialog from "../components/battle/battleDialog"
import BattleCharater from "../components/battle/battlecharacter"
import battleDirection from "../asset/image/battleDirection.png"
import battleAudioBack from "../asset/audio/battleAudioBack.mp3"
import "./PvpPage.css"

function MainPage(props) {
  const matchingData = props.matchingData
  const gameRunData = props.gameRunData
  const gameStartData = props.gameStartData

  return (
    <Box className="PvpPageBg" sx={{ position: "relative" }}>
      <div className="battleCharacter1">
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater />
        <p>신도림의 지배자 권태형</p>
      </div>
      <div className="battleCharacter2">
        <img src={battleDirection} alt="battleDirection" />
        <BattleCharater />
        <p>신도림의 지배자 권태형</p>
      </div>

      <BattleDialog />
    </Box>
  )
}

export default MainPage
