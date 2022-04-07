package com.mk.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.mk.db.entity.Purchase;
import com.mk.db.repository.PurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.ItemGetListResponseDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.db.code.Code;
import com.mk.db.entity.Item;
import com.mk.db.entity.User;
import com.mk.db.repository.ItemImageRepository;
import com.mk.db.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("ItemService")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemRepository itemRepository;

	private final ItemImageRepository itemImageRepository;

	private final PurchaseRepository purchaseRepository;

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
				.status(Code.C01)
				.build();

		if (itemRegisterRequestDto.getDivision() == Code.A01)
			item.setRentDate(itemRegisterRequestDto.getRentStartDate(), itemRegisterRequestDto.getRentEndDate());

		if(multipartFile != null) 
			itemImageService.uploadItemImages(item, multipartFile);

		return itemRepository.save(item);
	}
	@Override
	public List<ItemGetResponseDto> getItemList() {

		List<Item> items = itemRepository.findAll();

		List<ItemGetResponseDto> itemLists = new ArrayList<>();


		for(Item item : items){
			Map<String, String> itemImageList = new HashMap<String, String>();
			itemImageRepository.findByItem(item).forEach(file -> {

				String originFilename = file.getOriginFileName();
				String newFilename = file.getNewFileName();
				itemImageList.put(originFilename, itemImageService.getImagePath(newFilename));
			});

			DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
			DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
					.itemId(item.getId())
					.status(item.getStatus())
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

			//아이템 리스트 추가
			itemLists.add(itemGetResponseDto);


		}

		return itemLists;
	}

	@Override
	public List<ItemGetResponseDto> getMyItemList(String accessToken) {
		List<ItemGetResponseDto> itemGetResponseDtos = new ArrayList<>();

		User user = jwtTokenService.convertTokenToUser(accessToken);
		List<Item> itemList = itemRepository.findByUser(user);

		for (Item item: itemList) {
			if (item.isDelYn()) {
				continue;
			}
			Map<String, String> itemImageList = new HashMap<String, String>();
			itemImageRepository.findByItem(item).forEach( file -> {
				String originFileName = file.getOriginFileName();
				String newFileName = file.getNewFileName();

				itemImageList.put(originFileName, itemImageService.getImagePath(newFileName));
			});
			DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
					.itemId(item.getId())
					.status(item.getStatus())
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
			itemGetResponseDtos.add(itemGetResponseDto);

		}
		return itemGetResponseDtos;
	}

	@Override
	public List<ItemGetResponseDto> getItemList(int pageNumber, ItemGetListResponseDto dto) {
		Pageable pageable = PageRequest.of(pageNumber-1, 4, Sort.Direction.DESC, "id");
		Page<Item> items = itemRepository.findAll(pageable);
		log.info("totalpage : "+ items.getTotalPages());
		dto.setTotalPages(items.getTotalPages());
		List<ItemGetResponseDto> itemLists = new ArrayList<>();


		for(Item item : items){
			// 진열중인 애들만 표기
			if (item.getStatus() != Code.C01 ) {
				continue;
			}
			Map<String, String> itemImageList = new HashMap<String, String>();
			itemImageRepository.findByItem(item).forEach(file -> {

				String originFilename = file.getOriginFileName();
				String newFilename = file.getNewFileName();
				itemImageList.put(originFilename, itemImageService.getImagePath(newFilename));
			});

			DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
			DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
					.itemId(item.getId())
					.status(item.getStatus())
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

			//아이템 리스트 추가
			itemLists.add(itemGetResponseDto);


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
		DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
				.itemId(itemId)
				.status(item.getStatus())
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
				.profileImage(item.getUser().getProfileImage())
				.build();

		if (item.getDivision() == Code.A01)
			itemGetResponseDto.setRentDate(item.getRentStartDate().format(rentDateTimeFormatter),
					item.getRentEndDate().format(rentDateTimeFormatter));

		return itemGetResponseDto;
	}

	@Override
	public Boolean purchaseItem(String accessToken, String itemId) {
		User user = jwtTokenService.convertTokenToUser(accessToken);
		Item item = itemRepository.findById(itemId).orElse(null);
		if (item == null) {
			return false;
		}
		item.setStatus(Code.C02);
		itemRepository.save(item);

		Purchase purchase = Purchase.builder()
				.orderItemName(item.getItemName())
				.itemId(item.getId())
				.user(user)
				.build();
		purchaseRepository.save(purchase);
		return true;
	}

	@Override
	public Boolean purchaseConfirm(String accessToken, String itemId) {
		User user = jwtTokenService.convertTokenToUser(accessToken);
		Item item = itemRepository.findById(itemId).orElse(null);
		if (item == null) {
			return false;
		}
		item.setStatus(Code.C03);
		itemRepository.save(item);

		return true;
	}

	@Override
	public Boolean cancelPurchase(String accessToken, String itemId) {

		Item item = itemRepository.findById(itemId).get();
		User user = jwtTokenService.convertTokenToUser(accessToken);
		if (item == null) {
			return false;
		}
		item.setStatus(Code.C01);
		itemRepository.save(item);

		return true;
	}

	@Override
	public List<ItemGetResponseDto> purchaseList(String accessToken) {
		User user = jwtTokenService.convertTokenToUser(accessToken);
		List<Purchase> purchaseList = purchaseRepository.findByUser(user);
		List<ItemGetResponseDto> itemGetResponseDtos = new ArrayList<>();

		for (Purchase purchase: purchaseList) {

			Item item = itemRepository.findById(purchase.getItemId()).get();
			if (item.getStatus() == Code.C01 || item.getStatus() == Code.C04) {
				continue;
			}
			if (item.isDelYn()) {
				continue;
			}
			Map<String, String> itemImageList = new HashMap<String, String>();
			itemImageRepository.findByItem(item).forEach( file -> {
				String originFileName = file.getOriginFileName();
				String newFileName = file.getNewFileName();

				itemImageList.put(originFileName, itemImageService.getImagePath(newFileName));
			});

			DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder()
					.itemId(item.getId())
					.status(item.getStatus())
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
			itemGetResponseDtos.add(itemGetResponseDto);
		}

		return itemGetResponseDtos;
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
