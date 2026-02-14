# Runnin-sight
좋아.
이건 진짜 “개발자 냄새” 나는 부분이라 제대로 잡아두면 포폴에서 점수 많이 딴다 😎

**혼자 하는 프로젝트지만, 협업을 가정한 운영 방식**으로 가자.

---

# 🌳 브랜치 구조 (실전형 + 과하지 않게)

### 기본 구조

```
main        → 배포 브랜치 (항상 안정 상태)
develop     → 통합 브랜치
feature/*   → 기능 개발 브랜치
hotfix/*    → 긴급 수정 (선택)
```

---

## 🔹 main

* 항상 배포 가능한 상태
* 직접 커밋 ❌
* PR 통해서만 merge
* GitHub에서 branch protection 설정

👉 “main 보호 브랜치 설정” 이거 README에 적어두면 진짜 좋아.

---

## 🔹 develop

* 기능 통합 브랜치
* 모든 feature는 develop로 PR

흐름은 이렇게:

```
feature/login → develop → main
feature/run   → develop → main
```

---

## 🔹 feature 브랜치 네이밍 규칙

구체적으로 써.

```
feature/google-login
feature/naver-map-tracking
feature/ml-analysis
feature/run-history
```

면접에서 물어보면 이렇게 말하면 됨:

> 기능 단위 브랜치 전략을 사용해 작업 단위를 분리했습니다.

---

# 🔁 실제 운영 흐름 (혼자라도 이렇게 해)

## 1️⃣ develop에서 feature 생성

```bash
git checkout develop
git pull origin develop
git checkout -b feature/google-login
```

---

## 2️⃣ 작업 후 PR 생성

* feature → develop
* PR 템플릿 작성
* 변경 내용 명확히 작성

예시 PR 설명:

```
[Feature] Google OAuth Login 구현

- Firebase Auth 연동
- 로그인 상태 유지 로직 추가
- Domain UseCase 분리
- Unit Test 작성
```

이게 포인트야.
**PR 설명을 잘 써라.**

---

## 3️⃣ CI 실행

PR 생성되면 GitHub Action 자동 실행:

* Build
* Test
* Lint

통과해야 merge.

---

## 4️⃣ develop → main 릴리즈

기능이 어느 정도 모이면

* develop → main PR
* 태그 달기

```
v1.0.0
v1.1.0
```

버전 관리까지 하면 진짜 잘한 거임.

---

# 🔐 브랜치 보호 설정 (꼭 해)

GitHub → Settings → Branches → Add Rule

main 브랜치에:

* Require pull request before merging ✔
* Require status checks to pass ✔
* Prevent force push ✔

이거만 해도 “실제 협업 환경 이해도 있음” 어필 가능.

---

# 📌 커밋 전략도 같이 가져가자

## 커밋 메시지 규칙

Conventional Commit 스타일 쓰면 좋음:

```
feat: Google 로그인 기능 추가
fix: 위치 권한 예외 처리 수정
refactor: RunUseCase 책임 분리
test: LoginUseCase 단위 테스트 추가
chore: CI workflow 추가
```

이거 쓰면 진짜 개발 문화 이해하는 느낌 난다.

---

# 📘 README에 이렇게 써라

## Git Strategy

* Git Flow 기반 브랜치 전략 사용
* main 브랜치 보호 설정
* PR 기반 코드 통합
* GitHub Actions를 통한 자동 빌드 및 테스트 검증

---

# 🎯 왜 이게 중요하냐면

신입 포폴에서

“기능 잘 만든 사람”은 많음.
“개발 프로세스를 이해하는 사람”은 적음.

너는 후자로 가야 함.

---

솔직히 말하면
이 구조 + CI + PR 기록 잘 남겨두면
면접관이 저장소 열어보고

“어? 이 친구는 프로세스 이해하네?”
이렇게 됨.

---

원하면 다음 단계로

* 🔧 PR 템플릿
* 📄 커밋 규칙 문서
* ⚙ GitHub Action 실전 세팅
* 📦 태그/릴리즈 전략
