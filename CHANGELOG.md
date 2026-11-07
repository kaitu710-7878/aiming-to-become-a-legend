# Changelog / 変更履歴

All notable changes to this project will be documented in this file.  
本プロジェクトのすべての重要な変更はこのファイルに記録されます。

---

## [1.0.0-alpha] - 2026-11-07

### Added / 追加
- **EN:** Initial release of `Aiming to become a legend`.
  - Permanent attribute buffs (Max HP +3.0, Attack Damage +0.3, Armor +0.5, Armor Toughness +0.5) granted for every 5 combined points of completed advancements, unique mob types killed, and unique food types eaten.
  - Unlimited ceiling (no upper limit).
  - Event-driven architecture: no per-tick calculations for optimal performance.
  - Notification message "One step closer to becoming a legend." when reaching a new buff tier.
  - Persistent data storage ensuring buffs and counts persist through death and respawn.
  - Added `/legend status` and `/legend sync` command to check current progress and buff attributes.
  - Bilingual support (Japanese & English) for messages and configuration.
- **JA:** `Aiming to become a legend` 初回リリース。
  - 「達成した実績数」「倒したモブの種類数」「食べたアイテムの種類数」の合算値が5増えるごとに、「最大HP +3」「攻撃力 +0.3」「防御力 +0.5」「防具強度 +0.5」の永続バフを付与。
  - 上限なしの青天井仕様。
  - イベント駆動型設計により、毎ティックの再計算処理を一切行わず抜群の軽量パフォーマンスを実現。
  - バフ獲得時に「伝説へ一歩近づいた」の通知メッセージを表示。
  - プレイヤーの死亡・リスポーン時にも安全にカウントとバフを引き継ぐ永続データ管理。
  - 現在の合算値・内訳・ステータスボーナスを確認できる `/legend status` コマンドを追加。
  - 日英2言語（日本語・英語）による完全ローカライズ対応。
