const station = artifacts.require("Station");

module.exports = function (deployer) {
    deployer.deploy(station,1,"강남역");
    deployer.deploy(station,2,"역삼역");
}