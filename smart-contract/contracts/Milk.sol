// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.12;

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

    // event 는 emit으로 호출함
    event LogBuyProduct(address _buyer, uint _id);  // 상품 구입시 발생
    event LogDecidePayment(address _buyer, uint _id); // 구매 확정시 발생
    event LogDecideCancel(address _buyer, uint _id); // 구매 취소시 발생
    event LogPaymentProduct(address _manager, uint _id); // 금액 정산시 발생
    event LogCancelProduct(address _manager, uint _id); // 금액 환불시 발생
    
    constructor() {
        owner = msg.sender;
    }
//상품 구입시, 금액이 소유자 계정으로 이동
    //매개변수로 계정, 이름, 닉네임, 이메일, 집주소, 코인을 이용
    function buyProduct(uint _id, bytes32 _name, bytes32 _nickname, bytes32 _email, bytes32 _address, bytes32 _milk) public payable {
        require(_id >= 0 && _id <= 9, "the input shoud be included in the _id's ranges");
        buyers[_id] = msg.sender; //현재 상품을 구매하는 계정을 구매자배열에 저장하는데, 매물의 id를 인덱스값으로 저장
        buyerInfo[_id] = Buyer(msg.sender, _name, _nickname, _email, _address, _milk); //구매자의 정보를 저장

        payable(address(uint160(owner))).transfer(msg.value); // 코드 수정
        // address(uint160(owner)).transfer(msg.value); //함수에서 넘겨받은 값을 msg.value로 해서 이더를 전달
        emit LogBuyProduct(msg.sender, _id);
    }


    //구매확정
    function decidePayProduct(uint _id, uint _date) public {
        require(_id >= 0 && _id <= 9, "the input shoud be included in the _id's ranges");
        decidePayments[_id] = msg.sender;
        decidePaymentInfo[_id] = Payment(msg.sender, _date);
        //require(msg.sender == address(this), "to tranfer et   her from contract to address");
        emit LogDecidePayment(msg.sender, _id);
    }

    //금액정산
    function doPayProduct(uint _id, uint _date) public payable {
        require(_id >= 0 && _id <= 9, "the input shoud be included in the _id's ranges");
        doPayments[_id] = msg.sender;
        doPaymentInfo[_id] = doPay(msg.sender, _date);
        payable(address(uint160(sellers[_id]))).transfer(msg.value);
        emit LogPaymentProduct(msg.sender, _id);
    }

    //구매취소
    function decideCancelProduct(uint _id, uint _date) public {
        require(_id >= 0 && _id <= 9, "the input shoud be included in the _id's ranges");
        decideCancels[_id] = msg.sender;
        decideCancelInfo[_id] = Cancel(msg.sender, _date);
        emit LogDecideCancel(msg.sender, _id);
    }

    //금액환불
    function doCancelProduct(uint _id, uint _date) public payable {
        require(_id >= 0 && _id <= 9, "the input shoud be included in the _id's ranges");
        doCancels[_id] = msg.sender;
        doCancelInfo[_id] = doCancel(msg.sender, _date);
        payable(address(uint160(buyers[_id]))).transfer(msg.value);
        emit LogCancelProduct(msg.sender, _id);
    }

    //구매자 정보
    function getBuyerInfo(uint _id) public view returns (address, bytes32, bytes32, bytes32, bytes32, bytes32) {
        Buyer memory buyer = buyerInfo[_id];
        return (buyer.buyerAddress, buyer.user_name, buyer.user_nickname, buyer.user_email, buyer.user_address, buyer.user_milk);
    }

    //모든 구매자 정보
    function getAllBuyers() public view returns (address[10] memory) {
        return buyers;
    }

    //판매자 정보
    function getSellerInfo(uint _id) public view returns (address, bytes32, bytes32, bytes32, bytes32) {
        Seller memory seller = sellerInfo[_id];
        return (seller.sellerAddress, seller.user_name, seller.user_nickname, seller.user_email, seller.user_address);
    }

    //모든 판매자 정보
    function getAllSellers() public view returns (address[10] memory) {
        return sellers;
    }

    //구매확정 정보
    function getPaymentInfo(uint _id) public view returns (address, uint) {
        Payment memory payment = decidePaymentInfo[_id];
        return (payment.buyerAddress, payment.date);
    }

    //모든 구매확정 정보
    function getAllPayments() public view returns (address[10] memory) {
        return decidePayments;
    }

    //구매취소 정보
    function getCancelInfo(uint _id) public view returns (address, uint) {
        Cancel memory cancel = decideCancelInfo[_id];
        return (cancel.buyerAddress, cancel.date);
    }

    //모든 구매취소 정보
    function getAllCancels() public view returns (address[10] memory) {
        return decideCancels;
    }

    //정산정보
    function getdoPaymentInfo(uint _id) public view returns (address, uint) {
        doPay memory dopay = doPaymentInfo[_id];
        return (dopay.managerAddress, dopay.date);
    }

    //모든 정산 정보
    function getAlldoPayments() public view returns (address[10] memory) {
        return doPayments;
    }

    //환불정보
    function getdoCancelInfo(uint _id) public view returns (address, uint) {
        doCancel memory docancel = doCancelInfo[_id];
        return (docancel.managerAddress, docancel.date);
    }

    //모든 환불 정보
    function getAlldoCancels() public view returns (address[10] memory) {
        return doCancels;
    }

}