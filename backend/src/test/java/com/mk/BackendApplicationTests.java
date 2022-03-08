package com.mk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.mk.db.repository.CommunityRepository;

@Transactional
@SpringBootTest
class BackendApplicationTests {

	@Autowired
	CommunityRepository communityRepository;
	
	@Test
	@Rollback(value = false)
	void contextLoads() {
		
	}

}
