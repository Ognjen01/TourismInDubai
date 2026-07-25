# TourismInDubai

An Android portfolio project demonstrating offline-first data loading, layered architecture and reusable Jetpack Compose UI.

[![Android CI](https://github.com/Ognjen01/TourismInDubai/actions/workflows/android.yml/badge.svg)](https://github.com/Ognjen01/TourismInDubai/actions/workflows/android.yml)

## Product scope

The app presents a topic-picker screen of the kind used during onboarding: the user is shown categories of interests — sounds, visuals and places — and picks the ones they want on their profile.

The interesting part is not the screen, it is what happens behind it. The catalogue comes from a remote endpoint that may be slow, unreachable or incomplete, and the screen has to stay useful in all three cases. This repository exists to show how that is structured and tested, not to be a complete travel product.

## Key user flows

1. **First launch, network available** — topics load from the API, render, and are written to the local database.
2. **Later launch, network unavailable** — the previously cached topics render immediately, with a banner stating the content is saved rather than fresh.
3. **Network unavailable, nothing cached** — an explicit error is shown with a retry action, rather than an empty screen.

## Architecture overview

```
ui/          Compose screens and components. Renders a single immutable state object.
 └─ mainscreen/  MainScreen (stateless) + MainScreenViewModel + MainScreenUiState

domain/      Pure Kotlin. No Android, Retrofit or Room types.
 ├─ model/       Topics, Topic, Visual
 ├─ DataResult   Success(data, origin) | Failure(error)
 └─ repository/  TopicsRepository interface

data/        Implementation details, hidden behind the domain interfaces.
 ├─ api/         Retrofit service + DTOs with null-safe mapping to domain models
 ├─ database/    Room entities and DAOs
 ├─ source/      TopicsRemoteDataSource / TopicsLocalDataSource interfaces + impls
 └─ repository/  DefaultTopicsRepository
```

The app separates presentation, data access and persistence concerns. The repository coordinates remote loading and local fallback, while the ViewModel exposes a single immutable UI state to Compose. This keeps offline behaviour testable and prevents transport models from leaking into the UI layer.

Both data sources sit behind interfaces, so the repository's behaviour can be tested with fakes and no DI container, emulator or network.

## Data and offline flow

```
                ┌──────────────┐  success   ┌─────────────┐
 ViewModel ───▶ │  Repository  │ ─────────▶ │   Remote    │
                └──────┬───────┘            └─────────────┘
                       │  failure                   │
                       ▼                            ▼ write-through
                ┌──────────────┐            ┌─────────────┐
                │  Local cache │ ◀──────────│    Room     │
                └──────┬───────┘            └─────────────┘
                       │
        cache hit ─────┴───── cache empty
             │                     │
   Success(origin=CACHE)   Failure(NoDataAvailable)
```

The network is the source of truth whenever it is reachable, and every successful response refreshes the cache. A failed request never overwrites what was already stored.

## Technical decisions and tradeoffs

| Decision | Why | What it costs |
|---|---|---|
| `DataResult` carries an `Origin` | The UI can distinguish fresh data from stale data and say so | One extra type between repository and ViewModel |
| Cache miss reported as `Failure`, not an empty success | An empty list and a failed load are different states and deserve different screens | Callers must handle a case the previous code silently ignored |
| Nullable DTO fields mapped to non-null domain models | Gson constructs non-null Kotlin fields as null on a partial payload, crashing far from the cause | Incomplete entries are dropped silently rather than surfaced |
| Fakes instead of a mocking framework | Tests describe behaviour and survive refactoring | Slightly more test code to maintain |
| Robolectric for Compose state tests | UI state rendering is verified in CI without an emulator | Slower than a pure JVM test, and no substitute for on-device testing |
| Cancellation rethrown before the generic catch | A cancelled scope must not be converted into a data error | Requires the two-catch pattern at every suspending boundary |
| `kapt` retained rather than migrating to KSP | Keeps this change focused on architecture | kapt is slower and needs JDK module flags; see Known limitations |

## Error and loading states

The screen is a pure function of `MainScreenUiState`:

| State | Rendering |
|---|---|
| `isLoading` | Centred progress indicator over the current content |
| `isShowingCachedData` | Banner explaining the content is saved, not fresh |
| `errorMessageRes != null` | Dialog with the message plus **Retry** and **OK** |
| content present | Sound, Visuals and Places sections |

Errors are carried as string resource ids rather than formatted strings, so the ViewModel holds no presentation copy and the app stays translatable.

## Getting started

Requirements: Android Studio, JDK 17, Android SDK 34.

```bash
git clone https://github.com/Ognjen01/TourismInDubai.git
cd TourismInDubai
./gradlew assembleDebug
```

JDK 17 is required. `kapt` on Kotlin 1.9 fails on JDK 21 and later; the JVM module flags it needs are already set in `gradle.properties`.

## Running tests

The test suite focuses on critical behaviour:

- remote success and Room cache persistence
- network failure with local fallback
- network failure with an empty cache
- DTO mapping of partial and malformed payloads
- ViewModel state mapping and error recovery
- Compose rendering for loading, content, offline and error states

Run locally:

```bash
./gradlew testDebugUnitTest lintDebug assembleDebug
```

GitHub Actions runs the same checks on every pull request.

## Screenshots and demo

> Not yet captured. To add them, run the app on an emulator, save the images into `docs/screenshots/`, and link them here.

## Known limitations

- **No paging or search.** The endpoint returns a small fixed catalogue, so neither is implemented.
- **The "next" button is inert.** This repository is a single screen by design; there is no onboarding flow behind it.
- **The cache never expires.** Cached topics are served whenever the network fails, with no age limit. A real product would attach a TTL and a manual refresh.
- **No instrumented tests.** Compose behaviour is covered through Robolectric on the JVM; nothing runs on a real device in CI.
- **`kapt` rather than KSP.** Room and Hilt both support KSP, which would be faster and would remove the JDK module flags in `gradle.properties`. Deliberately left as a follow-up so this change stays about architecture.
- **Depends on a third-party endpoint** outside my control, so the online path can break without any change to this repository.

## License

[MIT](LICENSE)
