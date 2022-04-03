package com.mk.db.repository;

import com.mk.db.entity.Order;
import com.mk.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);
}
