const Milk = artifacts.require("Milk");

module.exports = function(deployer) {
  deployer.deploy(Milk);
};