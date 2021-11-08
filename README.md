# Document Approval

언어: Kotlin

서버 Framework: Spring (Spring Boot)

DB: `34.146.101.145:3306/docapp` Google Cloud Sql (id: root, pw 없음)

_편의상 Exception 최소화_

<br/><br />

## **중요 Entity**

```
    USER
    @Id
    val id: String,
    val password: String ?= null,
    val regDate: Date ?= null
```

```
    DOC
    @Id
    val id: Int,
    val title: String,
    val content: String,
    val status: DocStatus,
    val regDate: Date,

    @ManyToOne
    val category: Category?,
    @ManyToOne
    val user: User, // 문서 생성자
```

```
    DOC_APPROVAL
    @Id
    val id: Int,
    val seq: Int,
    val status: ApprovalStatus,
    val comment: String?,
    val regDate: Date,
    val modDate: Date,

    @ManyToOne
    val doc: Doc, // 결재 문서
    @ManyToOne
    val user: User, // 결재해야하는 유저
```

<br /><br />

## 1. 로그인 (auth/AuthController.kt)

Spring Security JWT Token Cookie 로그인

'/api' 로 시작하는 API 는 로그인 Cookie 확인

<br/>

**1.1 로그인**

`POST /auth/login`

Param (JSON):

```
    id: String,
    password: String
```

OK(200) : Cookie 헤더 포함

---

**1.2 회원가입**

`POST /auth/join`

Param (JSON):

```
    id: String,
    password: String // 편의상 비번 체크 입력 생략
```

OK(200) : 가입 아이디 Cookie 헤더 포함

Err(403) : 이미 가입된 아이디 있음

---

**1.3 로그아웃**

`POST /api/logout`

OK(200): Cookie 헤더 삭제

---

<br/><br />

## 2. 문서 목록 (doc/DocController.kt)

편의상 Doc 페이지에 Doc 정보 포함 (한개의 Doc 정보 부르는 API 생략)

<br/>

**2.1 OUTBOX**

`GET /api/doc/outbox`

Param(URL):

```
    pageNum: Int?,
    pageSize: Int?,
```

OK(200): ResOutboxPage (Doc Pages) 반환

---

**2.2 INBOX**

`GET /api/doc/inbox`

Param(URL):

```
    pageNum: Int?,
    pageSize: Int?,
```

OK(200): ResInboxPage (Doc Approval Pages) 반환

---

**2.3 ARCHIVE**

`GET /api/doc/archive`

Param(URL):

```
    pageNum: Int?,
    pageSize: Int?,
```

OK(200): ResOutboxPage (Doc Pages) 반환

---

<br /><br />

## 3. 문서 생성

**3.1 문서 생성에 필요한 카테고리 목록** (common/CommonController.kt)

`GET /api/category/all`

No Param

OK(200): 전체 Category 목록 반환

---

**3.2 문서 생성에 필요한 유저 목록** (user/UserController.kt)

Param(URL):

```
    pageNum: Int?,
    pageSize: Int?,
```

OK(200): User 페이지 반환

---

**3.3 문서 생성** (doc/DocController.kt)

`POST /api/doc`

Param(JSON):

```
    title: String,
    content: String,
    category: Int,
    users: List<String>? // 순서대로 Seq 저장
```

OK(200): 생성된 Doc 반환

---

<br /><br />

## 4. 문서 승인 (doc/DocController.kt)

**4.1 문서 승인**

`POST /api/doc/sign`

Param(JSON):

```
    docId: Int,
    decision: String, // APPROVED or DENIED
    comment: String?
```

OK(200): "success" 메시지 반환
