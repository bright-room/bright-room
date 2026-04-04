---
description: Kotlin コードの編集・生成時に適用
globs: ["*.kt", "*.kts"]
---

# Formatting Rules

- ktlint via Spotless でフォーマットする。コード変更後は `./gradlew spotlessApply` を実行すること。
- インデント: 4 spaces
- 改行コード: LF
- 関数パラメータが 3 つ以上の場合はマルチラインにする
- ktlint で無効化されているルール: `package-name`, `enum-entry-name-case`, `function-naming`
