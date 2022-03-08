// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract MilkToken is ERC20{
  uint public INITIAL_SUPPLY = 120000*10**18;

  constructor() public ERC20("Milk Token","MILK"){
    _mint(msg.sender, INITIAL_SUPPLY);
  }
}
