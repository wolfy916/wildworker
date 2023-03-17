// import * as React from "react"
// import { useState } from "react"
// import { Link } from "react-router-dom"
// import subwaymap from "../asset/image/subwaymap.png"
// import goMain from "../asset/image/goMain.png"
// import myMap from "../asset/image/myMap.png"
// import hotMap from "../asset/image/hotMap.png"
// import "./SubwayMapPage.css"

// function SubwayMapPage() {
//   const [position, setPosition] = useState({ x: 0, y: 0 })

//   const handleMouseDown = (e) => {
//     // Get the initial position of the mouse
//     const startX = e.pageX - position.x
//     const startY = e.pageY - position.y

//     const handleMouseMove = (e) => {
//       // Calculate the new position of the big picture based on the mouse position
//       const x = e.pageX - startX
//       const y = e.pageY - startY
//       setPosition({ x, y })
//     }

//     // Add the mouse move and mouse up event listeners
//     document.addEventListener("mousemove", handleMouseMove)
//     document.addEventListener("mouseup", () => {
//       document.removeEventListener("mousemove", handleMouseMove)
//     })
//   }

//   return (
//     <div>
//       <div
//         style={{
//           position: "relative",
//           width: "400px",
//           height: "400px",
//           border: "1px solid black",
//           overflow: "hidden",
//         }}
//       >
//         <div
//           style={{
//             position: "absolute",
//             top: `${position.y}px`,
//             left: `${position.x}px`,
//             width: "455px",
//             height: "535px",
//             backgroundImage: `url(${subwaymap})`,
//             backgroundPosition: "top left",
//             transform: "translate(-50%, -50%)",
//           }}
//           onMouseDown={handleMouseDown}
//         />
//       </div>
//       <nav>
//         <Link to="/map/detail">역 누르면 detail화면으로</Link>
//         <hr />
//         <Link to="/map/mine">
//           <img src={myMap} alt="myMap" />
//         </Link>
//         <Link to="/main">
//           <img src={goMain} alt="goMain" />
//         </Link>
//         <Link to="/map/hot">
//           <img src={hotMap} alt="hotMap" />
//         </Link>
//       </nav>
//     </div>
//   )
// }

// export default SubwayMapPage

// 위의 코드는 마우스 용~~~~~
// 아래는 모바일 터치 용~~~

import * as React from "react"
import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { Link } from "react-router-dom"
import subwaymap from "../asset/image/subwaymap.png"
import goMain from "../asset/image/goMain.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"
import "./SubwayMapPage.css"

