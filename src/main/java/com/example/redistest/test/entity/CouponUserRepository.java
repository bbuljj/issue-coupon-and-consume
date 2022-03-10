package com.example.redistest.test.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {
    boolean existsByUser_IdAndCoupon_Id(Long userId, Long couponId);
}
