import * as React from "react";
import "./GetCoinItem.css";

function GetCoinItem(props) {
  // useEffect 내부에서 props를 사용하는데
  // useRef를 사용하여 종속성을 부여하고, drop()의 재실행을 방지
  const getCoinCnt = () => {
    props.setIsClickDoc(prev => !prev);
  }
  const getCoinCntRef = React.useRef(getCoinCnt);

  // useEffect 내부의 재귀함수를 중지하기 위한 트리거
  let stopRecursion = props.isEnough;

  // useEffect를 사용하여 drop()을 한 번 실행
  React.useEffect(() => {
    // 수동채굴 아이템 클릭시 수집 이펙트 적용 함수
    function getCoinEffect(getCoinObject) {
      const target = document.querySelector(".subway-background");
      const element = getCoinObject;

      // 수집 아이템 이동 중 클릭하였을 때의 위치 좌표
      let elementTop = window.pageYOffset + element.getBoundingClientRect().top;
      let elementLeft =
        window.pageXOffset + element.getBoundingClientRect().left;

      // + 1 이펙트를 넣을 div Tag 생성 및 속성 설정
      const getCoinEffectObject = document.createElement("div");
      getCoinEffectObject.classList.add("get-coin-effect");
      getCoinEffectObject.innerHTML = "+ 1";
      getCoinEffectObject.style.position = "absolute";
      getCoinEffectObject.style.top = `${elementTop}px`;
      getCoinEffectObject.style.left = `${elementLeft}px`;

      // .subject-background 태그의 자식 태그로 추가
      target.appendChild(getCoinEffectObject);

      // 애니메이션(1초)가 끝난 후, 해당 태그 제거
      setTimeout(() => target.removeChild(getCoinEffectObject), 1000);
    }

    // 수동 채굴 아이템 클릭시 사라지는 이펙트 적용 함수
    function getCoinClickHandler(getCoinObject) {
      getCoinEffect(getCoinObject); // +1 효과 실행

      // CSS의 animation을 다중으로 적용하는 구문
      const effectProps = {
        duration: 1000,
        easing: "ease-in-out",
        iterations: 1,
        direction: "alternate",
        fill: "forwards",
        composite: "add",
      };
      const shake = [
        { transform: "rotate(0deg) scale(1)" },
        { transform: "rotate(-60deg) scale(1.2)" },
        { transform: "rotate(60deg) scale(1.3)" },
        { transform: "rotate(0deg) scale(1.1)" },
      ];
      getCoinObject.animate(shake, effectProps);
      setTimeout(() => {
        getCoinObject.style.visibility = "hidden";
      }, 1000);
    }

    // 수동 채굴 아이템 1개를 만드는 재귀 함수
    function getCoinItemAppear() {
      // 재귀함수 중지 트리거
      if (stopRecursion) return;

      // 수동 채굴 아이템 Tag 생성 및 속성 설정
      const targetValue =
        document.getElementsByClassName("subway-background")[0];
      const getCoinObject = document.createElement("div");
      const MIN_DURATION = 8.3;
      const delay = Math.random() * 5;
      getCoinObject.classList.add("get-coin-item");
      getCoinObject.style.bottom = `${Math.random() * 40}%`;
      getCoinObject.style.animation = `getCoinItemAppear ${MIN_DURATION}s linear`;
      getCoinObject.style.animationDelay = `${delay}s`;

      let isClicked = false; // 중복 클릭 방지
      getCoinObject.addEventListener("click", (event) => {
        if (!isClicked) {
          getCoinClickHandler(event.target); // 이펙트 효과 실행
          getCoinCntRef.current(); // 상위 컴포넌트(MainPage.js)의 함수 실행
          isClicked = !isClicked;
        }
      });

      targetValue.appendChild(getCoinObject);

      // (delay + MIN_DURATIOn)초가 지나면 하나의 아이템이 화면 왼쪽 바깥으로 나감
      // 보이지 않게된 아이템을 삭제하고, 함수 재실행하여 또 다른 아이템 1개를 생성하여 개수를 유지함
      setTimeout(() => {
        targetValue.removeChild(getCoinObject);
        getCoinItemAppear();
      }, (delay + MIN_DURATION) * 1000);
    }

    // getCoinItemAppear 함수를 반복문 횟수(아이템 개수 제한)만큼 호출
    function drop() {
      for (let index = 0; index < 5; index++) {
        setTimeout(getCoinItemAppear(), 500 * index);
      }
    }

    // 함수 실행
    drop();

    return () => {
      stopRecursion = true; // 재귀함수 중지 bool 값
    };
  }, [getCoinCntRef, stopRecursion]);

  return <div></div>;
}

export default GetCoinItem;
