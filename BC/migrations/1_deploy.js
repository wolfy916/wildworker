const station = artifacts.require("Station");
const won = artifacts.require("Won");

module.exports = function (deployer) {
  deployer.deploy(won);
  deployer.deploy(station, "0xDD5958A18606c91d012E02fa9b8B5897C7A26C54", 1, "강남역");
  // deployer.deploy("0x3C1e9714e9e3a919a541D92410D86F43a6646A8a",station, 2, "역삼역");
};
