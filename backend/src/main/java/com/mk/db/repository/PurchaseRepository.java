package com.mk.db.repository;

import com.mk.db.entity.Purchase;
import com.mk.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    List<Purchase> findByUser(User user);

}
