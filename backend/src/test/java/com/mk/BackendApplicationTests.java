package com.mk;

import java.text.ParseException;
import java.time.LocalDateTime;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.mk.db.code.Code;
import com.mk.db.entity.Community;
import com.mk.db.entity.Item;
import com.mk.db.repository.CommunityRepository;
import com.mk.db.repository.ItemRepository;

@Transactional
@SpringBootTest
class BackendApplicationTests {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CommunityRepository communityRepository;
	
	@Test
	@Rollback(value = false)
	void contextLoads() throws ParseException{
		Item item = Item.builder()
				.category(Code.B01)
				.description("설명")
				.division(Code.A01)
				.itemName("유모차")
				.price(10000)
				.regDate(LocalDateTime.now())
				.rentEndDate(LocalDateTime.now())
				.build();
		
		Community c = Community.builder()
				.content("지금")
				.regTime(LocalDateTime.now())
				.build();
		
		communityRepository.save(c);
//		itemRepository.save(item);
		
	}

}
