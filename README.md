# HappyHouse

# HappyHouse

### 주제 : 아파트 매물 검색 서비스

### 기술 스택 

#### - Web backend (Java, Spring framework, MySQL)

#### \- Web frontend (HTML5, CSS, SCSS, JS ES6, SPA, Vue.js)

#### \- GitLab 협업 flow ( code review )

#### \- Maven 사용 : ( build 자동화 tool, 유지보수성을 높임 )

#### \- ((MVVM모델) + MVC모델) 적용 

#### \- REST API 구축

#### \- Jwt ( 로그인 구현 )

#### \- Chart.js ( 데이터 시각화 )

#### \- JSOUP ( 크롤링 )

#### \- Mybatis ( DB Mapping )

#### \- NGINX 


## ERD

![image](https://user-images.githubusercontent.com/23431746/147460223-7cf542f9-71d3-4a84-93a4-2661750a495f.png)


## USECASE Diagram

### 1. 거래내역, 아파트 DTO
![image](https://user-images.githubusercontent.com/23431746/147460330-3302be07-423c-4d8d-be60-c3abc267716c.png)
![image](https://user-images.githubusercontent.com/23431746/147460338-e5dff666-02a6-450f-8d7a-bd2a7181304d.png)


### 2. User, 공지사항, Q&A, 상권, 환경, 주택 및 주변 편의, 복지 정보 DTO
![image](https://user-images.githubusercontent.com/23431746/147460382-ff12e15f-380c-42fa-b06b-df188c94e69c.png)


### 3. 주택 거래, 즐겨찾기 동네, 시/도 + 구/군 코드 DTO
![image](https://user-images.githubusercontent.com/23431746/147460423-ce92afaf-4a9a-4421-92e5-a8fd6c7d41f4.png)


### 4. USER MVC
![image](https://user-images.githubusercontent.com/23431746/147460468-1fbab047-d943-43d2-9494-4ad30645c551.png)


### 5. HOUSE MVC
![image](https://user-images.githubusercontent.com/23431746/147460480-73aa8078-e365-4b39-ab23-62b40fb3340e.png)


### 6. Q&A / 답글 MVC
![image](https://user-images.githubusercontent.com/23431746/147460505-a77631d0-6fe4-4390-a96e-07ec432d9073.png)


### 7. News/Store/Notice class
![image](https://user-images.githubusercontent.com/23431746/147460540-5e746fa3-ddd1-4798-8520-17d6cd9f475b.png)


## USECASE Diagram
![image](https://user-images.githubusercontent.com/23431746/147460651-9931f102-7406-4c58-a170-86fb2b469229.png)

### (위 actor = USER, 아래 actor = ADMIN)


## 주요 기능

### 회원관리
![image](https://user-images.githubusercontent.com/23431746/147459371-113c86b2-7a58-4081-afb3-65f5183a706e.png)

(회원 가입 창)


![image](https://user-images.githubusercontent.com/23431746/147459389-d53a7a09-110a-4a7d-b731-98fbe0fbfb18.png)
![image](https://user-images.githubusercontent.com/23431746/147459396-67ce8efe-a125-40c9-8d5b-d99222fe5be6.png)

(아이디 유효성 검사)

#### 회원 관리 기능
- 회원 가입
- 회원 정보 수정
- 회원 탈퇴


### 로그인 관리

![image](https://user-images.githubusercontent.com/23431746/147459455-687c38b7-2a5b-466c-b490-aad5d12758ea.png)
![image](https://user-images.githubusercontent.com/23431746/147459934-752cab0d-52e7-4d90-8176-01afef9dcce5.png)

(소셜 로그인 구현)


#### 로그인 관리 기능
- 로그인
- 비밀번호 찾기
- 로그아웃


### 메인 페이지

![image](https://user-images.githubusercontent.com/23431746/147458732-cd6ff655-1998-4b7c-931b-bef014af08c7.png)

(메인 페이지)

#### 메인 페이지 기능
- 이미지 로고
- 상단 네임 태그
- 사이드바
- 전국 기온
- 시/도 및 구/군 선택
- 뉴스 크롤링
- 코로나 확진자 현황
- 우측 상단 드롭다운


### 지도
![image](https://user-images.githubusercontent.com/23431746/147458801-cd5e2284-e625-4d1f-8ea2-b836ef0a3d35.png)

(아파트 실거래가 정보)

![image](https://user-images.githubusercontent.com/23431746/147458847-ba05725c-1df4-476f-9024-c1b01e5c5fd4.png)
![image](https://user-images.githubusercontent.com/23431746/147458855-fc717ba6-6d93-479f-89a2-d7ff70028624.png)
![image](https://user-images.githubusercontent.com/23431746/147458863-06ecdcd6-4035-4ceb-81b2-938166a2cc51.png)

(아파트 상세정보 [데이터 시각화])

![image](https://user-images.githubusercontent.com/23431746/147459004-2d15106b-0945-463d-9511-77c2e5ed8e27.png)

(아파트 실거래가 가격 범위별 검색)

![image](https://user-images.githubusercontent.com/23431746/147458956-32836d25-5fb7-45a2-b1ae-3a747baf8c9d.png)

(계정당 즐겨찾기 지역)

#### 지도 기능
- 지역 선택
- 아파트 실거래가
- 아파트 최저거래가
- 관심지역 즐겨찾기
- 주변지역 상권정보
- 주변지역 환경정보
- 아파트 상세정보


### 공지사항
![image](https://user-images.githubusercontent.com/23431746/147459142-d6143d2c-8ca4-406d-9c0f-c9ec1b1a1435.png)

(공지사항 페이지)

![image](https://user-images.githubusercontent.com/23431746/147459155-c908bcc0-3b18-4180-b885-49ef8bf57288.png)
![image](https://user-images.githubusercontent.com/23431746/147459270-d967bb52-5f71-4963-80f8-6ab0f06d12e1.png)

(공지사항 내용 < + 파일 업로드>)

![image](https://user-images.githubusercontent.com/23431746/147459190-8547a10e-6d80-4641-b23d-f5e1a46bd8a0.png)

(공지사항 수정, 삭제)

#### 공지사항 기능
- 목록
- 글 작성
- 글 보기
- 글 수정
- 글 삭제


### Q&A
![image](https://user-images.githubusercontent.com/23431746/147459284-164573c3-925a-4ffa-ad8a-5966984ab0c5.png)

(Q&A 목록)

![image](https://user-images.githubusercontent.com/23431746/147459307-f0aa7386-6070-4b05-8782-095e892be984.png)

(Q&A 등록/수정/삭제 및 답글)

#### Q&A 
- 목록
- 보기
- 질문 등록
- 질문 수정
- 질문 삭제
- 답변 등록
- 답변 수정
- 답변 삭제


## 결과 
- 최소기능 구현 완료
- 일일 stand-up 미팅, Lean SW 개발 방법론을 기반으로 최대한 고객에 니즈에 맞춘 데이터 시각화 서비스 제공하여 초기 테스터들에게 데이터 시각화와 관련해 아파트 상세정보를 한눈에 파악할 수 있다는 평가. 
- 규모가 있는 트래픽 처리에 장애가 생겨, NGINX기술을 적용, 약 1.5~2배 이상의 트래픽 처리 성능 향상을 성공 
- 추후에는 트래픽 변화 감지를 위한 Grafana를 적용, Mybatis로 구현한 DB Mapping를 JPA를 이용하여 데이터 유지보수가 용이 하도록 바꿀 예정.
