package com.mk.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Service("s3Service")
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3Client amazonS3Client;

	@Override
	public Map<String, String> uploadFile(List<MultipartFile> multipartFile) {
		Map<String, String> fileNameList = new HashMap<String, String>();

		multipartFile.forEach(file -> {
			String fileName = createFileName(file.getOriginalFilename());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			try (InputStream inputStream = file.getInputStream()) {
				amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
			}

			fileNameList.put(file.getOriginalFilename(), fileName);
		});
		return fileNameList;
	}

	@Override
	public String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	@Override
	public String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
		}
	}

	@Override
	public String getFilePath(String newFileName) {
		return amazonS3Client.getResourceUrl(bucket, newFileName);
	}

	@Override
	public void deleteFile(String newFileName) {
		try {
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, newFileName);
			amazonS3Client.deleteObject(deleteObjectRequest);
		} catch (AmazonServiceException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패했습니다.");
		}
	}

}
