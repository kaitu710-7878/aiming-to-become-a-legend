package com.github.yutak.aiming_to_become_a_legend;

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
    public final ForgeConfigSpec.BooleanValue onlyVisibleAdvancements;
    public final ForgeConfigSpec.BooleanValue showBuffMessage;
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
                .comment("Max Health gained per buff tier (3.0 = 1.5 hearts).")
                .translation("aiming_to_become_a_legend.config.healthPerTier")
                .defineInRange("healthPerTier", 3.0, 0.0, 1000.0);

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

        onlyVisibleAdvancements = builder
                .comment("Whether to count only advancements that have display info (ignoring internal recipe unlocks, etc.).")
                .translation("aiming_to_become_a_legend.config.onlyVisibleAdvancements")
                .define("onlyVisibleAdvancements", true);

        showBuffMessage = builder
                .comment("Whether to show the notification message when reaching a new buff tier.")
                .translation("aiming_to_become_a_legend.config.showBuffMessage")
                .define("showBuffMessage", true);

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
