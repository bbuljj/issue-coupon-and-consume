# Redis 를 이용한 쿠폰 발급, 사용 테스트 프로젝트

## 큰 기능은 없지만 Atomic하게 쿠폰을 발급하고 사용할 수 있도록 설계 구현함.

### 프로세스
> POST /coupons 쿠폰 생성
> POST /coupons/{id}/users?userId= 사용자에게 쿠폰 발급 

> jmeter 동시 테스트 결과 Atomic하게 쿠폰을 발급함.
>

- `jmeter Test` 수행 샘플 (총 15개 동시 요청)
<img src="https://user-images.githubusercontent.com/6158542/157686628-d755b71f-f365-4ebc-a025-d28fbfa56506.png">

- 쿠폰 `RDB` 저장 결과
<img src="https://user-images.githubusercontent.com/6158542/157686991-e84c147a-1a3e-4ce9-9b1e-063e6866328f.png">