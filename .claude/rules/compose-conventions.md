---
description: Compose UI コンポーネントの作成・編集時に適用
globs: ["src/commonMain/kotlin/**/*.kt"]
---

# Compose Conventions

- Composable 関数名は PascalCase (e.g., `HeroSection`, `MemberCard`)
- 画面セクションは `src/commonMain/kotlin/net/brightroom/homepage/screens/` 配下にパッケージ分け
- 共有コンポーネントは `components/` に配置
- 状態管理は `AppViewModel` + `StateFlow` + `CompositionLocal` パターン
- リソースアクセスは Compose Resources API (`Res.string.*`, `Res.drawable.*` 等) を使用
- 文字列は直接ハードコードせず `values/strings.xml` に定義して i18n 対応する
