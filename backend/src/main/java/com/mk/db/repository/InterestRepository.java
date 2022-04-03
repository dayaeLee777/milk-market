package com.mk.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mk.db.entity.Interest;
import com.mk.db.entity.Item;
import com.mk.db.entity.User;

public interface InterestRepository extends JpaRepository<Interest, String> {


    List<Interest> findByUserAndDelYn(User user, boolean delYn);
    Page<Interest> findByUserAndDelYn(User user, boolean delYn, Pageable pageable);
    Interest findByItemAndDelYn(Item item, boolean delYn);

    Interest findByUserAndDelYnAndItem(User user, boolean b, Item item);
    
    @Query(value="SELECT * FROM interest GROUP BY item_id ORDER BY count(id) DESC LIMIT 5", nativeQuery = true)
    List<Interest> findHotItem();
    
    @Query(value="SELECT * FROM interest i JOIN user u ON i.user_id=u.id WHERE u.bcode = :bcode GROUP BY i.item_id ORDER BY count(i.id) DESC LIMIT 5", nativeQuery = true)
    List<Interest> findHotItemBybcode(@Param("bcode") String bcode);
    
    long countByItem(Item item);
}
