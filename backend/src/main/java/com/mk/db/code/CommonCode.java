package com.mk.db.code;

public enum CommonCode {
	
	A("구분"),
	B("카테고리"),
	C("상태");
	
	private String name;

	private CommonCode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
