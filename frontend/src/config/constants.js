/**
 * 아래의 상수들을 임의로 변경하여 구현할 수 있습니다. 
 */
export const CATEGORIES = {
    DIGITAL: "D",
    CHILD: "C",
    HOBBY: "H"
};

export const ITEM_STATUS = {
    ONSALE: {
        symbol: "C01",
        explanation: "판매중"
    },
    CONFIRMED: {
        symbol: "C02",
        explanation: "판매완료(구매확정)"
    },
    CANCELED: {
        symbol: "X",
        explanation: "취소됨"
    },
    DELETED: {
        symbol: "N",
        explanation: "삭제됨"
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