package com.mk.db.repository;

import com.mk.db.entity.Interest;
import com.mk.db.entity.Item;
import com.mk.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, String> {


    List<Interest> findByUserAndDelYn(User user, boolean delYn);
    Page<Interest> findByUserAndDelYn(User user, boolean delYn, Pageable pageable);
    Interest findByItemAndDelYn(Item item, boolean delYn);

    Interest findByUserAndDelYnAndItem(User user, boolean b, Item item);
}
