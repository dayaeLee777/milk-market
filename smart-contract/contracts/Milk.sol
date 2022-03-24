// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.0;

contract Milk {
    // 구매자 정보
    struct Buyer {
        address buyerAddress;
        bytes32 user_name;
        bytes32 user_nickname;
        bytes32 user_email;
        bytes32 user_address;
        bytes32 user_milk;
    }

    // 판매자 정보
    struct Seller {
        address sellerAddress;
        bytes32 user_name;
        bytes32 user_nickname;
        bytes32 user_email;
        bytes32 user_address;
    }

    // 구매 확정
    struct Payment {
        address buyerAddress;
        uint date;
    }

    // 구매 취소
    struct Cancel {
        address buyerAddress;
        uint date;
    }

    // 결제 
    struct doPay {
        address managerAddress;
        uint date;
    }

    struct doCancel {
        address managerAddress;
        uint date;
    }

    



}