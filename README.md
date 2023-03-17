# 항해bnb

### 목차 

1️. [프로젝트 소개](#1-프로젝트-소개)  
2️. [주요 기능](#2-주요-기능)  
3️. [서비스 아키텍쳐](#3-서비스-아키텍쳐)  
4. [ERD](#4-ERD)  
5. [기술 스택](#5-기술-스택)  
6. [트러블 슈팅](#6-트러블-슈팅)  
7. [깃허브](#7-깃허브)  
8. [팀원정보](#8-팀원정보)  

------------------------------

### 1. 프로젝트 소개

🏖️ [에어비앤비](https://www.airbnb.co.kr/) 웹서비스를 클로닝한 프로젝트 입니다!

![hanghaebnb](https://user-images.githubusercontent.com/87196958/225794295-c85166ea-3ec7-4090-aea6-ce8bba3ed225.png)



🔗 [팀 노션 보러가기](https://eunsolan.notion.site/3-bnb-d494215839e84f418a584ab2fc76baf7)  
📸 [시연 영상 보러가기](https://youtu.be/FwMvImgOa3k)

------------------------------
### 2. 주요 기능

- Spring Security, JWT를 이용한 회원가입/로그인
- 이메일 인증을 통한 로그인 구현
- AWS S3를 이용한 다중 이미지 업로드
- JPA Pageable을 이용한 페이지 무한 스크롤
- 타입별, 가격별 필터링 기능
- 키워드 검색 기능
- 숙소 좋아요 기능
- swagger 적용
- 다중 이미지 업로드 CRUD(조회 시 이미지 preview)
------------------------------
### 3. 서비스 아키텍쳐
![serviceArchitecture](https://user-images.githubusercontent.com/87196958/225795843-d5b7ba21-dac5-4060-9232-4b5ba3bd7bd0.png)

------------------------------
### 4. ERD
![hanghaebnbERD](https://user-images.githubusercontent.com/87196958/225794379-fb5f45b9-7384-4bf8-b93e-5abf49864702.png)
------------------------------
### 5. 기술 스택
 
#### 🎨 **Front-end Stack**

- React , javascript
- Redux
- Redux Toolkit
- mui , styled-components
- axios

#### 🛠 **Back-end Stack**

- Spring boot
- Spring Security, JWT
- Spring Data JPA

#### 🌐 **Infrastructure**

- AWS EC2
- AWS S3
- AWS RDS(MySQL)

#### 🗂 **Dev tools**

- Swagger
- Git, Github

------------------------------
### 6. 트러블 슈팅
<details>
<summary>@Enablejpaauditing</summary>
<div markdown="1">

### 문제
게시글을 수정할 때, CreatedAt/ModifiedAt 값이 null로 반환되는 문제 발생.
![troubleShooting](https://user-images.githubusercontent.com/87196958/225794922-0d92d52d-3979-4ba8-86da-2a4a0c36aae2.png)

### 해결
@Enablejpaauditing 어노테이션 추가하여 해결.
```java
@EnableJpaAuditing
@SpringBootApplication
public class HanghaebnbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaebnbApplication.class, args);
    }

}
```

</div>
</details>

<details>
<summary>필터링 + 페이징 (with Spring Data JPA)</summary>
<div markdown="1">

### 문제
페이징과 카테고리별/가격별 조회를 한 번에 하려다보니 쿼리가 복잡해져서 Spring Data JPA의 Query Method만으로는 조회가 어려운 상황.
@Query 어노테이션과 native query를 이용하여 해결하려 하였으나 native query와 페이징을 함께 사용하기가 까다로웠음.

### 해결
countQuery를 이용하여 query문을 작성하고, @Param 어노테이션과 함께 메서드를 생성하여 해결.
```java
@Query(countQuery = "select count(*) from room r where (r.price between :minPrice and :maxPrice) and r.type = :type", nativeQuery = true)
Page<Room> findByPriceBetweenAndType(@Param("minPrice") int minPrice,
                                     @Param("maxPrice") int maxPrice,
                                     @Param("type") String type,
                                     Pageable pageable);
```

</div>
</details>

<details>
<summary>데이터 전달 시 타입 지정</summary>
<div markdown="1">

### 문제
String type으로 매개변수를 받아올 때 공백문자가 섞이는 에러가 발생.
포스트맨에서 body - text로 놓고 {}없이 그냥 String 썼어야 함. 여태까지는 왜 이런 에러가 발생 안 했는지 생각해보니 여태까지는 dto로 받았었음.

### 해결
이 문제를 해결하기 위해 제네릭스를 사용해서 해결했다가, 통일성위해 dto로 responsebody로 json형식으로 받아오는 방식으로 바꿈.
</div>
</details>

<details>
<summary>if-else와 try-catch</summary>
<div markdown="1">

### 문제
if-else문 내부 throw → 특정 조건에서만 던져지는 exception.

### 해결
try-catch문으로 변경.
catch시 try코드에서 어떤 exception이 터질지 알고있으니 그게 맞게 작성해주면 됨. </br>
? → 지금은 IOException이나 직접만든 CustomException 두개로 catch를 하고있지만 **에러가 더욱 많아지면 계속 해서 catch문을 추가해서 해당하는 에러를 잡아야하나?** </br>
! → 자바가 기본 제공하는 Exception중 해당 exception이 상속받는 상위 상위 상위 exception이 존재함. 초기에는 적절한 exception을 catch문으로 사용하여 잡으면 넓은 범위의 catch로 핸들링 할 수 있으며 이 범위는 최적화 과정에서 줄여나가면 됨. 
</div>
</details>


------------------------------
### 7. 깃허브
- Frontend : <https://github.com/hanghaebnb/FE>
- Backend : <https://github.com/hanghaebnb/BE>
------------------------------
### 8. 팀원정보
|이름|주특기|깃허브 주소|
|---|---|---|
|황미경|BE|<https://github.com/beautifulseoul>|
|안은솔|BE|<https://github.com/eunsol-an>|
|차이진|BE|<https://github.com/leejincha/>|
|김재영|BE|<https://github.com/code0613>|
|이민규|FE|<https://github.com/GosuEE>|
|노현승|FE|<https://github.com/rlaqndlf1>|
