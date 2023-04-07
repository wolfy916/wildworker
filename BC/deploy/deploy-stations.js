require('dotenv').config();

const fs = require("fs");
const Web3 = require("web3");
const solc = require("solc");
const mysql = require("mysql2");

const privateKey=process.env.PRIVATE_KEY;
const rpcURL = `http://${process.env.BLOCKCHAIN_HOST}:${process.env.BLOCKCHAIN_PORT}`;
const web3 = new Web3(rpcURL);

const account = web3.eth.accounts.privateKeyToAccount(privateKey);
web3.eth.accounts.wallet.add(account);

const input = {
  language: "Solidity",
  sources: {
    "Won.sol": {
      content: fs.readFileSync("./contracts/Won.sol", "utf8"),
    },
    "Station.sol": {
      content: fs.readFileSync("./contracts/Station.sol", "utf8"),
    },
  },
  settings: {
    optimizer: {
        enabled: true,
        runs: 1000,
      },
    outputSelection: {
      "*": {
        "*": ["*"],
      },
    },
  },
};

const output = JSON.parse(solc.compile(JSON.stringify(input)));

const wonBytecode =
output.contracts["Won.sol"].Won.evm.bytecode.object;
const wonAbi = output.contracts["Won.sol"].Won.abi;
console.log("won size : " +  Buffer.byteLength(wonBytecode, "utf8"));

const stationBytecode =
output.contracts["Station.sol"].Station.evm.bytecode.object;
const stationAbi = output.contracts["Station.sol"].Station.abi;

const WonContract = new web3.eth.Contract(wonAbi);

(async () => {
  const gasPrice = await web3.eth.getGasPrice();
    console.log("gas price : {}", gasPrice);

  const gasEstimate = await WonContract.deploy({ data: wonBytecode }).estimateGas();

  const wonInstance = await WonContract.deploy({ data: wonBytecode })
    .send({ from: account.address, gas: gasEstimate + Math.floor(gasEstimate * 0.3), gasPrice: gasPrice});

  console.log("Won 컨트랙트 주소:", wonInstance.options.address);
    const wonAddress =  wonInstance.options.address;
  const stationContract = new web3.eth.Contract(stationAbi);

  const connection = await mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE
  });

//   connection.connect(); // MySQL 서버에 연결

  const tableName = "station";

  for (let i = 1; i <= 51; i++) {
    // const query = `SELECT ${fieldName} FROM ${tableName} WHERE id = ${i};`;
    // const stationName = await connection.execute(query);
    const stationName = `Station ${i}`;
    console.log("stationName = " + JSON.stringify(stationName));

    const stationGasEstimate = await stationContract.deploy({ data: stationBytecode, arguments: [wonAddress, i, stationName] })
        .estimateGas();
    console.log("station estimate gas : " + stationGasEstimate);

    const stationInstance = await stationContract.deploy({ data: stationBytecode, arguments: [wonAddress, i, stationName] })
        .send({ from: account.address, gas: stationGasEstimate + Math.floor(stationGasEstimate * 0.3), gasPrice });

    console.log(`스테이션 컨트랙트 ${i} 주소:`, stationInstance.options.address);

    const updateQuery = `
    UPDATE ${tableName}
    SET address = "${stationInstance.options.address}"
    WHERE id=${i};`
  connection.query(updateQuery, (err, res) => {
    if (err) throw err;
    console.log(`스테이션 컨트랙트 ${i} 주소가 MySQL에 저장되었습니다.`);
  });
  }

  connection.end(); // MySQL 서버 연결 종료
})();
