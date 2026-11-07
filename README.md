# Aiming to become a legend

[![Minecraft 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen.svg)]()
[![Forge](https://img.shields.io/badge/Modloader-Forge%2047.3.0+-orange.svg)]()
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
  1. Completed Advancements (visible advancements only by default)
  2. Unique Mob Types Killed
  3. Unique Food / Consumable Items Eaten
  
  You permanently gain:
  - **Max Health:** `+3.0` (+1.5 hearts)
  - **Attack Damage:** `+0.3`
  - **Armor:** `+0.5`
  - **Armor Toughness:** `+0.5`

- **Unlimited Ceiling (No Upper Limit):**  
  There is no cap. The more you explore and conquer, the stronger you become.

- **High-Performance & Event-Driven:**  
  Zero per-tick calculations. Data is updated strictly when advancements are earned, mobs are slain, or food is consumed.

- **Legend Notification:**  
  Whenever your tier increases, a message logs: *"One step closer to becoming a legend."*

- **Commands:**  
  - `/legend` or `/legend status` - Check your current tier, counts (advancements, mobs, foods), and active attribute bonuses.
  - `/legend status <player>` - Check another player's progress (Operator permission level 2).
  - `/legend sync` - Recalculate and synchronize attributes.

### Installation
1. Install [Minecraft Forge](https://files.minecraftforge.net/) for 1.20.1 (recommended 47.3.0 or higher).
2. Download the latest jar file `aiming_to_become_a_legend-1.0.0-alpha.jar` from the release build.
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
  1. 達成した実績数（レシピ解除等の内部進捗を除外した目に見える実績）
  2. 倒したモブの種類数（ユニークなエンティティ種別）
  3. 食べたアイテムの種類数（ユニークな食品アイテム種別）
  
  **1段階（Tier）ごとの上昇値:**
  - **最大体力 (Max HP):** `+3.0`（ハート1.5個分）
  - **攻撃力 (Attack Damage):** `+0.3`
  - **防御力 (Armor):** `+0.5`
  - **防具強度 (Armor Toughness):** `+0.5`

- **青天井の成長上限:**  
  ステータス上限は一切ありません。世界中を探索し尽くすほど強大な存在へと近づきます。

- **超高パフォーマンス設計 (イベント駆動):**  
  毎tickの重い走査や計算は一切行いません。実績解除時・モブ討伐時・アイテム摂食時のみ差分を判定・加算するため、サーバー負荷は極小です。

- **通知メッセージ:**  
  新たな段階に到達するたびに「**伝説へ一歩近づいた**」というメッセージが表示されます。

- **コマンド:**  
  - `/legend` または `/legend status` - 現在の段階、各項目（実績・モブ・食事）の達成数、付与されているステータスボーナスを表示します。
  - `/legend status <player>` - 他のプレイヤーの進行状況を確認します（OP権限レベル2以上）。
  - `/legend sync` - 属性バフを再計算・同期します。

### 導入方法
1. Minecraft 1.20.1 用の [Minecraft Forge](https://files.minecraftforge.net/) (47.3.0以上推奨) をインストールします。
2. ビルドされた `aiming_to_become_a_legend-1.0.0-alpha.jar` をダウンロードします。
3. `.minecraft/mods` ディレクトリに本JARファイルを配置します。
4. ゲームを起動して伝説への道を歩み始めましょう！

---

## License / ライセンス
This project is licensed under the [GNU General Public License v3.0](LICENSE).  
本プロジェクトは GNU General Public License v3.0 のもとで公開されています。
