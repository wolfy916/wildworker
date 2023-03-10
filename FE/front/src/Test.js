import * as React from "react";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import "./Test.css";

function App() {
  return (
    <Container maxWidth="xs">
      <Box className="bg" sx={{ height: "100vh" }}>
        <div className="top"></div>
        <div className="subway"></div>
        <div className="bottom"></div>
      </Box>
    </Container>
  );
}

export default App;