package com.example.redistest.test.service;

import com.example.redistest.test.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CouponService {
    @Autowired
    private CouponRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponUserRepository couponUserRepository;

    private final static String COUPON_KEY_FORMAT = "user:coupon:%d";

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void create() {
        var count = repository.count();
        var coupon = new Coupon();
        coupon.setName(String.format("test %d", count));
        var saved = repository.save(coupon);

        ValueOperations<String, Object> vv = redisTemplate.opsForValue();
        var key = String.format(COUPON_KEY_FORMAT, saved.getId());
        vv.set(key, "7");
    }

    public void consume(Long id, Long userId) {
        System.out.println("request user : " + userId + ", request At : " + LocalDateTime.now());
        var key = String.format(COUPON_KEY_FORMAT, id);
        this.checkCoupon(key);

        var coupon = repository.findById(id).orElseThrow();
        var user = userRepository.findById(userId).orElseThrow();
        if (couponUserRepository.existsByUser_IdAndCoupon_Id(coupon.getId(), user.getId())) {
            throw new RuntimeException("이미 발행함.");
        }

        var remainAfterDecrement = redisTemplate.opsForValue().decrement(key);
        if (remainAfterDecrement >= 0) {
            this.couponUserSave(coupon, user);
        } else {
            throw new RuntimeException("쿠폰 없음");
        }
    }

    private void checkCoupon(String key) {
        if (Long.valueOf((String) redisTemplate.opsForValue().get(key)) < 1) {
            throw new RuntimeException("쿠폰 없음");
        }
    }

    private void couponUserSave(Coupon coupon, User user) {
        var cU = new CouponUser();
        cU.setCoupon(coupon);
        cU.setUser(user);
        couponUserRepository.save(cU);
    }

}