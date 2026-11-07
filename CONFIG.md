# Configuration Guide / コンフィグ設定ガイド

`aiming_to_become_a_legend` provides server-side configuration to customize the buffs, milestones, and notification preferences.  
`aiming_to_become_a_legend` では、バフの上昇量や合算値の閾値、通知の表示方法などをカスタマイズ可能なサーバー設定ファイルを提供しています。

Location / 設定ファイル配置場所: `config/aiming_to_become_a_legend-server.toml` (or inside world save `saves/<world>/serverconfig/aiming_to_become_a_legend-server.toml`)

---

## English

| Parameter | Type | Default | Range | Description |
|---|---|---|---|---|
| `milestoneStep` | Integer | `5` | 1 ~ 1000 | Combined milestone points (advancements + unique mobs killed + unique foods eaten) required to obtain 1 buff tier. |
| `healthPerTier` | Double | `3.0` | 0.0 ~ 1000.0 | Max health increase gained per buff tier (3.0 = 1.5 hearts). |
| `damagePerTier` | Double | `0.3` | 0.0 ~ 1000.0 | Attack damage increase gained per buff tier. |
| `armorPerTier` | Double | `0.5` | 0.0 ~ 1000.0 | Armor points gained per buff tier. |
| `armorToughnessPerTier` | Double | `0.5` | 0.0 ~ 1000.0 | Armor toughness points gained per buff tier. |
| `onlyVisibleAdvancements` | Boolean | `true` | true / false | If `true`, only advancements with visible display icons are counted (filters out internal recipe unlocks, etc.). |
| `showBuffMessage` | Boolean | `true` | true / false | Whether to display the notification message ("One step closer to becoming a legend.") when reaching a new buff tier. |
| `detailedBuffMessage` | Boolean | `false` | true / false | If `true`, shows detailed counts (tier, advancements, mobs, foods) in the notification message. |
| `messagePosition` | Enum | `"CHAT"` | `"CHAT"`, `"ACTION_BAR"` | Position where the notification message appears. |

---

## 日本語 (Japanese)

| パラメータ | データ型 | デフォルト値 | 設定範囲 | 説明 |
|---|---|---|---|---|
| `milestoneStep` | 整数 (Integer) | `5` | 1 ~ 1000 | バフの1段階を獲得するために必要な合算値（実績数 + 討伐モブ種類数 + 摂食アイテム種類数）の刻み幅。 |
| `healthPerTier` | 小数 (Double) | `3.0` | 0.0 ~ 1000.0 | 1段階ごとに上昇する最大体力（3.0 = ハート1.5個分）。 |
| `damagePerTier` | 小数 (Double) | `0.3` | 0.0 ~ 1000.0 | 1段階ごとに上昇する攻撃力。 |
| `armorPerTier` | 小数 (Double) | `0.5` | 0.0 ~ 1000.0 | 1段階ごとに上昇する防御力。 |
| `armorToughnessPerTier` | 小数 (Double) | `0.5` | 0.0 ~ 1000.0 | 1段階ごとに上昇する防具強度。 |
| `onlyVisibleAdvancements` | 真偽値 (Boolean) | `true` | true / false | `true` の場合、進捗画面に表示される実績のみをカウント対象とします（内部レシピ解放などのカウント混入を防止）。 |
| `showBuffMessage` | 真偽値 (Boolean) | `true` | true / false | 新しい段階に到達した際、「伝説へ一歩近づいた」という通知メッセージを表示するかどうか。 |
| `detailedBuffMessage` | 真偽値 (Boolean) | `false` | true / false | `true` の場合、通知メッセージに現在の段階や実績/モブ/食べ物の各内訳詳細を含めます。 |
| `messagePosition` | 列挙型 (Enum) | `"CHAT"` | `"CHAT"`, `"ACTION_BAR"` | 通知メッセージの表示位置（チャット欄 または アクションバー）。 |
