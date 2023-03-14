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
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { Link } from "react-router-dom"
import subwaymap from "../asset/image/subwaymap.png"
import goMain from "../asset/image/goMain.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"
import "./SubwayMapPage.css"

function SubwayMapPage() {
  const [position, setPosition] = useState({ x: 0, y: 0 })

  const handleTouchStart = (e) => {
    // Get the initial position of the touch
    const touch = e.touches[0]
    const startX = touch.pageX - position.x
    const startY = touch.pageY - position.y

    const handleTouchMove = (e) => {
      // Calculate the new position of the big picture based on the touch position
      const touch = e.touches[0]
      const x = touch.pageX - startX
      const y = touch.pageY - startY
      setPosition({ x, y })
    }

    // Add the touch move and touch end event listeners
    document.addEventListener("touchmove", handleTouchMove)
    document.addEventListener("touchend", () => {
      document.removeEventListener("touchmove", handleTouchMove)
    })
  }

  const navigate = useNavigate()

  function navigateToPage(detail) {
    navigate(`/page/${detail}`)
  }

  return (
    <div>
      <div
        style={{
          position: "relative",
          width: "400px",
          height: "500px",
          border: "1px solid black",
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
            <area onClick={() => navigateToPage(1)} />
            <area onClick={() => navigateToPage(2)} />
            <area onClick={() => navigateToPage(3)} />
            <area onClick={() => navigateToPage(4)} />
            <area onClick={() => navigateToPage(5)} />
            <area onClick={() => navigateToPage(6)} />
            <area onClick={() => navigateToPage(7)} />
            <area onClick={() => navigateToPage(8)} />
            <area onClick={() => navigateToPage(9)} />
            <area onClick={() => navigateToPage(10)} />
            <area onClick={() => navigateToPage(11)} />
            <area onClick={() => navigateToPage(12)} />
            <area onClick={() => navigateToPage(13)} />
            <area onClick={() => navigateToPage(14)} />
            <area onClick={() => navigateToPage(15)} />
            <area onClick={() => navigateToPage(16)} />
            <area onClick={() => navigateToPage(17)} />
            <area onClick={() => navigateToPage(18)} />
            <area onClick={() => navigateToPage(19)} />
            <area onClick={() => navigateToPage(20)} />
            <area onClick={() => navigateToPage(21)} />
            <area onClick={() => navigateToPage(22)} />
            <area onClick={() => navigateToPage(23)} />
            <area onClick={() => navigateToPage(24)} />
            <area onClick={() => navigateToPage(25)} />
            <area onClick={() => navigateToPage(26)} />
            <area onClick={() => navigateToPage(27)} />
            <area onClick={() => navigateToPage(28)} />
            <area onClick={() => navigateToPage(29)} />
            <area onClick={() => navigateToPage(30)} />
            <area onClick={() => navigateToPage(31)} />
            <area onClick={() => navigateToPage(32)} />
            <area onClick={() => navigateToPage(33)} />
            <area onClick={() => navigateToPage(34)} />
            <area onClick={() => navigateToPage(35)} />
            <area onClick={() => navigateToPage(36)} />
            <area onClick={() => navigateToPage(37)} />
            <area onClick={() => navigateToPage(38)} />
            <area onClick={() => navigateToPage(39)} />
            <area onClick={() => navigateToPage(40)} />
            <area onClick={() => navigateToPage(41)} />
            <area onClick={() => navigateToPage(42)} />
            <area onClick={() => navigateToPage(43)} />
            <area onClick={() => navigateToPage(44)} />
            <area onClick={() => navigateToPage(45)} />
            <area onClick={() => navigateToPage(46)} />
            <area onClick={() => navigateToPage(47)} />
            <area onClick={() => navigateToPage(48)} />
            <area onClick={() => navigateToPage(49)} />
            <area onClick={() => navigateToPage(50)} />
            <area onClick={() => navigateToPage(51)} />
          </map>
        </div>
      </div>
      <nav>
        <Link to="/map/detail">역 누르면 detail화면으로</Link>
        <hr />
        <Link to="/map/mine">
          <img src={myMap} alt="myMap" />
        </Link>
        <Link to="/main">
          <img src={goMain} alt="goMain" />
        </Link>
        <Link to="/map/hot">
          <img src={hotMap} alt="hotMap" />
        </Link>
      </nav>
    </div>
  )
}

export default SubwayMapPage
