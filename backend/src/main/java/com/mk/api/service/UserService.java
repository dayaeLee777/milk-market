package com.mk.api.service;

import com.mk.api.dto.request.LoginReq;
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


	public UserDTO getUserById(int id) {
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


	//구 matchPassword
	//생 비밀번호와, 암호화된 비밀번호를 입력받고, 두 비밀번호의 동일 여부를 반환
	private boolean comparePassword(String rawPassword, String encryptPassword) {
        return passwordEncoder.matches(rawPassword,encryptPassword);
    }
	
//	public Page<GetUserByProfileRes> getUsersByNickname(String nickname, Pageable pageable) {
//		Page<GetUserByProfileRes> list = Page.empty();
//		try {
//			Page<User> dataList = userRepository.findByNicknameContaining(nickname, pageable);
//			list = dataList.map(source -> modelMapper.map(source, GetUserByProfileRes.class));
//			return list;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//		return list;
//	}
//
//
//
//	public boolean updateUserByProfile(UpdateUserByProfileReq data, int id) {
//		try {
//			User entity = userRepository.getById(id);
//			userRepository.save(updateEntityByProfile(entity, data));
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return true;
//	}
//
//	public boolean updatePassword(UpdatePasswordReq data, int id) {
//		try {
//			User entity = userRepository.getById(id);
//			if(entity == null) return false;
//			entity = updateEntityWithPassword(entity, data);
//			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
//			userRepository.save(entity);
//			return true;
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public boolean updateUserByDetail(UpdateUserByDetailReq data, int id) {
//		try {
//			User entity = userRepository.getById(id);
//			if(entity == null) return false;
//			entity = updateEntityByAgeAndGender(entity, data);
//			userRepository.save(entity);
//			return true;
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public boolean deleteUser(DeleteUserReq data, int id) {
//
//		User entity = userRepository.getById(id);
//
//		if(!comparePassword(data.getPassword(), entity.getPassword())) return false;
//
//		userRepository.delete(entity);
//		return true;
//	}
//
//	private User updateEntityByProfile(User entity, UpdateUserByProfileReq data) {
//
//		entity.setNickname(data.getNickname());
//		entity.setProfileDescription(data.getProfileDescription());
//		entity.setProfileImage(data.getProfileImage());
//
//		return entity;
//	}
//
//	private User updateEntityWithPassword(User entity, UpdatePasswordReq data) {
//		entity.setPassword(data.getNewPassword());
//		return entity;
//	}
//
//	private User updateEntityByAgeAndGender(User entity, UpdateUserByDetailReq data) {
//		entity.setAge(data.getAge());
//		entity.setGender(data.getGender());
//		return entity;
//	}
//
//	private User updateUserEntity(User entity, User data) {
//
//		entity.setPassword(data.getPassword());
//		entity.setNickname(data.getNickname());
//		entity.setEmail(data.getEmail());
//		entity.setGender(data.getGender());
//		entity.setAge(data.getAge());
//		entity.setProfileImage(data.getProfileImage());
//		entity.setProfileDescription(data.getProfileDescription());
//		entity.setHostPoint(data.getHostPoint());
//		entity.setGuestPoint(data.getGuestPoint());
//        //entity.setRoles(data.getRoles());
//
//		return entity;
//	}
}
