/**
 * 아래의 상수들을 임의로 변경하여 구현할 수 있습니다. 
 */
export const CATEGORIES = {
    DIGITAL: "D",
    CHILD: "C",
    HOBBY: "H"
};

export const ITEM_STATUS = {
    "C01": {
        symbol: "C01",
        explanation: "판매중"
    },
    "C02": {
        symbol: "C02",
        explanation: "입금 완료"
    },
    "C03": {
        symbol: "C03",
        explanation: "구매 확정"
    },
    "C04": {
        symbol: "C04",
        explanation: "거래 취소"
    },
    symbolToStatus(symbol) {
        switch (symbol) {
            case "C01":
                return this.ONSALE;
            case "C02":
                return this.CONFIRMED;
            case "X":
                return this.CANCELED;
            case "N":
                return this.DELETED;
        }
    }
};

export const ESCROW_STATE = {
    PAID: {
        symbol: "P",
        explanation: "입금완료"
    },
    CONFIRMED: {
        symbol: "C",
        explanation: "구매확정"
    },
    CANCELED: {
        symbol: "X",
        explanation: "거래취소"
    },
    
    symbolToState(symbol) {
        switch (symbol) {
            case "P":
                return this.PAID;
            case "C":
                return this.CONFIRMED;
            case "X":
                return this.CANCELED;
        }
    }
};