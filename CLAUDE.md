# CLAUDE.md

このファイルは Claude Code (AI コーディングアシスタント) がプロジェクトのコンテキストを理解するためのものです。
Claude Code がこのリポジトリで作業する際に、ここに記載された情報を参照してプロジェクトの構成・規約・ワークフローに沿った提案やコード生成を行います。

## Project Overview

bright-room organization の公式ホームページ。Kotlin Multiplatform + Compose Multiplatform で構築された SPA。

- **Language:** Kotlin (Multiplatform)
- **Framework:** Compose Multiplatform (Material3)
- **Build:** Gradle (Kotlin DSL) / Java 25
- **Targets:** js(IR), wasmJs
- **Deploy:** GitHub Pages (wasmJs build)

## Build & Dev Commands

```bash
# ローカル開発サーバー (デバッグ用 - JS ターゲット推奨)
./gradlew jsBrowserDevelopmentRun

# コードフォーマットチェック
./gradlew spotlessCheck

# コードフォーマット自動修正
./gradlew spotlessApply

# CI チェック (PR/merge 時に実行される)
./gradlew check

# 本番ビルド (GitHub Pages デプロイ用)
./gradlew wasmJsBrowserDistribution
```

## Project Structure

```
src/
  commonMain/kotlin/net/brightroom/homepage/
    app/          # App, Theme, AppViewModel
    components/   # Footer, Navigation 等の共通コンポーネント
    data/         # ContentLoader, データモデル
    screens/      # 各セクション (hero, about, members, projects, etc.)
    shared/       # layout, lib, theme ユーティリティ
  commonMain/composeResources/
    drawable/     # SVG/PNG アセット
    files/        # JSON データ (members, projects, stats, techstack)
    font/         # Noto Sans JP フォント
    values/       # i18n 文字列リソース (ja/en)
  jsMain/         # JS プラットフォーム固有コード
  wasmJsMain/     # WASM プラットフォーム固有コード
  webMain/        # Web エントリーポイント (Main.kt)
```

## Code Conventions

- **Formatter:** ktlint via Spotless - コミット前に `./gradlew spotlessApply` を実行
- **Indent:** 4 spaces, LF line endings
- **Architecture:** MVVM (AppViewModel + StateFlow + CompositionLocal)
- **Composable 命名:** PascalCase (e.g., `HeroSection`, `Footer`)
- **Package:** `net.brightroom.homepage.*`
- **i18n:** 日本語がベース言語、英語は `values-en/`

## CI/CD

- PR / merge to main: `./gradlew check`
- Tag push (v*): wasmJs ビルド -> GitHub Pages デプロイ -> GitHub Release 作成
- リリースノートのラベル: `Kind: Feature`, `Kind: Enhancement`, `Kind: Bug Fix`, `Kind: Dependencies`, `Impact: Breaking`

## Important Notes

- デバッグ時は JS ターゲット (`jsBrowserDevelopmentRun`) を使用する。WASM はデバッグが困難。
- 依存バージョンは `gradle/libs.versions.toml` で `strictly` 管理されている。
- セキュリティ脆弱性のある transitive dependency は `YarnRootExtension.resolution()` で上書き。
