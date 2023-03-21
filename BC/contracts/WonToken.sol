// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Won {
    string public name = "Won Token";
    string public symbol = "Won";
    uint256 public totalSupply = 10000000000; // 1 million tokens 100억개
    uint8 public decimals = 0;

    event Transfer(address indexed _from, address indexed _to, uint256 _value);

    mapping(address => uint256) public balanceOf;

    constructor() {
        balanceOf[msg.sender] = totalSupply;
    }

    function transfer(
        address _to,
        uint256 _value
    ) public payable returns (bool success) {
        // require that the value is greater or equal for transfer
        require(balanceOf[msg.sender] >= _value);
        // transfer the amount and subtract the balance
        balanceOf[msg.sender] -= _value;
        // add the balance
        balanceOf[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
        return true;
    }

    //수동채굴 ( owner가 발급하도록)

    //게임비 유저로부터 지하철 역에 전달
    function sendGameCost(
        address _toStation,
        uint256 _value
    ) public payable returns (bool success) {
        //user(msg.sender)의 잔액이 충분한지 확인
        require(balanceOf[msg.sender] >= _value);
        //user의 잔액을 빼고
        balanceOf[msg.sender] -= _value;
        //station의 잔액에 더함.
        balanceOf[_toStation] += _value;
        emit Transfer(msg.sender, _toStation, _value);
        return true;
    }

    //추가로 있으면 좋을 것 같은 기능
    //- token추가로 생성(owner만 가능하게)
}
