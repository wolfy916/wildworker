import * as React from "react"
import "./MainPage.css"
import character from "../asset/image/moving_man.gif"

function MainPage() {
  return (
    <div className="subway-background">
      <div className="subway">
        <img src={character} alt="character" />
      </div>
    </div>
  )
}

export default MainPage
