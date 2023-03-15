import * as React from "react";
import "./GetCoinItem.css";

// 수동채굴 아이템 클릭시 수집 이펙트 적용 함수
function getCoinEffect(getCoinObject) {
  const target = document.querySelector(".subway-background");
  const element = getCoinObject;
  let elementTop = window.pageYOffset + element.getBoundingClientRect().top;
  let elementLeft = window.pageXOffset + element.getBoundingClientRect().left;
  const getCoinEffectObject = document.createElement("div");
  getCoinEffectObject.classList.add("get-coin-effect");
  getCoinEffectObject.innerHTML = "+ 1";
  getCoinEffectObject.style.position = "absolute";
  getCoinEffectObject.style.top = `${elementTop}px`;
  getCoinEffectObject.style.left = `${elementLeft}px`;
  target.appendChild(getCoinEffectObject);
  setTimeout(() => target.removeChild(getCoinEffectObject), 1000);
}

// 수동 채굴 아이템 클릭시 사라지는 이펙트 적용 함수
function getCoinClickHandler(getCoinObject) {
  getCoinEffect(getCoinObject);
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
  const targetValue = document.getElementsByClassName("subway-background")[0];
  const getCoinObject = document.createElement("div");
  const MIN_DURATION = 8.3;
  const delay = Math.random() * 5;
  
  let isClicked = false;

  getCoinObject.classList.add("get-coin-item");
  getCoinObject.style.bottom = `${Math.random() * 40}%`;
  getCoinObject.style.animation = `getCoinItemAppear ${MIN_DURATION}s linear`;
  getCoinObject.style.animationDelay = `${delay}s`;
  getCoinObject.addEventListener("click", (event) =>{
    if (!isClicked) {
      getCoinClickHandler(event.target)
      isClicked = !isClicked;
    }
  });
  

  targetValue.appendChild(getCoinObject);

  setTimeout(() => {
    targetValue.removeChild(getCoinObject);
    getCoinItemAppear();
  }, (delay + MIN_DURATION) * 1000);
}

// getCoinItemAppear 함수를 반복문 횟수만큼 호출
function drop() {
  for (let index = 0; index < 5; index++) {
    setTimeout(getCoinItemAppear(), 500 * index);
  }
}

function GetCoinItem() {
  drop();
  return <div></div>;
}

export default GetCoinItem;