function SubwayMapPage() {
  const [subwayList, setSubwayList] = useState([])
  const [position, setPosition] = useState({ x: 0, y: 0 })
  const mystationtestlist = [3, 4, 16]

  useEffect(() => {
    const subway = document.querySelectorAll("area")
    const subwayItems = Array.from(subway)
    setSubwayList(subwayItems)
  }, [])
  if (subwayList.length > 0) {
    for (let index = 0; index < subwayList.length; index++) {
      const element = subwayList[index]
      if (mystationtestlist.includes(index + 1)) {
        element.style.backgroundColor = "tomato"
        element.style.opacity = "0.4"
      }
    }
  }

  const handleTouchStart = (e) => {
    const touch = e.touches[0]
    const startX = touch.pageX - position.x
    const startY = touch.pageY - position.y

    const handleTouchMove = (e) => {
      const touch = e.touches[0]
      const x = touch.pageX - startX
      const y = touch.pageY - startY

      if (x < 350 && x > -520 && y < 180 && y > -610) {
        setPosition({ x, y })
      }
    }

    document.addEventListener("touchmove", handleTouchMove)
    document.addEventListener("touchend", () => {
      document.removeEventListener("touchmove", handleTouchMove)
    })
  }

  const navigate = useNavigate()

  function navigateToPage(subwayId) {
    navigate(`/map/detail`, { state: subwayId })
  }

  return (
    <div>
      <div
        style={{
          position: "relative",
          width: "100%",
          height: "100vh",
          overflow: "hidden",
        }}
      >
        <div
          className="test"
          style={{
            position: "absolute",
            top: `${position.y}px`,
            left: `${position.x}px`,
            width: "692px",
            height: "824px",
            backgroundImage: `url(${subwaymap})`,
            backgroundPosition: "top left",
            transform: "translate(0%,0%) scale(1.4)",
          }}
          useMap="#photo-map"
          onTouchStart={handleTouchStart}
        >
          <map name="photo-map">
            <area onClick={() => navigateToPage(1)} alt="area" />
            <area onClick={() => navigateToPage(2)} alt="area" />
            <area onClick={() => navigateToPage(3)} alt="area" />
            <area onClick={() => navigateToPage(4)} alt="area" />
            <area onClick={() => navigateToPage(5)} alt="area" />
            <area onClick={() => navigateToPage(6)} alt="area" />
            <area onClick={() => navigateToPage(7)} alt="area" />
            <area onClick={() => navigateToPage(8)} alt="area" />
            <area onClick={() => navigateToPage(9)} alt="area" />
            <area onClick={() => navigateToPage(10)} alt="area" />
            <area onClick={() => navigateToPage(11)} alt="area" />
            <area onClick={() => navigateToPage(12)} alt="area" />
            <area onClick={() => navigateToPage(13)} alt="area" />
            <area onClick={() => navigateToPage(14)} alt="area" />
            <area onClick={() => navigateToPage(15)} alt="area" />
            <area onClick={() => navigateToPage(16)} alt="area" />
            <area onClick={() => navigateToPage(17)} alt="area" />
            <area onClick={() => navigateToPage(18)} alt="area" />
            <area onClick={() => navigateToPage(19)} alt="area" />
            <area onClick={() => navigateToPage(20)} alt="area" />
            <area onClick={() => navigateToPage(21)} alt="area" />
            <area onClick={() => navigateToPage(22)} alt="area" />
            <area onClick={() => navigateToPage(23)} alt="area" />
            <area onClick={() => navigateToPage(24)} alt="area" />
            <area onClick={() => navigateToPage(25)} alt="area" />
            <area onClick={() => navigateToPage(26)} alt="area" />
            <area onClick={() => navigateToPage(27)} alt="area" />
            <area onClick={() => navigateToPage(28)} alt="area" />
            <area onClick={() => navigateToPage(29)} alt="area" />
            <area onClick={() => navigateToPage(30)} alt="area" />
            <area onClick={() => navigateToPage(31)} alt="area" />
            <area onClick={() => navigateToPage(32)} alt="area" />
            <area onClick={() => navigateToPage(33)} alt="area" />
            <area onClick={() => navigateToPage(34)} alt="area" />
            <area onClick={() => navigateToPage(35)} alt="area" />
            <area onClick={() => navigateToPage(36)} alt="area" />
            <area onClick={() => navigateToPage(37)} alt="area" />
            <area onClick={() => navigateToPage(38)} alt="area" />
            <area onClick={() => navigateToPage(39)} alt="area" />
            <area onClick={() => navigateToPage(40)} alt="area" />
            <area onClick={() => navigateToPage(41)} alt="area" />
            <area onClick={() => navigateToPage(42)} alt="area" />
            <area onClick={() => navigateToPage(43)} alt="area" />
            <area onClick={() => navigateToPage(44)} alt="area" />
            <area onClick={() => navigateToPage(45)} alt="area" />
            <area onClick={() => navigateToPage(46)} alt="area" />
            <area onClick={() => navigateToPage(47)} alt="area" />
            <area onClick={() => navigateToPage(48)} alt="area" />
            <area onClick={() => navigateToPage(49)} alt="area" />
            <area onClick={() => navigateToPage(50)} alt="area" />
            <area onClick={() => navigateToPage(51)} alt="area" />
          </map>
        </div>
        <Link className="map-router-my-btn" to="/map/mine">
          <img src={myMap} alt="myMap" />
        </Link>
        <Link className="map-router-main-btn" to="/main">
          <img src={goMain} alt="goMain" />
        </Link>
        <Link className="map-router-hot-btn" to="/map/hot">
          <img src={hotMap} alt="hotMap" />
        </Link>
      </div>
    </div>
  )
}

export default SubwayMapPage
