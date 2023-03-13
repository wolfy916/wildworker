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

  return (
    <div>
      <div
        style={{
          position: "relative",
          width: "400px",
          height: "400px",
          border: "1px solid black",
          overflow: "hidden",
        }}
      >
        <div
          style={{
            position: "absolute",
            top: `${position.y}px`,
            left: `${position.x}px`,
            width: "460px",
            height: "535px",
            backgroundImage: `url(${subwaymap})`,
            backgroundPosition: "top left",
            transform: "translate(-50%, -50%)",
          }}
          onTouchStart={handleTouchStart}
        />
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
