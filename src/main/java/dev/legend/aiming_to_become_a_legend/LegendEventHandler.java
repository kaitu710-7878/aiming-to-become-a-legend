package dev.legend.aiming_to_become_a_legend;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

@EventBusSubscriber(modid = AimingToBecomeALegend.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LegendEventHandler {

    public static boolean isValidAdvancement(Advancement advancement) {
        if (Config.SERVER.onlyVisibleAdvancements.get() && advancement.display().isEmpty()) {
            return false;
        }
        if (Config.SERVER.ignoreRootAdvancements.get() && advancement.parent().isEmpty()) {
            return false;
        }
        return true;
    }

    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        AdvancementHolder holder = event.getAdvancement();
        if (!isValidAdvancement(holder.value())) {
            return;
        }

        if (LegendData.addAdvancement(player, holder.id())) {
            LegendData.updateBuffs(player, true);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim instanceof Player) {
            return;
        }

        Entity attacker = event.getSource().getEntity();
        ServerPlayer player = null;
        if (attacker instanceof ServerPlayer sp) {
            player = sp;
        } else if (attacker instanceof TamableAnimal tamable && tamable.getOwner() instanceof ServerPlayer sp) {
            player = sp;
        }

        if (player == null) {
            return;
        }

        ResourceLocation mobId = BuiltInRegistries.ENTITY_TYPE.getKey(victim.getType());
        if (mobId != null) {
            if (LegendData.addKilledMob(player, mobId)) {
                LegendData.updateBuffs(player, true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack itemStack = event.getItem();
        if (!itemStack.isEmpty() && itemStack.has(DataComponents.FOOD)) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            if (itemId != null) {
                if (LegendData.addEatenItem(player, itemId)) {
                    LegendData.updateBuffs(player, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onXpChange(PlayerXpEvent.XpChange event) {
        if (LegendData.isUsingApothicXp()) {
            return;
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            int originalAmount = event.getAmount();
            if (originalAmount > 0) {
                AttributeInstance xpAttr = player.getAttribute(ModAttributes.EXPERIENCE_MULTIPLIER);
                if (xpAttr != null && xpAttr.getValue() > 0.0) {
                    double mult = xpAttr.getValue();
                    double bonus = originalAmount * mult;
                    int intBonus = (int) bonus;
                    if (player.getRandom().nextDouble() < (bonus - intBonus)) {
                        intBonus++;
                    }
                    event.setAmount(originalAmount + intBonus);
                }
            }
        }
    }

    /**
     * Universal damage reduction pipeline for NeoForge 1.21.1.
     * Priority is set to LOW so all vanilla & modded armor/resistance/piercing calculations have taken effect,
     * allowing pure universal reduction on the final incoming damage value without hardcoding any specific mod or damage type.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        DamageSource source = event.getSource();
        float damage = event.getNewDamage();
        if (damage <= 0.0F) {
            return;
        }

        // 1. Enchantment-like damage reduction (+1% per tier, capped at 80% total with armor, ineffective if armor already hits the cap)
        if (!source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            float epf = EnchantmentHelper.getDamageProtection(player.serverLevel(), player, source);
            float armorProtFraction = Math.min(20.0F, epf) / 25.0F; // vanilla enchantment protection: 0.0 to 0.80

            if (armorProtFraction < 0.80F) {
                AttributeInstance enchantAttr = player.getAttribute(ModAttributes.ENCHANT_DAMAGE_REDUCTION);
                if (enchantAttr != null && enchantAttr.getValue() > 0.0) {
                    float totalProt = (float) Math.min(0.80, armorProtFraction + enchantAttr.getValue());
                    if (totalProt > armorProtFraction) {
                        float factor = (1.0F - totalProt) / (1.0F - armorProtFraction);
                        damage *= factor;
                    }
                }
            }
        }

        // 2. Pure Universal Non-Enchantment Damage Reduction (+0.1% per tier, capped at 80%)
        // Applies to ALL damage sources unconditionally (including armor-piercing and enchantment-piercing damage).
        AttributeInstance legendAttr = player.getAttribute(ModAttributes.LEGEND_DAMAGE_REDUCTION);
        if (legendAttr != null && legendAttr.getValue() > 0.0) {
            float legendReduction = (float) Math.min(Config.SERVER.maxLegendDamageReduction.get(), legendAttr.getValue());
            damage *= (1.0F - legendReduction);
        }

        event.setNewDamage(damage);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server != null) {
            for (AdvancementHolder holder : server.getAdvancements().getAllAdvancements()) {
                if (!isValidAdvancement(holder.value())) {
                    continue;
                }
                if (player.getAdvancements().getOrStartProgress(holder).isDone()) {
                    LegendData.addAdvancement(player, holder.id());
                }
            }
        }

        LegendData.updateBuffs(player, false);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
            CompoundTag oldTag = LegendData.getModData(oldPlayer);
            CompoundTag newTag = LegendData.getModData(newPlayer);
            newTag.merge(oldTag.copy());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            LegendData.updateBuffs(player, false);
        }
    }
}
