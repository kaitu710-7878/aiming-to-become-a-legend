# Changelog / 変更履歴

All notable changes to this project will be documented in this file.  
本プロジェクトのすべての重要な変更はこのファイルに記録されます。

---

## [1.3.0-beta] - 2026-11-07

### Changed / 変更
- **EN:**
  - Transitioned release channel to **Beta**.
  - Adjusted Max Health bonus per tier from `3.0` to `1.0` (0.5 hearts per tier).
  - Filtered out root/parent category advancements (`advancement.getParent() == null`) by default (`ignoreRootAdvancements = true`). Fixes the issue where arriving in the Nether counted both the category root and the entry advancement (2 points).
- **JA:**
  - リリース種別を **Beta版** へ移行。
  - 1段階あたりの最大体力（HP）上昇量を `3.0` から `1.0`（ハート0.5個分）へ変更。
  - デフォルトで解放される各進捗タブの起点となる親実績（ルート進捗: `advancement.getParent() == null`）をカウント対象外とする仕様を追加（`ignoreRootAdvancements = true`）。ネザー初回到達時に親実績と到達実績の2重獲得となってしまう問題を解消。

---

## [1.2.0-alpha] - 2026-11-07

### Added / 追加
- **EN:**
  - Integrated with **Apothic Attributes** (`attributeslib:experience_gained`) for experience multiplier attribute progression.
  - Implemented **Pure Universal Damage Reduction**:
    - The non-enchantment damage reduction is now evaluated unconditionally across all damage sources (including void damage, Draconic Evolution chaos damage, Cataclysm abyssal curse & Harbinger laser, Warden sonic boom, Iron's Spells true/eldritch damage, etc.) using a clean universal algorithm without any hardcoded damage types or external code copying.
    - Set execution priority to `LOW` in `LivingDamageEvent` to guarantee proper mitigation after all upstream damage transformations.
- **JA:**
  - **Apothic Attributes** の公式経験値属性（`attributeslib:experience_gained`）との直接連携に対応。
  - **純粋汎用全ダメージ軽減ロジックの実装:**
    - 非エンチャント扱いの独自全ダメージ軽減率について、DamageSourceの除外ガードを撤廃。奈落、ドラコニックエヴォリューションの混沌ダメージ、カタクリズムの深淵の呪い/レーザー、ウォーデンの音波、ISSのエルドリッチ/trueダメージなど、あらゆるダメージを個別専用処理や外部コード流用なしの完全汎用ロジックで軽減可能に改良。
    - `LivingDamageEvent` の優先度を `LOW` に設定し、他MODによる計算・貫通処理後の最終実ダメージに対して正確に軽減率を適用。

---

## [1.1.0-alpha] - 2026-11-07

### Added / 追加
- **EN:** Added new Attribute buffs per tier (XP multiplier, enchant-like damage reduction, unique damage reduction).
- **JA:** 新たな属性バフを追加（経験値倍率、エンチャント軽減率、独自全ダメージ軽減率）。

---

## [1.0.0-alpha] - 2026-11-07

### Added / 追加
- **EN:** Initial release of `Aiming to become a legend`.
- **JA:** `Aiming to become a legend` 初回リリース。
