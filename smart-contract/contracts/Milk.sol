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

    mapping (uint => Buyer) public buyerInfo;
    mapping (uint => Seller) public sellerInfo;
    mapping (uint => Payment) public decidePaymentInfo;
    mapping (uint => Cancel) public decideCancelInfo;
    mapping (uint => doPay) public doPaymentInfo;
    mapping (uint => doCancel) public doCancelInfo;

    address public owner; // 컨트랙트 소유자 계정
    address[10] public buyers; // 구매자 목록
    address[10] public sellers; // 판매자 목록
    address[10] public decidePayments; //구매 확적 목록
    address[10] public decideCancels; // 구매 취소 목록
    address[10] public doPayments; // 정산 목록
    address[10] public doCancels; // 환불 목록


}