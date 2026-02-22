# RunninSight Android Multi-Module Architecture Plan

## 1) Clean Architecture boundaries

This project is organized in four architectural rings:

- **App/Composition (`:app`)**: navigation graph, DI wiring, startup orchestration.
- **Presentation (`:feature:*`)**: UI, ViewModel, UI state/events, feature-specific user flows.
- **Domain (`:domain`)**: entities, repository contracts, use cases, domain errors/policies.
- **Data (`:data`)**: repository implementations, remote/local data sources, mappers.

Dependency direction is always inward toward domain abstractions.

## 2) Module structure

```text
:app
:domain
:data
:feature:auth
:feature:tracking
:feature:analysis
:feature:history
```

### Dependency graph (strict)

```text
:app -> :feature:auth
:app -> :feature:tracking
:app -> :feature:analysis
:app -> :feature:history
:app -> :data

:feature:* -> :domain
:data -> :domain
:domain -> (no project module)
```

Rules enforced by this structure:

1. Feature modules depend **only** on `:domain` among project modules.
2. `:data` implements interfaces declared in `:domain`.
3. `:domain` is pure Kotlin/JVM and has no Android framework dependency.

## 3) Package structure by module

## `:domain` (pure business logic)

```text
com.runninsight.domain
├── model
│   ├── RunSession.kt
│   ├── RunRecord.kt
│   ├── OcrAnalysisResult.kt
│   └── UserProfile.kt
├── repository
│   ├── AuthRepository.kt
│   ├── RunTrackerRepository.kt
│   ├── TextRecognitionRepository.kt
│   └── RunHistoryRepository.kt
├── usecase
│   ├── auth
│   │   ├── SignInWithGoogleUseCase.kt
│   │   └── SignOutUseCase.kt
│   ├── tracking
│   │   ├── StartRunTrackingUseCase.kt
│   │   ├── UpdateRunLocationUseCase.kt
│   │   ├── StopRunTrackingUseCase.kt
│   │   └── PersistRunUseCase.kt
│   ├── analysis
│   │   └── AnalyzeRunTextUseCase.kt
│   └── history
│       ├── GetRunHistoryUseCase.kt
│       └── DeleteRunRecordUseCase.kt
└── common
    ├── DomainError.kt
    └── Result.kt
```

## `:data` (implementations)

```text
com.runninsight.data
├── repository
│   ├── AuthRepositoryImpl.kt                // implements domain AuthRepository
│   ├── RunTrackerRepositoryImpl.kt          // implements domain RunTrackerRepository
│   ├── TextRecognitionRepositoryImpl.kt     // implements domain TextRecognitionRepository
│   └── RunHistoryRepositoryImpl.kt          // implements domain RunHistoryRepository
├── datasource
│   ├── remote
│   │   ├── auth
│   │   │   └── GoogleOAuthDataSource.kt
│   │   ├── map
│   │   │   └── NaverMapTrackingDataSource.kt
│   │   └── ml
│   │       └── MlKitTextRecognitionDataSource.kt
│   └── local
│       └── history
│           ├── room
│           │   ├── RunHistoryDatabase.kt
│           │   ├── RunHistoryDao.kt
│           │   └── RunHistoryEntity.kt
│           └── firestore
│               └── RunHistoryFirestoreDataSource.kt
├── mapper
│   ├── AuthMapper.kt
│   ├── RunMapper.kt
│   └── AnalysisMapper.kt
└── di
    └── DataModule.kt
```

## `:feature:auth`

```text
com.runninsight.feature.auth
├── ui
│   ├── login/LoginScreen.kt
│   └── components/
├── presentation
│   ├── LoginViewModel.kt
│   ├── LoginUiState.kt
│   └── LoginUiEvent.kt
└── navigation
    └── AuthRoute.kt
```

## `:feature:tracking`

```text
com.runninsight.feature.tracking
├── ui
│   ├── tracking/TrackingScreen.kt
│   └── components/MapOverlay.kt
├── presentation
│   ├── TrackingViewModel.kt
│   ├── TrackingUiState.kt
│   └── TrackingUiEvent.kt
└── navigation
    └── TrackingRoute.kt
```

## `:feature:analysis`

```text
com.runninsight.feature.analysis
├── ui
│   ├── analysis/AnalysisScreen.kt
│   └── components/
├── presentation
│   ├── AnalysisViewModel.kt
│   ├── AnalysisUiState.kt
│   └── AnalysisUiEvent.kt
└── navigation
    └── AnalysisRoute.kt
```

## `:feature:history`

```text
com.runninsight.feature.history
├── ui
│   ├── history/HistoryScreen.kt
│   └── components/
├── presentation
│   ├── HistoryViewModel.kt
│   ├── HistoryUiState.kt
│   └── HistoryUiEvent.kt
└── navigation
    └── HistoryRoute.kt
```

## `:app` (composition root)

```text
com.runninsight.app
├── navigation
│   ├── AppNavHost.kt
│   └── Destinations.kt
├── di
│   └── AppModule.kt
└── MainActivity.kt
```

## 4) Feature-to-technology mapping

- **Google OAuth Login**
  - Domain: `SignInWithGoogleUseCase`, `AuthRepository`
  - Data: Google Sign-In API adapter in `GoogleOAuthDataSource`
  - Presentation: `feature:auth` ViewModel + state/events

- **Naver Map run tracking**
  - Domain: `StartRunTrackingUseCase`, `UpdateRunLocationUseCase`, `StopRunTrackingUseCase`
  - Data: Naver Map SDK adapter in `NaverMapTrackingDataSource`
  - Presentation: `feature:tracking`

- **MLKit text recognition analysis**
  - Domain: `AnalyzeRunTextUseCase`, `TextRecognitionRepository`
  - Data: `MlKitTextRecognitionDataSource`
  - Presentation: `feature:analysis`

- **Run history persistence (Room or Firestore)**
  - Domain: `GetRunHistoryUseCase`, `PersistRunUseCase`, `DeleteRunRecordUseCase`
  - Data: Room and/or Firestore implementation behind `RunHistoryRepositoryImpl`
  - Presentation: `feature:history`

## 5) UI state and event handling policy

Every feature follows MVI-like separation:

- `UiState`: immutable screen render state.
- `UiEvent`: one-off user intents/actions.
- `ViewModel`: maps events to use case calls and produces new state.

No domain/data object should directly drive composables without mapping to `UiState`.

## 6) Unit testing strategy (default)

Each module includes unit tests by default:

- `:domain`: UseCase tests with fake repositories.
- `:data`: repository implementation tests with fake/spy data sources.
- `:feature:*`: ViewModel tests for state transition and event handling.

Suggested package pattern:

```text
src/test/java/com/runninsight/<module>/...
```

## 7) Dependency violations found and fixed

### Violation risk before fix

- Feature modules had no explicit dependency on `:domain`, making use case-driven design unenforced.
- `:app` was not explicitly composing feature/data modules.

### Fix applied

- Added `implementation(project(":domain"))` to all feature modules.
- Added app composition dependencies on all feature modules and `:data`.
- Added `implementation(project(":domain"))` to `:data` for repository contract implementation.

This now enforces the required dependency direction at build level.
