import * as React from "react"
import Box from "@mui/material/Box"
import Container from "@mui/material/Container"
import "./MainPage.css"
import { Link } from "react-router-dom"
import character from "../moving_man.gif"
import goMap from "../asset/image/goMap.png";

function MainPage() {
  return (
    <Container className="container" maxWidth="xs">
      <Box className="bg" sx={{ height: "100vh" }}>
        <div className="subway-background">
          <div className="subway">
            <img src={character} alt="character" />
            <Link to="/map"> <img src={goMap} alt="goMap"/></Link>
          </div>
        </div>
      </Box>
    </Container>
  )
}

export default MainPage
