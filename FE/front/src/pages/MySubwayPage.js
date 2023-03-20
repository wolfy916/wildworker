import * as React from "react"
import { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from "react-router-dom"
import "./MySubwayPage.css"
import goMap from "../asset/image/goMap.png"
import myMap from "../asset/image/myMap.png"
import hotMap from "../asset/image/hotMap.png"

function MySubwayPage() {
  // const [data, setData] = useState([]);

  // useEffect(() => {
  //   axios.get('/api/data')
  //     .then(response => {
  //       setData(response.data);
  //     })
  //     .catch(error => {
  //       console.log(error);
  //     });
  // }, []);

  return (
    <div className="my-background">
      <div className="my-holder">
        <div className="my-title">
          <div>
            <p className="my-subject">zl존원석</p>
          </div>
          <div>
            <p className="my-subject"></p>
          </div>
          <div>
            <p className="my-subject">dwqdw</p>
          </div>
        </div>
      </div>
      <Link className="my-router-my-btn" to="/map/mine">
        <img src={myMap} alt="myMap" />
      </Link>
      <Link className="my-router-map-btn" to="/map">
        <img src={goMap} alt="goMap" />
      </Link>
      <Link className="my-router-hot-btn" to="/map/hot">
        <img src={hotMap} alt="hotMap" />
      </Link>
    </div>
  )
}

export default MySubwayPage
