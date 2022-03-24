package com.mk.api.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mk.db.entity.Item;
import com.mk.db.entity.ItemImage;
import com.mk.db.repository.ItemImageRepository;

import lombok.RequiredArgsConstructor;

@Service("ItemImageService")
@RequiredArgsConstructor
public class ItemImageServiceImpl implements ItemImageService {

	private final ItemImageRepository itemImageRepository;
	
	private final S3Service S3Service;
	
	@Transactional
	@Override
	public void uploadItemImages(Item item, List<MultipartFile> multipartFile) {
		
		Map<String, String> fileNameList = S3Service.uploadFile(multipartFile);
		
		fileNameList.forEach((o, n) -> {
			ItemImage itemImage = ItemImage.builder()
					.originFileName(o)
					.newFileName(n)
					.item(item)
					.build();
			
			itemImageRepository.save(itemImage);
		});
	}

	@Transactional
	@Override
	public void deleteItemImages(Item item) {
		
		itemImageRepository.findByItem(item).forEach(itemImage -> {
			
			S3Service.deleteFile(itemImage.getNewFileName());
			itemImageRepository.delete(itemImage);
			
		});
	}

	@Override
	public String getImagePath(String filename) {
		return S3Service.getFilePath(filename);
	}

}
