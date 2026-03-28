web-study
📌 프로젝트 소개
회원가입, 로그인, 소셜 로그인 등 웹 서비스 공통 기능을 학습하고 구현하는 프로젝트입니다.
🛠 기술 스택
Frontend

React + Vite
Axios, React Router, TanStack Query, Zustand

Backend

Java 17, Spring Boot 3.1.5
Spring Security, JWT, OAuth2
Spring Data JPA, PostgreSQL
Redis (이메일 인증)

🐳 로컬 개발 환경 실행 방법
사전 준비

Docker Desktop 설치 및 실행
Node.js v20.19+
Java 17+

1. DB 실행 (Docker)
bashdocker-compose up -d
2. Backend 실행
bashcd backend
./gradlew bootRun
→ http://localhost:8080
3. Frontend 실행
bashcd frontend
npm run dev
```
→ http://localhost:5173

## 📋 주요 기능
- 회원가입 / 로그인 (JWT)
- 소셜 로그인 (Google, Kakao)
- 이메일 인증
- 역할/권한 관리 (ROLE)

## 🗂 프로젝트 구조
```
web-study/
├── docker-compose.yml
├── frontend/        ← React + Vite
└── backend/         ← Spring Boot
