package com.mk.api.service;

import com.mk.api.dto.request.LoginReq;
import com.mk.api.dto.request.UpdatePasswordReq;
import com.mk.api.dto.request.UserDTO;
import com.mk.config.JwtTokenProvider;
import com.mk.db.entity.User;
import com.mk.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Slf4j
@Service
public class UserService {
	
	private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private ModelMapper modelMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.modelMapper = new ModelMapper();
	}
	
	public boolean createUser(UserDTO userDto)  {
		//throws AlreadyExistEmailException, AlreadyExistNicknameException
		String email = userDto.getEmail();
		Optional<User> userByEmail = userRepository.findByEmail(email);
		String nickname = userDto.getNickname();
		Optional<User> userByNickname = userRepository.findByNickname(nickname);

		if (userByEmail.isPresent()) {
			log.info("user email already exists");
			//throw new AlreadyExistEmailException();
		}

		if (userByNickname.isPresent()) {
			log.info("user nickname already exists");
			//throw new AlreadyExistNicknameException();
		}

		User entity = modelMapper.map(userDto, User.class);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		userRepository.save(entity);
		return true;
	}


	//Login 데이터를 받고, JWT를 반환하는 메소드
	public String login(LoginReq data) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다.") );
		if(comparePassword(data.getPassword(), user.getPassword())) {
			return jwtTokenProvider.createToken(user,user.getRoles());
		}
		
		return "";
	}

	//토큰으로 사용할 시 암호 비교
	public String login2(LoginReq data) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다.") );
		if(data.getPassword().equals(user.getPassword())) {
			return jwtTokenProvider.createToken(user,user.getRoles());
		}

		return "";
	}


	public UserDTO getUserById(String id) {
		try {
			User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
			return modelMapper.map(user, UserDTO.class);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public UserDTO getUserByEmail(String email) throws Exception {
		Optional<User> source = userRepository.findByEmail(email);
		if(!source.isPresent()){
			return null;
		}
		log.info("gutUserByEmail" + source.toString());

		return modelMapper.map(source.get(), UserDTO.class);
	}

	@Transactional
	public boolean updatePassword(UpdatePasswordReq updatePasswordReq, String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()){
			user.get().setPassword(passwordEncoder.encode(updatePasswordReq.getNewPassword()));
			userRepository.save(user.get());
			return true;
		}
		return false;

	}


	//구 matchPassword
	//생 비밀번호와, 암호화된 비밀번호를 입력받고, 두 비밀번호의 동일 여부를 반환
	private boolean comparePassword(String rawPassword, String encryptPassword) {
        return passwordEncoder.matches(rawPassword,encryptPassword);
    }


	@Transactional
    public boolean delete(String id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent() && user.get().isWithdrawal() ==false){
			user.get().setWithdrawal(true);
			userRepository.save(user.get());
			return true;
		}else{
			return false;
		}
    }
}
