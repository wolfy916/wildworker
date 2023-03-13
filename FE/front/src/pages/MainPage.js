import * as React from "react"
import Box from "@mui/material/Box"
import Container from "@mui/material/Container"
import "./MainPage.css"
import character from "../asset/image/moving_man.gif"

function MainPage() {
  return (
    <Container className="container" maxWidth="xs">
      <Box className="bg" sx={{ height: "100vh" }}>
        <div className="subway-background">
          <div className="subway">
            <img src={character} alt="character" />
          </div>
        </div>
      </Box>
    </Container>
  )
}

export default MainPage
