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

    //추가로 있으면 좋을 것 같은 기능
    //- token추가로 생성(owner만 가능하게)
}
