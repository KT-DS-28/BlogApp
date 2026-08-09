# 코드 리뷰용 구조 안내 (A팀 / BlogApp)
---

## 1. 패키지 구조

```
com.ktdsuniv.blogapp
├── Main.java              메뉴 출력 · 입력 분기 · 예외 출력 (진입점)
├── domain/                데이터와 그 데이터의 규칙
│   ├── User.java          사용자. 이웃 목록 · 게시글 목록 · 로그인 실패 횟수 보유
│   ├── Post.java          게시글. 좋아요 · 조회 · 댓글 · 태그 · 발행 시각 보유
│   ├── Comment.java       댓글. 작성자 · 내용 · 작성 시각 · 좋아요 보유
│   └── Neighbor.java      이웃 관계. 신청자 · 수신자 · 상태 보유
├── enums/
│   └── NeighborState.java PENDING, ACCEPTED
├── exception/             기능 수행 중 발생하는 예외 (11개)
│   └── BlogException      나머지 10개의 부모
├── service/               기능 구현 (인터페이스 + 구현체 4쌍)
│   ├── UserService(Impl)      등록 · 로그인 · 로그아웃 · 탈퇴
│   ├── PostService(Impl)      등록 · 목록 · 상세 · 발행 · 좋아요
│   ├── CommentService(Impl)   작성 · 수정 · 삭제 · 좋아요
│   └── NeighborService(Impl)  신청 · 처리 · 목록 · 해제
└── util/
    ├── Session.java       현재 로그인 사용자 보관 (static, 동시 1명)
    └── ScannerUtil.java   콘솔 입력 (Scanner 공유, close 금지)
```


---

## 2. 데이터 구조 — 사용자가 모든 데이터의 뿌리

```
USERS (static List<User>)
  └── User
        ├── postList  ─── Post
        │                   ├── likeUsers   → User 참조
        │                   ├── viewedUsers → User 참조
        │                   ├── comments ─── Comment
        │                   │                  ├── author    → User 참조
        │                   │                  └── likeUsers → User 참조
        │                   └── tags (String)
        └── neighbors ─── Neighbor  ← 신청자·수신자 양쪽이 같은 객체를 공유
                            ├── requester → User 참조
                            └── receiver  → User 참조
```
---

