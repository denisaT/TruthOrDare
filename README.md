# Truth or Dare

A party game for Android: add the players, pick a pack, and the app takes turns asking each player for a truth or a dare.

Built with Kotlin and Jetpack Compose.

## Features

- Player setup with names, gender icons and turn order
- Question packs (Party, Dirty, Sexy), with a free Lite selection and full packs unlocked through Google Play Billing
- Two product flavors: `teen` and `adult`, each with its own content and application ID
- In-app language switching
- Questions loaded from CSV into a local Room database, so the game works offline

## Tech stack

| Area | Libraries |
| --- | --- |
| UI | Jetpack Compose, Material, Compose Destinations |
| Architecture | MVVM, repository pattern, Hilt dependency injection |
| Data | Room, Coroutines and Flow, OpenCSV |
| Monetization | Google Play Billing |
| Monitoring | Firebase Crashlytics and Analytics |

## Building

Open the project in Android Studio and run the `teenDebug` or `adultDebug` configuration, or from the command line:

```bash
./gradlew assembleTeenDebug
./gradlew testTeenDebugUnitTest
```

### Release signing

Signing details are never committed. To build a signed release, add these to `local.properties` (or set them as environment variables):

```properties
RELEASE_STORE_FILE=/path/to/keystore
RELEASE_STORE_PASSWORD=...
RELEASE_KEY_ALIAS=...
RELEASE_KEY_PASSWORD=...
```

## Roadmap

- Upgrade to Kotlin 2.x with the Compose compiler plugin, current AGP and a version catalog
- Move annotation processing from kapt to KSP
- Material 3 and edge-to-edge UI
- Fix keyboard flicker when adding players
- Broader test coverage for repositories and Compose screens
