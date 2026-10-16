# Liquid Client

An Android launcher project for Minecraft Bedrock Edition, built from scratch.

- **Application ID:** `com.sally.liquidclient`
- **Minimum Android:** 9 (API 28)
- **Compile SDK:** 35
- **Java:** 17
- **Current stage:** Stage 1 — launcher shell and private APK import

## Stage 1 scope

- Responsive dark launcher dashboard
- APK document picker
- Copies the selected `.apk` into app-private storage
- Shows app-private import storage usage
- GitHub Actions debug APK build

**Important:** importing an APK is not the same as installing or launching Minecraft. Game runtime integration, version isolation, and native module loading are not implemented in this stage.

## Build with GitHub Actions

1. Create a new GitHub repository.
2. Upload the contents of this project to the repository root.
3. Open **Actions** and enable workflows if GitHub asks.
4. Push a commit or manually run **Liquid Client Android Build**.
5. Open the successful workflow run and download the `liquid-client-debug` artifact.
6. Extract the artifact ZIP to get the debug APK.

The repository includes the standard Gradle wrapper, so the workflow uses the project's pinned Gradle version.

## Local build

Requirements: JDK 17 and Android SDK platform 35 with build-tools 35.0.0. The Gradle wrapper downloads Gradle 8.9 automatically.

```bash
./gradlew assembleDebug
```

Output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Planned stages

1. Launcher shell and APK import
2. App architecture, settings, and storage abstractions
3. Game package inspection and version records
4. Runtime integration research and prototype
5. Native module SDK integration
6. Worlds, resource packs, and additional management tools

Do not use pirated game packages. Import only game files you are legally entitled to use.
