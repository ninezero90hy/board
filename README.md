# 앱 커뮤니티 게시판



## 어플리케이션 실행 방법

- 명령어 실행 위치 -> 프로젝트 디렉토리

```bash
# 메이븐 패키징
$ mvn clean package

# 패키징된 Jar로 서버 기동
$ java -jar target/board-0.0.1-SNAPSHOT.jar
```



## H2 데이터베이스 정보

- URL: http://localhost:8080/lightning
- JDBC URL: jdbc:h2:~/lightning
- UserName: lightning
- Password: 1234



---



## 등록

*[**POST**] /api/boards*



**설명**

| Property   | Description                           | 필수 여부 |
| ---------- | ------------------------------------- | --------- |
| `title`    | 제목은 20자                           | O         |
| `content`  | 내용은 200자                          | O         |
| `author`   | 작성자                                | O         |
| `password` | 비밀번호는 6자 이상, 영어와 숫자 조합 | O         |

**샘플**

```bash
curl --location --request POST 'localhost:8080/api/boards' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "value1",
    "content": "value2",
    "author": "value3",
    "password": "value4"
}'
```



---



## 단 건 조회

*[**GET**] /api/boards/{id}*



**Path variable**

| Property | Description   |
| -------- | ------------- |
| `id`     | 게시글 아이디 |

**샘플**

```bash
curl --location --request GET 'localhost:8080/api/boards/1' \
--header 'Content-Type: application/json' \
--data-raw ''
```



---



## 목록 조회

*[**GET**] /api/boards*



**Parameters**

| Property | Description                 | 기본값           |
| -------- | --------------------------- | ---------------- |
| `page`   | 페이지 번호                 | 0                |
| `size`   | 페이지 사이즈               | 10               |
| `sort`   | 정렬할 컬럼명 / (desc\|asc) | createdDate,desc |

**샘플**

```bash
curl --location --request GET 'localhost:8080/api/boards' \
--header 'Content-Type: application/json' \
--data-raw ''
```



---



## 삭제

*[**DELETE**] /api/boards/{id}*



**Parameters**

| Property   | Description |
| ---------- | ----------- |
| `password` | 글 비밀번호 |

**샘플**

```bash
curl --location --request DELETE 'localhost:8080/api/boards/1?password=test123' \
--header 'Content-Type: application/json' \
--data-raw ''
```