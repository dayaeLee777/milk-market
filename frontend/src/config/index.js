/**
 * TODO: 개발 및 배포 환경 에 맞추어 아래의 상수들을 지정합니다.
 */
// const API_BASE_URL = "http://j6a504.p.ssafy.io";
const API_BASE_URL = "http://localhost:8080";
const BLOCKCHAIN_URL = "http://localhost:8545";
const BLOCKCHAIN_WEBSOCKET_URL = "ws://127.0.0.1:8545";
const ITEM_INVENTORY_CONTRACT_ADDRESS =
    "0x290C3e8e97e3Dfa2DfAA2Fa46C2eCC29663f1b67";
// 토큰
const CASH_CONTRACT_ADDRESS = "0x0c958b82caFbc7B6644870A39379Ae20F05641dA";

// 에스크로
const PURCHASE_RECORD_CONTRACT_ADDRESS =
    "0x9B40DD2180F491Cd11741710be8522e3a5bb2351";

export {
    API_BASE_URL,
    BLOCKCHAIN_URL,
    BLOCKCHAIN_WEBSOCKET_URL,
    ITEM_INVENTORY_CONTRACT_ADDRESS,
    CASH_CONTRACT_ADDRESS,
    PURCHASE_RECORD_CONTRACT_ADDRESS,
};