# Changelog / 変更履歴 (NeoForge 1.21.1)

All notable changes to this project will be documented in this file.  
本プロジェクトのすべての重要な変更はこのファイルに記録されます。

---

## [1.3.2-beta] - 2026-11-24

### Added / 追加
- **EN:**
  - Initial port to **Minecraft 1.21.1 (NeoForge)** with full feature parity to 1.20.1 Forge `v1.3.2-beta`.
  - Updated attribute modifier IDs to 1.21.1 `ResourceLocation` format and `DataComponents.FOOD` item consumption detection.
  - Integrated with Apothic Attributes 1.21.1 (`apothic_attributes:experience_gained`, with fallback to `attributeslib:experience_gained` and internal multiplier).
  - Pure universal damage reduction via NeoForge `LivingDamageEvent.Pre` (`EventPriority.LOW`) and 1.21.1 `EnchantmentHelper.getDamageProtection`.
  - Growth & Legend notifications ("Feeling yourself growing stronger." / "One step closer to becoming a legend.") with mutual exclusion on multiples of 5.
- **JA:**
  - **Minecraft 1.21.1 (NeoForge)** への完全移植版をリリース（1.20.1 Forge `v1.3.2-beta` と同等の全機能を搭載）。
  - 1.21.1 の `ResourceLocation` ベースの属性修飾子および `DataComponents.FOOD` による摂食判定に対応。
  - Apothic Attributes 1.21.1 (`apothic_attributes:experience_gained`) との連携に対応（旧IDおよび独自属性フォールバックも完備）。
  - NeoForge の `LivingDamageEvent.Pre` (`EventPriority.LOW`) による完全汎用全ダメージ軽減および 1.21.1 対応のエンチャント防御扱い軽減を実装。
  - 成長通知（「成長を感じる」）および5の倍数到達時の伝説通知（「伝説へ一歩近づいた」）を実装。
