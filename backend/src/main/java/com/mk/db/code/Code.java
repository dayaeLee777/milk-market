package com.mk.db.code;

import java.util.Random;

public enum Code {
	
	A01("대여",true),
	A02("판매",true),
	B01("신생아",true),
	B02("유모차",true),
	B03("카시트",true),
	B04("발육",true),
	B05("수유",true),
	B06("이유식",true),
	B07("소독/살균",true),
	B08("스킨/바디",true),
	B09("유아가구",true),
	B10("목욕",true),
	B11("구강청결",true),
	B12("세제",true),
	B13("안전",true),
	B14("위생/건강",true),
	B15("임산부",true),
	B16("유아침구",true),
	C01("판매 중", true),
	C02("입금 완료", true),
	C03("구매 확정", true),
	C04("거래 취소", true);
	
	private String name;
	
	private boolean useYn;
	
	private Code(String name, boolean useYn) {
		this.name = name;
		this.useYn = useYn;
	}

	public String getName() {
		return name;
	}

	public boolean isUseYn() {
		return useYn;
	}

	public static Code getRandomCode(){
		Random random = new Random();
		return values()[random.nextInt(values().length-2)+2];
	}
}
