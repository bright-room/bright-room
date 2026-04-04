# Project Context

このファイルはプロジェクト固有の情報を記載し、共通スキル（create-plan, update-plan, implement-plan, review, fix-review, triage）が参照する。

---

## コードベース調査ガイド

### モジュール構成の把握方法

シングルモジュール構成。以下のファイルを順に確認する:

1. `gradle/libs.versions.toml` - 依存バージョン一覧（`strictly` で固定）
2. `build.gradle.kts` - ビルド設定、ターゲット（js/wasmJs）、Spotless 設定、npm resolution
3. `settings.gradle.kts` - リポジトリ設定（シングルモジュール）

ソースセット構成:

| ソースセット | 役割 |
|-------------|------|
| `commonMain` | 共通コード（UI コンポーネント、データモデル、ViewModel） |
| `webMain` | Web エントリーポイント（`Main.kt`） |
| `jsMain` | JS プラットフォーム固有の実装（`expect/actual`） |
| `wasmJsMain` | WASM プラットフォーム固有の実装（`expect/actual`） |
| `commonTest` | 共通テスト |

### 既存パターンの調査手順

新機能実装時にリファレンスとすべきパターン:

- **画面セクション追加**: `src/commonMain/kotlin/net/brightroom/homepage/screens/` 配下の既存セクション（例: `hero/HeroSection.kt`, `about/AboutSection.kt`）を参照。1 セクション = 1 パッケージ + 1 Composable 関数
- **共有コンポーネント追加**: `src/commonMain/kotlin/net/brightroom/homepage/components/` 配下の既存コンポーネント（例: `StandardCard.kt`, `SectionContainer.kt`）を参照
- **データモデル追加**: `src/commonMain/kotlin/net/brightroom/homepage/data/` の既存モデル（例: `MemberData.kt`）を参照。JSON データは `src/commonMain/composeResources/files/` に配置し、`ContentLoader.kt` でロード
- **プラットフォーム固有実装**: `shared/lib/BrowserUtils.kt`（expect）と `jsMain`/`wasmJsMain` の actual 実装を参照
- **状態管理**: `AppViewModel` に `MutableStateFlow` でデータを保持し、`LocalAppViewModel`（CompositionLocal）経由で各 Composable からアクセス
- **テーマ・レイアウト**: `shared/theme/`（Colors, Fonts, Dimensions）と `shared/layout/Layout.kt` を参照

### テスト構成の確認方法

| テスト種類 | フレームワーク | 配置場所 | 実行コマンド |
|-----------|--------------|---------|-------------|
| ユニットテスト | kotlin-test | `src/commonTest/kotlin/` | `./gradlew check` |

- 現在テストファイルは未作成だが、`commonTest` ソースセットに kotlin-test 依存が設定済み
- 外部依存（Docker 等）は不要

## 実装ガイド

### ビルド・フォーマットコマンド

| 用途 | コマンド | 備考 |
|------|---------|------|
| フォーマット適用 | `./gradlew spotlessApply` | ktlint ベース。コード変更後に必ず実行 |
| フォーマットチェック | `./gradlew spotlessCheck` | CI で実行される |
| ローカル開発サーバー | `./gradlew jsBrowserDevelopmentRun` | JS ターゲット推奨（WASM はデバッグ困難） |
| CI チェック | `./gradlew check` | spotlessCheck + テスト |
| 本番ビルド | `./gradlew wasmJsBrowserDistribution` | GitHub Pages デプロイ用 |

### 言語固有の実装規約

- Composable 関数名は PascalCase（例: `HeroSection`, `MemberCard`）
- パッケージ: `net.brightroom.homepage.*`
- 文字列は `values/strings.xml`（日本語）と `values-en/strings.xml`（英語）に定義し、`Res.string.*` でアクセス。ハードコード禁止
- 画像・アイコンは `composeResources/drawable/` に配置し、`Res.drawable.*` でアクセス
- JSON データは `composeResources/files/` に配置し、`ContentLoader` 経由でロード
- クラスシグネチャのパラメータが 3 つ以上の場合はマルチラインにする
- ktlint で無効化されているルール: `package-name`, `enum-entry-name-case`, `function-naming`

### テスト配置ルール

| テスト種類 | 配置先 |
|-----------|-------|
| ユニットテスト | `src/commonTest/kotlin/net/brightroom/homepage/` 配下に、テスト対象と同じパッケージ構造で配置 |

### 実装順序

該当なし（シングルモジュール構成のため順序の制約なし）

### CI に委ねてよい項目

- `./gradlew check` は CI（GitHub Actions）で PR 時・merge 時に自動実行されるため、ローカルでは `spotlessApply` のみで十分
- wasmJs ビルド（`wasmJsBrowserDistribution`）は tag push 時に CI で実行

## レビューガイド

### ファイルパス → カテゴリマッピング

