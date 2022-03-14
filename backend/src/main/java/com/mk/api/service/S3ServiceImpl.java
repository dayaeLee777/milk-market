package com.mk.api.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;

@Service("S3Service")
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

	@Value("${cloud.aws.s3.bucket}")
    private String bucket;
	
    private final AmazonS3Client amazonS3Client;
	
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

}
