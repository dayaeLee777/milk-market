// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.12;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract MilkToken is ERC20{
  uint public INITIAL_SUPPLY = 12000*10**18;

  constructor() public ERC20("Milk Token","MILK"){
    _mint(msg.sender, INITIAL_SUPPLY);
  }
}
