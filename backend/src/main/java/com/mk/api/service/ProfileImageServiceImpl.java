package com.mk.api.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mk.db.entity.ProfileImage;
import com.mk.db.entity.User;
import com.mk.db.repository.ProfileImageRepository;
import com.mk.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service("profileImageService")
@RequiredArgsConstructor
public class ProfileImageServiceImpl implements ProfileImageService {

	private final JwtTokenService jwtTokenService;

	private final UserRepository userRepository;
	
	private final ProfileImageRepository profileImageRepository;
	
	private final S3Service S3Service;
	
	@Transactional
	@Override
	public void uploadProfileImg(String accessToken, List<MultipartFile> multipartFile) {
		
		User user = jwtTokenService.convertTokenToUser(accessToken);
		
		ProfileImage profileimage = profileImageRepository.findByUser(user).orElse(null);
		
		if(multipartFile == null) {
			return;
		}
		
		if(profileimage == null) {
			Map<String, String> fileNameList = S3Service.uploadFile(multipartFile);
			fileNameList.forEach((o, n) -> {
				
				user.setProfileImage(S3Service.getFilePath(n));
				userRepository.save(user);
				
				ProfileImage profileImage = ProfileImage.builder()
				.originFileName(o)
				.newFileName(n)
				.user(user)
				.build();
			
				profileImageRepository.save(profileImage);
			});
		} else {
			S3Service.deleteFile(profileimage.getNewFileName());
			
			Map<String, String> fileNameList = S3Service.uploadFile(multipartFile);
			fileNameList.forEach((o, n) -> {
				
				user.setProfileImage(S3Service.getFilePath(n));
				userRepository.save(user);
				
				profileimage.modifyProfileImage(o, n);
			
				profileImageRepository.save(profileimage);
			});
		}
		
		return;
	}

}
