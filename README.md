# 📒 Navigation 3 Practice - Adaptive Note App

> **Jetpack Navigation 3**와 **Material3 Adaptive UI**를 활용한 간단한 노트 앱 예제입니다.  
> Nav3의 `NavKey` 기반 백스택, SceneStrategy를 통한 2-pane 적응형 UI,  
> Entry Decorator를 활용한 상태 및 ViewModel 관리를 실습합니다.

---

## 📌 프로젝트 소개
이 앱은 **Navigation 3**의 주요 기능을 연습하기 위해 제작한 간단한 **노트 목록 & 상세 화면** 앱입니다.  
화면 크기에 따라 **1-pane / 2-pane** 자동 전환이 되며,  
`NoteList`와 `NoteDetail`을 동시에 표시하는 **적응형 레이아웃**을 지원합니다.

---

## 🛠 사용 기술

### 📦 의존성
```kotlin
// Navigation 3
implementation(libs.nav3.runtime)
implementation(libs.nav3.ui)
implementation(libs.androidx.lifecycle.viewmodel.nav3)

// Serialization
implementation(libs.kotlinx.serialization.core)
implementation(libs.kotlinx.serialization.json)

// Compose Lifecycle & ViewModel
implementation(libs.androidx.lifecycle.runtime.compose)
implementation(libs.androidx.lifecycle.viewmodel.compose)

// Adaptive UI
implementation(libs.androidx.compose.material3.windowsizeclass)
implementation(libs.androidx.material3.adaptive)

// DI
implementation(libs.koin.core)
implementation(libs.koin.android)
implementation(libs.koin.androidx.compose)
````

---

## ✨ 주요 기능 및 구현 포인트

### 1. **NavKey 기반 네비게이션**

* Nav3에서는 `NavKey`를 사용하여 화면을 식별하고 백스택을 관리합니다.
* 이 예제에서는 `NoteListScreen`과 `NoteDetailScreen(id: Int)`를 `@Serializable`로 선언하여 **데이터 직렬화 기반 네비게이션**을 구현했습니다.

```kotlin
@Serializable
data object NoteListScreen : NavKey

@Serializable
data class NoteDetailScreen(val id: Int) : NavKey
```

---

### 2. **백스택 관리 (`NavBackStack`)**

* `rememberNavBackStack()`을 사용하면 `SnapshotStateList<NavKey>` 형태의 백스택을 생성합니다.
* 화면 전환 시 `backStack.add(newKey)`로 간단하게 푸시 가능.
* 기존 NavController 방식보다 단순하며, 개발자가 직접 스택을 조작합니다.

---

### 3. **적응형 UI (`SceneStrategy`)**

* `TwoPaneSceneStrategy`를 구현하여 **가로폭이 Medium 이상**일 때
  `NoteList`와 `NoteDetail`을 **2-pane**으로 동시에 표시.
* `Scene<T>` 구현체(`TwoPaneScene`)를 통해 화면 비율(0.3 : 0.7)로 레이아웃 구성.

```kotlin
if (windowClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
    // 2개의 NavEntry가 모두 TwoPaneKey를 포함하면 TwoPaneScene 생성
}
```

---

### 4. **Entry Decorator로 상태 관리**

* Nav3의 **Entry Decorator**는 각 `NavEntry`의 상태/라이프사이클을 확장합니다.
* 사용한 데코레이터:

  1. `rememberSavedStateNavEntryDecorator()` → 화면 상태 저장
  2. `rememberViewModelStoreNavEntryDecorator()` → ViewModel 스코프 관리
  3. `rememberSceneSetupNavEntryDecorator()` → Scene 초기 설정

---

### 5. **DI와 ViewModel 연동**

* `NoteDetailScreenUi`에서 `koinViewModel { parametersOf(noteId) }`로 ViewModel 주입.
* Navigation 키의 데이터를 그대로 ViewModel 초기 파라미터로 전달.

---

## 📱 실행 화면 예시

### Small 화면 (모바일)

```
[NoteList] → 클릭 → [NoteDetail]
```

### Medium+ 화면 (태블릿, 폴더블)

```
[NoteList] | [NoteDetail]
```

---

## 📚 알게 된 점 (Study Notes)

1. **Nav3의 핵심**

   * NavController 없이 `SnapshotStateList<NavKey>`로 백스택을 직접 관리.
   * 직렬화된 NavKey 덕분에 인자 전달이 안전하고 직관적.

2. **Adaptive UI의 강점**

   * `SceneStrategy`로 레이아웃 전환을 중앙에서 제어 가능.
   * WindowSizeClass 활용으로 폴더블, 태블릿 대응 용이.

3. **Entry Decorator 활용**

   * 상태 저장, ViewModel 스코프, 초기화 로직을 각 Entry에 주입 가능.
   * Nav3에서는 상태/라이프사이클을 Entry 단위로 생각하는 것이 핵심.

4. **기존 Nav2 대비**

   * 선언형 UI에 맞춰 설계되어 코드가 간결.
   * 백스택 로직을 명확하게 커스터마이징 가능.
   * 단, 초기 학습 난이도와 알파 버전 안정성 문제 고려 필요.

---

