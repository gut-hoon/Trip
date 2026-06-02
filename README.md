# WANDARY 프로젝트

## 설계서

### 요구사항 정의서

| 순번 | 요구사항명                          | 요구사항 상세                                                  |
| ---- | ----------------------------------- | -------------------------------------------------------------- |
| 1    | 지역별 관광지 정보 수집             | 지역별 관광지 정보를 얻어와 화면에 표시                        |
| 2    | 관광지, 숙박, 음식점 조회           | 관광지 정보를 지역별 원하는 컨텐츠 별 조회                     |
| 3    | 문화시설, 공연, 여행코스, 쇼핑 조회 | 관광지 정보를 지역별 원하는 컨텐츠 별 조회                     |
| 4    | 여행 계획 경로 설정                 | 조회한 관광지를 활용하여 여행 계획, 여행 경로를 저장           |
| 5    | 회원 주도의 HotPlace 등록           | 지도와 사진을 활용한 HotPlace 등록                             |
| 6    | 회원 관리                           | JWT와 Spring Security를 통한 회원가입, 수정, 조회, 탈퇴        |
| 7    | 로그인 관리                         | JWT와 Spring Security를 통한 로그인 / 로그아웃 / 비밀번호 찾기 |
| 8    | 공지사항                            | 공지사항 등록, 수정, 삭제, 조회                                |
| 9    | 공유게시판                          | 게시판 등록, 수정, 삭제, 조회                                  |
| 10   | 여행 계획                           | 여행 계획 등록, 수정, 삭제, 조회                               |
| 11   | 인공지능 서비스                     | ChatGPT API를 활용한 서비스 제공                               |

## Use Case Diagram

![UseCase](docs/images/usecases.jpg)

## ERD

![ERD](docs/images/image.png)

## 클래스 다이어그램

### 여행 계획 클래스 다이어그램

![PlanClassDiagram](docs/images/PlanClassDiagram.cld.jpg)

### 게시판 클래스 다이어그램

![ArticleClassDiagram](docs/images/ArticleClassDiagram.cld.jpg)

---

# 화면 설계서

## 메인 페이지

![Main1](<docs/images/image (1).png>)

![Main2](<docs/images/image (2).png>)

![Main3](<docs/images/image (3).png>)

---

## 여행 계획 등록 페이지

![PlanCreate1](<docs/images/image (4).png>)

![PlanCreate2](<docs/images/image (5).png>)

![PlanCreate3](<docs/images/image (6).png>)

---

## 여행 계획 목록 페이지

![PlanList](<docs/images/image (7).png>)

---

## 여행 계획 상세 페이지

![PlanDetail](<docs/images/image (8).png>)

---

## 여행 계획 수정 페이지

![PlanUpdate1](<docs/images/image (9).png>)

![PlanUpdate2](<docs/images/image (10).png>)

---

## 게시글 목록 페이지

![ArticleList](<docs/images/image (11).png>)

---

## 게시글 상세 페이지

![ArticleDetail](<docs/images/image (12).png>)

---

## 게시글 수정 페이지

![ArticleUpdate](<docs/images/image (13).png>)

---

## 핫플레이스 목록 페이지

![HotPlaceList](<docs/images/image (14).png>)

---

## 핫플레이스 상세 페이지

![HotPlaceDetail](<docs/images/image (15).png>)

---

## AI 관광지 추천 페이지

![AIRecommendation](<docs/images/image (16).png>)

---

## 로그인 페이지

![Login](<docs/images/image (17).png>)

### 회원가입 페이지

![Signup](<docs/images/image (18).png>)

### 비밀번호 수정 페이지

![Password](<docs/images/image (19).png>)

---

## 인기 여행지 차트

![Chart](<docs/images/image (20).png>)

---

## 내 여행 계획 목록

![MyPlan](<docs/images/image (21).png>)
