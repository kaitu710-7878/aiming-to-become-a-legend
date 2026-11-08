# Configuration Guide / コンフィグ設定ガイド

`aiming_to_become_a_legend` provides server-side configuration to customize the buffs, milestones, and notification preferences.  
`aiming_to_become_a_legend` では、バフの上昇量や合算値の閾値、通知の表示方法などをカスタマイズ可能なサーバー設定ファイルを提供しています。

Location / 設定ファイル配置場所: `config/aiming_to_become_a_legend-server.toml` (or inside world save `saves/<world>/serverconfig/aiming_to_become_a_legend-server.toml`)

---

## English

| Parameter | Type | Default | Range | Description |
|---|---|---|---|---|
| `milestoneStep` | Integer | `5` | 1 ~ 1000 | Combined milestone points (advancements + unique mobs killed + unique foods eaten) required to obtain 1 buff tier. |
| `healthPerTier` | Double | `2.0` | 0.0 ~ 1000.0 | Max health increase gained per buff tier (2.0 = 1 heart). |
| `damagePerTier` | Double | `0.3` | 0.0 ~ 1000.0 | Attack damage increase gained per buff tier. |
| `armorPerTier` | Double | `0.5` | 0.0 ~ 1000.0 | Armor points gained per buff tier. |
| `armorToughnessPerTier` | Double | `0.5` | 0.0 ~ 1000.0 | Armor toughness points gained per buff tier. |
| `experienceMultiplierPerTier` | Double | `0.1` | 0.0 ~ 100.0 | Experience multiplier gained per buff tier (0.1 = +10%). Directly modifies Apothic Attributes' `attributeslib:experience_gained` when present. |
| `enchantDamageReductionPerTier` | Double | `0.01` | 0.0 ~ 1.0 | Enchantment-like damage reduction gained per buff tier (0.01 = +1%). Capped at 80% total with armor enchantments, and ineffective if armor already hits the cap. |
| `legendDamageReductionPerTier` | Double | `0.001` | 0.0 ~ 1.0 | Pure universal non-enchantment damage reduction gained per buff tier (0.001 = +0.1%). Unconditionally mitigates all damage types (including armor-piercing and enchantment-piercing damage). |
| `maxLegendDamageReduction` | Double | `0.80` | 0.0 ~ 1.0 | Maximum damage reduction cap for unique legend damage reduction (0.80 = 80%). |
| `onlyVisibleAdvancements` | Boolean | `true` | true / false | If `true`, only advancements with visible display icons are counted (filters out internal recipe unlocks, etc.). |
| `ignoreRootAdvancements` | Boolean | `true` | true / false | If `true`, ignores root/parent category advancements (e.g. initial tab icons like 'Nether', 'Story', etc.) to prevent double-counting upon reaching new dimensions. |
| `showBuffMessage` | Boolean | `true` | true / false | Whether to display the notification message ("One step closer to becoming a legend.") when reaching a new buff tier. |
| `showPointMessage` | Boolean | `true` | true / false | Whether to display the notification message ("Feeling yourself growing stronger.") when gaining 1 point, except when a buff tier is reached. |
| `detailedBuffMessage` | Boolean | `false` | true / false | If `true`, shows detailed counts (tier, advancements, mobs, foods) in the notification message. |
| `messagePosition` | Enum | `"CHAT"` | `"CHAT"`, `"ACTION_BAR"` | Position where the notification message appears. |

---

## 日本語 (Japanese)

| パラメータ | データ型 | デフォルト値 | 設定範囲 | 説明 |
|---|---|---|---|---|
| `milestoneStep` | 整数 (Integer) | `5` | 1 ~ 1000 | バフの1段階を獲得するために必要な合算値（実績数 + 討伐モブ種類数 + 摂食アイテム種類数）の刻み幅。 |
| `healthPerTier` | 小数 (Double) | `2.0` | 0.0 ~ 1000.0 | 1段階ごとに上昇する最大体力（2.0 = ハート1個分）。 |
| `damagePerTier` | 小数 (Double) | `0.3` | 0.0 ~ 1000.0 | 1段階ごとに上昇する攻撃力。 |
| `armorPerTier` | 小数 (Double) | `0.5` | 0.0 ~ 1000.0 | 1段階ごとに上昇する防御力。 |
| `armorToughnessPerTier` | 小数 (Double) | `0.5` | 0.0 ~ 1000.0 | 1段階ごとに上昇する防具強度。 |
| `experienceMultiplierPerTier` | 小数 (Double) | `0.1` | 0.0 ~ 100.0 | 1段階ごとに上昇する獲得経験値倍率（0.1 = +10%）。Apothic Attributes 導入時は公式の `attributeslib:experience_gained` 属性に直接連動。 |
| `enchantDamageReductionPerTier` | 小数 (Double) | `0.01` | 0.0 ~ 1.0 | 1段階ごとに上昇する「エンチャント防御」扱いの全ダメージ軽減率（0.01 = +1%）。防具エンチャントと合算して最大80%上限。防具のみで80%上限に達している場合は無効。 |
| `legendDamageReductionPerTier` | 小数 (Double) | `0.001` | 0.0 ~ 1.0 | 1段階ごとに上昇する純粋汎用全ダメージ軽減率（0.001 = +0.1%）。貫通ダメージを含むあらゆるダメージを無条件に汎用ロジックで軽減。 |
| `maxLegendDamageReduction` | 小数 (Double) | `0.80` | 0.0 ~ 1.0 | 独自全ダメージ軽減率の最大上限キャップ（0.80 = 80%）。 |
| `onlyVisibleAdvancements` | 真偽値 (Boolean) | `true` | true / false | `true` の場合、進捗画面に表示される実績のみをカウント対象とします（内部レシピ解放などのカウント混入を防止）。 |
| `ignoreRootAdvancements` | 真偽値 (Boolean) | `true` | true / false | `true` の場合、タブの起点となる親実績（「ネザー」「冒険」「農業」などの初期項目）をカウントから除外します（ネザー到達時に親項目と到達実績の2重カウントが発生する問題を防止）。 |
| `showBuffMessage` | 真偽値 (Boolean) | `true` | true / false | 新しい段階に到達した際、「伝説へ一歩近づいた」という通知メッセージを表示するかどうか。 |
| `showPointMessage` | 真偽値 (Boolean) | `true` | true / false | 合算値が1増えた際、「成長を感じる」という通知メッセージを表示するかどうか（段階到達時は除外）。 |
| `detailedBuffMessage` | 真偽値 (Boolean) | `false` | true / false | `true` の場合、通知メッセージに現在の段階や実績/モブ/食べ物の各内訳詳細を含めます。 |
| `messagePosition` | 列挙型 (Enum) | `"CHAT"` | `"CHAT"`, `"ACTION_BAR"` | 通知メッセージの表示位置（チャット欄 または アクションバー）。 |
