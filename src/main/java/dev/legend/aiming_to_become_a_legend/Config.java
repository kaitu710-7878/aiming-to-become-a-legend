package dev.legend.aiming_to_become_a_legend;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public final ForgeConfigSpec.IntValue milestoneStep;
    public final ForgeConfigSpec.DoubleValue healthPerTier;
    public final ForgeConfigSpec.DoubleValue damagePerTier;
    public final ForgeConfigSpec.DoubleValue armorPerTier;
    public final ForgeConfigSpec.DoubleValue armorToughnessPerTier;
    public final ForgeConfigSpec.DoubleValue experienceMultiplierPerTier;
    public final ForgeConfigSpec.DoubleValue enchantDamageReductionPerTier;
    public final ForgeConfigSpec.DoubleValue legendDamageReductionPerTier;
    public final ForgeConfigSpec.DoubleValue maxLegendDamageReduction;
    public final ForgeConfigSpec.BooleanValue onlyVisibleAdvancements;
    public final ForgeConfigSpec.BooleanValue ignoreRootAdvancements;
    public final ForgeConfigSpec.BooleanValue showBuffMessage;
    public final ForgeConfigSpec.BooleanValue showPointMessage;
    public final ForgeConfigSpec.BooleanValue detailedBuffMessage;
    public final ForgeConfigSpec.EnumValue<MessagePosition> messagePosition;

    public enum MessagePosition {
        CHAT,
        ACTION_BAR
    }

    static {
        Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }

    private Config(ForgeConfigSpec.Builder builder) {
        builder.comment("Aiming to become a legend - Configuration").push("general");

        milestoneStep = builder
                .comment("Points required (sum of advancements + unique mobs killed + unique foods eaten) to obtain 1 buff tier.")
                .translation("aiming_to_become_a_legend.config.milestoneStep")
                .defineInRange("milestoneStep", 5, 1, 1000);

        healthPerTier = builder
                .comment("Max Health gained per buff tier (2.0 = 1 heart).")
                .translation("aiming_to_become_a_legend.config.healthPerTier")
                .defineInRange("healthPerTier", 2.0, 0.0, 1000.0);

        damagePerTier = builder
                .comment("Attack Damage gained per buff tier.")
                .translation("aiming_to_become_a_legend.config.damagePerTier")
                .defineInRange("damagePerTier", 0.3, 0.0, 1000.0);

        armorPerTier = builder
                .comment("Armor points gained per buff tier.")
                .translation("aiming_to_become_a_legend.config.armorPerTier")
                .defineInRange("armorPerTier", 0.5, 0.0, 1000.0);

        armorToughnessPerTier = builder
                .comment("Armor Toughness points gained per buff tier.")
                .translation("aiming_to_become_a_legend.config.armorToughnessPerTier")
                .defineInRange("armorToughnessPerTier", 0.5, 0.0, 1000.0);

        experienceMultiplierPerTier = builder
                .comment("Experience multiplier gained per buff tier (0.1 = +10%).")
                .translation("aiming_to_become_a_legend.config.experienceMultiplierPerTier")
                .defineInRange("experienceMultiplierPerTier", 0.1, 0.0, 100.0);

        enchantDamageReductionPerTier = builder
                .comment("Enchantment-like damage reduction gained per buff tier (0.01 = +1%). Capped at 80% total with armor enchantments, and ineffective if armor already hits the cap.")
                .translation("aiming_to_become_a_legend.config.enchantDamageReductionPerTier")
                .defineInRange("enchantDamageReductionPerTier", 0.01, 0.0, 1.0);

        legendDamageReductionPerTier = builder
                .comment("Unique non-enchantment damage reduction gained per buff tier (0.001 = +0.1%). Stacks with enchantment reduction and reduces damage unconditionally (including armor-piercing and enchantment-piercing attacks).")
                .translation("aiming_to_become_a_legend.config.legendDamageReductionPerTier")
                .defineInRange("legendDamageReductionPerTier", 0.001, 0.0, 1.0);

        maxLegendDamageReduction = builder
                .comment("Maximum damage reduction limit for the unique legend damage reduction attribute (0.80 = 80%).")
                .translation("aiming_to_become_a_legend.config.maxLegendDamageReduction")
                .defineInRange("maxLegendDamageReduction", 0.80, 0.0, 1.0);

        onlyVisibleAdvancements = builder
                .comment("Whether to count only advancements that have display info (ignoring internal recipe unlocks, etc.).")
                .translation("aiming_to_become_a_legend.config.onlyVisibleAdvancements")
                .define("onlyVisibleAdvancements", true);

        ignoreRootAdvancements = builder
                .comment("Whether to ignore root/parent category advancements (e.g. initial tab icons like 'Nether', 'Story', etc.).")
                .translation("aiming_to_become_a_legend.config.ignoreRootAdvancements")
                .define("ignoreRootAdvancements", true);

        showBuffMessage = builder
                .comment("Whether to show the notification message ('One step closer to becoming a legend.') when reaching a new buff tier.")
                .translation("aiming_to_become_a_legend.config.showBuffMessage")
                .define("showBuffMessage", true);

        showPointMessage = builder
                .comment("Whether to show the notification message ('Feeling yourself growing stronger.') when gaining 1 point, except when a buff tier is reached.")
                .translation("aiming_to_become_a_legend.config.showPointMessage")
                .define("showPointMessage", true);

        detailedBuffMessage = builder
                .comment("Whether to show detailed breakdown (tier, advancements, mobs, foods) in the buff notification.")
                .translation("aiming_to_become_a_legend.config.detailedBuffMessage")
                .define("detailedBuffMessage", false);

        messagePosition = builder
                .comment("Where to display the buff notification: CHAT or ACTION_BAR.")
                .translation("aiming_to_become_a_legend.config.messagePosition")
                .defineEnum("messagePosition", MessagePosition.CHAT);

        builder.pop();
    }
}
