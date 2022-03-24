package com.mk.api.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{

    private final InterestRepository interestRepository;
    private final JwtTokenService jwtTokenService;
    private final ItemService itemService;
    private final ItemImageRepository itemImageRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Interest registerInterest(String accessToken, String itemId) {

        User user = jwtTokenService.convertTokenToUser(accessToken);
        Item item = itemService.getItemEntity(itemId);

        //사용자가 없거나 삭제한 멤버일 경우
        if(user == null || user.isDelYn()==true){
            log.info("사용자가 없거나 탈퇴");
            return null;
        }
        //관심 상품이 없을 때
        if(item == null){
            log.info("상품이 없음");
            return null;
        }
        if(interestRepository.findByUserAndDelYnAndItem(user, false, item)!=null){
            log.info("이미 같은 상품 관심 등록");
            return null;
        }

        Interest interest = Interest.builder().item(item).user(user).build();
        return interestRepository.save(interest);

    }


    @Override
    public InterestGetListResponseDto getItemList(String accessToken) {
        List<ItemGetResponseDto> itemList = new ArrayList<>();
        Map<String, String> itemImageList = new HashMap<String, String>();

        User user = jwtTokenService.convertTokenToUser(accessToken);

        //Interest를 조회, User와 false로 찾기
        interestRepository.findByUserAndDelYn(user,false).forEach(interest ->{

            //아이템 사진 경로 위치
            itemImageRepository.findByItem(interest.getItem()).forEach(file -> {
                itemImageList.put(file.getNewFileName(), file.getOriginFileName());
            });

            //interest.getItem(). -> 관심상품 아이템 객체
            ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder().itemId(interest.getItem().getId()).division(interest.getItem().getDivision())
                    .itemName(interest.getItem().getItemName()).category(interest.getItem().getCategory()).price(interest.getItem().getPrice())
                    .description(interest.getItem().getDescription()).regDate(interest.getItem().getRegDate()).position(interest.getItem().getPosition())
                    .files(itemImageList).build();
            //상품이 판매인지 대여인지 판별.
            //대여이면 -> 종료 시작일. Code.A01 대여
            if (interest.getItem().getDivision() == Code.A01)
                interest.getItem().setRentDate(interest.getItem().getRentStartDate(), interest.getItem().getRentEndDate());

            //관심상품 정보 List에 추가
            itemList.add(itemGetResponseDto);

            //관심상품 사진 초기화
            itemImageList.clear();
        });

        InterestGetListResponseDto interestGetListResponseDto = InterestGetListResponseDto.builder()
                .InterestListResponseDto(itemList)
                .build();

        return interestGetListResponseDto;
    }

    @Override
    public InterestGetListResponseDto getItemLists(String accessToken, Pageable pageable) {
        List<ItemGetResponseDto> itemList = new ArrayList<>();
        Map<String, String> itemImageList = new HashMap<String, String>();

        User user = jwtTokenService.convertTokenToUser(accessToken);

        //Interest를 조회, User와 false로 찾기
        Page<Interest> interests = interestRepository.findByUserAndDelYn(user,false,pageable);
        interests.forEach(interest ->{

            //아이템 사진 경로 위치
            itemImageRepository.findByItem(interest.getItem()).forEach(file -> {
                itemImageList.put(file.getNewFileName(), file.getOriginFileName());
            });

            //interest.getItem(). -> 관심상품 아이템 객체
            ItemGetResponseDto itemGetResponseDto = ItemGetResponseDto.builder().itemId(interest.getItem().getId()).division(interest.getItem().getDivision())
                    .itemName(interest.getItem().getItemName()).category(interest.getItem().getCategory()).price(interest.getItem().getPrice())
                    .description(interest.getItem().getDescription()).regDate(interest.getItem().getRegDate()).position(interest.getItem().getPosition())
                    .files(itemImageList).build();
            //상품이 판매인지 대여인지 판별.
            //대여이면 -> 종료 시작일. Code.A01 대여
            if (interest.getItem().getDivision() == Code.A01)
                interest.getItem().setRentDate(interest.getItem().getRentStartDate(), interest.getItem().getRentEndDate());

            //관심상품 정보 List에 추가
            itemList.add(itemGetResponseDto);

            //관심상품 사진 초기화
            itemImageList.clear();
        });

        InterestGetListResponseDto interestGetListResponseDto = InterestGetListResponseDto.builder()
                .InterestListResponseDto(itemList)
                .totalPage(interests.getTotalPages())
                .build();

        return interestGetListResponseDto;
    }

    @Override
    @Transactional
    public Interest deleteInterest(String itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item==null)
            return null;
        Interest interest = interestRepository.findByItemAndDelYn(item,false);

        if(interest == null)
            return null;

        //delyn = true;로 변경
        interest.deleteInterest();

        return interestRepository.save(interest);
    }
}
