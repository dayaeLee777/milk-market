package com.mk.api.service;

import com.mk.db.entity.User;

public interface JwtTokenService {
	
	User convertTokenToUser(String accessToken);
	
}
