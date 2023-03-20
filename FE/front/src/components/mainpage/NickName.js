import * as React from "react";
import "./NickName.css";

function NickName(props) {
    return (
        <div className="modal-component">
            <div className="modal-title">나의 닉네임</div>
            <div className="modal-content">
                <div className="current-nickname">
                    현재 닉네임 : {props.nickname}
                </div>
            </div>
        </div>
    )
}

export default NickName;