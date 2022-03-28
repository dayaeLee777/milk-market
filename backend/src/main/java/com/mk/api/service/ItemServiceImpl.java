package com.mk.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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

		Item item = Item.builder()
				.division(itemRegisterRequestDto.getDivision())
				.itemName(itemRegisterRequestDto.getItemName())
				.category(itemRegisterRequestDto.getCategory())
				.price(itemRegisterRequestDto.getPrice())
				.description(itemRegisterRequestDto.getDescription())
				.regDate(currentDateTime)
				.user(user)
				.build();

		if (itemRegisterRequestDto.getDivision() == Code.A01)
			item.setRentDate(itemRegisterRequestDto.getRentStartDate(), itemRegisterRequestDto.getRentEndDate());

		if(multipartFile != null) 
			itemImageService.uploadItemImages(item, multipartFile);

		return itemRepository.save(item);
	}
	@Override
	public List<ItemGetResponseDto> getItemList() {
		//아이템을 찾고,
		//그 아이템으로 이미지 파일 이름, 경로를 찾고
		//
		List<Item> items = itemRepository.findAll();
		Map<String, String> itemImageList = new HashMap<String, String>();
		List<ItemGetResponseDto> itemLists = new ArrayList<>();
		for(Item item : items){

			itemImageRepository.findByItem(item).forEach(file -> {
				String originFilename = file.getOriginFileName();
				String newFilename = file.getNewFileName();
				log.info("파일 이름 : "+ originFilename+" "+ newFilename);
				itemImageList.put(originFilename, itemImageService.getImagePath(newFilename));
				log.info("files : "+ originFilename+" "+ itemImageList.get(originFilename));
			});

			DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
			DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd KK:mm");

			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
					.itemId(item.getId())
					.userId(item.getUser().getUsername())
					.userNickname(item.getUser().getNickname())
					.division(item.getDivision())
					.itemName(item.getItemName())
					.category(item.getCategory())
					.price(item.getPrice())
					.description(item.getDescription())
					.regDate(item.getRegDate().format(regDateFormatter))
					.bcode(item.getUser().getBcode())
					.bname(item.getUser().getBname())
					.files(itemImageList)
					.build();

			if (item.getDivision() == Code.A01)
				itemGetResponseDto.setRentDate(item.getRentStartDate().format(rentDateTimeFormatter),
						item.getRentEndDate().format(rentDateTimeFormatter));

			//테스트
			log.info("keys :"+ itemGetResponseDto.getFiles().keySet() +"\n values : "+itemGetResponseDto.getFiles().values());
			//아이템 리스트 추가
			itemLists.add(itemGetResponseDto);

			//아이템 이미지 초기화( 이름, 경로)
			itemImageList.clear();
		}

		return itemLists;
	}
	@Override
	public ItemGetResponseDto getItem(String itemId) {

		Item item = itemRepository.findById(itemId).orElse(null);

		if (item == null)
			return null;

		Map<String, String> itemImageList = new HashMap<String, String>();

		itemImageRepository.findByItem(item).forEach(file -> {
			String originFilename = file.getOriginFileName();
			String newFilename = file.getNewFileName();
			itemImageList.put(originFilename, itemImageService.getImagePath(newFilename));
		});

		DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd KK:mm");
		
		ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
				.itemId(itemId)
				.userId(item.getUser().getUsername())
				.userNickname(item.getUser().getNickname())
				.division(item.getDivision())
				.itemName(item.getItemName())
				.category(item.getCategory())
				.price(item.getPrice())
				.description(item.getDescription())
				.regDate(item.getRegDate().format(regDateFormatter))
				.bcode(item.getUser().getBcode())
				.bname(item.getUser().getBname())
				.files(itemImageList)
				.build();

		if (item.getDivision() == Code.A01)
			itemGetResponseDto.setRentDate(item.getRentStartDate().format(rentDateTimeFormatter),
					item.getRentEndDate().format(rentDateTimeFormatter));

		return itemGetResponseDto;
	}


	@Transactional
	@Override
	public Item modifyItem(ItemModifyRequestDto itemModifyRequestDto, List<MultipartFile> multipartFile) {
		Item item = itemRepository.findById(itemModifyRequestDto.getItemId()).orElse(null);

		if (item == null)
			return null;

		item.modifyItem(itemModifyRequestDto);

		if(multipartFile != null) {
			itemImageService.deleteItemImages(item);
			itemImageService.uploadItemImages(item, multipartFile);
		}

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
