package com.kick_api.repository;

import com.kick_api.entity.Order;
import com.kick_api.constant.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    Optional<Order> findByOrderNo(String orderNo);

    Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);
}
