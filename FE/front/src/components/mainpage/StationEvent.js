import * as React from "react";
import "./StationEvent.css";

import lotteWoman from "../../asset/image/lotteWoman.png";
import lotteMan from "../../asset/image/lotteMan.png";
import ssafyMan1 from "../../asset/image/ssafyMan1.png";
import ssafyMan2 from "../../asset/image/ssafyMan2.png";
import ssafyWoman1 from "../../asset/image/ssafyWoman1.png";
import ssafyWoman2 from "../../asset/image/ssafyWoman2.png";
import businessMan1 from "../../asset/image/businessMan1.png";
import businessMan2 from "../../asset/image/businessMan2.png";
import businessWoman from "../../asset/image/businessWoman.png";
import blackMan from "../../asset/image/blackMan.png";
import whiteMan from "../../asset/image/whiteMan.png";
import hiphopMan from "../../asset/image/hiphopMan.png";

function StationEvent(props) {
  const stationName = props.stationName;
  const stationEventObj = {
    없음: { person: [ssafyMan1], limit: 0 },
    역삼역: {
      person: [ssafyMan1, ssafyMan2, ssafyWoman1, ssafyWoman2],
      limit: 15,
    },
    신도림역: {
      person: [businessMan1, businessMan2, businessWoman],
      limit: 20,
    },
    사당역: { person: [businessMan1, businessMan2, businessWoman], limit: 20 },
    잠실역: { person: [lotteWoman, lotteMan], limit: 2 },
    홍대입구역: { person: [blackMan, whiteMan, hiphopMan], limit: 5 },
    멀티캠퍼스: {
      person: [ssafyMan1, ssafyMan2, ssafyWoman1, ssafyWoman2],
      limit: 25,
    },
  };
  // useEffect 내부의 재귀함수를 중지하기 위한 트리거
  let stopRecursion = !props.startStationEvent;

  // useEffect를 사용하여 drop()을 한 번 실행
  React.useEffect(() => {
    const personLimit = stationEventObj[stationName].limit;

    // 수동 채굴 아이템 1개를 만드는 재귀 함수
    function humanObjectAppear() {
      // 재귀함수 중지 트리거
      if (stopRecursion) return;
      const randomValue =
        stationEventObj[stationName].person[
          Math.floor(Math.random() * stationEventObj[stationName].person.length)
        ];

      // 수동 채굴 아이템 Tag 생성 및 속성 설정
      const targetValue =
        document.getElementsByClassName("subway-background")[0];
      const humanObject = document.createElement("div");
      const MIN_DURATION = 13;
      const delay = Math.random() * 5;
      const heightPosition = Math.random() * 30;
      humanObject.classList.add("human-object");
      humanObject.style.bottom = `${heightPosition}%`;
      humanObject.style.animation = `humanObjectAppear ${MIN_DURATION}s linear`;
      humanObject.style.animationDelay = `${delay}s`;
      humanObject.style.backgroundImage = `url(${randomValue})`;
      humanObject.style.zIndex = `${30 - Math.floor(heightPosition) + 1000}`;

      targetValue.appendChild(humanObject);

      // (delay + MIN_DURATIOn)초가 지나면 하나의 아이템이 화면 왼쪽 바깥으로 나감
      // 보이지 않게된 아이템을 삭제하고, 함수 재실행하여 또 다른 아이템 1개를 생성하여 개수를 유지함
      setTimeout(() => {
        targetValue.removeChild(humanObject);
        humanObjectAppear();
      }, (delay + MIN_DURATION) * 1000);
    }

    // 함수를 반복문 횟수(아이템 개수 제한)만큼 호출
    function drop() {
      for (let index = 0; index < personLimit; index++) {
        setTimeout(humanObjectAppear(), 5000 * Math.random() * index);
      }
    }

    // 함수 실행
    drop();

    return () => {
      stopRecursion = true; // 재귀함수 중지 bool 값
    };
  }, [stopRecursion]);

  return <div className="station-event-component"></div>;
}
export default StationEvent;
