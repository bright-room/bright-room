---
description: ビルド・デバッグ・CI 関連の作業時に適用
globs: ["build.gradle.kts", "settings.gradle.kts", "gradle/**", ".github/**"]
---

# Build & Debug

- ローカル開発・デバッグには `./gradlew jsBrowserDevelopmentRun` (JS ターゲット) を使う。WASM ターゲットはデバッグが困難。
- 本番デプロイビルドは `./gradlew wasmJsBrowserDistribution`
- 依存バージョンは `gradle/libs.versions.toml` で一元管理。`strictly` 指定で固定されている。
- セキュリティ脆弱性のある npm transitive dependency は `build.gradle.kts` の `YarnRootExtension.resolution()` で上書き。
- CI は GitHub Actions: PR/merge 時に `check`、tag push 時にデプロイ。
