# Changelog / 変更履歴

All notable changes to this project will be documented in this file.  
本プロジェクトのすべての重要な変更はこのファイルに記録されます。

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
- **EN:** Added new Attribute buffs per tier:
  - Experience Multiplier (+0.1 / +10% per tier).
  - Enchantment-like Damage Reduction (+1.0% per tier, capped at 80% total with armor enchantments).
  - Unique Non-Enchantment Damage Reduction (+0.1% per tier, capped at 80%).
- **JA:** 新たな属性（Attribute）バフを追加：
  - 獲得経験値倍率 (+0.1 / +10% / Tier)。
  - 「エンチャント防御」扱いの全ダメージ軽減率 (+1.0% / Tier、防具と合算して最大80%)。
  - 非エンチャント扱いの独自全ダメージ軽減率 (+0.1% / Tier、最大80%)。

---

## [1.0.0-alpha] - 2026-11-07

### Added / 追加
- **EN:** Initial release of `Aiming to become a legend`.
- **JA:** `Aiming to become a legend` 初回リリース。
