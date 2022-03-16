package com.mk.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.db.code.Code;
import com.mk.db.entity.Item;
import com.mk.db.entity.ItemImage;
import com.mk.db.entity.User;
import com.mk.db.repository.ItemImageRepository;
import com.mk.db.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service("ItemService")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemRepository itemRepository;
	
	private final ItemImageRepository itemImageRepository;
	
	private final JwtTokenService jwtTokenService;

	private final S3Service S3Service;
	
	@Transactional
	@Override
	public Item registerItem(String accessToken, List<MultipartFile> multipartFile, ItemRegisterRequestDto itemRegisterRequestDto) {
		
		User user = jwtTokenService.convertTokenToUser(accessToken);
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		Item item = Item.builder()
				.division(itemRegisterRequestDto.getDivision())
				.itemName(itemRegisterRequestDto.getItemName())
				.category(itemRegisterRequestDto.getCategory())
				.price(itemRegisterRequestDto.getPrice())
				.description(itemRegisterRequestDto.getDescription())
				.regDate(currentDateTime)
//				.position(user.getPosition())
				.user(user)
				.build();
		
		if(itemRegisterRequestDto.getDivision() == Code.A01)
			item.setrentDate(itemRegisterRequestDto.getRentStartDate(), itemRegisterRequestDto.getRentEndDate());
		
		if(multipartFile != null) {
			Map<String, String> fileNameList = S3Service.uploadFile(multipartFile);
			
			fileNameList.forEach((o, n) -> {
				ItemImage itemImage = ItemImage.builder()
						.originFileName(o)
						.newFileName(n)
						.item(item)
						.build();
			});
		}
		
		
		return itemRepository.save(item);
	}

}
