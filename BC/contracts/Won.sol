// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Won {
    string public name = "Won Token";
    string public symbol = "Won";
    uint256 public totalSupply = 10000000000;
    uint8 public decimals = 0;
    address owner;

    event Transfer(address indexed _from, address indexed _to, uint256 _value);
    event TransferFailed(address indexed _from, address indexed _to, uint256 _value, string reason);

    mapping(address => uint256) public balanceOf;

    modifier onlyOwner() {
        require(msg.sender == owner, "onlyOwner can call this function");
        _;
    }

    constructor() {
        balanceOf[msg.sender] = totalSupply;
        owner = msg.sender;
    }

    function transfer(
        address _from,
        address _to,
        uint256 _amount
    ) public returns (bool success) {
        if (balanceOf[_from] >= _amount) {
            balanceOf[_from] -= _amount;
            balanceOf[_to] += _amount;
            emit Transfer(_from, _to, _amount);
            return true;
        } else {
            emit TransferFailed(_from, _to, _amount, "Not enough balance");
            return false;
        }
    }

    function manualMine(address _toUser, uint _amount) public onlyOwner {
        transfer(msg.sender, _toUser, _amount);
    }

    function sendGameCost(address _toStation, uint256 _amount) public {
        transfer(msg.sender, _toStation, _amount);
    }
}
