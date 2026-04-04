# bright-room

bright-room organization の公式ホームページ。
Kotlin Multiplatform + Compose Multiplatform で構築された SPA です。

## Tech Stack

| 項目 | 技術 |
|------|------|
| Language | Kotlin (Multiplatform) |
| Framework | Compose Multiplatform (Material3) |
| Build | Gradle (Kotlin DSL) / Java 25 |
| Targets | js(IR), wasmJs |
| Formatter | ktlint via Spotless |
| Deploy | GitHub Pages (wasmJs build) |

## Prerequisites

- Java 25+
- Git

## Getting Started

```bash
# リポジトリをクローン
git clone https://github.com/bright-room/bright-room.git
cd bright-room

# ローカル開発サーバーを起動 (JS ターゲット)
./gradlew jsBrowserDevelopmentRun
```

ブラウザで `http://localhost:8080` が自動的に開きます。

## Build Commands

| コマンド | 用途 |
|---------|------|
| `./gradlew jsBrowserDevelopmentRun` | ローカル開発サーバー起動 |
| `./gradlew spotlessCheck` | コードフォーマットチェック |
| `./gradlew spotlessApply` | コードフォーマット自動修正 |
| `./gradlew check` | CI チェック (フォーマット + テスト) |
| `./gradlew wasmJsBrowserDistribution` | 本番ビルド (GitHub Pages 用) |

## Project Structure

```
src/
  commonMain/kotlin/net/brightroom/homepage/
    app/          # App, Theme, AppViewModel
    components/   # Footer, Navigation 等の共通コンポーネント
    data/         # ContentLoader, データモデル
    screens/      # 各セクション (hero, about, members, projects 等)
    shared/       # layout, lib, theme ユーティリティ
  commonMain/composeResources/
    drawable/     # SVG/PNG アセット
    files/        # JSON データ (members, projects, stats, techstack)
    font/         # Noto Sans JP フォント
    values/       # i18n 文字列リソース (ja)
    values-en/    # i18n 文字列リソース (en)
  jsMain/         # JS プラットフォーム固有コード
  wasmJsMain/     # WASM プラットフォーム固有コード
  webMain/        # Web エントリーポイント
  commonTest/     # ユニットテスト
```

## CI/CD

| トリガー | アクション |
|---------|-----------|
| Pull Request | `./gradlew check` |
| main への merge | `./gradlew check` (キャッシュ seed) |
| Tag push (`v*`) | wasmJs ビルド → GitHub Pages デプロイ → GitHub Release 作成 → Milestone クローズ |
| Workflow dispatch | バージョンバリデーション → 署名付きタグ作成 |

## Contributing

1. リポジトリを Fork し、ローカルに Clone
2. `feature/xxx` や `fix/xxx` 形式でブランチを作成
3. コーディング規約に沿って開発（コミット前に `./gradlew spotlessApply` を実行）
4. Pull Request を作成

詳細は [CLAUDE.md](CLAUDE.md) の Code Conventions セクションを参照してください。

## License

All rights reserved. Copyright bright-room.
