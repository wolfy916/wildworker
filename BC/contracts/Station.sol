// SPDX-License-Identifier: MIT

pragma solidity 0.8.19;

contract Station {

    uint16 public id;
    string public name;

    mapping(address => uint) public investors;

    constructor(uint16 _id, string memory _name) {
        id = _id;
        name = _name;
    }

    // todo 지하철 진입 시 돈 지급

    // todo 게임에 대한 승리 수당 지급

    // todo 투자에 대한 처리
    
}