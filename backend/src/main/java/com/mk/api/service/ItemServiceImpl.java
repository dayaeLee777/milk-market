package com.mk.api.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.db.code.Code;
import com.mk.db.entity.Item;
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

	private final ItemImageService itemImageService;

	@Transactional
	@Override
	public Item registerItem(String accessToken, List<MultipartFile> multipartFile,
			ItemRegisterRequestDto itemRegisterRequestDto) {

		User user = jwtTokenService.convertTokenToUser(accessToken);
		LocalDateTime currentDateTime = LocalDateTime.now();

		Item item = Item.builder().division(itemRegisterRequestDto.getDivision())
				.itemName(itemRegisterRequestDto.getItemName()).category(itemRegisterRequestDto.getCategory())
				.price(itemRegisterRequestDto.getPrice()).description(itemRegisterRequestDto.getDescription())
				.regDate(currentDateTime)
//				.position(user.getPosition())
				.user(user).build();

		if (itemRegisterRequestDto.getDivision() == Code.A01)
			item.setRentDate(itemRegisterRequestDto.getRentStartDate(), itemRegisterRequestDto.getRentEndDate());

		itemImageService.uploadItemImages(item, multipartFile);

		return itemRepository.save(item);
	}

	@Override
	public Item getItemEntity(String itemId) {
		Item item = itemRepository.findById(itemId).orElse(null);
		if(item ==null)
			return null;
		return item;
	}

	@Override
	public ItemGetResponseDto getItem(String itemId) {

		Item item = itemRepository.findById(itemId).orElse(null);

		if (item == null)
			return null;

		Map<String, String> itemImageList = new HashMap<String, String>();

		itemImageRepository.findByItem(item).forEach(file -> {
			itemImageList.put(file.getNewFileName(), file.getOriginFileName());
		});

		ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder().itemId(itemId).division(item.getDivision())
				.itemName(item.getItemName()).category(item.getCategory()).price(item.getPrice())
				.description(item.getDescription()).regDate(item.getRegDate()).position(item.getPosition())
				.files(itemImageList).build();

		if (item.getDivision() == Code.A01)
			item.setRentDate(item.getRentStartDate(), item.getRentEndDate());

		return itemGetResponseDto;
	}


	@Transactional
	@Override
	public Item modifyItem(ItemModifyRequestDto itemModifyRequestDto, List<MultipartFile> multipartFile) {
		Item item = itemRepository.findById(itemModifyRequestDto.getItemId()).orElse(null);

		if (item == null)
			return null;

		item.modifyItem(itemModifyRequestDto);

		itemImageService.deleteItemImages(item);
		itemImageService.uploadItemImages(item, multipartFile);

		return itemRepository.save(item);
	}

	@Transactional
	@Override
	public Item deleteItem(String itemId) {
		Item item = itemRepository.findById(itemId).orElse(null);

		if (item == null)
			return null;
		
		itemImageService.deleteItemImages(item);
		item.deleteItem();
		
		
		return itemRepository.save(item);
	}

}
