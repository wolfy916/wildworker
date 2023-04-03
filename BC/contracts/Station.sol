// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./Won.sol";

contract Station {
    uint16 public id;
    string public name;
    Won public won;
    //총투자금(일주일)
    uint256 public investmentAmount;
    address owner;

    address[] investorWallets;
    mapping(address => uint) public investors;

    //Won의 컨트랙트 주소를 기입
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

    // 게임에 대한 승리 수당 지급
    function payReward(
        address payable _toUser,
        uint256 _amount
    ) public onlyOwner {
        won.transfer(msg.sender, _toUser, _amount);
    }

    // 자동채굴(지하철 진입 시 돈 지급)
    //msg.sender가 station
    function autoMine(address _toUser, uint256 _amount) public onlyOwner {
        won.transfer(msg.sender, _toUser, _amount);
    }

    // 일주일마다 초기화
    function resetInvestmentAmount() public onlyOwner {
        investmentAmount = 0;
        uint investorWalletsLength = investorWallets.length;
        for (uint i = 0; i < investorWalletsLength; i++) {
            delete investors[investorWallets[i]];
            investorWallets.pop();
        }
    }

    // 수수료 정산(10분당)
    //msg.sender = owner
    function countChargeEvery10Min(uint _commission) public onlyOwner {
        for (uint i = 0; i < investorWallets.length; i++) {
            //investorWallets[i] = user의 지갑주소
            //user의 투자금
            uint userShare = (investors[investorWallets[i]] /
                investmentAmount) * 1000;
            uint chargeByShare = (userShare * _commission) / 1000;
            //
            won.transfer(msg.sender, investorWallets[i], chargeByShare);
        }
    }

    // 투자내역기록
    //mapping은 iterable하지 못해서 investorWallets를 array로 생성해서 user의 목록으로
    //------msg.sender가 owner
    //------sender 가 user
    function recordInvestment(address _sender, uint256 _amount) public {
        bool isIn = false;
        for (uint256 i = 0; i < investorWallets.length; i++) {
            if (investorWallets[i] == _sender) {
                //array 안에 이미 user가 있을 경우 true
                isIn = true;
                break;
            }
        }
        if (isIn) {
            investors[_sender] += _amount;
            //총 투자금
        } else {
            //array안에 user가 없으면 추가
            investorWallets.push(_sender);
            //user가 투자한 금액
            investors[_sender] = _amount;
        }
        //총 투자금에 기록(일주일 유지)
        investmentAmount += _amount;
        //Won 송금
        won.transfer(_sender, owner, _amount);
    }
}
