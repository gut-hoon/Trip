# WANDARY 프로젝트 성시훈\_홍민기

## 설계서

### 요구사항 정의서

| 순번 | 요구사항명                          | 요구사항 상세                                                  |
| ---- | ----------------------------------- | -------------------------------------------------------------- |
| 1    | 지역별 관광지 정보 수집             | 지역별 관광지 정보를 얻어와 화면에 표시                        |
| 2    | 관광지, 숙박, 음식점 조회           | 관광지 정보를 지역별 원하는 컨텐츠 별 조회                     |
| 3    | 문화시설, 공연, 여행코스, 쇼핑 조회 | 관광지 정보를 지역별 원하는 컨텐츠 별 조회                     |
| 4    | 여행 계획 경로 설정                 | 조회한 관광지를 활용하여 여행 계획, 여행 경로를 저장           |
| 5    | 회원 주도의 hotplace 등록           | 지도와 사진을 활용한 hotplace 등록                             |
| 6    | 회원 관리                           | JWT와 Spring Security를 통한 회원가입, 수정, 조회, 탈퇴        |
| 7    | 로그인 관리                         | JWT와 Spring Security를 통한 로그인 / 로그아웃 / 비밀번호 찾기 |
| 8    | 공지사항                            | 공지사항 등록, 수정, 삭제, 조회                                |
| 9    | 공유게시판                          | 게시판 등록, 수정, 삭제, 조회                                  |
| 10   | 여행 계획                           | 여행 계획 등록, 수정, 삭제, 조회                               |
| 11   | 인공지능 서비스                     | ChatGPT API를 활용한 서비스 제공                               |

![usecases](/uploads/0f9a98fc9ac2f0c3e76037579edb2959/usecases.jpg)

### 테이블 구조도(ERD)

![image](/uploads/b88edf739de76c550b8b0cb2ff602b8c/image.png){width=663 height=740}

### 클래스 다이어그램

- 여행 계획 클래스다이어그램

  ![PlanClassDiagram.cld](/uploads/efc3019929428b4d37e29aeafd32e4a4/PlanClassDiagram.cld.jpg)

- 게시판 클래스 다이어그램

  ![ArticleClassDiagram.cld](/uploads/0be34c2797aac94b8b92a6c8267f7869/ArticleClassDiagram.cld.jpg)

### 화면 설계서

- 메인 페이지

  ![image](/uploads/7e9cb50498ba1058f2a3d53dede1ac55/image.png){width=1913 height=775}
  ![image](/uploads/1aed605decb1075067695157fa3811a6/image.png){width=1908 height=769}
  ![image](/uploads/2f7e13d5e1830dd3210367b99842c9d8/image.png){width=1910 height=926}

- 여향 계획 등록 페이지

  ![image](/uploads/c4eee12ff551a409e492ced8ed12a39f/image.png){width=1103 height=698}
  ![image](/uploads/5595520ea95227f8bda45f63872f6681/image.png){width=1186 height=720}
  ![image](/uploads/252ef53a5ff3ad35863936904162881e/image.png){width=780 height=454}

- 여행 계획 목록 페이지

  ![image](/uploads/9f6bf9e6c56bbfb3131ce176d6d71f1e/image.png){width=1098 height=459}

- 여행 계획 상세 페이지

  ![image](/uploads/6136e67504a70322b9d2687beccad4d8/image.png){width=1440 height=768}

- 여행 계획 수정 페이지

  ![image](/uploads/5b0f286e981e4f708a826ef929521e2e/image.png){width=1173 height=723}
  ![image](/uploads/0079141f25367f87edb422c2455f7b67/image.png){width=1011 height=725}

- 게시글 목록 페이지

  ![image](/uploads/ab5ba7ab9558206935b8472ef2a76243/image.png){width=1620 height=305}

- 게시글 상세 페이지

  ![image](/uploads/44e3bb17d3b9259e31be2f39ad877663/image.png){width=1101 height=677}

- 게시글 수정 페이지

  ![image](/uploads/04c5e1da630fc78df4ebc3830e5481c6/image.png){width=659 height=704}

- 핫플레이스 목록 페이지

  ![image](/uploads/713006f75f47d156b1607b5008d9dc75/image.png){width=993 height=733}

- 핫플레이스 상세 페이지

  ![image](/uploads/d3eae0032c66e41baf72b781f0480be6/image.png){width=1118 height=712}

- AI 관광지 추천 페이지

  ![image](/uploads/e778face8f320c2527c9b53a747f2bb2/image.png){width=1287 height=700}

- 로그인, 회원가입, 비밀번호 수정 페이지

  - ![image](/uploads/6e26ebb95337fe908f3f7b72dbf18a97/image.png){width=563 height=477}

  - ![image](/uploads/01a62bad748502068e0049c51a9e2ead/image.png){width=702 height=588}

  - ![image](/uploads/83f59de179315e0a9d448e61fd360568/image.png){width=417 height=552}

- 인기 여행지 차트

  ![image](/uploads/dbde837a875c71da5ad8429cc987cd5e/image.png){width=1611 height=324}

- 내 여행 계획 목록

  ![image](/uploads/df60610436cf61a5c416495c874ae4dd/image.png){width=982 height=388}
