// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract WonToken {
    string public name = "Won Token";
    string public symbol = "Won";
    // uint256 public totalSupply = 10000000000000000000000000000; // 1 million tokens 100억개
    uint256 public totalSupply = 100000000000; // 1 million tokens 100억개
    uint8 public decimals = 0;
    address owner;

    event Transfer(address indexed _from, address indexed _to, uint256 _value);

    mapping(address => uint256) public balanceOf;

    //밑줄 문자를 입력하면 함수가 계속되게 됨 위 제어자를 실행한 다음 함수로 이어지라는 것. 
    //조건에 부합하지 않는다면 함수를 계속하지 않아서 접근하는 것을 막음.
    modifier onlyOwner {
        require(msg.sender == owner, "onlyOwner can call this function");
        _;
    }

    constructor() {
        balanceOf[msg.sender] = totalSupply;
        owner = msg.sender;
    }

    function transfer(address _to, uint256 _value)
        public payable
        returns (bool success)
    {
        // require that the value is greater or equal for transfer
        require(balanceOf[msg.sender] >= _value);
        // transfer the amount and subtract the balance
        balanceOf[msg.sender] -= _value;
        // add the balance
        balanceOf[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
        return true;
    }

    //수동채굴 (owner가 발급하도록)
    function manualMine(address _toUser, uint _value) public payable onlyOwner{
        transfer(_toUser, _value);
    }


    //게임비 유저로부터 지하철 역에 전달
        function sendGameCost(address _toStation, uint256 _value)
        public payable{
            transfer(_toStation, _value);
    }


}
