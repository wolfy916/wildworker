// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./Won.sol";

contract Station {
    uint16 public id;
    string public name;
    Won public won;
    uint256 public investmentAmount;
    address owner;

    address[] investorWallets;
    mapping(address => uint) public investors;

    constructor(Won _won, uint16 _id, string memory _name) {
        won = _won;
        id = _id;
        name = _name;
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "onlyOwner can call this function");
        _;
    }

    function payReward(
        address payable _toUser,
        uint256 _amount
    ) public onlyOwner {
        bool success = won.transfer(msg.sender, _toUser, _amount);
        require(success, "Won transfer failed");
    }

    function autoMine(address _toUser, uint256 _amount) public onlyOwner {
        bool success = won.transfer(msg.sender, _toUser, _amount);
        require(success, "Won transfer failed");
    }

    function resetInvestmentAmount() public onlyOwner {
        investmentAmount = 0;
        uint investorWalletsLength = investorWallets.length;
        for (uint i = 0; i < investorWalletsLength; i++) {
            delete investors[investorWallets[i]];
            investorWallets.pop();
        }
    }

    function countChargeEvery10Min(uint _commission) public onlyOwner {
        for (uint i = 0; i < investorWallets.length; i++) {
            uint userShare = (investors[investorWallets[i]] /
                investmentAmount) * 1000;
            uint chargeByShare = (userShare * _commission) / 1000;
            bool success = won.transfer(msg.sender, investorWallets[i], chargeByShare);        
            require(success, "Won transfer failed");
        }
    }

    function recordInvestment(address _sender, uint256 _amount) public {
        bool isIn = false;
        for (uint256 i = 0; i < investorWallets.length; i++) {
            if (investorWallets[i] == _sender) {
                isIn = true;
                break;
            }
        }
        if (isIn) {
            investors[_sender] += _amount;
        } else {
            investorWallets.push(_sender);
            investors[_sender] = _amount;
        }
        investmentAmount += _amount;
        bool success = won.transfer(_sender, owner, _amount);
        require(success, "Won transfer failed");
    }
}
