const MilkToken = artifacts.require("MilkToken");

module.exports = function(deployer) {
  deployer.deploy(MilkToken);
};