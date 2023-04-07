const station = artifacts.require("Station");
const won = artifacts.require("Won");

module.exports = function (deployer) {
  // deployer.deploy(won);
  deployer.deploy(station, "0x2A76648e069B04d321078f05dfE1d8105d930F4b", 1, "강남역");
  // deployer.deploy("0x3C1e9714e9e3a919a541D92410D86F43a6646A8a",station, 2, "역삼역");
};
