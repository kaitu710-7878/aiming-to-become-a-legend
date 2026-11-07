package com.github.yutak.aiming_to_become_a_legend;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LegendData {
    public static final String DATA_KEY = "AimingToBecomeALegend";
    private static final String TAG_ADVANCEMENTS = "Advancements";
    private static final String TAG_KILLED_MOBS = "KilledMobs";
    private static final String TAG_EATEN_ITEMS = "EatenItems";
    private static final String TAG_APPLIED_TIER = "AppliedTier";

    // Fixed UUIDs for attribute modifiers
    public static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a901");
    public static final UUID DAMAGE_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a902");
    public static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a903");
    public static final UUID ARMOR_TOUGHNESS_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a904");
    public static final UUID EXPERIENCE_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a905");
    public static final UUID ENCHANT_REDUCTION_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a906");
    public static final UUID LEGEND_REDUCTION_MODIFIER_UUID = UUID.fromString("b3e2b291-72da-4b8c-b9b5-68045607a907");

    public static CompoundTag getModData(ServerPlayer player) {
        CompoundTag persistent = player.getPersistentData();
        if (!persistent.contains(DATA_KEY, Tag.TAG_COMPOUND)) {
            persistent.put(DATA_KEY, new CompoundTag());
        }
        return persistent.getCompound(DATA_KEY);
    }

    public static Set<String> getSet(CompoundTag tag, String listKey) {
        Set<String> set = new HashSet<>();
        if (tag.contains(listKey, Tag.TAG_LIST)) {
            ListTag list = tag.getList(listKey, Tag.TAG_STRING);
            for (int i = 0; i < list.size(); i++) {
                set.add(list.getString(i));
            }
        }
        return set;
    }

    public static void saveSet(CompoundTag tag, String listKey, Set<String> set) {
        ListTag list = new ListTag();
        for (String s : set) {
            list.add(StringTag.valueOf(s));
        }
        tag.put(listKey, list);
    }

    public static boolean addAdvancement(ServerPlayer player, ResourceLocation id) {
        CompoundTag tag = getModData(player);
        Set<String> set = getSet(tag, TAG_ADVANCEMENTS);
        if (set.add(id.toString())) {
            saveSet(tag, TAG_ADVANCEMENTS, set);
            return true;
        }
        return false;
    }

    public static boolean addKilledMob(ServerPlayer player, ResourceLocation id) {
        CompoundTag tag = getModData(player);
        Set<String> set = getSet(tag, TAG_KILLED_MOBS);
        if (set.add(id.toString())) {
            saveSet(tag, TAG_KILLED_MOBS, set);
            return true;
        }
        return false;
    }

    public static boolean addEatenItem(ServerPlayer player, ResourceLocation id) {
        CompoundTag tag = getModData(player);
        Set<String> set = getSet(tag, TAG_EATEN_ITEMS);
        if (set.add(id.toString())) {
            saveSet(tag, TAG_EATEN_ITEMS, set);
            return true;
        }
        return false;
    }

    public static int getAppliedTier(ServerPlayer player) {
        CompoundTag tag = getModData(player);
        return tag.getInt(TAG_APPLIED_TIER);
    }

    public static void setAppliedTier(ServerPlayer player, int tier) {
        CompoundTag tag = getModData(player);
        tag.putInt(TAG_APPLIED_TIER, tier);
    }

    public static int getAdvancementCount(ServerPlayer player) {
        CompoundTag tag = getModData(player);
        return getSet(tag, TAG_ADVANCEMENTS).size();
    }

    public static int getKilledMobCount(ServerPlayer player) {
        CompoundTag tag = getModData(player);
        return getSet(tag, TAG_KILLED_MOBS).size();
    }

    public static int getEatenItemCount(ServerPlayer player) {
        CompoundTag tag = getModData(player);
        return getSet(tag, TAG_EATEN_ITEMS).size();
    }

    public static int getTotalPoints(ServerPlayer player) {
        CompoundTag tag = getModData(player);
        return getSet(tag, TAG_ADVANCEMENTS).size()
                + getSet(tag, TAG_KILLED_MOBS).size()
                + getSet(tag, TAG_EATEN_ITEMS).size();
    }

    /**
     * Recalculates tier and applies attribute modifiers.
     * Called only on milestone events, login, respawn, or clone - never tick-based!
     */
    public static void updateBuffs(ServerPlayer player, boolean notifyOnTierUp) {
        int step = Math.max(1, Config.SERVER.milestoneStep.get());
        int total = getTotalPoints(player);
        int targetTier = total / step;
        int currentTier = getAppliedTier(player);

        double healthPerTier = Config.SERVER.healthPerTier.get();
        double damagePerTier = Config.SERVER.damagePerTier.get();
        double armorPerTier = Config.SERVER.armorPerTier.get();
        double toughnessPerTier = Config.SERVER.armorToughnessPerTier.get();
        double xpMultiplierPerTier = Config.SERVER.experienceMultiplierPerTier.get();
        double enchantReductionPerTier = Config.SERVER.enchantDamageReductionPerTier.get();
        double legendReductionPerTier = Config.SERVER.legendDamageReductionPerTier.get();

        double totalHealthBonus = targetTier * healthPerTier;
        double totalDamageBonus = targetTier * damagePerTier;
        double totalArmorBonus = targetTier * armorPerTier;
        double totalToughnessBonus = targetTier * toughnessPerTier;
        double totalXpMultiplier = targetTier * xpMultiplierPerTier;
        double totalEnchantReduction = Math.min(0.80, targetTier * enchantReductionPerTier);
        double totalLegendReduction = Math.min(Config.SERVER.maxLegendDamageReduction.get(), targetTier * legendReductionPerTier);

        // Apply attribute modifiers
        applyModifier(player, Attributes.MAX_HEALTH, HEALTH_MODIFIER_UUID, "Legend Max Health", totalHealthBonus);
        applyModifier(player, Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_UUID, "Legend Attack Damage", totalDamageBonus);
        applyModifier(player, Attributes.ARMOR, ARMOR_MODIFIER_UUID, "Legend Armor", totalArmorBonus);
        applyModifier(player, Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_MODIFIER_UUID, "Legend Armor Toughness", totalToughnessBonus);
        applyModifier(player, ModAttributes.EXPERIENCE_MULTIPLIER.get(), EXPERIENCE_MODIFIER_UUID, "Legend XP Multiplier", totalXpMultiplier);
        applyModifier(player, ModAttributes.ENCHANT_DAMAGE_REDUCTION.get(), ENCHANT_REDUCTION_MODIFIER_UUID, "Legend Enchant Damage Reduction", totalEnchantReduction);
        applyModifier(player, ModAttributes.LEGEND_DAMAGE_REDUCTION.get(), LEGEND_REDUCTION_MODIFIER_UUID, "Legend Unique Damage Reduction", totalLegendReduction);

        // If tier increased
        if (targetTier > currentTier) {
            int tierDiff = targetTier - currentTier;
            setAppliedTier(player, targetTier);

            // Heal player by the newly gained max health
            if (healthPerTier > 0.0) {
                player.heal((float) (tierDiff * healthPerTier));
            }

            if (notifyOnTierUp && Config.SERVER.showBuffMessage.get()) {
                sendBuffNotification(player, targetTier);
            }
        } else if (targetTier < currentTier) {
            // In case points decreased or config changed
            setAppliedTier(player, targetTier);
        }
    }

    private static void sendBuffNotification(ServerPlayer player, int newTier) {
        Component message;
        if (Config.SERVER.detailedBuffMessage.get()) {
            message = Component.translatable(
                    "message.aiming_to_become_a_legend.buff_received_detailed",
                    newTier,
                    getAdvancementCount(player),
                    getKilledMobCount(player),
                    getEatenItemCount(player)
            );
        } else {
            message = Component.translatable("message.aiming_to_become_a_legend.buff_received");
        }

        boolean actionBar = Config.SERVER.messagePosition.get() == Config.MessagePosition.ACTION_BAR;
        player.sendSystemMessage(message, actionBar);
    }

    private static void applyModifier(ServerPlayer player, Attribute attribute, UUID uuid, String name, double amount) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) {
            return;
        }

        AttributeModifier existing = instance.getModifier(uuid);
        if (existing != null) {
            // If the amount is already exactly what we want, do nothing
            if (Math.abs(existing.getAmount() - amount) < 1e-6) {
                return;
            }
            instance.removeModifier(uuid);
        }

        if (amount > 0.0) {
            instance.addPermanentModifier(new AttributeModifier(
                    uuid,
                    name,
                    amount,
                    AttributeModifier.Operation.ADDITION
            ));
        }
    }
}
