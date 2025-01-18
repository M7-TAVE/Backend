# 여행가방 - BackEnd

## 🖥️ 프로젝트 소개
2024 M7 팀의 여행가방 프로젝트의 백엔드입니다.

---

## 🕰️ 개발 기간
2024.11.29 - 2025.01.25

---

## 🛠️ 개발 스택

### 프레임워크
- Spring Boot

### 사용 언어
- Java

### 데이터베이스
- MySQL  
- H2 (개발/테스트 환경)

### ORM
- JPA (Hibernate)

### 보안
- Spring Security  
- OAuth 2.0 (카카오 로그인)

### 빌드 도구
- Gradle

### 기타
- Swagger (API 문서화)  
- Lombok (코드 간소화)  
- Postman (API 테스트)

---

## 🗂️ 프로젝트 구조

```plaintext
src
├── main
│   ├── java/com/example/travelbag
│   │   ├── config          # 설정 파일 (Security, Swagger 등)
│   │   ├── domain          # 주요 도메인별 패키지
│   │   │   ├── member      # 회원 관련 (엔티티, 레포지토리, 서비스 등)
│   │   │   ├── bag         # 여행가방 관련 로직
│   │   │   ├── item        # 준비물 관련 로직
│   │   ├── global          # 공통 처리 (에러 핸들링, 유틸 등)
│   │   └── TravelbagApplication.java
│   └── resources
│       ├── application.yml # 환경설정 파일
│       └── data.sql        # 초기 데이터 설정
└── test
    └── java/com/example/travelbag
        └── ... 테스트 코드 ...
