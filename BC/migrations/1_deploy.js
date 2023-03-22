const wonToken = artifacts.require("WonToken");
const station = artifacts.require("Station");
const won = artifacts.require("Won");

module.exports = function (deployer) {
  deployer.deploy(station, 1, "강남역");
  deployer.deploy(station, 2, "역삼역");
};
