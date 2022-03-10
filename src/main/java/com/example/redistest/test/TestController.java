package com.example.redistest.test;

import com.example.redistest.test.entity.CouponRepository;
import com.example.redistest.test.entity.CouponUserRepository;
import com.example.redistest.test.entity.UserRepository;
import com.example.redistest.test.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUserRepository couponUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final static String COUPON_KEY_FORMAT = "user:coupon:%d";

    @PostMapping("/coupons/{id}/users")
    public void consumerCoupon(@PathVariable Long id, @RequestParam("userId") Long userId) {
        couponService.consume(id, userId);
    }

    @PostMapping("/coupons")
    public void createCoupon() {
        couponService.create();
    }

    @GetMapping("/coupons")
    public void getCoupons() {
        System.out.println("gets");
    }
}