| 変更ファイルのパスパターン | 選択されるカテゴリ |
|--------------------------|-------------------|
| `src/commonMain/**/screens/**` | code |
| `src/commonMain/**/components/**` | code |
| `src/commonMain/**/shared/**` | architecture |
| `src/commonMain/**/app/**` | architecture |
| `src/commonMain/**/data/**` | code |
| `src/commonMain/composeResources/**` | docs |
| `src/{jsMain,wasmJsMain}/**` | code |
| `src/webMain/**` | architecture |
| `src/commonTest/**` | test |
| `build.gradle.kts`, `settings.gradle.kts` | build |
| `gradle/**` | build |
| `.github/**` | build |
| `CLAUDE.md`, `.claude/**` | docs |
| `README.md` | docs |

### カテゴリ別レビュー観点

#### architecture

- `AppViewModel` の責務が肥大化していないか（状態管理の単一責任）
- `CompositionLocal` の適切な使用（過剰なグローバル状態の回避）
- `expect/actual` の対称性（jsMain と wasmJsMain の両方に actual 実装があるか）
- ソースセット間の依存方向（commonMain → platform 固有コードへの依存がないか）

#### code

- Composable 関数が PascalCase で命名されているか
- 文字列がハードコードされていないか（`Res.string.*` を使用しているか）
- Material3 のコンポーネント・テーマトークンを適切に使用しているか
- レスポンシブ対応（`WindowSizeClass` に応じたレイアウト切り替え）

#### test

- テスト対象と同じパッケージ構造で `commonTest` に配置されているか
- kotlin-test の `@Test` アノテーションと assertion を使用しているか

#### security

- npm 依存の脆弱性（`YarnRootExtension.resolution()` での対応が必要か）
- 外部リソースの読み込みに関するセキュリティ（XSS 等）

#### docs

- i18n: 日本語（`values/strings.xml`）と英語（`values-en/strings.xml`）の両方が更新されているか
- JSON データファイルのスキーマ整合性

#### build

- `gradle/libs.versions.toml` のバージョンに `strictly` が付いているか
- npm transitive dependency の脆弱性対応（`resolution()` の追加が必要か）
- Spotless 設定の変更が意図的か

### セキュリティチェックリスト

| チェック項目 | 結果 | 備考 |
|-------------|:----:|------|
| npm transitive dependency の脆弱性 | ✅ / ❌ / N/A | `YarnRootExtension.resolution()` で対応 |
| 外部 URL のハードコード | ✅ / ❌ / N/A | XSS リスクの確認 |
| ユーザー入力の直接レンダリング | ✅ / ❌ / N/A | 静的サイトのため通常 N/A |

### テストカバレッジマトリクス（テンプレート）

| 対象 | 関数/メソッド | ユニットテスト | 備考 |
|------|-------------|:-------------:|------|

## プランテンプレート補足

### 影響範囲テーブル

| パッケージ | 影響 | 備考 |
|-----------|------|------|

パッケージ単位の例: `app`, `components`, `data`, `screens/*`, `shared/theme`, `shared/layout`, `shared/lib`

### ファイル構成の記述例

```
src/commonMain/kotlin/net/brightroom/homepage/
  screens/
    newfeature/
      NewFeatureSection.kt          # 新規セクション Composable
  components/
    NewComponent.kt                 # 共有コンポーネント
  data/
    NewData.kt                      # データモデル
src/commonMain/composeResources/
  files/newdata.json                # JSON データ
  values/strings.xml                # 日本語文字列（追加分）
  values-en/strings.xml             # 英語文字列（追加分）
```

### テスト戦略テーブル

| 対象 | テスト種類 | テストファイル | 検証内容 |
|------|-----------|--------------|---------|

### ドキュメント更新対象

| ドキュメント | 更新条件 |
|-------------|---------|
| `README.md` | 新機能・設定変更 |
| `CLAUDE.md` | アーキテクチャ変更・ビルドコマンド変更 |
| `.claude/rules/compose-conventions.md` | Compose 規約の変更 |
| `.claude/rules/build-and-debug.md` | ビルド・デバッグ手順の変更 |
| `.claude/rules/formatting.md` | フォーマット規約の変更 |
| `.claude/skills/references/project-context.md` | パッケージ構成変更・ビルドコマンド変更・レビュー観点変更 |

## ラベル・ワークフロー規約

### Issue/PR ラベルの prefix

| Prefix | 用途 | 例 |
|--------|------|-----|
| `Kind:` | 変更の種類 | `Kind: Feature`, `Kind: Enhancement`, `Kind: Bug Fix`, `Kind: Refactoring`, `Kind: Dependencies`, `Kind: Documentation`, `Kind: Tests` |
| `Priority:` | 優先度 | `Priority: Critical`, `Priority: High`, `Priority: Medium`, `Priority: Low` |
| `Impact:` | 影響範囲 | `Impact: Breaking` |
| `Need:` | 対応要求 | `Need: Help Wanted`, `Need: Discussion` |
| `Close:` | クローズ理由 | `Close: Duplicate`, `Close: WontFix`, `Close: Invalid` |
| `Meta:` | メタ情報 | `Meta: Release note ignored` |
| `Type:` | 質問等 | `Type: Question` |

### コード生成

該当なし
