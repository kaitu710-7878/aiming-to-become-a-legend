# Aiming to become a legend

[![Minecraft 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen.svg)]()
[![Forge](https://img.shields.io/badge/Modloader-Forge%2047.3.0+-orange.svg)]()
[![Release](https://img.shields.io/badge/Release-Beta-blueviolet.svg)]()
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

[English](#english) | [日本語](#日本語-japanese)

---

## English

### Overview
**Aiming to become a legend** is a lightweight, performance-friendly Minecraft Forge mod for 1.20.1.  
Every action in your adventure—completing advancements, hunting diverse mobs, and savoring new food delicacies—makes you fundamentally stronger.

### Features
- **Permanent Attribute Progression:**  
  For every **5 points** gained from the sum of:
  1. Completed Advancements (ignoring root category items by default to prevent dimension double-counting)
  2. Unique Mob Types Killed
  3. Unique Food / Consumable Items Eaten
  
  You permanently gain:
  - **Max Health:** `+2.0` (+1 heart)
  - **Attack Damage:** `+0.3`
  - **Armor:** `+0.5`
  - **Armor Toughness:** `+0.5`
  - **Experience Multiplier:** `+0.1` (+10%, directly integrates with Apothic Attributes `attributeslib:experience_gained`)
  - **Enchantment-like Damage Reduction:** `+1.0%` (capped at 80% total with armor enchantments, ineffective if armor already hits the cap)
  - **Pure Universal Damage Reduction:** `+0.1%` (stacks multiplicatively with enchantment reduction; mitigates all damage types including armor-piercing and enchantment-piercing damage via pure universal reduction; capped at 80%)

- **Unlimited Ceiling (No Upper Limit):**  
  There is no cap on milestones and base tiers. The more you explore and conquer, the stronger you become.

- **High-Performance & Event-Driven:**  
  Zero per-tick calculations. Data is updated strictly when advancements are earned, mobs are slain, food is consumed, or damage/XP events occur.

- **Growth & Legend Notifications:**  
  - Whenever you gain 1 point, you see: *"Feeling yourself growing stronger."*
  - Whenever you reach a milestone multiple of 5 and gain a buff tier, you see: *"One step closer to becoming a legend."* (point notification is suppressed).

- **Commands:**  
  - `/legend` or `/legend status` - Check your current tier, counts (advancements, mobs, foods), and active attribute bonuses.
  - `/legend status <player>` - Check another player's progress (Operator permission level 2).
  - `/legend sync` - Recalculate and synchronize attributes.

### Installation
1. Install [Minecraft Forge](https://files.minecraftforge.net/) for 1.20.1 (recommended 47.3.0 or higher).
2. Download the latest jar file `aiming_to_become_a_legend-1.3.2-beta.jar` from the release build.
3. Place the jar file into your `.minecraft/mods` folder.
4. Launch the game and embark on your legendary journey!

---

## 日本語 (Japanese)

### 概要
**Aiming to become a legend** は、Minecraft 1.20.1 (Forge) 向けの超軽量・高パフォーマンスな成長系MODです。  
冒険の中で「実績を達成する」「多種多様なモブを討伐する」「食べたことのないアイテムを食す」という行動を積み重ねることで、プレイヤーの基本能力が永久に強化されていきます。

### 主な特徴
- **永続的なステータス強化:**  
  以下の3つの合算値が **5増えるごと** に永続バフを獲得します。
  1. 達成した実績数（ルート親実績やレシピ解放を除外し、純粋な実績のみを正確にカウント）
  2. 倒したモブの種類数（ユニークなエンティティ種別）
  3. 食べたアイテムの種類数（ユニークな食品アイテム種別）
  
  **1段階（Tier）ごとの上昇値:**
  - **最大体力 (Max HP):** `+2.0`（ハート1個分）
  - **攻撃力 (Attack Damage):** `+0.3`
  - **防御力 (Armor):** `+0.5`
  - **防具強度 (Armor Toughness):** `+0.5`
  - **獲得経験値倍率 (XP Multiplier):** `+0.1` (+10%, Apothic Attributesの `attributeslib:experience_gained` と直接連動)
  - **「エンチャント防御」扱いの全ダメージ軽減率:** `+1.0%` (防具エンチャントと合算して最大80%上限、防具だけで到達している場合は無効)
  - **純粋汎用全ダメージ軽減率:** `+0.1%` (エンチャント防御と重複乗算軽減。貫通ダメージを含むあらゆるダメージを完全汎用ロジックで無条件に軽減、最大80%上限)

- **青天井の成長上限:**  
  合算値および段階（Tier）に上限はありません。世界中を探索し尽くすほど強大な存在へと近づきます。

- **超高パフォーマンス設計 (イベント駆動):**  
  毎tickの重い走査や計算は一切行いません。実績解除時・モブ討伐時・アイテム摂食時のみ差分を判定・加算するため、サーバー負荷は極小です。

- **成長および伝説通知メッセージ:**  
  - 合算値が1増えるごとに「**成長を感じる**」と表示されます。
  - 5の倍数に達して段階（Tier）が上がった際は「**伝説へ一歩近づいた**」のみが表示され、「成長を感じる」は出力されません。

- **コマンド:**  
  - `/legend` または `/legend status` - 現在の段階、各項目（実績・モブ・食事）の達成数、付与されているステータスボーナスを表示します。
  - `/legend status <player>` - 他のプレイヤーの進行状況を確認します（OP権限レベル2以上）。
  - `/legend sync` - 属性バフを再計算・同期します。

### 導入方法
1. Minecraft 1.20.1 用の [Minecraft Forge](https://files.minecraftforge.net/) (47.3.0以上推奨) をインストールします。
2. ビルドされた `aiming_to_become_a_legend-1.3.2-beta.jar` をダウンロードします。
3. `.minecraft/mods` ディレクトリに本JARファイルを配置します。
4. ゲームを起動して伝説への道を歩み始めましょう！

---

## License / ライセンス
This project is licensed under the [GNU General Public License v3.0](LICENSE).  
本プロジェクトは GNU General Public License v3.0 のもとで公開されています。
