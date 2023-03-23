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
import SubwayTime from "../components/subwaymap/SubwayTime";
import subwaymap from "../asset/image/subwaymap.png"
import goMain from "../asset/image/goMain.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"
import black from "../asset/image/black.png"
import tomato from "../asset/image/tomato.png"
import "./SubwayMapPage.css"

function SubwayMapPage() {
  const [position, setPosition] = useState({ x: 0, y: 0 })
  const [isReady, setIsReady] = useState(false)
  const MY_STATION_LIST = [3, 4, 16]
  const [zoomLevel, setZoomLevel] = useState(1);

  function handleDoubleTouchMove(e) {
    if (e.touches.length == 2) {
      const [touch1, touch2] = e.touches;
      const distance = Math.sqrt(
        (touch1.clientX - touch2.clientX) ** 2 +
          (touch1.clientY - touch2.clientY) ** 2
      );
      setZoomLevel(distance / 300);
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

  const PAGE_COUNT = 51
  const photoMapTag = document.getElementsByName("photo-map")[0]

  if (isReady) {
    for (let i = 1; i <= PAGE_COUNT; i++) {
      const areaTag = document.createElement("area")
      areaTag.setAttribute("alt", "area")
      if (MY_STATION_LIST.includes(i)) {
        areaTag.style.backgroundColor = "tomato"
        areaTag.style.opacity = "0.4"
      }
      areaTag.addEventListener("click", () => {
        return navigateToPage(i)
      })
      photoMapTag.appendChild(areaTag)
    }
  }

  React.useEffect(() => {
    setIsReady(true)
  }, [])



  return (
    <div>
      <SubwayTime/>
      <div className="map-station-color">
        <p className="map-station-color-content">
          <img className="map-station-color-img" src={tomato} alt="color" />{" "}
          나의 역
        </p>
        <p className="map-station-color-content">
          <img className="map-station-color-img" src={black} alt="color" /> 현재
          역
        </p>
      </div>
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
            transition: "transform 2s ease-out",
            // transform: "translate(0%,0%) scale(1.4)",
            // transformOrigin: "0 0",
            zoom: zoomLevel,
          }}
          useMap="#photo-map"
          onTouchStart={handleTouchStart}
          onTouchMove={handleDoubleTouchMove}
        >
          <map name="photo-map"></map>
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
