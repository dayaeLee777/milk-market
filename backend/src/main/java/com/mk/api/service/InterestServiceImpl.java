package com.mk.api.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.api.dto.response.HotItemGetResponseDto;
import com.mk.api.dto.response.InterestGetListResponseDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.db.code.Code;
import com.mk.db.entity.Interest;
import com.mk.db.entity.Item;
import com.mk.db.entity.User;
import com.mk.db.repository.InterestRepository;
import com.mk.db.repository.ItemImageRepository;
import com.mk.db.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

	private final InterestRepository interestRepository;
	private final JwtTokenService jwtTokenService;
	private final ItemImageRepository itemImageRepository;
	private final ItemRepository itemRepository;
	private final ItemImageService itemImageService;

	@Override
	@Transactional
	public Interest registerInterest(String accessToken, String itemId) {

		User user = jwtTokenService.convertTokenToUser(accessToken);
		Item item = itemRepository.findById(itemId).orElse(null);
		Interest interestItem = interestRepository.findByItemAndDelYn(item, false);

		// 사용자가 없거나 삭제한 멤버일 경우
		if (user == null || user.isDelYn() == true) {
			log.info("사용자가 없거나 탈퇴");
			return null;
		}
		// 관심 상품이 없을 때
		if (item == null) {
			log.info("상품이 없음");
			return null;
		}
		if (interestRepository.findByUserAndDelYnAndItem(user, false, item) != null) {
			log.info("이미 같은 상품 관심 등록");
			// 이미 관심 상품이 있을 경우는 관심 여부를 변경
			return null;
		}

		Interest interest = Interest.builder().item(item).user(user).interestLike(true).build();
		return interestRepository.save(interest);

	}

	@Override
	public InterestGetListResponseDto getItemList(String accessToken) {
		List<ItemGetResponseDto> itemList = new ArrayList<>();
		Map<String, String> itemImageList = new HashMap<String, String>();

		User user = jwtTokenService.convertTokenToUser(accessToken);

		DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		// Interest를 조회, User와 false로 찾기
		interestRepository.findByUserAndDelYn(user, false).forEach(interest -> {

			// 아이템 사진 경로 위치
			itemImageRepository.findByItem(interest.getItem()).forEach(file -> {
				itemImageList.put(file.getNewFileName(), file.getOriginFileName());
			});

			// interest.getItem(). -> 관심상품 아이템 객체
			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder().itemId(interest.getItem().getId())
					.division(interest.getItem().getDivision()).itemName(interest.getItem().getItemName())
					.category(interest.getItem().getCategory()).price(interest.getItem().getPrice())
					.description(interest.getItem().getDescription())
					.regDate(interest.getItem().getRegDate().format(regDateFormatter))
					.bcode(interest.getItem().getUser().getBcode()).bname(interest.getItem().getUser().getBname())
					.files(itemImageList).build();
			// 상품이 판매인지 대여인지 판별.
			// 대여이면 -> 종료 시작일. Code.A01 대여
			if (interest.getItem().getDivision() == Code.A01)
				itemGetResponseDto.setRentDate(interest.getItem().getRentStartDate().format(rentDateTimeFormatter),
						interest.getItem().getRentEndDate().format(rentDateTimeFormatter));

			// 관심상품 정보 List에 추가
			itemList.add(itemGetResponseDto);

			// 관심상품 사진 초기화
			itemImageList.clear();
		});

		InterestGetListResponseDto interestGetListResponseDto = InterestGetListResponseDto.builder()
				.InterestListResponseDto(itemList).build();

		return interestGetListResponseDto;
	}

	@Override
	public InterestGetListResponseDto getItemLists(String accessToken, Pageable pageable) {
		List<ItemGetResponseDto> itemList = new ArrayList<>();
		Map<String, String> itemImageList = new HashMap<String, String>();

		User user = jwtTokenService.convertTokenToUser(accessToken);

		DateTimeFormatter regDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		// Interest를 조회, User와 false로 찾기
		Page<Interest> interests = interestRepository.findByUserAndDelYn(user, false, pageable);
		interests.forEach(interest -> {

			// 아이템 사진 경로 위치
			itemImageRepository.findByItem(interest.getItem()).forEach(file -> {
				itemImageList.put(file.getNewFileName(), file.getOriginFileName());
			});

			// interest.getItem(). -> 관심상품 아이템 객체
			ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder().itemId(interest.getItem().getId())
					.division(interest.getItem().getDivision()).itemName(interest.getItem().getItemName())
					.category(interest.getItem().getCategory()).price(interest.getItem().getPrice())
					.description(interest.getItem().getDescription())
					.regDate(interest.getItem().getRegDate().format(regDateFormatter))
					.bcode(interest.getItem().getUser().getBcode()).bname(interest.getItem().getUser().getBname())
					.files(itemImageList).build();
			// 상품이 판매인지 대여인지 판별.
			// 대여이면 -> 종료 시작일. Code.A01 대여
			if (interest.getItem().getDivision() == Code.A01)
				itemGetResponseDto.setRentDate(interest.getItem().getRentStartDate().format(rentDateTimeFormatter),
						interest.getItem().getRentEndDate().format(rentDateTimeFormatter));
			// 관심상품 정보 List에 추가
			itemList.add(itemGetResponseDto);

			// 관심상품 사진 초기화
			itemImageList.clear();
		});

		InterestGetListResponseDto interestGetListResponseDto = InterestGetListResponseDto.builder()
				.InterestListResponseDto(itemList).totalPage(interests.getTotalPages()).build();

		return interestGetListResponseDto;
	}

	@Override
	@Transactional
	public Interest deleteInterest(String itemId) {
		Item item = itemRepository.findById(itemId).orElse(null);
		if (item == null)
			return null;
		Interest interest = interestRepository.findByItemAndDelYn(item, false);

		if (interest == null)
			return null;

		// delyn = true;로 변경
		interest.deleteInterest();

		return interestRepository.save(interest);
	}

	@Override
	public List<HotItemGetResponseDto> getHotItem() {
		List<HotItemGetResponseDto> hotItemGetResponseDtoList = new ArrayList<HotItemGetResponseDto>();
		List<Interest> hotlist= interestRepository.findHotItem();

		hotlist.forEach(interest -> {
			Item item = interest.getItem();
			int count = (int) interestRepository.countByItem(item);

			List<String> itemImageList = new ArrayList();
			itemImageRepository.findByItem(item).forEach(file -> {

				String newFilename = file.getNewFileName();
				itemImageList.add(itemImageService.getImagePath(newFilename));
			});

			LocalDateTime regDateTime = item.getRegDate();
			LocalDateTime currentDateTime = LocalDateTime.now();

			Duration duration = Duration.between(regDateTime, currentDateTime);
			String regDateString = "";
			long minute = duration.getSeconds() / 60 ;
			if(minute < 60) {
				regDateString = minute + "분 전";
			}
			else if (minute < 1440) {
				minute /= 60;
				regDateString = minute + "시간 전";
			}
			else {
				LocalDate regDate = regDateTime.toLocalDate();
				LocalDate currentDate = currentDateTime.toLocalDate();
				Period period = Period.between(regDate, currentDate);
				if (period.getYears() > 0)
					regDateString = period.getYears() + "년 전";
				else if (period.getMonths() > 0)
					regDateString = period.getMonths() + "개월 전";
				else if (period.getDays() > 0)
					regDateString = period.getDays() + "일 전";
			}

			HotItemGetResponseDto hotItemGetResponseDto = HotItemGetResponseDto.builder().itemId(item.getId())
					.division(item.getDivision().getName()).itemName(item.getItemName())
					.status(item.getStatus().getName()).category(item.getCategory().getName()).price(item.getPrice())
					.description(item.getDescription()).count(count).regDate(regDateString)
					.bname(interest.getUser().getBname()).files(itemImageList).build();

			hotItemGetResponseDtoList.add(hotItemGetResponseDto);
		});
		return hotItemGetResponseDtoList;
	}

}
