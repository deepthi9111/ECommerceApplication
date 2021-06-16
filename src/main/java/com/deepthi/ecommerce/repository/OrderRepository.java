package com.deepthi.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepthi.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
