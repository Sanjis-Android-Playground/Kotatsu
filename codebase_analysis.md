# Kotatsu Repository Analysis

## 1. Project Overview
**Kotatsu** is a free and open-source manga reader application for Android. 
- **Purpose**: Allows users to read manga from online sources, download chapters for offline reading, and track their progress on services like MyAnimeList.
- **Current Status**: The project `README.md` indicates that **support is shutting down** due to external challenges.
- **Structure**: Single-module Android project (`:app`).

## 2. Technology Stack

### Languages & Core
- **Language**: Kotlin 2.2.10 (Latest)
- **Minimum SDK**: Android 6.0+ (API 23)
- **Concurrency**: Kotlin Coroutines & Flow

### Architecture
- **Pattern**: Likely MVVM (Model-View-ViewModel) given the use of Jetpack Lifecycle, LiveData/Flow, and Hilt.
- **Dependency Injection**: Hilt (Dagger).
- **Navigation**: Likely Fragment-based navigation (references to `Fragment` in dependencies).

### UI / Presentation
- **Framework**: Android Views (XML Layouts) & Material Components.
- **Image Loading**: Coil 3.3.0.
- **Custom Views**: Subsampling Scale Image View (for high-res manga pages).

### Data & Networking
- **Database**: Room 2.7.2 (SQLite abstraction).
- **Networking**: OkHttp 5.x, Retrofit (implied by Moshi/OkHttp usage).
- **Serialization**: Kotlinx Serialization & Moshi (JSON).
- **Parsers**: Uses a separate `kotatsu-parsers` library for source extraction.

## 3. Key Modules (Package Structure)
The source code at `app/src/main/kotlin/org/koitharu/kotatsu` is organized by **feature**:

| Package | Description |
| :--- | :--- |
| `reader` | Core reading interface (Viewpager, gestures, image rendering). |
| `download` | Logic for downloading chapters and managing offline content. |
| `parsers` | *External dependency*: Logic to scrape/parse various manga websites. |
| `scrobbling` / `tracker` | Integration with tracking services (MAL, AniList, Shikimori). |
| `favourites` / `history` | User library management. |
| `settings` | App configuration and preferences. |
| `core` | Shared utilities, base classes, and extensions. |
| `browser` | Internal web browser or source explorer. |
| `sync` | Data synchronization across devices. |

## 4. Build System
- **Gradle**: Uses Kotlin DSL (`build.gradle.kts` expected).
- **Version Catalog**: Dependencies are managed centrally in `gradle/libs.versions.toml`, ensuring consistent versions across the project.
- **CI/CD**: GitHub Actions workflows present in `.github/workflows`.

## 5. Important Notes
- **Parsers**: The actual scraping logic seems to be decoupled into a separate repository/artifact (`kotatsu-parsers`), which allows updating sources without updating the main app APK.
- **Legacy/Shutdown**: The project is in a sunset phase, so care should be taken if planning to fork or maintain it, as upstream updates will cease.
