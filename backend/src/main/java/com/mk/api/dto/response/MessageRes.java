package com.mk.api.dto.response;

import lombok.Data;

@Data
public class MessageRes {
	private String message;
	private Object data;

	public MessageRes() {
		this.message = null;
		this.data = null;
	}
}
