package com.mk.api.service;

import org.springframework.stereotype.Service;

import com.mk.config.JwtTokenProvider;
import com.mk.db.entity.User;
import com.mk.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service("jwtTokenService")
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

	private final JwtTokenProvider jwtTokenProvider;
	
	private final UserRepository userRepository;
	
	@Override
	public User convertTokenToUser(String accessToken) {
		
		String token = accessToken.split(" ")[1];
		String loginId = jwtTokenProvider.getUserPk(token);
		User user = userRepository.findById(loginId).get();
		
		return user;
	}

}
